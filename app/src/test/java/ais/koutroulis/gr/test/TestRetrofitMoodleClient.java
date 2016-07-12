package ais.koutroulis.gr.test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ais.koutroulis.gr.client.MoodleClient;
import ais.koutroulis.gr.client.RetrofitMoodleClient;
import ais.koutroulis.gr.model.Assignment;
import ais.koutroulis.gr.model.Course;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.MarkAsReadResponse;
import ais.koutroulis.gr.model.Message;
import ais.koutroulis.gr.model.Messages;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.model.User;
import retrofit2.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Chris on 02-Jul-16.
 */
public class TestRetrofitMoodleClient {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule();

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private static final String BASE_URL = "http://localhost:8080/moodle/";

    private static final String FORMAT = "json";
    private static final String ASSIGNMENTS_FUNCTION = "mod_assign_get_assignments";
    private static final String USER_DETAILS_FUNCTION = "core_user_get_users_by_field";
    private static final String GET_MESSAGES_FUNCTION = "core_message_get_messages";
    private static final String MARK_AS_READ_FUNCTION = "core_message_mark_message_read";

    private static final String LOGIN_SCRIPT = "token.php";
    private static final String FUNCTIONS_SCRIPT = "server.php";
    private static final String LOGIN_SERVICE = "moodle_mobile_app";

    private String callingUsername;
    private String callingPassword;

    private MoodleClient moodleClient;
    private Token expectedToken = new Token();
    private Response<Token> response;

    private static Map<String, String> registeredUsers;
    private String ais0058UserId = "5";
    private String anyUser = "0";
    private String readFalse = "0";
    private String readTrue = "1";
    private String unReadMessageId = "4";
    private String timeReadinMillis = "1468315655";

    @Before
    public void setup() {
        JsonResponseProvider.getAis0058ReadMessagesJsonString();
        JsonResponseProvider.getAis0058UnreadMessagesJsonString();
        JsonResponseProvider.getAis0058UserDetailsJsonString();
        JsonResponseProvider.getFourCoursesAndTwoAssignmentsJsonString();
        response = null;
        registeredUsers = new HashMap<>();
        registeredUsers.put("user1", "rightpassword1");
        registeredUsers.put("user2", "rightpassword2");
        registeredUsers.put("user3", "rightpassword3");
        registeredUsers.put("admin", "rightpassword4");
        callingUsername = "user1";
        callingPassword = "rightpassword1";
        expectedToken.setToken("grantAccessToken");
        moodleClient = new RetrofitMoodleClient(BASE_URL);
    }

    @Test
    public void registeredUserGetsToken() {
        createAppropriateStubForThisUser(callingUsername, callingPassword);

        try {
            response = moodleClient.login(LOGIN_SCRIPT, LOGIN_SERVICE, callingUsername, callingPassword);
            Assert.assertEquals("The incoming token does not match the expected.",
                    expectedToken.getToken(), response.body().getToken());
        } catch (IOException ie) {

        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/login/" + LOGIN_SCRIPT + "?username="
                + callingUsername + "&password="
                + callingPassword + "&service=" + LOGIN_SERVICE)));
        WireMock.reset();
    }

    @Test
    public void notRegisteredUsersDoNotGetToken() {
        callingUsername = "wrongUser";
        callingPassword = "wrongPass";
        createAppropriateStubForThisUser(callingUsername, callingPassword);

        try {
            response = moodleClient.login(LOGIN_SCRIPT, LOGIN_SERVICE, callingUsername, callingPassword);
            Assert.assertEquals("The status code is not 200.", 200, response.code());
            Assert.assertNull("A token was returned for non registered user.", response.body().getToken());
        } catch (IOException ie) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/login/" + LOGIN_SCRIPT + "?username="
                + callingUsername + "&password="
                + callingPassword + "&service=" + LOGIN_SERVICE)));
        WireMock.reset();
    }

    @Test
    public void serverOutOfReachShouldThrowIOException() {

        wireMockRule.stop();

        try {
            response = moodleClient.login(LOGIN_SCRIPT, LOGIN_SERVICE, callingUsername, callingPassword);
            Assert.fail("Server should have been out of reach.");
        } catch (IOException e) {
            Assert.assertNull(response);
        }
    }

    @Test
    public void responseShouldContainFourCourses() {
        assertNotNull("The response string is null!", JsonResponseProvider.getFourCoursesAndTwoAssignmentsJsonString());
        wireMockStubForFunction(ASSIGNMENTS_FUNCTION);
        try {
            Response<Courses> responseCourses = moodleClient.getCourses(FUNCTIONS_SCRIPT, FORMAT, expectedToken.getToken(), ASSIGNMENTS_FUNCTION);
            Assert.assertEquals("The status code is not 200.", 200, responseCourses.code());
            assertEquals("There should be 4 courses in this response.", 4, responseCourses.body().getCourses().size());
        } catch (IOException ie) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + ASSIGNMENTS_FUNCTION)));
        WireMock.reset();
    }

    @Test
    public void responseShouldContainTwoAssignments() {
        wireMockStubForFunction(ASSIGNMENTS_FUNCTION);

        try {
            Response<Courses> responseCourses = moodleClient.getCourses(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), ASSIGNMENTS_FUNCTION);
            List<Course> courses = responseCourses.body().getCourses();
            List<Assignment> aggregatedAssignments = new ArrayList<>();
            for (Course oneCourse : courses) {
                aggregatedAssignments.addAll(oneCourse.getAssignments());
            }
            assertEquals("The Assignments were not 2.", 2, aggregatedAssignments.size());

        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + ASSIGNMENTS_FUNCTION)));
        WireMock.reset();
    }

    @Test
    public void responseShouldHaveASpecificAssignmentIntro() {
        wireMockStubForFunction(ASSIGNMENTS_FUNCTION);

        try {
            Response<Courses> responseCourses = moodleClient.getCourses(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), ASSIGNMENTS_FUNCTION);
            List<Course> courses = responseCourses.body().getCourses();
            List<Assignment> aggregatedAssignments = new ArrayList<>();
            for (Course oneCourse : courses) {
                aggregatedAssignments.addAll(oneCourse.getAssignments());
            }
            boolean found = false;
            for (Assignment assignment : aggregatedAssignments) {
                if (assignment.getIntro().equals("<p>Μια διαδικτυακή εφαρμογή με Java και MySQL.</p>")) {
                    found = true;
                }
            }
            assertTrue("The Assignments list should contain a specific Assignment", found);

        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + ASSIGNMENTS_FUNCTION)));
        WireMock.reset();
    }

    @Test
    public void shouldContainSpecificDetailsForAGivenUsername() {
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + FUNCTIONS_SCRIPT))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(USER_DETAILS_FUNCTION))
                .withQueryParam("field", equalTo("username"))
                .withQueryParam("values[0]", equalTo("ais0058"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(JsonResponseProvider.getAis0058UserDetailsJsonString())));

        try {
            Response<User> responseUserDetails = moodleClient.getUserDetails(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), USER_DETAILS_FUNCTION, "username", "ais0058");
            assertEquals("The user does not have the expected full name.", "Ioannis Antonatos",
                    responseUserDetails.body().getFullname());
            assertEquals("The user's email is not the expected one.", "antonatos@hotmail.com", responseUserDetails.body().getEmail());
        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + USER_DETAILS_FUNCTION
                + "&field=" + "username" + "&values[0]=" + "ais0058")));
        WireMock.reset();
    }

    @Test
    public void shouldContainTwoSpecificUnreadMessages() {
        wireMockStubForGettingUnreadMessages();
        wireMockStubForMarkingAsReadMessages();

        List<Message> messageList = null;

        try {
            Response<Messages> responseUnreadMessages = moodleClient.getMessages(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), GET_MESSAGES_FUNCTION, MARK_AS_READ_FUNCTION,
                    ais0058UserId, anyUser, readFalse, timeReadinMillis);
            messageList = responseUnreadMessages.body().getMessages();

            assertEquals("The unread messages were not 2 as expected.", 2, responseUnreadMessages.body().getMessages().size());

            assertTrue("The first message is not the one that was expected.",
                    responseUnreadMessages.body().getMessages().get(0).getFullmessage().contains("Σου στέλνω ξανά"));

            assertTrue("The second message is not the one that was expected.",
                    responseUnreadMessages.body().getMessages().get(1).getFullmessage().contains("Πάρε κι εσύ ένα μήνυμα."));

        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + GET_MESSAGES_FUNCTION
                + "&useridto=" + ais0058UserId + "&useridfrom=" + anyUser + "&read=" + readFalse)));

        if (messageList != null) {
            for (Message oneMessage : messageList) {
                WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                        + "json" + "&wstoken="
                        + expectedToken.getToken() + "&wsfunction=" + MARK_AS_READ_FUNCTION
                        + "&messageid=" + oneMessage.getId() + "&timeread=" + timeReadinMillis)));
            }
        } else {
            Assert.fail("The messsage list should have had 2 messages, but it was null.");
        }

        WireMock.reset();
    }

    @Test
    public void shouldContainThreeSpecificReadMessages() {
        wireMockStubForGettingReadMessages();
        try {
            Response<Messages> responseReadMessages = moodleClient.getMessages(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), GET_MESSAGES_FUNCTION, MARK_AS_READ_FUNCTION,
                    ais0058UserId, anyUser, readTrue, timeReadinMillis);
            assertEquals("The read messages were not 3 as expected.", 3, responseReadMessages.body().getMessages().size());
            assertTrue("The first message is not the one that was expected.",
                    responseReadMessages.body().getMessages().get(0).getFullmessage()
                            .contains("Σου στέλνω ακόμα ένα για να διαβάσεις ένα από τα προηγούμενα."));
            assertTrue("The second message is not the one that was expected.",
                    responseReadMessages.body().getMessages().get(1).getFullmessage().contains("Σου στέλνω ξανά"));
            assertTrue("The third message is not the one that was expected.",
                    responseReadMessages.body().getMessages().get(2).getFullmessage().contains("Πάρε κι εσύ ένα μήνυμα."));

        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + GET_MESSAGES_FUNCTION
                + "&useridto=" + ais0058UserId + "&useridfrom=" + anyUser + "&read=" + readTrue)));
        WireMock.reset();
    }

    @Test
    public void markAsReadCallShouldReturnAReadMessageId() {

        wireMockStubForMarkingAsReadMessages();
        try {
            Response<MarkAsReadResponse> responseMarkAsRead = moodleClient.markAsReadMessage(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), MARK_AS_READ_FUNCTION, unReadMessageId, timeReadinMillis);

            assertNotNull("There was no read message id returned.", responseMarkAsRead.body().getMessageIdAsRead());
            assertEquals("The returned read message id was not 4", 4, responseMarkAsRead.body().getMessageIdAsRead());

        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + MARK_AS_READ_FUNCTION
                + "&messageid=" + unReadMessageId + "&timeread=" + timeReadinMillis)));
        WireMock.reset();
    }

    @Test
    public void gettingUnreadMessagesShouldAlsoMarkThemAsRead() {
        //TODO Implement this test
        //Check that the unread message list got empty
        //Check that the read messages list has acquired the previously unread messages.
        //Check that when asking for unread messages, the markAllUnreadMessagesAsRead() gets called
        //as many times as the unread messages. Maybe with WireMock.verify in a loop for each messageId.

        wireMockStubForGettingUnreadMessages();
        wireMockStubForMarkingAsReadMessages();
        List<Message> messageList = null;

        try {
            Response<Messages> responseUnreadMessages = moodleClient.getMessages(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), GET_MESSAGES_FUNCTION, MARK_AS_READ_FUNCTION,
                    ais0058UserId, anyUser, readFalse, timeReadinMillis);
            messageList = responseUnreadMessages.body().getMessages();
        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + GET_MESSAGES_FUNCTION
                + "&useridto=" + ais0058UserId + "&useridfrom=" + anyUser + "&read=" + readFalse)));

        if (messageList != null) {
            for (Message oneMessage : messageList) {
                WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                        + "json" + "&wstoken="
                        + expectedToken.getToken() + "&wsfunction=" + MARK_AS_READ_FUNCTION
                        + "&messageid=" + oneMessage.getId() + "&timeread=" + timeReadinMillis)));
            }
        } else {
            Assert.fail("The messsage list should have had 2 messages, but it was null.");
        }

        WireMock.reset();
    }

    private void createAppropriateStubForThisUser(String username, String password) {
        if (isRegisteredUser()) {
            stubFor(get(urlPathEqualTo("/moodle/login/" + LOGIN_SCRIPT))
                    .withQueryParam("username", equalTo(callingUsername))
                    .withQueryParam("password", equalTo(callingPassword))
                    .withQueryParam("service", equalTo(LOGIN_SERVICE))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody(JsonResponseProvider.getAis0058GrantTokenJsonString())));
        } else {
            stubFor(get(urlPathEqualTo("/moodle/login/" + LOGIN_SCRIPT))
                    .withQueryParam("username", equalTo(callingUsername))
                    .withQueryParam("password", equalTo(callingPassword))
                    .withQueryParam("service", equalTo(LOGIN_SERVICE))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody(JsonResponseProvider.getAis0058DeniedTokenJsonString())));
        }
    }

    private boolean isRegisteredUser() {
        if (registeredUsers.containsKey(callingUsername) && registeredUsers.get(callingUsername).equals(callingPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private void wireMockStubForFunction(String function) {
        String script = "server.php";
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + script))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(function))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(JsonResponseProvider.getFourCoursesAndTwoAssignmentsJsonString())));
    }

    private void wireMockStubForGettingUnreadMessages() {
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + FUNCTIONS_SCRIPT))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(GET_MESSAGES_FUNCTION))
                .withQueryParam("useridto", equalTo(ais0058UserId))
                .withQueryParam("useridfrom", equalTo(anyUser))
                .withQueryParam("read", equalTo(readFalse))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(JsonResponseProvider.getAis0058UnreadMessagesJsonString())));
    }

    private void wireMockStubForGettingReadMessages() {
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + FUNCTIONS_SCRIPT))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(GET_MESSAGES_FUNCTION))
                .withQueryParam("useridto", equalTo(ais0058UserId))
                .withQueryParam("useridfrom", equalTo(anyUser))
                .withQueryParam("read", equalTo(readTrue))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(JsonResponseProvider.getAis0058ReadMessagesJsonString())));
    }

    private void wireMockStubForMarkingAsReadMessages() {
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + FUNCTIONS_SCRIPT))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(MARK_AS_READ_FUNCTION))
//                .withQueryParam("messageid", equalTo(unReadMessageId))
                .withQueryParam("messageid", matching("^[1-9][0-9]*$")) //any digit greater than zero
                .withQueryParam("timeread", equalTo(timeReadinMillis))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(JsonResponseProvider.getAis0058MarkAsReadMessageJsonString())));
    }

    private void wireMockStubForGettingZeroUnreadMessages() {
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + FUNCTIONS_SCRIPT))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(GET_MESSAGES_FUNCTION))
                .withQueryParam("useridto", equalTo(ais0058UserId))
                .withQueryParam("useridfrom", equalTo(anyUser))
                .withQueryParam("read", equalTo(readFalse))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(JsonResponseProvider.getAis0058SecondCallForUnreadMessagesJsonString())));
    }
}


