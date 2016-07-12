package ais.koutroulis.gr.client;

import java.io.IOException;
import java.util.List;

import ais.koutroulis.gr.model.Courses;
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

    public Response<User> getUserDetails(String script, String format, String token,
                                         String function, String byField, String fieldValue) throws IOException {
        Call<User> getUserDetailsCall = clientInitializer.getService()
                .getUserDetails(script, format, token, function, byField, fieldValue);
        Response<User> response = getUserDetailsCall.execute();
        return response;
    }

    public Response<Messages> getMessages(String script, String format, String token, String getMessagesFunction, String markAsReadFunction,
                                          String sentToId, String sentFromId, String oneForReadZeroForUnread, String timeReadInMillis) throws IOException {
        Call<Messages> getMessagesCall = clientInitializer.getService()
                .getMessages(script, format, token, getMessagesFunction, sentToId, sentFromId, oneForReadZeroForUnread);
        Response<Messages> response = getMessagesCall.execute();

        //if the call was for unread messages
        if(oneForReadZeroForUnread.equals("0")) {
            markAllUnreadMessagesAsRead(script, format, token, markAsReadFunction, response.body().getMessages(), timeReadInMillis);
        }

        return response;
    }

    public Response<MarkAsReadResponse> markAsReadMessage(String script, String format, String token, String function,
                                                          String unreadMessageId, String timeReadInMillis) throws IOException {
        Call<MarkAsReadResponse> markAsReadCall = clientInitializer.getService()
                .markAsReadMessage(script, format, token, function, unreadMessageId, timeReadInMillis);
        Response<MarkAsReadResponse> response = markAsReadCall.execute();
        return response;
    }

    private void markAllUnreadMessagesAsRead(String script, String format, String token,
                                            String function, List<Message> messageList, String timeReadInMillis) throws IOException {
        if (!messageList.isEmpty()) {
            for (Message oneMessage: messageList) {
                markAsReadMessage(script, format, token, function,
                        Integer.toString(oneMessage.getId()), timeReadInMillis);
            }
        }

    }

}
