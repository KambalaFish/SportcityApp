package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Organizer;
import sportcityApp.entities.Sportsman;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface CompetitionServiceApi extends CrudServiceApi{

    @GET("/competition/{id}/sportsmen")
    Call<Page<Sportsman>> getSportsmen(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @POST("/competition/{id}/removeSportsman/{sportsmanId}")
    Call<Void> removeSportsmanFromCompetition(@Path("id") Integer competitionId, @Path("sportsmanId") Integer sportsmanId);

    @GET("/competition/{id}/organizers")
    Call<Page<Organizer>> getOrganizers(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @POST("/competition/{id}/removeOrganizer/{organizerId}")
    Call<Void> removeOrganizerFromCompetition(@Path("id") Integer competitionId, @Path("organizerId") Integer organizerId);
}
