package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Competition;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface SportFacilityServiceApi extends CrudServiceApi{

    @GET("/sportFacility/{id}/competitions")
    Call<Page<Competition>> getCompetitions(@Path("id") Integer sportFacilityId, @QueryMap Map<String, Object> pageInfo);

    @PUT("/sportFacility/{id}/removeCompetition/{competitionId}")
    Call<Void> removeCompetitionFromSportFacility(@Path("id") Integer sportFacilityId, @Path("competitionId") Integer competitionId);

    @GET("/sportFacility/lastIdNumber")
    Call<Integer> getLastIdNumber();
}
