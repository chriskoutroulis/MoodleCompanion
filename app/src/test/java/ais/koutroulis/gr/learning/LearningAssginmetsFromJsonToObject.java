package ais.koutroulis.gr.learning;


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


import ais.koutroulis.gr.model.Assignment;
import ais.koutroulis.gr.model.Assignments;
import ais.koutroulis.gr.model.Course;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.service.MoodleRetroFitService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by c0nfr0ntier on 8/7/2016.
 */
public class LearningAssginmetsFromJsonToObject {
    private static final String BASE_URL = "http://ais-temp.daidalos.teipir.gr/moodle/";
    private static final String SCRIPT = "server.php";
    //Token for ais0058
    private static final String TOKEN = "2800aeb20f71838d9405768415096765";
    private static final String FORMAT = "json";
    private static final String FUNCTION = "mod_assign_get_assignments";

    private MoodleRetroFitService moodleService;
    private static final String SPECIFIC_ASSIGNMENT = "Εργασία για τα βιομηχανικά πρωτόκολλα.";


    @Before
    public void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        moodleService = retrofit.create(MoodleRetroFitService.class);
    }

    @Test
    public void shouldContainFourCourses() {
        Call<Courses> assginmentsCall = moodleService.getCourses(SCRIPT, FORMAT, TOKEN, FUNCTION);
        //Synchronous...
        try {
            Response<Courses> response = assginmentsCall.execute();
            assertEquals("The status code is not 200.", 200, response.code());

            assertEquals("The courses list size was not 4", 4,response.body().getCourses().size());
        } catch (IOException ie) {
            Assert.fail("Network error.");
        }
    }

    @Test
    public void shouldContainSpecificAssignment() {
        Call<Courses> assginmentsCall = moodleService.getCourses(SCRIPT, FORMAT, TOKEN, FUNCTION);

        //Synchronous...
        try {
            Response<Courses> response = assginmentsCall.execute();
            assertEquals("The status code is not 200.", 200, response.code());

         List<Course> courses = response.body().getCourses();
            boolean found = false;
            for(Course oneCourse : courses){
                for (Assignment oneAssignment : oneCourse.getAssignments()) {
                    if (oneAssignment.getName().equals(SPECIFIC_ASSIGNMENT)) {
                        found = true;
                    }
                }
            }
            assertTrue("The expected assignment was not found.", found);
        } catch (IOException ie) {
            Assert.fail("Network error.");
        }
    }

}

