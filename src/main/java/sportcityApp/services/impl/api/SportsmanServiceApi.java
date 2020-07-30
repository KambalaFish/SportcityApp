package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Ability;
import sportcityApp.entities.Coach;
import sportcityApp.entities.Competition;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface SportsmanServiceApi extends CrudServiceApi{

    @GET("/sportsman/{id}/abilities")
    Call<Page<Ability>> getAbilities(@Path("id") Integer sportsmanId, @QueryMap Map<String, Object> pageInfo);

    @GET("/sportsman/{id}/coaches")
    Call<Page<Coach>> getCoaches(@Path("id") Integer sportsmanId, @QueryMap Map<String, Object> pageInfo);

    @POST("/sportsman/{id}/removeCoach/{coachId}")
    Call<Void> removeCoachFromSportsman(@Path("id") Integer sportsmanId, @Path("coachId") Integer coachId);

    @GET("/sportsman/{id}/competitions")
    Call<Page<Competition>> getCompetitions(@Path("id") Integer sportsmanId, @QueryMap Map<String, Object> pageInfo);

    @POST("/sportsman/{id}/removeCompetition/{competitionId}")
    Call<Void> removeCompetitionFromSportsman(@Path("id") Integer sportsmanId, @Path("competitionId") Integer competitionId);
}
