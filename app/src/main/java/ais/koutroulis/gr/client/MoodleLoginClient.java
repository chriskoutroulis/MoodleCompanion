package ais.koutroulis.gr.client;

import junit.framework.Assert;

import java.io.IOException;

import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import ais.koutroulis.gr.service.RetroFitClientInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by c0nfr0ntier on 7/7/2016.
 */
public class MoodleLoginClient {

    private RetroFitClientInitializer<MoodleRetroFitService> clientInitializer;
    private String baseUrl;
    private String script;
    private String apiService;
    private String username;
    private String password;
    private Token token;

    public MoodleLoginClient(String baseUrl, String script, String apiService) {
        this.script = script;
        this.apiService=apiService;
        clientInitializer =  new RetroFitClientInitializer<>(baseUrl, MoodleRetroFitService.class);

    }

    public void login(String username, String password) {
        Call<Token> loginCall = clientInitializer.getService()
                .getToken(script, username, password, apiService);


        //Asynchronous...
        loginCall.enqueue(new Callback<Token>() {

            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                token =  response.body();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                token = null;
            }
        });

        //Synchronous...
//       try {
//            Response<Token> response = loginCall.execute();
//            token = response.body();
//
//        } catch (IOException ie) {
//
//        }
    }

    public Token getToken() {
        return token;
    }
}
