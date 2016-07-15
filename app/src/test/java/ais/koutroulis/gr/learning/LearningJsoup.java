package ais.koutroulis.gr.learning;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

/**
 * Created by c0nfr0ntier on 15/7/2016.
 */
public class LearningJsoup {

    @Test
    public void shouldBeAbleToAccessLoginProtectedPage(){
        int discussionId = 5;
        String expectedPhraseInPage = "Απάντηση στο τέταρτο Discussion.";
        String loginPageUrl = "http://ais-temp.daidalos.teipir.gr/moodle/login/index.php";
        String urlYouNeedToBeLoggedInToAccess = "http://ais-temp.daidalos.teipir.gr/moodle/mod/forum/discuss.php?d=" + discussionId;

        try {
            Connection.Response res = Jsoup
                    .connect(loginPageUrl)
                    .data("username", "ais0058", "password", "Masterais0056!")
                    .method(Method.POST)
                    .execute();
//This will get you cookies
            Map<String, String> loginCookies = res.cookies();

//And this is the easiest way I've found to remain in session
            Document doc = Jsoup.connect(urlYouNeedToBeLoggedInToAccess)
                    .cookies(loginCookies)
                    .get();

            System.out.println(doc.body().toString());
            assertTrue(doc.body().toString().contains(expectedPhraseInPage));

        }catch (IOException e){

        }
    }
}
