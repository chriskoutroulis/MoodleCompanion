package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 11/7/2016.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Messages {

    @SerializedName("messages")
    @Expose
    private List<Message> messages = new ArrayList<Message>();
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = new ArrayList<Object>();

    /**
     * @return The messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * @param messages The messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * @return The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     * @param warnings The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }
}


