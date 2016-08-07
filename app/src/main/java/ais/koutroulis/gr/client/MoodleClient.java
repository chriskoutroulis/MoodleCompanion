package ais.koutroulis.gr.client;

import java.io.IOException;
import java.util.List;

import ais.koutroulis.gr.model.CourseToDisplay;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
public interface MoodleClient {

    public <T> T login(String script, String loginService, String username, String password) throws IOException;

    public <T> T getCoursesAndAssignments(MoodleUrlCommonParts urlCommonParts) throws IOException;

    public <T> T scanForCurrentAssignments(MoodleUrlCommonParts urlCommonParts, long currentEpochTime) throws IOException;

    public <T> T getUserDetails(MoodleUrlCommonParts urlCommonParts, String byField, String fieldValue) throws IOException;

    public <T> T getMessages(MoodleUrlCommonParts urlCommonParts, String markAsReadFunction, String sentToId,
                             String sentFromId, String oneForReadZeroForUnread, String timeReadInMillis) throws IOException;

    public <T> T markAsReadMessage(MoodleUrlCommonParts urlCommonParts, String unreadMessageId,
                                   String timeReadInMillis) throws IOException;

    public <T> T scanForUnreadMessages(MoodleUrlCommonParts urlCommonParts, String sentToId, String sentFromId) throws IOException;

    public <T> T getForumsByCourse(MoodleUrlCommonParts urlCommonParts, String courseId) throws IOException;

    public <T> T getForumDiscussions(MoodleUrlCommonParts urlCommonParts, String forumId) throws IOException;

    public <T> T getForumDiscussionPosts(MoodleUrlCommonParts urlCommonParts, String discussionId) throws IOException;

    public String markForumDiscussionPostsAsRead(String baseUrl, String username, String password,
                                                 int discussionId) throws IOException;

    public <T> T getAllForumPostsAndMarkAsRead(MoodleUrlCommonParts urlCommonParts, String username, String password) throws IOException;

    public <T> T scanForUnreadForumDiscussionPosts(MoodleUrlCommonParts urlCommonParts) throws IOException;

}
