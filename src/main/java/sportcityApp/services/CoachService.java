package sportcityApp.services;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface CoachService extends Service<Coach>{

    ServiceResponse<Page<Sportsman>> getSportsmen(Integer coachId, PageInfo pageInfo);

    ServiceResponse<Void> removeSportsmanFromCoach(Integer coachId, Integer sportsmanId);

}
