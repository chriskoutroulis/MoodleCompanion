package ais.koutroulis.gr.service;

import ais.koutroulis.gr.model.Assignments;
import ais.koutroulis.gr.model.Courses;
import ais.koutroulis.gr.model.ForumByCourse;
import ais.koutroulis.gr.model.MarkAsReadResponse;
import ais.koutroulis.gr.model.Messages;
import ais.koutroulis.gr.model.Token;
import ais.koutroulis.gr.model.User;
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

    @GET("webservice/rest/{script}")
    Call<User> getUserDetails(@Path("script") String script, @Query("moodlewsrestformat") String format,
                              @Query("wstoken") String token, @Query("wsfunction") String function, @Query("field") String field,
                              @Query("values[0]") String username );

    @GET("webservice/rest/{script}")
    Call<Messages> getMessages(@Path("script") String script, @Query("moodlewsrestformat") String format,
                               @Query("wstoken") String token, @Query("wsfunction") String function, @Query("useridto") String sentToId,
                               @Query("useridfrom") String sentFromId, @Query("read") String oneForReadZeroForUnread);

    @GET("webservice/rest/{script}")
    Call<MarkAsReadResponse> markAsReadMessage (@Path("script") String script, @Query("moodlewsrestformat") String format,
                                                @Query("wstoken") String token, @Query("wsfunction") String function, @Query("messageid") String unreadMessageId,
                                                @Query("timeread") String timeReadInMillis);

    @GET("webservice/rest/{script}")
    Call<ForumByCourse> getForumByCourse (@Path("script") String script, @Query("moodlewsrestformat") String format,
                                          @Query("wstoken") String token, @Query("wsfunction") String function, @Query("courseids[0]") String courseId);

}
