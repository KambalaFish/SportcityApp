package sportcityApp.services;

import sportcityApp.entities.Ability;
import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface SportsmanService extends Service<Sportsman> {

    ServiceResponse<Page<Ability>> getAbilities(Integer sportsmanId, PageInfo pageInfo);

    ServiceResponse<Page<Coach>> getCoaches(Integer sportsmanId, PageInfo pageInfo);

    ServiceResponse<Page<Coach>> removeCoachFromSportsman(Integer sportsmanId, Integer coachId, PageInfo pageInfo);

}
