package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.SportFacility;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface SportFacilityService extends Service<SportFacility>{

    ServiceResponse<Page<Competition>> getCompetitions(Integer sportFacilityId, PageInfo pageInfo);

    ServiceResponse<Void> removeCompetitionFromSportFacility(Integer sportFacilityId, Integer competitionId);

    ServiceResponse<Integer> getLastIdNumber();

}
