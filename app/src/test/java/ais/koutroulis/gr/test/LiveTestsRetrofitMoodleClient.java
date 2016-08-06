package ais.koutroulis.gr.test;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ais.koutroulis.gr.client.MoodleClient;
import ais.koutroulis.gr.client.MoodleUrlCommonParts;
import ais.koutroulis.gr.client.RetrofitMoodleClient;
import ais.koutroulis.gr.model.CourseToDisplay;
import ais.koutroulis.gr.model.DiscussionToDisplay;
import ais.koutroulis.gr.model.ForumToDisplay;
import ais.koutroulis.gr.model.Post;
import ais.koutroulis.gr.model.Token;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Chris on 06-Aug-16.
 */
public class LiveTestsRetrofitMoodleClient {

    private MoodleClient moodleClient;
    private String callingUsername;
    private String callingPassword;
    private MoodleUrlCommonParts urlCommonParts;
    private Token expectedToken = new Token();

    private static final String BASE_URL = "http://localhost:8080/moodle/";
    private static final String FORMAT = "json";
    private static final String FUNCTIONS_SCRIPT = "server.php";
    private static final String ASSIGNMENTS_FUNCTION = "mod_assign_get_assignments";

    @Before
    public void setup() {
        callingUsername = "ais0058";
        callingPassword = "Masterais0056!";
        expectedToken.setToken("grantAccessToken");
        moodleClient = new RetrofitMoodleClient(BASE_URL);
        urlCommonParts = new MoodleUrlCommonParts(FUNCTIONS_SCRIPT, FORMAT,
                expectedToken.getToken(), null);
    }

    @Test
    public void markForumDiscussionPostsAsReadShouldReturnUrlThatContainsDiscussPhp() {

        String baseUrl = "http://ais-temp.daidalos.teipir.gr/moodle/";
        int discussionId = 1;
        String httpResponseUrl = null;
        try {
            httpResponseUrl = moodleClient.markForumDiscussionPostsAsRead(baseUrl, callingUsername, callingPassword, discussionId);
        } catch (IOException e) {
            fail("There was a network error.");
        }
        //if the login has failed then the response url, is pointing at the login page.
        assertTrue("The response url shows that a redirection was made due to possible login failure or that the resource is missing.",
                httpResponseUrl.contains("discuss.php"));
    }

    @Test
    //After running this test we can visually verify that all posts are marked as read when this is done.
    public void getAllForumPostsShouldReturnSpecificNumberOfTotalPosts() {

        moodleClient = new RetrofitMoodleClient("http://ais-temp.daidalos.teipir.gr/moodle/");
        urlCommonParts.setFunction(ASSIGNMENTS_FUNCTION);
        String liveTokenForAis0058 = "2800aeb20f71838d9405768415096765";
        urlCommonParts.setToken(liveTokenForAis0058);
        List<Post> postList = new ArrayList<>();
        int expectedNumberOfPosts = 9;

        try {
            List<CourseToDisplay> courseToDisplayList = moodleClient.getAllForumPostsAndMarkAsRead(urlCommonParts,
                    callingUsername, callingPassword);
            for (CourseToDisplay oneCourse : courseToDisplayList) {
                List<ForumToDisplay> forumToDisplayList = oneCourse.getForumToDisplayList();
                for (ForumToDisplay oneForum : forumToDisplayList) {
                    List<DiscussionToDisplay> discussionToDisplayListList = oneForum.getDiscussionToDisplayList();
                    for (DiscussionToDisplay oneDiscussion : discussionToDisplayListList) {
                        postList.addAll(oneDiscussion.getPostList());
                    }
                }
            }
            assertEquals("The expected number of posts were not found.", expectedNumberOfPosts, postList.size());
        } catch (IOException ie) {
        }
    }

}
