package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignments {

    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = new ArrayList<Assignment>();

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
