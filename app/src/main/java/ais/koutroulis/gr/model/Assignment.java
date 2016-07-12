package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("cmid")
    @Expose
    private int cmid;
    @SerializedName("course")
    @Expose
    private int course;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("duedate")
    @Expose
    private int duedate;
    @SerializedName("allowsubmissionsfromdate")
    @Expose
    private int allowsubmissionsfromdate;
    @SerializedName("grade")
    @Expose
    private int grade;
    @SerializedName("timemodified")
    @Expose
    private int timemodified;
    @SerializedName("completionsubmit")
    @Expose
    private int completionsubmit;
    @SerializedName("cutoffdate")
    @Expose
    private int cutoffdate;
    @SerializedName("intro")
    @Expose
    private String intro;
    @SerializedName("introformat")
    @Expose
    private int introformat;

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
     * @return The duedate
     */
    public int getDuedate() {
        return duedate;
    }

    /**
     * @param duedate The duedate
     */
    public void setDuedate(int duedate) {
        this.duedate = duedate;
    }

    /**
     * @return The allowsubmissionsfromdate
     */
    public int getAllowsubmissionsfromdate() {
        return allowsubmissionsfromdate;
    }

    /**
     * @param allowsubmissionsfromdate The allowsubmissionsfromdate
     */
    public void setAllowsubmissionsfromdate(int allowsubmissionsfromdate) {
        this.allowsubmissionsfromdate = allowsubmissionsfromdate;
    }

    /**
     * @return The grade
     */
    public int getGrade() {
        return grade;
    }

    /**
     * @param grade The grade
     */
    public void setGrade(int grade) {
        this.grade = grade;
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
     * @return The completionsubmit
     */
    public int getCompletionsubmit() {
        return completionsubmit;
    }

    /**
     * @param completionsubmit The completionsubmit
     */
    public void setCompletionsubmit(int completionsubmit) {
        this.completionsubmit = completionsubmit;
    }

    /**
     * @return The cutoffdate
     */
    public int getCutoffdate() {
        return cutoffdate;
    }

    /**
     * @param cutoffdate The cutoffdate
     */
    public void setCutoffdate(int cutoffdate) {
        this.cutoffdate = cutoffdate;
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
}
