package ais.koutroulis.gr.service;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ais.koutroulis.gr.client.MoodleClient;
import ais.koutroulis.gr.client.RetrofitMoodleClient;
import ais.koutroulis.gr.model.Token;
import retrofit2.Response;

/**
 * Created by c0nfr0ntier on 20/8/2016.
 */
public class ServiceCaller {

    private static Response<Token> tokenResponse;
    private static final String TOKEN_KEY = "token";

    public static void performLoginCall(String url, final String username, final String password, final Activity activity) {
        //Perform the login call to moodle
        final MoodleClient moodleClient = new RetrofitMoodleClient(url);

        ExecutorService service = Executors.newCachedThreadPool();
        final Future<Response<Token>> futureLoginResponse = service.submit(new Callable<Response<Token>>() {
            @Override
            public Response<Token> call() throws Exception {
                Response<Token> response = null;
                try {
                    response = moodleClient.login("token.php", "moodle_mobile_app", username, password);
                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                }
                return response;
            }
        });

        final ProgressDialog progress = ProgressDialog.show(activity, "Please wait...",
                "Logging in", true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    tokenResponse = futureLoginResponse.get();

                    //Persist the token in the SharedPreferences
                    SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(TOKEN_KEY, tokenResponse.body().getToken());
                    editor.commit();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        if (tokenResponse != null) {
                            if (tokenResponse.body().getToken() != null) {
                                Snackbar.make(activity.getCurrentFocus(), "Login was successful.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(activity.getCurrentFocus(), "Login failed. Please enter the correct details and try again.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), "Internet connection error or server error.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                });
            }
        });

    }

}
