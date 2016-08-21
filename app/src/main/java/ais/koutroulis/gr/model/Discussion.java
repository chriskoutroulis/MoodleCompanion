package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 14/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Discussion implements Serializable {

    @SerializedName("id")
    @Expose
    private int firstPostId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("groupid")
    @Expose
    private int groupid;
    @SerializedName("timemodified")
    @Expose
    private int timemodified;
    @SerializedName("usermodified")
    @Expose
    private int usermodified;
    @SerializedName("timestart")
    @Expose
    private int timestart;
    @SerializedName("timeend")
    @Expose
    private int timeend;
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
    @SerializedName("userfullname")
    @Expose
    private String userfullname;
    @SerializedName("usermodifiedfullname")
    @Expose
    private String usermodifiedfullname;
    @SerializedName("userpictureurl")
    @Expose
    private String userpictureurl;
    @SerializedName("usermodifiedpictureurl")
    @Expose
    private String usermodifiedpictureurl;
    @SerializedName("numreplies")
    @Expose
    private int numreplies;
    @SerializedName("numunread")
    @Expose
    private int numunread;
    @SerializedName("pinned")
    @Expose
    private boolean pinned;

    /**
     * @return The firstPostId
     */
    public int getFirstPostId() {
        return firstPostId;
    }

    /**
     * @param firstPostId The id
     */
    public void setFirstPostId(int firstPostId) {
        this.firstPostId = firstPostId;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The groupid
     */
    public int getGroupid() {
        return groupid;
    }

    /**
     * @param groupid The groupid
     */
    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    /**
     * @return The timemodified
     */
    public int getTimemodified() {
        return timemodified;
    }

    /**
     * @param timemodified The timemodified
     */
    public void setTimemodified(int timemodified) {
        this.timemodified = timemodified;
    }

    /**
     * @return The usermodified
     */
    public int getUsermodified() {
        return usermodified;
    }

    /**
     * @param usermodified The usermodified
     */
    public void setUsermodified(int usermodified) {
        this.usermodified = usermodified;
    }

    /**
     * @return The timestart
     */
    public int getTimestart() {
        return timestart;
    }

    /**
     * @param timestart The timestart
     */
    public void setTimestart(int timestart) {
        this.timestart = timestart;
    }

    /**
     * @return The timeend
     */
    public int getTimeend() {
        return timeend;
    }

    /**
     * @param timeend The timeend
     */
    public void setTimeend(int timeend) {
        this.timeend = timeend;
    }

    /**
     * @return The discussionId
     */
    public int getdiscussionId() {
        return discussionId;
    }

    /**
     * @param discussionId The discussionId
     */
    public void setdiscussionId(int discussionId) {
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
     * @return The usermodifiedfullname
     */
    public String getUsermodifiedfullname() {
        return usermodifiedfullname;
    }

    /**
     * @param usermodifiedfullname The usermodifiedfullname
     */
    public void setUsermodifiedfullname(String usermodifiedfullname) {
        this.usermodifiedfullname = usermodifiedfullname;
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

    /**
     * @return The usermodifiedpictureurl
     */
    public String getUsermodifiedpictureurl() {
        return usermodifiedpictureurl;
    }

    /**
     * @param usermodifiedpictureurl The usermodifiedpictureurl
     */
    public void setUsermodifiedpictureurl(String usermodifiedpictureurl) {
        this.usermodifiedpictureurl = usermodifiedpictureurl;
    }

    /**
     * @return The numreplies
     */
    public int getNumreplies() {
        return numreplies;
    }

    /**
     * @param numreplies The numreplies
     */
    public void setNumreplies(int numreplies) {
        this.numreplies = numreplies;
    }

    /**
     * @return The numunread
     */
    public int getNumunread() {
        return numunread;
    }

    /**
     * @param numunread The numunread
     */
    public void setNumunread(int numunread) {
        this.numunread = numunread;
    }

    /**
     * @return The pinned
     */
    public boolean isPinned() {
        return pinned;
    }

    /**
     * @param pinned The pinned
     */
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

}