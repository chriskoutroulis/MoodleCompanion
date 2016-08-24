package ais.koutroulis.gr.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by c0nfr0ntier on 19/7/2016.
 */
public class ForumToDisplay implements Serializable {
    private int id;

    private String name;

    private int timemodified;

    private List<DiscussionToDisplay> discussionToDisplayList;

    public List<DiscussionToDisplay> getDiscussionToDisplayList() {
        return discussionToDisplayList;
    }

    public void setDiscussionToDisplayList(List<DiscussionToDisplay> discussionToDisplayList) {
        this.discussionToDisplayList = discussionToDisplayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimemodified() {
        return timemodified;
    }

    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }
}
