package ais.koutroulis.gr.client;

import java.io.IOException;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
public interface MoodleClient {

    public <T> T login(String script, String loginService, String username, String password) throws IOException;

    public <T> T getCourses(String script, String format, String token, String function) throws IOException;

    public <T> T getUserDetails(String script, String format, String token, String function, String byField, String fieldValue) throws IOException;
}
