package sportcityApp.services;

import sportcityApp.entities.*;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface CompetitionService extends Service<Competition>{

    ServiceResponse<Page<Sportsman>> getSportsmen(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Void> removeSportsmanFromCompetition(Integer competitionId, Integer sportsmanId);

    ServiceResponse<Page<Organizer>> getOrganizers(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Void> removeOrganizerFromCompetition(Integer competitionId, Integer organizerId);

    ServiceResponse<Page<SportFacility>> getSportFacilities(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Void> removeSportFacilityFromCompetition(Integer competitionId, Integer sportFacilityId);

    ServiceResponse<Page<Court>> getCourts(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Page<Stadium>> getStadiums(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Page<IceArena>> getIceArenas(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Page<VolleyballArena>> getVolleyballArenas(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Page<Sportsman>> getWinners(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Void> removePrizeWinnerFromCompetition(Integer competitionId, Integer prizeWinnerId);
}
