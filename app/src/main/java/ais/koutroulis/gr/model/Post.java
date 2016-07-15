package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 15/7/2016.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("discussion")
    @Expose
    private int discussionId;
    @SerializedName("parent")
    @Expose
    private int parent;
    @SerializedName("userid")
    @Expose
    private int userid;
    @SerializedName("created")
    @Expose
    private int created;
    @SerializedName("modified")
    @Expose
    private int modified;
    @SerializedName("mailed")
    @Expose
    private int mailed;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("messageformat")
    @Expose
    private int messageformat;
    @SerializedName("messagetrust")
    @Expose
    private int messagetrust;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("totalscore")
    @Expose
    private int totalscore;
    @SerializedName("mailnow")
    @Expose
    private int mailnow;
    @SerializedName("children")
    @Expose
    private List<Integer> children = new ArrayList<Integer>();
    @SerializedName("canreply")
    @Expose
    private boolean canreply;
    @SerializedName("postread")
    @Expose
    private boolean postread;
    @SerializedName("userfullname")
    @Expose
    private String userfullname;
    @SerializedName("userpictureurl")
    @Expose
    private String userpictureurl;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The discussion
     */
    public int getDiscussionId() {
        return discussionId;
    }

    /**
     * @param discussionId The discussion
     */
    public void setDiscussionId(int discussionId) {
        this.discussionId = discussionId;
    }

    /**
     * @return The parent
     */
    public int getParent() {
        return parent;
    }

    /**
     * @param parent The parent
     */
    public void setParent(int parent) {
        this.parent = parent;
    }

    /**
     * @return The userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid The userid
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * @return The created
     */
    public int getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(int created) {
        this.created = created;
    }

    /**
     * @return The modified
     */
    public int getModified() {
        return modified;
    }

    /**
     * @param modified The modified
     */
    public void setModified(int modified) {
        this.modified = modified;
    }

    /**
     * @return The mailed
     */
    public int getMailed() {
        return mailed;
    }

    /**
     * @param mailed The mailed
     */
    public void setMailed(int mailed) {
        this.mailed = mailed;
    }

    /**
     * @return The subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The messageformat
     */
    public int getMessageformat() {
        return messageformat;
    }

    /**
     * @param messageformat The messageformat
     */
    public void setMessageformat(int messageformat) {
        this.messageformat = messageformat;
    }

    /**
     * @return The messagetrust
     */
    public int getMessagetrust() {
        return messagetrust;
    }

    /**
     * @param messagetrust The messagetrust
     */
    public void setMessagetrust(int messagetrust) {
        this.messagetrust = messagetrust;
    }

    /**
     * @return The attachment
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * @param attachment The attachment
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * @return The totalscore
     */
    public int getTotalscore() {
        return totalscore;
    }

    /**
     * @param totalscore The totalscore
     */
    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    /**
     * @return The mailnow
     */
    public int getMailnow() {
        return mailnow;
    }

    /**
     * @param mailnow The mailnow
     */
    public void setMailnow(int mailnow) {
        this.mailnow = mailnow;
    }

    /**
     * @return The children
     */
    public List<Integer> getChildren() {
        return children;
    }

    /**
     * @param children The children
     */
    public void setChildren(List<Integer> children) {
        this.children = children;
    }

    /**
     * @return The canreply
     */
    public boolean isCanreply() {
        return canreply;
    }

    /**
     * @param canreply The canreply
     */
    public void setCanreply(boolean canreply) {
        this.canreply = canreply;
    }

    /**
     * @return The postread
     */
    public boolean isPostread() {
        return postread;
    }

    /**
     * @param postread The postread
     */
    public void setPostread(boolean postread) {
        this.postread = postread;
    }

    /**
     * @return The userfullname
     */
    public String getUserfullname() {
        return userfullname;
    }

    /**
     * @param userfullname The userfullname
     */
    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    /**
     * @return The userpictureurl
     */
    public String getUserpictureurl() {
        return userpictureurl;
    }

    /**
     * @param userpictureurl The userpictureurl
     */
    public void setUserpictureurl(String userpictureurl) {
        this.userpictureurl = userpictureurl;
    }

}

