package ais.koutroulis.gr.service;

import ais.koutroulis.gr.model.Assignments;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Chris on 02-Jul-16.
 */
public interface MoodleRetroFitService {

    @GET("login/{script}")
    Call<Token> getToken(@Path("script") String script, @Query("username") String username, @Query("password") String password, @Query("service") String service);

    @GET("webservice/rest/{script}")
    Call<Courses> getCourses(@Path("script") String script, @Query("moodlewsrestformat") String format,
                                 @Query("wstoken") String token, @Query("wsfunction") String function);
}
