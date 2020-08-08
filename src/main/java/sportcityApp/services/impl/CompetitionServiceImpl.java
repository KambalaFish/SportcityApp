package sportcityApp.services.impl;

import sportcityApp.entities.*;
import sportcityApp.services.CompetitionService;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.impl.api.CompetitionServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public class CompetitionServiceImpl extends AbstractCrudServiceImpl<Competition> implements CompetitionService{
    public CompetitionServiceImpl(String baseUrl){
        super(CompetitionServiceApi.class, Competition.class, baseUrl, "competition");
    }

    @Override
    public ServiceResponse<Page<Sportsman>> getSportsmen(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getSportsmen(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeSportsmanFromCompetition(Integer competitionId, Integer sportsmanId) {
        var call = getServiceApi().removeSportsmanFromCompetition(competitionId, sportsmanId);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Organizer>> getOrganizers(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getOrganizers(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeOrganizerFromCompetition(Integer competitionId, Integer organizerId) {
        var call = getServiceApi().removeOrganizerFromCompetition(competitionId, organizerId);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<SportFacility>> getSportFacilities(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getSportFacilities(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeSportFacilityFromCompetition(Integer competitionId, Integer sportFacilityId) {
        var call = getServiceApi().removeSportFacilityFromCompetition(competitionId, sportFacilityId);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Court>> getCourts(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getCourts(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Stadium>> getStadiums(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getStadiums(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<IceArena>> getIceArenas(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getIceArenas(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<VolleyballArena>> getVolleyballArenas(Integer competitionId, PageInfo pageInfo) {
        var call = getServiceApi().getVolleyballArenas(competitionId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }


    private CompetitionServiceApi getServiceApi(){
        return (CompetitionServiceApi) getCrudServiceApi();
    }

}
