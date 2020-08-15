package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Stadium;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface StadiumService extends Service<Stadium>{

    ServiceResponse<Page<Stadium>> getPageWithStadiumById(Integer id, PageInfo pageInfo);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheStadium(Integer stadiumId, PageInfo pageInfo);

    ServiceResponse<Void> removeCompetitionFromStadium(Integer stadiumId, Integer competitionId);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheStadiumByFilter(CompetitionOfSFFilter filter, PageInfo pageInfo);
}
