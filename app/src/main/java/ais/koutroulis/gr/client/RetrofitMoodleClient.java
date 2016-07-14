package ais.koutroulis.gr.client;

import java.io.IOException;
import java.util.List;

import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.ForumByCourse;
import ais.koutroulis.gr.model.MarkAsReadResponse;
import ais.koutroulis.gr.model.Message;
import ais.koutroulis.gr.model.Messages;
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
    private String baseUrl;

    public RetrofitMoodleClient(String baseUrl) {
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
        if(oneForReadZeroForUnread.equals("0")) {

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

    public Response<ForumByCourse> getForumByCourse(MoodleUrlCommonParts urlCommonParts, String courseId) throws IOException {
        Call<ForumByCourse> getForumByCourseCall = clientInitializer.getService()
                .getForumByCourse(urlCommonParts.getPhpScript(), urlCommonParts.getResponseFormat(),
                        urlCommonParts.getToken(), urlCommonParts.getFunction(), courseId);
        Response<ForumByCourse> response = getForumByCourseCall.execute();
        return response;
    }

    private void markAllUnreadMessagesAsRead(MoodleUrlCommonParts urlCommonParts, List<Message> messageList,
                                             String timeReadInMillis) throws IOException {
        if (!messageList.isEmpty()) {
            for (Message oneMessage: messageList) {
                markAsReadMessage(urlCommonParts, Integer.toString(oneMessage.getId()), timeReadInMillis);
            }
        }
    }
}
