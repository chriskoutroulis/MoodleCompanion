package ais.koutroulis.gr.client;

import java.io.IOException;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
public interface MoodleClient {

    //TODO Create 2 objects to hold the parameters for the last 4 methods.
    //It should probably hold only the common to all parameters (String script, String format, String token, String function).
    //The login method is probably ok, with only 4 String parameters.
    //Maybe the rest of the arguments could be inside a Map<String,String> .. Maybe

    public <T> T login(String script, String loginService, String username, String password) throws IOException;

    public <T> T getCourses(MoodleUrlCommonParts urlCommonParts) throws IOException;

    public <T> T getUserDetails(MoodleUrlCommonParts urlCommonParts, String byField, String fieldValue) throws IOException;

    public <T> T getMessages(MoodleUrlCommonParts urlCommonPartsn, String markAsReadFunction, String sentToId,
                             String sentFromId, String oneForReadZeroForUnread, String timeReadInMillis) throws IOException;

    public <T> T markAsReadMessage(MoodleUrlCommonParts urlCommonParts, String unreadMessageId,
                                   String timeReadInMillis) throws IOException;
}
