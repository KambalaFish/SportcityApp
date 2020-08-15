package sportcityApp.services.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.SportFacility;
import sportcityApp.entities.VolleyballArena;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.SportFacilityService;
import sportcityApp.services.VolleyballArenaService;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.impl.api.VolleyballArenaServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.ServiceFactory;

public class VolleyballArenaServiceImpl extends AbstractCrudServiceImpl<VolleyballArena> implements VolleyballArenaService{

    public VolleyballArenaServiceImpl(String baseUrl){
        super(VolleyballArenaServiceApi.class, VolleyballArena.class, baseUrl, "volleyballArena");
    }

    private SportFacilityService sportFacilityService = ServiceFactory.getSportFacilityService();

    @Override
    public ServiceResponse<Page<VolleyballArena>> getPageWithVolleyballArenaById(Integer id, PageInfo pageInfo) {
        var call = getServiceApi().getPageWithVolleyballArenaById(id, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitionsOfTheVolleyballArena(Integer volleyballArenaId, PageInfo pageInfo) {
        return sportFacilityService.getCompetitions(volleyballArenaId, pageInfo);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromVolleyballArena(Integer volleyballArenaId, Integer competitionId) {
        return sportFacilityService.removeCompetitionFromSportFacility(volleyballArenaId, competitionId);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitionsOfTheVolleyballArenaByFilter(CompetitionOfSFFilter filter, PageInfo pageInfo) {
        return sportFacilityService.getCompetitionsByFilter(filter, pageInfo);
    }

    private VolleyballArenaServiceApi getServiceApi(){
        return (VolleyballArenaServiceApi) getCrudServiceApi();
    }
}
