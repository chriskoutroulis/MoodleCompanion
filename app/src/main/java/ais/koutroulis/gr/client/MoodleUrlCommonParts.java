package ais.koutroulis.gr.client;

/**
 * Created by c0nfr0ntier on 13/7/2016.
 */
public class MoodleUrlCommonParts {

    private String phpScript;
    private String responseFormat;
    private String token;
    private String function;

    public MoodleUrlCommonParts(String phpScript, String responseFormat, String token, String function) {
        this.phpScript = phpScript;
        this.responseFormat = responseFormat;
        this.token = token;
        this.function = function;
    }

    public String getPhpScript() {
        return phpScript;
    }

    public void setPhpScript(String phpScript) {
        this.phpScript = phpScript;
    }

    public String getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(String responseFormat) {
        this.responseFormat = responseFormat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
