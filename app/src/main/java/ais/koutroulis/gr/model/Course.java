package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("shortname")
    @Expose
    private String shortname;
    @SerializedName("timemodified")
    @Expose
    private int timemodified;
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = new ArrayList<Assignment>();

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
     * @return The fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname The fullname
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return The shortname
     */
    public String getShortname() {
        return shortname;
    }

    /**
     * @param shortname The shortname
     */
    public void setShortname(String shortname) {
        this.shortname = shortname;
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
     * @return The assignments
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * @param assignments The assignments
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

}
