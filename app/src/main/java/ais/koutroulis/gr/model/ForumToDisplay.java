package ais.koutroulis.gr.model;

import java.util.List;

/**
 * Created by c0nfr0ntier on 19/7/2016.
 */
public class ForumToDisplay {
    private int id;

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
}
