package ais.koutroulis.gr.client;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ais.koutroulis.gr.model.Course;
import ais.koutroulis.gr.model.CourseToDisplay;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.Discussion;
import ais.koutroulis.gr.model.DiscussionToDisplay;
import ais.koutroulis.gr.model.Discussions;
import ais.koutroulis.gr.model.ForumToDisplay;
import ais.koutroulis.gr.model.ForumByCourse;
import ais.koutroulis.gr.model.MarkAsReadResponse;
import ais.koutroulis.gr.model.Message;
import ais.koutroulis.gr.model.Messages;
import ais.koutroulis.gr.model.Post;
import ais.koutroulis.gr.model.Posts;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.model.User;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by c0nfr0ntier on 7/7/2016.
 */
public class RetrofitMoodleClient implements MoodleClient {

    private RetroFitClientInitializer<MoodleRetroFitService> clientInitializer;
    private static final String LOGIN_PATH = "/login/index.php";
    private static final String READ_DISCUSSION_PATH = "/mod/forum/discuss.php?d=";

    private static final String FORMAT = "json";
    private static final String ASSIGNMENTS_FUNCTION = "mod_assign_get_assignments";
    private static final String USER_DETAILS_FUNCTION = "core_user_get_users_by_field";
    private static final String GET_MESSAGES_FUNCTION = "core_message_get_messages";
    private static final String MARK_AS_READ_FUNCTION = "core_message_mark_message_read";
    private static final String GET_FORUM_BY_COURSES_FUNCTION = "mod_forum_get_forums_by_courses";
    private static final String GET_FORUM_DISCUSSIONS_FUNCTION = "mod_forum_get_forum_discussions_paginated";
    private static final String GET_FORUM_DISCUSSION_POSTS_FUNCTION = "mod_forum_get_forum_discussion_posts";

    private static final String LOGIN_SCRIPT = "token.php";
    private static final String FUNCTIONS_SCRIPT = "server.php";
    private static final String LOGIN_SERVICE = "moodle_mobile_app";

    private String baseUrl;



    public RetrofitMoodleClient(String baseUrl) {
        this.baseUrl = baseUrl;
        clientInitializer = new RetroFitClientInitializer<>(baseUrl, MoodleRetroFitService.class);
    }

    //TODO Create a method to validate the new MoodleUrlCommonParts, before making the call.
    //Or in a different class maybe?? To perform several validations?
    //Do not allow unappropriate calls.

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

    public Response<Courses> getCourses(MoodleUrlCommonParts urlCommonParts) throws IOException {
        Call<Courses> getCoursesCall = clientInitializer.getService()
                .getCourses(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction());
        Response<Courses> response = getCoursesCall.execute();

        return response;
    }

    public Response<User> getUserDetails(MoodleUrlCommonParts urlCommonParts, String byField,
                                         String fieldValue) throws IOException {
        Call<User> getUserDetailsCall = clientInitializer.getService()
                .getUserDetails(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), byField, fieldValue);
        Response<User> response = getUserDetailsCall.execute();
        return response;
    }

    public Response<Messages> getMessages(MoodleUrlCommonParts urlCommonParts, String markAsReadFunction,
                                          String sentToId, String sentFromId, String oneForReadZeroForUnread, String timeReadInMillis) throws IOException {
        Call<Messages> getMessagesCall = clientInitializer.getService()
                .getMessages(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), sentToId, sentFromId, oneForReadZeroForUnread);
        Response<Messages> response = getMessagesCall.execute();

        //if the call was for unread messages
        if (oneForReadZeroForUnread.equals("0")) {

            urlCommonParts.setFunction(markAsReadFunction);
            markAllUnreadMessagesAsRead(urlCommonParts, response.body().getMessages(), timeReadInMillis);
        }

        return response;
    }

    public Response<MarkAsReadResponse> markAsReadMessage(MoodleUrlCommonParts urlCommonParts, String unreadMessageId,
                                                          String timeReadInMillis) throws IOException {
        Call<MarkAsReadResponse> markAsReadCall = clientInitializer.getService()
                .markAsReadMessage(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), unreadMessageId, timeReadInMillis);
        Response<MarkAsReadResponse> response = markAsReadCall.execute();
        return response;
    }

    public Response<List<ForumByCourse>> getForumsByCourse(MoodleUrlCommonParts urlCommonParts, String courseId) throws IOException {
        Call<List<ForumByCourse>> getForumByCourseCall = clientInitializer.getService()
                .getForumByCourse(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), courseId);
        Response<List<ForumByCourse>> response = getForumByCourseCall.execute();
        return response;
    }

    public Response<Discussions> getForumDiscussions(MoodleUrlCommonParts urlCommonParts, String forumId) throws IOException {
        Call<Discussions> getForumDiscussionsCall = clientInitializer.getService()
                .getForumDiscussions(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), forumId);
        Response<Discussions> response = getForumDiscussionsCall.execute();
        return response;
    }

    public Response<Posts> getForumDiscussionPosts(MoodleUrlCommonParts urlCommonParts, String discussionId) throws IOException {
        Call<Posts> getForumDiscussionPostsCall = clientInitializer.getService()
                .getForumDiscussionPosts(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), discussionId);
        Response<Posts> response = getForumDiscussionPostsCall.execute();
        return response;
    }

    public String markForumDiscussionsAsRead(String baseUrl, String username, String password, int discussionId) throws IOException {

        String loginPageUrl = baseUrl + LOGIN_PATH;
        String urlYouNeedToBeLoggedInToAccess = baseUrl + READ_DISCUSSION_PATH;
        Connection.Response discussionResponse = null;

        try {
            Connection.Response loginResponse = Jsoup
                    .connect(loginPageUrl)
                    .data("username", username, "password", password)
                    .method(Connection.Method.POST)
                    .execute();
//This will get you cookies
            Map<String, String> loginCookies = loginResponse.cookies();

//And this is the easiest way I've found to remain in session
            discussionResponse = Jsoup.connect(urlYouNeedToBeLoggedInToAccess + discussionId)
                    .cookies(loginCookies)
                    .execute();
        } catch (org.jsoup.HttpStatusException je) {
            return "Discussion id not found!";
        }
        return discussionResponse.url().toString();
    }

    public List<CourseToDisplay> getAllForumPosts(MoodleUrlCommonParts urlCommonParts, String username, String password) throws IOException {

        List<CourseToDisplay> courseToDisplayList = new ArrayList<>();

        Response<Courses> coursesResponse = getCourses(urlCommonParts);
        List<Course> coursesList = coursesResponse.body().getCourses();

        //Iterate Courses
        for (Course oneCourse : coursesList) {
            List<ForumToDisplay> forumToDisplayList = new ArrayList<>();

            CourseToDisplay oneCourseToDisplay = new CourseToDisplay();
            oneCourseToDisplay.setId(oneCourse.getId());
            oneCourseToDisplay.setFullName(oneCourse.getFullname());

            //Get all the forums for a specific course
            urlCommonParts.setFunction(GET_FORUM_BY_COURSES_FUNCTION);
            String courseIdString = Integer.toString(oneCourse.getId());
            Response<List<ForumByCourse>> forumByCourseResponse = getForumsByCourse(urlCommonParts, courseIdString);

            List<ForumByCourse> forumByCourseList = forumByCourseResponse.body();

            //Iterate Forums
            for (ForumByCourse oneForum : forumByCourseList) {
                List<DiscussionToDisplay> discussionToDisplayList = new ArrayList<>();

                ForumToDisplay oneForumToDisplay = new ForumToDisplay();
                oneForumToDisplay.setId(oneForum.getId());

                urlCommonParts.setFunction(GET_FORUM_DISCUSSIONS_FUNCTION);
                String forumIdString = Integer.toString(oneForum.getId());
                Response<Discussions> discussionsResponse = getForumDiscussions(urlCommonParts, forumIdString);

                List<Discussion> discussionList = discussionsResponse.body().getDiscussions();

                for (Discussion oneDiscussion : discussionList) {
                    DiscussionToDisplay oneDiscussionToDisplay = new DiscussionToDisplay();
                    oneDiscussionToDisplay.setId(oneDiscussion.getdiscussionId());
                    oneDiscussionToDisplay.setNumReplies(oneDiscussion.getNumreplies());
                    oneDiscussionToDisplay.setNumUnread(oneDiscussion.getNumunread());

                    urlCommonParts.setFunction(GET_FORUM_DISCUSSION_POSTS_FUNCTION);
                    String discussionIdString = Integer.toString(oneDiscussion.getdiscussionId());
                    Response<Posts> postsResponse = getForumDiscussionPosts(urlCommonParts, discussionIdString);
                    List<Post> postList = postsResponse.body().getPosts();
                    oneDiscussionToDisplay.setPostList(postList);
                    discussionToDisplayList.add(oneDiscussionToDisplay);

                    //Mark each discussion as read only if there are unread posts
                    int numberOfUnreadPosts = oneDiscussion.getNumunread();
                    if (numberOfUnreadPosts>0) {
                        markForumDiscussionsAsRead(baseUrl, username, password,
                                oneDiscussion.getdiscussionId());
                    }
                }

                oneForumToDisplay.setDiscussionToDisplayList(discussionToDisplayList);
                forumToDisplayList.add(oneForumToDisplay);
            }
            oneCourseToDisplay.setForumToDisplayList(forumToDisplayList);
            courseToDisplayList.add(oneCourseToDisplay);
        }

        return courseToDisplayList;
    }

    private void markAllUnreadMessagesAsRead(MoodleUrlCommonParts urlCommonParts, List<Message> messageList,
                                             String timeReadInMillis) throws IOException {
        if (!messageList.isEmpty()) {
            for (Message oneMessage : messageList) {
                markAsReadMessage(urlCommonParts, Integer.toString(oneMessage.getId()), timeReadInMillis);
            }
        }
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
