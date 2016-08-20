package ais.koutroulis.gr.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;

import ais.koutroulis.gr.client.MoodleClient;
import ais.koutroulis.gr.client.RetrofitMoodleClient;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.service.ServiceCaller;
import retrofit2.Response;

/**
 * Created by Chris on 08-Aug-16.
 */
public class SettingsFragment extends Fragment {

    private EditText urlEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button saveButton;

    private String url;
    private String username;
    private String password;

    private ProgressDialog progress;
    private Response<Token> userToken;

    public static final String URL_KEY = "url";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String DOES_NOT_EXIST = "The value does not exist";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        final SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        urlEditText = (EditText) getView().findViewById(R.id.urlEditText);
        usernameEditText = (EditText) getView().findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) getView().findViewById(R.id.passwordEditText);
        saveButton = (Button) getView().findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                url = urlEditText.getText().toString();
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (url == null || url.isEmpty() || !url.contains("http://")) {
                    Snackbar.make(v, getString(R.string.invalid_url_message), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (url != null && !url.isEmpty() && url.contains("http://") && !url.endsWith("/")) {
                    Snackbar.make(v, getString(R.string.url_must_end_with), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (username == null || username.isEmpty()) {
                    Snackbar.make(v, getString(R.string.invalid_username_message), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (password == null || password.isEmpty()) {
                    Snackbar.make(v, getString(R.string.invalid_password_message), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                saveToSharedPreferences(sharedPref);

                Snackbar.make(v, getString(R.string.save_successful_message), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               ServiceCaller.performLoginCall(url, username, password, getActivity());
            }
        });

        String savedUrl = sharedPref.getString(URL_KEY, DOES_NOT_EXIST);
        if (savedUrl != DOES_NOT_EXIST) {
            urlEditText.setText(savedUrl);
        }

        String savedUsername = sharedPref.getString(USERNAME_KEY, DOES_NOT_EXIST);
        if (savedUsername != DOES_NOT_EXIST) {
            usernameEditText.setText(savedUsername);
        }

        String savedPassword = sharedPref.getString(PASSWORD_KEY, DOES_NOT_EXIST);
        if (savedPassword != DOES_NOT_EXIST) {
            passwordEditText.setText(savedPassword);
        }

        super.onActivityCreated(savedInstanceState);
    }

    private void saveToSharedPreferences(SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(URL_KEY, url);
        editor.putString(USERNAME_KEY, username);
        editor.putString(PASSWORD_KEY, password);
        editor.commit();
    }
}


