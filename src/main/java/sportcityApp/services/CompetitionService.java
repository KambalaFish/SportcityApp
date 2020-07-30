package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Organizer;
import sportcityApp.entities.Sportsman;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface CompetitionService extends Service<Competition>{

    ServiceResponse<Page<Sportsman>> getSportsmen(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Void> removeSportsmanFromCompetition(Integer competitionId, Integer sportsmanId);

    ServiceResponse<Page<Organizer>> getOrganizers(Integer competitionId, PageInfo pageInfo);

    ServiceResponse<Void> removeOrganizerFromCompetition(Integer competitionId, Integer organizerId);

}
