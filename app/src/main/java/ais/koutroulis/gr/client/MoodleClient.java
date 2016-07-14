package ais.koutroulis.gr.client;

import java.io.IOException;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
public interface MoodleClient {

    public <T> T login(String script, String loginService, String username, String password) throws IOException;

    public <T> T getCourses(MoodleUrlCommonParts urlCommonParts) throws IOException;

    public <T> T getUserDetails(MoodleUrlCommonParts urlCommonParts, String byField, String fieldValue) throws IOException;

    public <T> T getMessages(MoodleUrlCommonParts urlCommonParts, String markAsReadFunction, String sentToId,
                             String sentFromId, String oneForReadZeroForUnread, String timeReadInMillis) throws IOException;

    public <T> T markAsReadMessage(MoodleUrlCommonParts urlCommonParts, String unreadMessageId,
                                   String timeReadInMillis) throws IOException;

    public <T> T getForumByCourse(MoodleUrlCommonParts urlCommonParts, String courseId) throws IOException;

}
