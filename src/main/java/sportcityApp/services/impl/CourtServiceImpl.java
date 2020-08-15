package sportcityApp.services.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Court;
import sportcityApp.services.CourtService;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.SportFacilityService;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.impl.api.CourtServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.ServiceFactory;

public class CourtServiceImpl extends AbstractCrudServiceImpl<Court> implements CourtService {

    public CourtServiceImpl(String baseUrl){
        super(CourtServiceApi.class, Court.class, baseUrl, "court");
    }

    private SportFacilityService sportFacilityService = ServiceFactory.getSportFacilityService();

    @Override
    public ServiceResponse<Page<Court>> getPageWithCourtById(Integer id, PageInfo pageInfo) {
        var call = getServiceApi().getPageWithCourtById(id, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitionsOfTheCourt(Integer courtId, PageInfo pageInfo) {
        return sportFacilityService.getCompetitions(courtId, pageInfo);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromCourt(Integer courtId, Integer competitionId) {
        return sportFacilityService.removeCompetitionFromSportFacility(courtId, competitionId);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitionsOfTheCourtByFilter(CompetitionOfSFFilter filter, PageInfo pageInfo) {
        return sportFacilityService.getCompetitionsByFilter(filter, pageInfo);
    }

    private CourtServiceApi getServiceApi(){
        return (CourtServiceApi) getCrudServiceApi();
    }
}
