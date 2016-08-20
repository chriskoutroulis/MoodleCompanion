package ais.koutroulis.gr.service;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ais.koutroulis.gr.client.MoodleClient;
import ais.koutroulis.gr.client.MoodleUrlCommonParts;
import ais.koutroulis.gr.client.RetrofitMoodleClient;
import ais.koutroulis.gr.model.Assignment;
import ais.koutroulis.gr.model.Course;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.ui.ContentFragment;
import ais.koutroulis.gr.ui.R;
import retrofit2.Response;

/**
 * Created by c0nfr0ntier on 20/8/2016.
 */
public class ServiceCaller {

    private static Response<Token> tokenResponse;
    private static Response<Courses> coursesResponse;
    public static Bundle fragmentArgs;
    private static MoodleUrlCommonParts urlCommonParts;
    private static MoodleClient moodleClient;

    public static final String COURSES_KEY = "courses";
    public static final String TOKEN_KEY = "token";

    private static final String LOGIN_SCRIPT = "token.php";
    private static final String FUNCTIONS_SCRIPT = "server.php";
    private static final String LOGIN_SERVICE = "moodle_mobile_app";
    private static final String FORMAT = "json";
    private static final String ASSIGNMENTS_FUNCTION = "mod_assign_get_assignments";
    private static final String USER_DETAILS_FUNCTION = "core_user_get_users_by_field";
    private static final String GET_MESSAGES_FUNCTION = "core_message_get_messages";
    private static final String MARK_AS_READ_FUNCTION = "core_message_mark_message_read";
    private static final String GET_FORUM_BY_COURSES_FUNCTION = "mod_forum_get_forums_by_courses";
    private static final String GET_FORUM_DISCUSSIONS_FUNCTION = "mod_forum_get_forum_discussions_paginated";
    private static final String GET_FORUM_DISCUSSION_POSTS_FUNCTION = "mod_forum_get_forum_discussion_posts";

    public static String itemsToShow = null;
    public static final String ITEM_ASSIGNMENT = "assignments";
    public static final String ITEM_MESSAGES = "messages";
    public static final String ITEM_FORUMS = "forums";


    public static void performLoginCall(String url, final String username, final String password, final Activity activity) {
        //Perform the login call to moodle
        moodleClient = new RetrofitMoodleClient(url);

        ExecutorService service = Executors.newCachedThreadPool();
        final Future<Response<Token>> futureLoginResponse = service.submit(new Callable<Response<Token>>() {
            @Override
            public Response<Token> call() throws Exception {
                Response<Token> response = null;
                try {
                    //TODO remove the hardcoded strings below and place all of the moodle calls related strings in strings.xml

                    response = moodleClient.login(LOGIN_SCRIPT, LOGIN_SERVICE, username, password);
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
//                    SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString(TOKEN_KEY, tokenResponse.body().getToken());
//                    editor.commit();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (tokenResponse != null) {
                        fragmentArgs = new Bundle();
                        fragmentArgs.putString(TOKEN_KEY, tokenResponse.body().getToken());
                    }


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

                        //This is the place to perform the other calls. In the ui thread.
                        performGetAssignments(tokenResponse.body().getToken(), activity);
                    }
                });
            }
        });
    }

    public static void performGetAssignments(final String token, final Activity activity) {

        urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                token, ASSIGNMENTS_FUNCTION);
        ExecutorService service = Executors.newCachedThreadPool();
        final Future<Response<Courses>> futureCoursesResponse = service.submit(new Callable<Response<Courses>>() {
            @Override
            public Response<Courses> call() throws Exception {
                Response<Courses> response = null;
                try {
                    //TODO remove the hardcoded strings below and place all of the moodle calls related strings in strings.xml

                    response = moodleClient.getCoursesAndAssignments(urlCommonParts);
                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                }
                return response;
            }
        });

        final ProgressDialog progress = ProgressDialog.show(activity, "Please wait...",
                "Getting Assignments", true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    coursesResponse = futureCoursesResponse.get();

                    //Persist the token in the SharedPreferences
//                    SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    editor.putString(TOKEN_KEY, tokenResponse.body().getToken());
//                    editor.commit();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (coursesResponse != null) {
                        fragmentArgs.putSerializable(COURSES_KEY, coursesResponse.body());
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        if (coursesResponse != null) {
                            if (coursesResponse.body().getCourses() != null) {
                                Snackbar.make(activity.getCurrentFocus(), "Assignments updated.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(activity.getCurrentFocus(), "Failed updating Assingments.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), "Internet connection error or server error.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        //TODO populate this fragment with the Assignmets
                        //After all the updates, populate the first fragment.

                        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);

                        navigationView.setCheckedItem(R.id.nav_assignments);
                        itemsToShow = ITEM_ASSIGNMENT;

                        ContentFragment fragment = new ContentFragment();
                        fragment.setArguments(fragmentArgs);
                        android.support.v4.app.FragmentTransaction fragmentTransaction = ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.coordinator, fragment);
                        fragmentTransaction.commit();
                    }
                });
            }
        });

        service.shutdown();
    }

}
