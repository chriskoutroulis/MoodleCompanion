package ais.koutroulis.gr.test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ais.koutroulis.gr.client.RetrofitMoodleLoginClient;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import retrofit2.Response;

/**
 * Created by Chris on 02-Jul-16.
 */
public class TestRetrofitMoodleLoginClient {

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

    private static final String SCRIPT = "token.php";
    private static final String SERVICE = "moodle_mobile_app";
    private String callingUsername;
    private String callingPassword;

    private RetrofitMoodleLoginClient loginClient;

    private Token expectedToken = new Token();
    private MoodleRetroFitService service;
    private Response<Token> response;

    private static Map<String, String> registeredUsers;

    @Before
    public void setup() {
        response = null;
        registeredUsers = new HashMap<>();
        registeredUsers.put("user1", "rightpassword1");
        registeredUsers.put("user2", "rightpassword2");
        registeredUsers.put("user3", "rightpassword3");
        registeredUsers.put("admin", "rightpassword4");
        callingUsername = "user1";
        callingPassword = "rightpassword1";
        expectedToken.setToken("grantAccessToken");
        loginClient = new RetrofitMoodleLoginClient(BASE_URL, SCRIPT, SERVICE);
    }

    @Test
    public void registeredUserGetsToken() {
        createAppropriateStubForThisUser(callingUsername, callingPassword);

        try {
            response = loginClient.login(callingUsername, callingPassword);
            Assert.assertEquals("The incoming token does not match the expected.",
                    expectedToken.getToken(), response.body().getToken());
        } catch (IOException ie) {

        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/login/" + SCRIPT + "?username="
                + callingUsername + "&password="
                + callingPassword + "&service=" + SERVICE)));
        WireMock.reset();
    }

    @Test
    public void notRegisteredUsersDoNotGetToken() {
        callingUsername = "wrongUser";
        callingPassword = "wrongPass";
        createAppropriateStubForThisUser(callingUsername, callingPassword);

        try {
            response = loginClient.login(callingUsername, callingPassword);
            Assert.assertEquals("The status code is not 200.", 200, response.code());
            Assert.assertNull("A token was returned for non registered user.", response.body().getToken());
        } catch (IOException ie) {
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/login/" + SCRIPT + "?username="
                + callingUsername + "&password="
                + callingPassword + "&service=" + SERVICE)));
        WireMock.reset();
    }

    @Test
    public void serverOutOfReachShouldThrowIOException() {

        wireMockRule.stop();

        try {
            response = loginClient.login(callingUsername, callingPassword);
            Assert.fail("Server should have been out of reach.");
        } catch (IOException e) {
            Assert.assertNull(response);
        }
    }

    private void createAppropriateStubForThisUser(String username, String password) {
        if (isRegisteredUser()) {
            stubFor(get(urlPathEqualTo("/moodle/login/" + SCRIPT))
                    .withQueryParam("username", equalTo(callingUsername))
                    .withQueryParam("password", equalTo(callingPassword))
                    .withQueryParam("service", equalTo(SERVICE))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                            .withBody(GRANT_TOKEN_RESPONSE_STRING)));
        } else {
            stubFor(get(urlPathEqualTo("/moodle/login/" + SCRIPT))
                    .withQueryParam("username", equalTo(callingUsername))
                    .withQueryParam("password", equalTo(callingPassword))
                    .withQueryParam("service", equalTo(SERVICE))
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

}
