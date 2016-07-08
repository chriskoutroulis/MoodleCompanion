package ais.koutroulis.gr.client;

import java.io.IOException;

import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by c0nfr0ntier on 7/7/2016.
 */
public class RetrofitMoodleClient implements MoodleClient{

    private RetroFitClientInitializer<MoodleRetroFitService> clientInitializer;
    private String baseUrl;

    public RetrofitMoodleClient(String baseUrl) {
        clientInitializer = new RetroFitClientInitializer<>(baseUrl, MoodleRetroFitService.class);
    }

    public Response<Token> login(String script, String loginService, String username, String password) throws IOException {
        Call<Token> loginCall = clientInitializer.getService()
                .getToken(script, username, password, loginService);



        //Asynchronous...
//        loginCall.enqueue(new Callback<Token>() {
//
//            @Override
//            public void onResponse(Call<Token> call, Response<Token> response) {
//                token =  response.body();
//            }
//
//            @Override
//            public void onFailure(Call<Token> call, Throwable t) {
//                token = null;
//            }
//        });

        //Synchronous...
        Response<Token> response = loginCall.execute();

        return response;
    }

    public Response<Courses> getCourses(String script, String format, String token, String function) throws IOException {
        Call<Courses> getCoursesCall = clientInitializer.getService()
                .getCourses(script, format, token, function);
        Response<Courses> response = getCoursesCall.execute();

        return response;
    }

}
