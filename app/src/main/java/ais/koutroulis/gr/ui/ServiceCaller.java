package ais.koutroulis.gr.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ais.koutroulis.gr.client.MoodleClient;
import ais.koutroulis.gr.client.MoodleUrlCommonParts;
import ais.koutroulis.gr.client.RetrofitMoodleClient;
import ais.koutroulis.gr.model.CourseToDisplay;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.Messages;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.model.User;
import retrofit2.Response;

/**
 * Created by c0nfr0ntier on 20/8/2016.
 */
public class ServiceCaller {

    private static Response<Token> tokenResponse;
    private static Response<Courses> coursesResponse;
    private static Response<Messages> unReadMessagesResponse;
    private static Response<Messages> readMessagesResponse;
    private static List<CourseToDisplay> forumPostsResponse;
    public static Bundle fragmentArgs;
    private static MoodleUrlCommonParts urlCommonParts;
    private static MoodleClient moodleClient;

    public static final String BUNDLE_FORUM_POSTS_KEY = "forum_posts";
    public static final String BUNDLE_COURSES_KEY = "courses";
    public static final String BUNDLE_UNREAD_MESSAGES_KEY = "unread_messages";
    public static final String BUNDLE_READ_MESSAGES_KEY = "read_messages";
    public static final String BUNDLE_TOKEN_KEY = "token";

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

    private static final String ANY_USER = "0";
    private static final String READ_FALSE = "0";
    private static final String READ_TRUE = "1";


    public static void performLoginAndUpdateAll(String url, final String username, final String password, final Activity activity) {
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

        final ProgressDialog progress = ProgressDialog.show(activity, activity.getString(R.string.please_wait),
                activity.getString(R.string.logging_in), true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    tokenResponse = futureLoginResponse.get();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (tokenResponse != null) {
                        fragmentArgs = new Bundle();
                        fragmentArgs.putString(BUNDLE_TOKEN_KEY, tokenResponse.body().getToken());
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
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.login_sucess), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.login_fail), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                return;
                            }
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.internet_error), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
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

        final ProgressDialog progress = ProgressDialog.show(activity, activity.getString(R.string.please_wait),
                "Getting Assignments", true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    coursesResponse = futureCoursesResponse.get();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (coursesResponse != null) {
                        fragmentArgs.putSerializable(BUNDLE_COURSES_KEY, coursesResponse.body());
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
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.assignments_updated), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.assignments_failed_update), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.internet_error), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        performGetUnreadMessages(token, activity);
                    }
                });
            }
        });

        service.shutdown();
    }

    public static void performGetUnreadMessages(final String token, final Activity activity) {


        ExecutorService service = Executors.newCachedThreadPool();
        final Future<Response<Messages>> futureUnreadMessagesResponse = service.submit(new Callable<Response<Messages>>() {
            @Override
            public Response<Messages> call() throws Exception {
                Response<Messages> response = null;

                Long timeReadInMillisNumber = System.currentTimeMillis();
                String timeReadInMillis = Long.toString(timeReadInMillisNumber);
                String epochTimeRead =
                        timeReadInMillis.substring(0, timeReadInMillis.length() - 3);
                try {
                    //TODO remove the hardcoded strings below and place all of the moodle calls related strings in strings.xml

                    //Find out the current user's Id number first.
                    String currentUsername = activity.getPreferences(Context.MODE_PRIVATE).getString(SettingsFragment.USERNAME_KEY, null);

                    urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                            token, USER_DETAILS_FUNCTION);
                    Response<List<User>> responseUserDetails = moodleClient.getUserDetails(urlCommonParts, "username",
                            currentUsername);

                    int currentUserIdNumber = responseUserDetails.body().get(0).getId();
                    String currentUserId = Integer.toString(currentUserIdNumber);

                    //Prepare the urlCommonParts for the next call.
                    urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                            token, GET_MESSAGES_FUNCTION);

                    response = moodleClient.getMessages(urlCommonParts, MARK_AS_READ_FUNCTION, currentUserId,
                            ANY_USER, READ_FALSE, epochTimeRead);

                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                }
                return response;
            }
        });

        final ProgressDialog progress = ProgressDialog.show(activity, activity.getString(R.string.please_wait),
                "Getting Unread Messages", true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    unReadMessagesResponse = futureUnreadMessagesResponse.get();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (unReadMessagesResponse != null) {

                        fragmentArgs.putSerializable(BUNDLE_UNREAD_MESSAGES_KEY, unReadMessagesResponse.body());
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
                        if (unReadMessagesResponse != null) {
                            if (unReadMessagesResponse.body().getMessages() != null) {
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.unread_messages_updated), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.unread_messages_failed_update), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.internet_error), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        performGetReadMessages(token, activity);
                    }
                });
            }
        });

        service.shutdown();
    }

    public static void performGetReadMessages(final String token, final Activity activity) {

        ExecutorService service = Executors.newCachedThreadPool();
        final Future<Response<Messages>> futureReadMessagesResponse = service.submit(new Callable<Response<Messages>>() {
            @Override
            public Response<Messages> call() throws Exception {
                Response<Messages> response = null;

                Long timeReadInMillisNumber = System.currentTimeMillis();
                String timeReadInMillis = Long.toString(timeReadInMillisNumber);
                String epochTimeRead =
                        timeReadInMillis.substring(0, timeReadInMillis.length() - 3);
                try {
                    //TODO remove the hardcoded strings below and place all of the moodle calls related strings in strings.xml

                    //Find out the current user's Id number first.
                    String currentUsername = activity.getPreferences(Context.MODE_PRIVATE).getString(SettingsFragment.USERNAME_KEY, null);

                    urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                            token, USER_DETAILS_FUNCTION);
                    Response<List<User>> responseUserDetails = moodleClient.getUserDetails(urlCommonParts, "username",
                            currentUsername);

                    int currentUserIdNumber = responseUserDetails.body().get(0).getId();
                    String currentUserId = Integer.toString(currentUserIdNumber);

                    //Prepare the urlCommonParts for the next call.
                    urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                            token, GET_MESSAGES_FUNCTION);

                    response = moodleClient.getMessages(urlCommonParts, MARK_AS_READ_FUNCTION, currentUserId,
                            ANY_USER, READ_TRUE, epochTimeRead);

                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                }
                return response;
            }
        });

        final ProgressDialog progress = ProgressDialog.show(activity, activity.getString(R.string.please_wait),
                "Getting Read Messages", true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    readMessagesResponse = futureReadMessagesResponse.get();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (readMessagesResponse != null) {

                        fragmentArgs.putSerializable(BUNDLE_READ_MESSAGES_KEY, readMessagesResponse.body());
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
                        if (readMessagesResponse != null) {
                            if (readMessagesResponse.body().getMessages() != null) {
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.read_messages_updated), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.read_messages_failed_update), Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.internet_error), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                        performGetForumPosts(token, activity);
                    }
                });
            }
        });

        service.shutdown();
    }

    public static void performGetForumPosts(final String token, final Activity activity) {

        ExecutorService service = Executors.newCachedThreadPool();
        final Future<List<CourseToDisplay>> futureForumPostsResponse = service.submit(new Callable<List<CourseToDisplay>>() {
            @Override
            public List<CourseToDisplay> call() throws Exception {
                List<CourseToDisplay> response = null;

                try {
                    //TODO remove the hardcoded strings below and place all of the moodle calls related strings in strings.xml

                    String currentUsername = activity.getPreferences(Context.MODE_PRIVATE).getString(SettingsFragment.USERNAME_KEY, null);
                    String currentPassword = activity.getPreferences(Context.MODE_PRIVATE).getString(SettingsFragment.PASSWORD_KEY, null);

                    urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                            token, ASSIGNMENTS_FUNCTION);
                    response = moodleClient.getAllForumPostsAndMarkAsRead(urlCommonParts,
                            currentUsername, currentPassword);

                } catch (IOException e) {
                    e.printStackTrace();
                    response = null;
                }
                return response;
            }
        });

        final ProgressDialog progress = ProgressDialog.show(activity, activity.getString(R.string.please_wait),
                "Getting Forum Posts", true);

        //Get the result from the previous login call in a blocking call.
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    forumPostsResponse = futureForumPostsResponse.get();

                    //Persist the token in a Bundle that will go inside the fragment that will be called as arguments.
                    if (forumPostsResponse != null) {

                        fragmentArgs.putSerializable(BUNDLE_FORUM_POSTS_KEY, (Serializable) forumPostsResponse);
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
                        if (forumPostsResponse != null) {
                            Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.forum_posts_updated), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        } else {
                            Snackbar.make(activity.getCurrentFocus(), activity.getString(R.string.internet_error), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }


                        //After all the updates, populate the first fragment.
                        //This should be moved at the last calling method.

                        NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
                        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

                        navigationView.setCheckedItem(R.id.nav_assignments);
                        toolbar.setTitle(R.string.moodle_assignments);

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
