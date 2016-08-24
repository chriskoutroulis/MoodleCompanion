package ais.koutroulis.gr.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by c0nfr0ntier on 19/7/2016.
 */
public class DiscussionToDisplay implements Serializable {
    private int id;

    private int numUnread;

    private int numReplies;

    private String userFullName;

    private String subject;

    private List<Post> postList;

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumUnread() {
        return numUnread;
    }

    public void setNumUnread(int numUnread) {
        this.numUnread = numUnread;
    }

    public int getNumReplies() {
        return numReplies;
    }

    public void setNumReplies(int numReplies) {
        this.numReplies = numReplies;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
