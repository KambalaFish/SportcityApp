package sportcityApp.services.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.SportFacility;
import sportcityApp.entities.Stadium;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.SportFacilityService;
import sportcityApp.services.StadiumService;
import sportcityApp.services.impl.api.StadiumServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.ServiceFactory;

public class StadiumServiceImpl extends AbstractCrudServiceImpl<Stadium> implements StadiumService {

    public StadiumServiceImpl(String baseUrl){
        super(StadiumServiceApi.class, Stadium.class, baseUrl, "stadium");
    }

    private SportFacilityService sportFacilityService = ServiceFactory.getSportFacilityService();

    @Override
    public ServiceResponse<Page<Stadium>> getPageWithStadiumById(Integer id, PageInfo pageInfo) {
        var call = getServiceApi().getPageWithStadiumById(id, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitionsOfTheStadium(Integer stadiumId, PageInfo pageInfo) {
        return sportFacilityService.getCompetitions(stadiumId, pageInfo);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromStadium(Integer stadiumId, Integer competitionId) {
        return sportFacilityService.removeCompetitionFromSportFacility(stadiumId, competitionId);
    }

    private StadiumServiceApi getServiceApi(){
        return (StadiumServiceApi) getCrudServiceApi();
    }
}
