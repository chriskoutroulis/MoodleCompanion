package ais.koutroulis.gr.client;

import java.io.IOException;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
public interface MoodleClient {

    public <T> T login(String username, String password) throws IOException;
}
