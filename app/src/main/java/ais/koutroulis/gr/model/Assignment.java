package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cmid")
    @Expose
    private Integer cmid;
    @SerializedName("course")
    @Expose
    private Integer course;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("duedate")
    @Expose
    private Integer duedate;
    @SerializedName("allowsubmissionsfromdate")
    @Expose
    private Integer allowsubmissionsfromdate;
    @SerializedName("grade")
    @Expose
    private Integer grade;
    @SerializedName("timemodified")
    @Expose
    private Integer timemodified;
    @SerializedName("completionsubmit")
    @Expose
    private Integer completionsubmit;
    @SerializedName("cutoffdate")
    @Expose
    private Integer cutoffdate;
    @SerializedName("intro")
    @Expose
    private String intro;
    @SerializedName("introformat")
    @Expose
    private Integer introformat;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The cmid
     */
    public Integer getCmid() {
        return cmid;
    }

    /**
     * @param cmid The cmid
     */
    public void setCmid(Integer cmid) {
        this.cmid = cmid;
    }

    /**
     * @return The course
     */
    public Integer getCourse() {
        return course;
    }

    /**
     * @param course The course
     */
    public void setCourse(Integer course) {
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
    public Integer getDuedate() {
        return duedate;
    }

    /**
     * @param duedate The duedate
     */
    public void setDuedate(Integer duedate) {
        this.duedate = duedate;
    }

    /**
     * @return The allowsubmissionsfromdate
     */
    public Integer getAllowsubmissionsfromdate() {
        return allowsubmissionsfromdate;
    }

    /**
     * @param allowsubmissionsfromdate The allowsubmissionsfromdate
     */
    public void setAllowsubmissionsfromdate(Integer allowsubmissionsfromdate) {
        this.allowsubmissionsfromdate = allowsubmissionsfromdate;
    }

    /**
     * @return The grade
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade The grade
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * @return The timemodified
     */
    public Integer getTimemodified() {
        return timemodified;
    }

    /**
     * @param timemodified The timemodified
     */
    public void setTimemodified(Integer timemodified) {
        this.timemodified = timemodified;
    }

    /**
     * @return The completionsubmit
     */
    public Integer getCompletionsubmit() {
        return completionsubmit;
    }

    /**
     * @param completionsubmit The completionsubmit
     */
    public void setCompletionsubmit(Integer completionsubmit) {
        this.completionsubmit = completionsubmit;
    }

    /**
     * @return The cutoffdate
     */
    public Integer getCutoffdate() {
        return cutoffdate;
    }

    /**
     * @param cutoffdate The cutoffdate
     */
    public void setCutoffdate(Integer cutoffdate) {
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
    public Integer getIntroformat() {
        return introformat;
    }

    /**
     * @param introformat The introformat
     */
    public void setIntroformat(Integer introformat) {
        this.introformat = introformat;
    }
}
