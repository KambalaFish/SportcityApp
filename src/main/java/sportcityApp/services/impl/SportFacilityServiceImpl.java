package sportcityApp.services.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.SportFacility;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.SportFacilityService;
import sportcityApp.services.impl.api.SportFacilityServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public class SportFacilityServiceImpl extends AbstractCrudServiceImpl<SportFacility> implements SportFacilityService {

    public SportFacilityServiceImpl(String baseUrl){
        super(SportFacilityServiceApi.class, SportFacility.class, baseUrl, "sportFacility");
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitions(Integer sportFacilityId, PageInfo pageInfo) {
        var call = getServiceApi().getCompetitions(sportFacilityId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromSportFacility(Integer sportFacilityId, Integer competitionId) {
        var call = getServiceApi().removeCompetitionFromSportFacility(sportFacilityId, competitionId);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Integer> getLastIdNumber() {
        var call = getServiceApi().getLastIdNumber();
        return getServerResponse(call);
    }

    private SportFacilityServiceApi getServiceApi(){
        return (SportFacilityServiceApi) getCrudServiceApi();
    }
}
