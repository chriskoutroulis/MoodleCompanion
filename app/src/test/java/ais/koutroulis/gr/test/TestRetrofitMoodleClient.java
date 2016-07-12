package ais.koutroulis.gr.test;

import static org.junit.Assert.*;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
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
import ais.koutroulis.gr.model.Messages;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.model.User;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import retrofit2.Response;

/**
 * Created by Chris on 02-Jul-16.
 */
public class TestRetrofitMoodleClient {

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule();

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    private static final String BASE_URL = "http://localhost:8080/moodle/";
    private static final String GRANT_TOKEN_RESPONSE_STRING = "{\n" +
            "  \"token\": \"grantAccessToken\"\n" +
            "}";
    private static final String DENIED_TOKEN_RESPONSE_STRING = "{\n" +
            "  \"error\": \"Invalid login, please try again\",\n" +
            "  \"stacktrace\": null,\n" +
            "  \"debuginfo\": null,\n" +
            "  \"reproductionlink\": null\n" +
            "}";
    private String fourCoursesAndTwoAssignmentsResponse;
    private String ais0058UserDetails;
    private String ais0058UnreadMessages;
    private String ais0058ReadMessages;

    private static final String FORMAT = "json";
    private static final String ASSIGNMENTS_FUNCTION = "mod_assign_get_assignments";
    private static final String USER_DETAILS_FUNCTION = "core_user_get_users_by_field";
    private static final String GET_MESSAGES_FUNCTION = "core_message_get_messages";

    private static final String LOGIN_SCRIPT = "token.php";
    private static final String FUNCTIONS_SCRIPT = "server.php";
    private static final String LOGIN_SERVICE = "moodle_mobile_app";
    private String callingUsername;
    private String callingPassword;
    private MoodleClient moodleClient;

    private Token expectedToken = new Token();
    private MoodleRetroFitService service;
    private Response<Token> response;

    private static Map<String, String> registeredUsers;

    @Before
    public void setup() {
        resetAis0058ReadMessages();
        resetAis0058UnreadMessages();
        resetAis0058UserDetails();
        resetAssignmentsResponseString();
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
        assertNotNull("The response string is null!", fourCoursesAndTwoAssignmentsResponse);
        wireMockStubForFuntions(ASSIGNMENTS_FUNCTION);
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
        wireMockStubForFuntions(ASSIGNMENTS_FUNCTION);

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
        wireMockStubForFuntions(ASSIGNMENTS_FUNCTION);

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
        String script = "server.php";
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + script))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(USER_DETAILS_FUNCTION))
                .withQueryParam("field", equalTo("username"))
                .withQueryParam("values[0]", equalTo("ais0058"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(ais0058UserDetails)));

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
        String ais0058UserId = "5";
        String anyUser = "0";
        String script = "server.php";
        String readFalse = "0";
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + script))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(GET_MESSAGES_FUNCTION))
                .withQueryParam("useridto", equalTo(ais0058UserId))
                .withQueryParam("useridfrom", equalTo(anyUser))
                .withQueryParam("read", equalTo(readFalse))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(ais0058UnreadMessages)));

        try {
            Response<Messages> responseUnreadMessages = moodleClient.getMessages(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), GET_MESSAGES_FUNCTION, ais0058UserId, anyUser, readFalse);
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
        WireMock.reset();
    }

    @Test
    public void shouldContainThreeSpecificReadMessages() {
        String ais0058UserId = "5";
        String anyUser = "0";
        String script = "server.php";
        String readTrue = "1";
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + script))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(GET_MESSAGES_FUNCTION))
                .withQueryParam("useridto", equalTo(ais0058UserId))
                .withQueryParam("useridfrom", equalTo(anyUser))
                .withQueryParam("read", equalTo(readTrue))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(ais0058ReadMessages)));

        try {
            Response<Messages> responseReadMessages = moodleClient.getMessages(FUNCTIONS_SCRIPT, FORMAT,
                    expectedToken.getToken(), GET_MESSAGES_FUNCTION, ais0058UserId, anyUser, readTrue);
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

    private void createAppropriateStubForThisUser(String username, String password) {
        if (isRegisteredUser()) {
            stubFor(get(urlPathEqualTo("/moodle/login/" + LOGIN_SCRIPT))
                    .withQueryParam("username", equalTo(callingUsername))
                    .withQueryParam("password", equalTo(callingPassword))
                    .withQueryParam("service", equalTo(LOGIN_SERVICE))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody(GRANT_TOKEN_RESPONSE_STRING)));
        } else {
            stubFor(get(urlPathEqualTo("/moodle/login/" + LOGIN_SCRIPT))
                    .withQueryParam("username", equalTo(callingUsername))
                    .withQueryParam("password", equalTo(callingPassword))
                    .withQueryParam("service", equalTo(LOGIN_SERVICE))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody(DENIED_TOKEN_RESPONSE_STRING)));
        }
    }

    private boolean isRegisteredUser() {
        if (registeredUsers.containsKey(callingUsername) && registeredUsers.get(callingUsername).equals(callingPassword)) {
            return true;
        } else {
            return false;
        }
    }

    private void wireMockStubForFuntions(String function) {
        String script = "server.php";
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + script))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(function))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(fourCoursesAndTwoAssignmentsResponse)));
    }

    private void resetAssignmentsResponseString() {
        fourCoursesAndTwoAssignmentsResponse = "{\n" +
                "  \"courses\": [\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"fullname\": \"Βιομηχανική Πληροφορική\",\n" +
                "      \"shortname\": \"indcomp\",\n" +
                "      \"timemodified\": 1467304055,\n" +
                "      \"assignments\": [\n" +
                "        {\n" +
                "          \"id\": 2,\n" +
                "          \"cmid\": 6,\n" +
                "          \"course\": 2,\n" +
                "          \"name\": \"Εργασία για τα βιομηχανικά πρωτόκολλα.\",\n" +
                "          \"nosubmissions\": 0,\n" +
                "          \"submissiondrafts\": 0,\n" +
                "          \"sendnotifications\": 0,\n" +
                "          \"sendlatenotifications\": 0,\n" +
                "          \"sendstudentnotifications\": 1,\n" +
                "          \"duedate\": 1473886800,\n" +
                "          \"allowsubmissionsfromdate\": 1467925200,\n" +
                "          \"grade\": 100,\n" +
                "          \"timemodified\": 1467961291,\n" +
                "          \"completionsubmit\": 0,\n" +
                "          \"cutoffdate\": 1473922500,\n" +
                "          \"teamsubmission\": 0,\n" +
                "          \"requireallteammemberssubmit\": 0,\n" +
                "          \"teamsubmissiongroupingid\": 0,\n" +
                "          \"blindmarking\": 0,\n" +
                "          \"revealidentities\": 0,\n" +
                "          \"attemptreopenmethod\": \"none\",\n" +
                "          \"maxattempts\": -1,\n" +
                "          \"markingworkflow\": 0,\n" +
                "          \"markingallocation\": 0,\n" +
                "          \"requiresubmissionstatement\": 0,\n" +
                "          \"configs\": [\n" +
                "            {\n" +
                "              \"id\": 11,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"onlinetext\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 12,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 13,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"maxfilesubmissions\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 14,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"maxsubmissionsizebytes\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 15,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"comments\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 16,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"comments\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 17,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"comments\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"commentinline\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 18,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"editpdf\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 19,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"offline\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 20,\n" +
                "              \"assignment\": 2,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"intro\": \"<p>Διαβάστε τα βιομηχανικά πρωτόκολλα επικοινωνίας και αναφέρετε περιληπτικά το καθένα από αυτά.</p>\",\n" +
                "          \"introformat\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"fullname\": \"Προηγμένα Θέματα Αντικειμενοστραφούς Προγραμματισμού\",\n" +
                "      \"shortname\": \"advprogr\",\n" +
                "      \"timemodified\": 1467303957,\n" +
                "      \"assignments\": [\n" +
                "        {\n" +
                "          \"id\": 1,\n" +
                "          \"cmid\": 5,\n" +
                "          \"course\": 3,\n" +
                "          \"name\": \"Εργασία 1η\",\n" +
                "          \"nosubmissions\": 0,\n" +
                "          \"submissiondrafts\": 0,\n" +
                "          \"sendnotifications\": 0,\n" +
                "          \"sendlatenotifications\": 0,\n" +
                "          \"sendstudentnotifications\": 1,\n" +
                "          \"duedate\": 1470776400,\n" +
                "          \"allowsubmissionsfromdate\": 1467493200,\n" +
                "          \"grade\": 100,\n" +
                "          \"timemodified\": 1467567633,\n" +
                "          \"completionsubmit\": 0,\n" +
                "          \"cutoffdate\": 1470850500,\n" +
                "          \"teamsubmission\": 0,\n" +
                "          \"requireallteammemberssubmit\": 0,\n" +
                "          \"teamsubmissiongroupingid\": 0,\n" +
                "          \"blindmarking\": 0,\n" +
                "          \"revealidentities\": 0,\n" +
                "          \"attemptreopenmethod\": \"none\",\n" +
                "          \"maxattempts\": -1,\n" +
                "          \"markingworkflow\": 0,\n" +
                "          \"markingallocation\": 0,\n" +
                "          \"requiresubmissionstatement\": 0,\n" +
                "          \"configs\": [\n" +
                "            {\n" +
                "              \"id\": 1,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"onlinetext\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 2,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 3,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"maxfilesubmissions\",\n" +
                "              \"value\": \"10\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 4,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"maxsubmissionsizebytes\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 5,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"comments\",\n" +
                "              \"subtype\": \"assignsubmission\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 6,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"comments\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"1\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 7,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"comments\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"commentinline\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 8,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"editpdf\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 9,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"offline\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"id\": 10,\n" +
                "              \"assignment\": 1,\n" +
                "              \"plugin\": \"file\",\n" +
                "              \"subtype\": \"assignfeedback\",\n" +
                "              \"name\": \"enabled\",\n" +
                "              \"value\": \"0\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"intro\": \"<p>Μια διαδικτυακή εφαρμογή με Java και MySQL.</p>\",\n" +
                "          \"introformat\": 1\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"fullname\": \"VHDL και Προηγμένη Λογική Σχεδίαση\",\n" +
                "      \"shortname\": \"vhdl\",\n" +
                "      \"timemodified\": 1467304203,\n" +
                "      \"assignments\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"fullname\": \"Ανοικτό Λογισμικό Android\",\n" +
                "      \"shortname\": \"android\",\n" +
                "      \"timemodified\": 1467304247,\n" +
                "      \"assignments\": []\n" +
                "    }\n" +
                "  ],\n" +
                "  \"warnings\": []\n" +
                "}";
    }

    private void resetAis0058UserDetails() {
        this.ais0058UserDetails = "  {\n" +
                "    \"id\": 5,\n" +
                "    \"username\": \"ais0058\",\n" +
                "    \"fullname\": \"Ioannis Antonatos\",\n" +
                "    \"email\": \"antonatos@hotmail.com\",\n" +
                "    \"department\": \"\",\n" +
                "    \"firstaccess\": 1467307347,\n" +
                "    \"lastaccess\": 1468219321,\n" +
                "    \"description\": \"\",\n" +
                "    \"descriptionformat\": 1,\n" +
                "    \"city\": \"Athens\",\n" +
                "    \"country\": \"GR\",\n" +
                "    \"profileimageurlsmall\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f2\",\n" +
                "    \"profileimageurl\": \"http://ais-temp.daidalos.teipir.gr/moodle/theme/image.php/clean/core/1467270948/u/f1\",\n" +
                "    \"preferences\": [\n" +
                "      {\n" +
                "        \"name\": \"auth_forcepasswordchange\",\n" +
                "        \"value\": \"0\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"email_bounce_count\",\n" +
                "        \"value\": \"1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"email_send_count\",\n" +
                "        \"value\": \"3\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"login_failed_count_since_success\",\n" +
                "        \"value\": \"1\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"_lastloaded\",\n" +
                "        \"value\": 1468221629\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n";
    }

    private void resetAis0058UnreadMessages() {
        this.ais0058UnreadMessages = "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"useridfrom\": 3,\n" +
                "      \"useridto\": 5,\n" +
                "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
                "      \"text\": \"<p>&Icirc;&pound;&Icirc;&iquest;&Iuml;&#133; &Iuml;&#131;&Iuml;&#132;&Icirc;&shy;&Icirc;&raquo;&Icirc;&frac12;&Iuml;&#137; &Icirc;&frac34;&Icirc;&plusmn;&Icirc;&frac12;&Icirc;&not;</p>\",\n" +
                "      \"fullmessage\": \"Σου στέλνω ξανά\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
                "      \"fullmessageformat\": 0,\n" +
                "      \"fullmessagehtml\": \"\",\n" +
                "      \"smallmessage\": \"Σου στέλνω ξανά\",\n" +
                "      \"notification\": 0,\n" +
                "      \"contexturl\": null,\n" +
                "      \"contexturlname\": null,\n" +
                "      \"timecreated\": 1467568241,\n" +
                "      \"timeread\": 0,\n" +
                "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
                "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"useridfrom\": 3,\n" +
                "      \"useridto\": 5,\n" +
                "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
                "      \"text\": \"<p>&Icirc;&nbsp;&Icirc;&not;&Iuml;&#129;&Icirc;&micro; &Icirc;&ordm;&Icirc;&sup1; &Icirc;&micro;&Iuml;&#131;&Iuml;&#141; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&frac14;&Icirc;&reg;&Icirc;&frac12;&Iuml;&#133;&Icirc;&frac14;&Icirc;&plusmn;.</p>\",\n" +
                "      \"fullmessage\": \"Πάρε κι εσύ ένα μήνυμα.\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
                "      \"fullmessageformat\": 0,\n" +
                "      \"fullmessagehtml\": \"\",\n" +
                "      \"smallmessage\": \"Πάρε κι εσύ ένα μήνυμα.\",\n" +
                "      \"notification\": 0,\n" +
                "      \"contexturl\": null,\n" +
                "      \"contexturlname\": null,\n" +
                "      \"timecreated\": 1467567827,\n" +
                "      \"timeread\": 0,\n" +
                "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
                "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"warnings\": []\n" +
                "}";
    }

    private void resetAis0058ReadMessages() {
        this.ais0058ReadMessages = "{\n" +
                "  \"messages\": [\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"useridfrom\": 3,\n" +
                "      \"useridto\": 5,\n" +
                "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
                "      \"text\": \"<p>&Icirc;&pound;&Icirc;&iquest;&Iuml;&#133; &Iuml;&#131;&Iuml;&#132;&Icirc;&shy;&Icirc;&raquo;&Icirc;&frac12;&Iuml;&#137; &Icirc;&plusmn;&Icirc;&ordm;&Iuml;&#140;&Icirc;&frac14;&Icirc;&plusmn; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&sup3;&Icirc;&sup1;&Icirc;&plusmn; &Icirc;&frac12;&Icirc;&plusmn; &Icirc;&acute;&Icirc;&sup1;&Icirc;&plusmn;&Icirc;&sup2;&Icirc;&not;&Iuml;&#131;&Icirc;&micro;&Icirc;&sup1;&Iuml;&#130; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&plusmn;&Iuml;&#128;&Iuml;&#140; &Iuml;&#132;&Icirc;&plusmn; &Iuml;&#128;&Iuml;&#129;&Icirc;&iquest;&Icirc;&middot;&Icirc;&sup3;&Icirc;&iquest;&Iuml;&#141;&Icirc;&frac14;&Icirc;&micro;&Icirc;&frac12;&Icirc;&plusmn;.</p>\",\n" +
                "      \"fullmessage\": \"Σου στέλνω ακόμα ένα για να διαβάσεις ένα από τα προηγούμενα.\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
                "      \"fullmessageformat\": 0,\n" +
                "      \"fullmessagehtml\": \"\",\n" +
                "      \"smallmessage\": \"Σου στέλνω ακόμα ένα για να διαβάσεις ένα από τα προηγούμενα.\",\n" +
                "      \"notification\": 0,\n" +
                "      \"contexturl\": null,\n" +
                "      \"contexturlname\": null,\n" +
                "      \"timecreated\": 1468241947,\n" +
                "      \"timeread\": 1468241988,\n" +
                "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
                "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"useridfrom\": 3,\n" +
                "      \"useridto\": 5,\n" +
                "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
                "      \"text\": \"<p>&Icirc;&pound;&Icirc;&iquest;&Iuml;&#133; &Iuml;&#131;&Iuml;&#132;&Icirc;&shy;&Icirc;&raquo;&Icirc;&frac12;&Iuml;&#137; &Icirc;&frac34;&Icirc;&plusmn;&Icirc;&frac12;&Icirc;&not;</p>\",\n" +
                "      \"fullmessage\": \"Σου στέλνω ξανά\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
                "      \"fullmessageformat\": 0,\n" +
                "      \"fullmessagehtml\": \"\",\n" +
                "      \"smallmessage\": \"Σου στέλνω ξανά\",\n" +
                "      \"notification\": 0,\n" +
                "      \"contexturl\": null,\n" +
                "      \"contexturlname\": null,\n" +
                "      \"timecreated\": 1467568241,\n" +
                "      \"timeread\": 1468241988,\n" +
                "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
                "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"useridfrom\": 3,\n" +
                "      \"useridto\": 5,\n" +
                "      \"subject\": \"New message from Christodoulos Koutroulis\",\n" +
                "      \"text\": \"<p>&Icirc;&nbsp;&Icirc;&not;&Iuml;&#129;&Icirc;&micro; &Icirc;&ordm;&Icirc;&sup1; &Icirc;&micro;&Iuml;&#131;&Iuml;&#141; &Icirc;&shy;&Icirc;&frac12;&Icirc;&plusmn; &Icirc;&frac14;&Icirc;&reg;&Icirc;&frac12;&Iuml;&#133;&Icirc;&frac14;&Icirc;&plusmn;.</p>\",\n" +
                "      \"fullmessage\": \"Πάρε κι εσύ ένα μήνυμα.\\n\\n---------------------------------------------------------------------\\nThis is a copy of a message sent to you at \\\"MSc AIS\\\". Go to http://ais-temp.daidalos.teipir.gr/moodle/message/index.php?user=5&id=3 to reply.\",\n" +
                "      \"fullmessageformat\": 0,\n" +
                "      \"fullmessagehtml\": \"\",\n" +
                "      \"smallmessage\": \"Πάρε κι εσύ ένα μήνυμα.\",\n" +
                "      \"notification\": 0,\n" +
                "      \"contexturl\": null,\n" +
                "      \"contexturlname\": null,\n" +
                "      \"timecreated\": 1467567827,\n" +
                "      \"timeread\": 1468241987,\n" +
                "      \"usertofullname\": \"Ioannis Antonatos\",\n" +
                "      \"userfromfullname\": \"Christodoulos Koutroulis\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"warnings\": []\n" +
                "}";
    }

}
