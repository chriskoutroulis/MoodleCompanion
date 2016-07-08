package ais.koutroulis.gr.model;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Courses {

    @SerializedName("courses")
    @Expose
    private List<Course> courses = new ArrayList<Course>();
    @SerializedName("warnings")
    @Expose
    private List<Object> warnings = new ArrayList<Object>();

    /**
     *
     * @return
     * The courses
     */
    public List<Course> getCourses() {
        return courses;
    }

    /**
     *
     * @param courses
     * The courses
     */
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    /**
     *
     * @return
     * The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     *
     * @param warnings
     * The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

}