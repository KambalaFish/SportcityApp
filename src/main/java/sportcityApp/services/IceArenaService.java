package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.IceArena;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface IceArenaService extends Service<IceArena>{
    ServiceResponse<Page<IceArena>> getPageWithIceArenaById(Integer id, PageInfo pageInfo);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheIceArena(Integer iceArenaId, PageInfo pageInfo);

    ServiceResponse<Void> removeCompetitionFromIceArena(Integer iceArenaId, Integer competitionId);

    ServiceResponse<Page<Competition>> getCompetitionsOfTheIceArenaByFilter(CompetitionOfSFFilter filter, PageInfo pageInfo);
}
