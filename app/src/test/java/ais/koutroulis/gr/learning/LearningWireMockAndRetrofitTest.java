package ais.koutroulis.gr.learning;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Chris on 02-Jul-16.
 */
public class LearningWireMockAndRetrofitTest {

    //    private static final String urlString = "http://ais-temp.daidalos.teipir.gr/" +
//            "moodle/login/token.php?username=ais0058&password=Masterais0056!" +
//            "&service=moodle_mobile_app";

    private static final String BASE_URL = "http://localhost:8080/moodle/";

    private static final String RESPONSE_STRING = "{\n" +
            "  \"token\": \"2800aeb20f71838d9405768415096765\"\n" +
            "}";

    private static final String SCRIPT = "token.php";
    private static final String USERNAME = "ais0058";
    private static final String PASSWORD = "Masterais0056!";
    private static final String SERVICE = "moodle_mobile_app";

    @ClassRule
    public static WireMockClassRule wireMockRule = new WireMockClassRule();

    @Rule
    public WireMockClassRule instanceRule = wireMockRule;

    @Test
    public void mockServerShouldReturnCorrectToken() {

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/moodle/login/" + SCRIPT + "?username=" + USERNAME
                + "&password=" + PASSWORD + "&service=" + SERVICE))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(RESPONSE_STRING)));

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        MoodleRetroFitService service = retrofit.create(MoodleRetroFitService.class);

        final Token expectedToken = new Token();
        expectedToken.setToken("2800aeb20f71838d9405768415096765");

        Call<Token> loginCall = service.getToken(SCRIPT, USERNAME, PASSWORD, SERVICE);

        //Asynchronous...
//        returnedCall.enqueue(new Callback<Token>() {
//
//            @Override
//            public void onResponse(Call<Token> call, Response<Token> response) {
//
//                Assert.assertEquals("The status code is not 200.", 200, response.code());
//                if (response.isSuccessful()) {
//                    Assert.assertEquals("The incoming token does not match the expected.",
//                            expectedToken.getToken(), response.body().getToken());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Token> call, Throwable t) {
//                Assert.fail(t.getLocalizedMessage());
//            }
//        });


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


//        This sleep() is needed for the verify to pass, when testing Asynchronous call!!
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/moodle/login/" + SCRIPT + "?username=" + USERNAME + "&password="
                + PASSWORD + "&service=" + SERVICE)));
        WireMock.reset();
    }

}
