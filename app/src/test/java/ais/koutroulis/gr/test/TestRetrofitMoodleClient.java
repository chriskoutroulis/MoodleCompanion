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
import ais.koutroulis.gr.model.Token;
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
    private String assignmentsResponseString;

    private static final String FORMAT = "json";
    private static final String ASSIGNMENTS_FUNCTION = "mod_assign_get_assignments";

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
        assertNotNull("The response string is null!", assignmentsResponseString);
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
            for (Course oneCourse: courses) {
                aggregatedAssignments.addAll(oneCourse.getAssignments());
            }
            assertEquals("The Assignments were not 2.", 2, aggregatedAssignments.size() );

        } catch (IOException e) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/webservice/rest/" + "server.php" + "?moodlewsrestformat="
                + "json" + "&wstoken="
                + expectedToken.getToken() + "&wsfunction=" + ASSIGNMENTS_FUNCTION)));
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

    private void wireMockStubForFuntions(String function){
        String script = "server.php";
        stubFor(get(urlPathEqualTo("/moodle/webservice/rest/" + script))
                .withQueryParam("moodlewsrestformat", equalTo(FORMAT))
                .withQueryParam("wstoken", equalTo(expectedToken.getToken()))
                .withQueryParam("wsfunction", equalTo(function))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(assignmentsResponseString)));
    }

    private void resetAssignmentsResponseString() {
        assignmentsResponseString = "{\n" +
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

}
