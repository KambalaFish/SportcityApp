package sportcityApp.services.impl.api;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.*;
import sportcityApp.entities.Competition;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface OrganizerServiceApi extends CrudServiceApi{

    @GET("/organizer/{id}/competitions")
    Call<Page<Competition>> getCompetitions(@Path("id") Integer organizerId, @QueryMap Map<String, Object> pageInfo);

    @POST("/organizer/{id}/removeCompetition/{competitionId}")
    Call<Void> removeCompetitionFromOrganizer(@Path("id") Integer organizerId, @Path("competitionId") Integer competitionId);

    @POST("organizer/{id}/numberOfCompetitionForPeriod")
    Call<Integer> getNumberOfCompetitionForPeriod(@Path("id") Integer organizerId, @Body JsonElement filter);

}
