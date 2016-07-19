package ais.koutroulis.gr.model;

import java.util.List;

/**
 * Created by c0nfr0ntier on 19/7/2016.
 */
public class CourseToDisplay {
    private int id;

    private String fullName;

    private List<ForumToDisplay> forumToDisplayList;

    public List<ForumToDisplay> getForumToDisplayList() {
        return forumToDisplayList;
    }

    public void setForumToDisplayList(List<ForumToDisplay> forumToDisplayList) {
        this.forumToDisplayList = forumToDisplayList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
