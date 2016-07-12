package ais.koutroulis.gr.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkAsReadResponse {

    @SerializedName("messageid")
    @Expose
    private int messageIdAsRead;
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = new ArrayList<Object>();

    /**
     * @return The messageIdAsRead
     */
    public int getMessageIdAsRead() {
        return messageIdAsRead;
    }

    /**
     * @param messageIdAsRead The messageid
     */
    public void setMmssageIdAsRead(int messageIdAsRead) {
        this.messageIdAsRead = messageIdAsRead;
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


