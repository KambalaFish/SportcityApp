package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.VolleyballArena;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface VolleyballArenaService extends Service<VolleyballArena>{
    ServiceResponse<Page<VolleyballArena>> getPageWithVolleyballArenaById(Integer id, PageInfo pageInfo);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheVolleyballArena(Integer volleyballArenaId, PageInfo pageInfo);

    ServiceResponse<Void> removeCompetitionFromVolleyballArena(Integer volleyballArenaId, Integer competitionId);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheVolleyballArenaByFilter(CompetitionOfSFFilter filter, PageInfo pageInfo);
}
