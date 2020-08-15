package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Court;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface CourtService extends Service<Court> {

    ServiceResponse<Page<Court>> getPageWithCourtById(Integer id, PageInfo pageInfo);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheCourt(Integer courtId, PageInfo pageInfo);

    ServiceResponse<Void> removeCompetitionFromCourt(Integer courtId, Integer competitionId);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheCourtByFilter(CompetitionOfSFFilter filter, PageInfo pageInfo);
}
