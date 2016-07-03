package ais.koutroulis.gr.learning;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.MoodleRestService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chris on 02-Jul-16.
 */
public class LearningRetrofitOnLiveServer {

    private static final String BASE_URL = "http://ais-temp.daidalos.teipir.gr/moodle/";
    private static final String SCRIPT = "token.php";
    private static final String USERNAME = "ais0058";
    private static final String PASSWORD = "Masterais0056!";
    private static final String SERVICE = "moodle_mobile_app";
    private static final String RESPONSE_STRING = "{\n" +
            "  \"token\": \"2800aeb20f71838d9405768415096765\"\n" +
            "}";

    private MoodleRestService moodleService;
    private Token expectedToken = new Token();

    @Before
    public void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        moodleService = retrofit.create(MoodleRestService.class);
        expectedToken.setToken("2800aeb20f71838d9405768415096765");
    }

    @Test
    public void shouldReturnCorrectToken() {

        Call<Token> loginCall = moodleService.getToken(SCRIPT, USERNAME, PASSWORD, SERVICE);

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
    }

    @Test
    public void shouldNotReturnTokenForInvalidUsername() {
        Call<Token> loginCall = moodleService.getToken(SCRIPT, "wrongUserName", PASSWORD, SERVICE);
        try {
            Response<Token> response = loginCall.execute();
            Assert.assertNull("A token was returned but it should not.", response.body().getToken());
//            Assert.assertNotEquals("The token received was the same as the expected one.",
//                    expectedToken.getToken(), response.body().getToken());
        }catch(IOException ie) {
            Assert.fail(ie.getLocalizedMessage());
        }
    }
}
