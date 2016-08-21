package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 14/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ForumByCourse implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("course")
    @Expose
    private int course;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("intro")
    @Expose
    private String intro;
    @SerializedName("introformat")
    @Expose
    private int introformat;
    @SerializedName("assessed")
    @Expose
    private int assessed;
    @SerializedName("assesstimestart")
    @Expose
    private int assesstimestart;
    @SerializedName("assesstimefinish")
    @Expose
    private int assesstimefinish;
    @SerializedName("timemodified")
    @Expose
    private int timemodified;
    @SerializedName("completiondiscussions")
    @Expose
    private int completiondiscussions;
    @SerializedName("completionreplies")
    @Expose
    private int completionreplies;
    @SerializedName("completionposts")
    @Expose
    private int completionposts;
    @SerializedName("cmid")
    @Expose
    private int cmid;
    @SerializedName("numdiscussions")
    @Expose
    private int numdiscussions;
    @SerializedName("cancreatediscussions")
    @Expose
    private Boolean cancreatediscussions;

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
     * @return The course
     */
    public int getCourse() {
        return course;
    }

    /**
     * @param course The course
     */
    public void setCourse(int course) {
        this.course = course;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
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
     * @return The intro
     */
    public String getIntro() {
        return intro;
    }

    /**
     * @param intro The intro
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * @return The introformat
     */
    public int getIntroformat() {
        return introformat;
    }

    /**
     * @param introformat The introformat
     */
    public void setIntroformat(int introformat) {
        this.introformat = introformat;
    }

    /**
     * @return The assessed
     */
    public int getAssessed() {
        return assessed;
    }

    /**
     * @param assessed The assessed
     */
    public void setAssessed(int assessed) {
        this.assessed = assessed;
    }

    /**
     * @return The assesstimestart
     */
    public int getAssesstimestart() {
        return assesstimestart;
    }

    /**
     * @param assesstimestart The assesstimestart
     */
    public void setAssesstimestart(int assesstimestart) {
        this.assesstimestart = assesstimestart;
    }

    /**
     * @return The assesstimefinish
     */
    public int getAssesstimefinish() {
        return assesstimefinish;
    }

    /**
     * @param assesstimefinish The assesstimefinish
     */
    public void setAssesstimefinish(int assesstimefinish) {
        this.assesstimefinish = assesstimefinish;
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
     * @return The completiondiscussions
     */
    public int getCompletiondiscussions() {
        return completiondiscussions;
    }

    /**
     * @param completiondiscussions The completiondiscussions
     */
    public void setCompletiondiscussions(int completiondiscussions) {
        this.completiondiscussions = completiondiscussions;
    }

    /**
     * @return The completionreplies
     */
    public int getCompletionreplies() {
        return completionreplies;
    }

    /**
     * @param completionreplies The completionreplies
     */
    public void setCompletionreplies(int completionreplies) {
        this.completionreplies = completionreplies;
    }

    /**
     * @return The completionposts
     */
    public int getCompletionposts() {
        return completionposts;
    }

    /**
     * @param completionposts The completionposts
     */
    public void setCompletionposts(int completionposts) {
        this.completionposts = completionposts;
    }

    /**
     * @return The cmid
     */
    public int getCmid() {
        return cmid;
    }

    /**
     * @param cmid The cmid
     */
    public void setCmid(int cmid) {
        this.cmid = cmid;
    }

    /**
     * @return The numdiscussions
     */
    public int getNumdiscussions() {
        return numdiscussions;
    }

    /**
     * @param numdiscussions The numdiscussions
     */
    public void setNumdiscussions(int numdiscussions) {
        this.numdiscussions = numdiscussions;
    }

    /**
     * @return The cancreatediscussions
     */
    public Boolean getCancreatediscussions() {
        return cancreatediscussions;
    }

    /**
     * @param cancreatediscussions The cancreatediscussions
     */
    public void setCancreatediscussions(Boolean cancreatediscussions) {
        this.cancreatediscussions = cancreatediscussions;
    }
}

