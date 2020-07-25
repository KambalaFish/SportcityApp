package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Ability;
import sportcityApp.entities.Coach;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface SportsmanServiceApi extends CrudServiceApi{

    @GET("/sportsman/{id}/abilities")
    Call<Page<Ability>> getAbilities(@Path("id") Integer sportsmanId, @QueryMap Map<String, Object> pageInfo);

    @GET("/sportsman/{id}/coaches")
    Call<Page<Coach>> getCoaches(@Path("id") Integer sportsmanId, @QueryMap Map<String, Object> pageInfo);

    @POST("/sportsman/{id}/removeCoach/{coachId}")
    Call<Page<Coach>> removeCoachFromSportsman(@Path("id") Integer sportsmanId, @Path("coachId") Integer coachId, @QueryMap Map<String, Object> pageInfo);


}
