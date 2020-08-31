package sportcityApp.services.impl.api;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.*;
import sportcityApp.entities.Competition;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface SportFacilityServiceApi extends CrudServiceApi{

    @GET("sportFacility/{id}/competitions")
    Call<Page<Competition>> getCompetitions(@Path("id") Integer sportFacilityId, @QueryMap Map<String, Object> pageInfo);

    @PUT("sportFacility/{id}/removeCompetition/{competitionId}")
    Call<Void> removeCompetitionFromSportFacility(@Path("id") Integer sportFacilityId, @Path("competitionId") Integer competitionId);

    @GET("sportFacility/lastIdNumber")
    Call<Integer> getLastIdNumber();

    @POST("sportFacility/competitionsByFilter")
    Call<Page<Competition>> getCompetitionsByFilter(@Body JsonElement filter, @QueryMap Map<String, Object> pageInfo);
}
