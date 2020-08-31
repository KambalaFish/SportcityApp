package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.*;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface CompetitionServiceApi extends CrudServiceApi{

    @GET("competition/{id}/sportsmen")
    Call<Page<Sportsman>> getSportsmen(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @POST("competition/{id}/removeSportsman/{sportsmanId}")
    Call<Void> removeSportsmanFromCompetition(@Path("id") Integer competitionId, @Path("sportsmanId") Integer sportsmanId);

    @GET("competition/{id}/organizers")
    Call<Page<Organizer>> getOrganizers(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @POST("competition/{id}/removeOrganizer/{organizerId}")
    Call<Void> removeOrganizerFromCompetition(@Path("id") Integer competitionId, @Path("organizerId") Integer organizerId);

    @GET("competition/{id}/sportFacilities")
    Call<Page<SportFacility>> getSportFacilities(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @POST("competition/{id}/removeSportFacility/{sportFacilityId}")
    Call<Void> removeSportFacilityFromCompetition(@Path("id") Integer competitionId, @Path("sportFacilityId") Integer sportFacilityId);

    @GET("competition/{id}/courts")
    Call<Page<Court>> getCourts(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @GET("competition/{id}/stadiums")
    Call<Page<Stadium>> getStadiums(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @GET("competition/{id}/iceArenas")
    Call<Page<IceArena>> getIceArenas(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @GET("competition/{id}/volleyballArenas")
    Call<Page<VolleyballArena>> getVolleyballArenas(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @GET("competition/{id}/prizeWinners")
    Call<Page<Sportsman>> getWinners(@Path("id") Integer competitionId, @QueryMap Map<String, Object> pageInfo);

    @POST("competition/{id}/removePrizeWinner/{prizeWinnerId}")
    Call<Void> removePrizeWinnerFromCompetition(@Path("id") Integer competitionId, @Path("prizeWinnerId") Integer prizeWinnerId);
}
