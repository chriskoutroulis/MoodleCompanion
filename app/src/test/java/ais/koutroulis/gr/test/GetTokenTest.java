package ais.koutroulis.gr.test;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.MoodleRestService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chris on 02-Jul-16.
 */
public class GetTokenTest {

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

    private Token expectedToken = new Token();
    private MoodleRestService service;

    private static Map<String, String> registeredUsers;


    @BeforeClass
    public static void classSetup() {
        registeredUsers = new HashMap<>();
        registeredUsers.put("user1", "rightpassword1");
        registeredUsers.put("user2", "rightpassword2");
        registeredUsers.put("user3", "rightpassword3");
        registeredUsers.put("admin", "rightpassword4");
    }

    @Before
    public void setup() {
        callingUsername = "user1";
        callingPassword = "rightpassword1";

        expectedToken.setToken("grantAccessToken");
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
         service = retrofit.create(MoodleRestService.class);
    }

    @Test
    public void registeredUserGetsToken() {
        Call<Token> loginCall = service.getToken(SCRIPT, callingUsername, callingPassword, SERVICE);
        createAppropriateStubForThisUser(callingUsername, callingPassword);

        //Synchronous...
        try {
            Response<Token> response = loginCall.execute();
            Assert.assertEquals("The status code is not 200.", 200, response.code());
            if (response.isSuccessful()) {
                Assert.assertEquals("The incoming token does not match the expected.",
                        expectedToken.getToken(), response.body().getToken());
            }
        } catch (IOException ie) {
            Assert.fail(ie.getLocalizedMessage());
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

        Call<Token> loginCall = service.getToken(SCRIPT, callingUsername, callingPassword, SERVICE);
        createAppropriateStubForThisUser(callingUsername, callingPassword);

        //Synchronous...
        try {
            Response<Token> response = loginCall.execute();
            Assert.assertEquals("The status code is not 200.", 200, response.code());
            if (response.isSuccessful()) {
                Assert.assertNull("A token was returned for non registered user.", response.body().getToken());
            }
        } catch (IOException ie) {
            Assert.fail(ie.getLocalizedMessage());
        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/login/" + SCRIPT + "?username="
                + callingUsername + "&password="
                + callingPassword + "&service=" + SERVICE)));
        WireMock.reset();
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
