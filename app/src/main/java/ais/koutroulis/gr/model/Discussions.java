package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 14/7/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Discussions implements Serializable {

    @SerializedName("discussions")
    @Expose
    private List<Discussion> discussions = new ArrayList<Discussion>();
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = new ArrayList<Object>();

    /**
     * @return The discussions
     */
    public List<Discussion> getDiscussions() {
        return discussions;
    }

    /**
     * @param discussions The discussions
     */
    public void setDiscussions(List<Discussion> discussions) {
        this.discussions = discussions;
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
