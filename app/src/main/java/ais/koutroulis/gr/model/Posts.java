package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 15/7/2016.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Posts implements Serializable {

    @SerializedName("posts")
    @Expose
    private List<Post> posts = new ArrayList<Post>();
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = new ArrayList<Object>();

    /**
     * @return The posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     * @param posts The posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
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
