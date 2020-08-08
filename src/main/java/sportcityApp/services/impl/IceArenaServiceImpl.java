package sportcityApp.services.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.IceArena;
import sportcityApp.services.IceArenaService;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.SportFacilityService;
import sportcityApp.services.impl.api.IceArenaServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.ServiceFactory;

public class IceArenaServiceImpl extends AbstractCrudServiceImpl<IceArena> implements IceArenaService{

    public IceArenaServiceImpl(String baseUrl){
        super(IceArenaServiceApi.class, IceArena.class, baseUrl, "iceArena");
    }

    private SportFacilityService sportFacilityService = ServiceFactory.getSportFacilityService();

    @Override
    public ServiceResponse<Page<IceArena>> getPageWithIceArenaById(Integer id, PageInfo pageInfo) {
        var call = getServiceApi().getPageWithIceArenaById(id, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitionsOfTheIceArena(Integer iceArenaId, PageInfo pageInfo) {
        return sportFacilityService.getCompetitions(iceArenaId, pageInfo);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromIceArena(Integer iceArenaId, Integer competitionId) {
        return sportFacilityService.removeCompetitionFromSportFacility(iceArenaId, competitionId);
    }

    private IceArenaServiceApi getServiceApi(){
        return (IceArenaServiceApi) getCrudServiceApi();
    }
}
