package sportcityApp.services.impl;

import sportcityApp.entities.*;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.SportsmanService;
import sportcityApp.services.impl.api.SportsmanServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public class SportsmanServiceImpl extends AbstractCrudServiceImpl<Sportsman> implements SportsmanService {

    public SportsmanServiceImpl(String baseUrl){
        super(SportsmanServiceApi.class, Sportsman.class, baseUrl, "sportsman");
    }

    @Override
    public ServiceResponse<Page<Ability>> getAbilities(Integer sportsmanId, PageInfo pageInfo) {
        var call = getServiceApi().getAbilities(sportsmanId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Coach>> getCoaches(Integer sportsmanId, PageInfo pageInfo) {
        var call = getServiceApi().getCoaches(sportsmanId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeCoachFromSportsman(Integer sportsmanId, Integer coachId) {
        var call = getServiceApi().removeCoachFromSportsman(sportsmanId, coachId);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitions(Integer sportsmanId, PageInfo pageInfo){
        var call = getServiceApi().getCompetitions(sportsmanId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromSportsman(Integer sportsmanId, Integer competitionId){
        var call = getServiceApi().removeCompetitionFromSportsman(sportsmanId, competitionId);
        return getServerResponse(call);
    }


    private SportsmanServiceApi getServiceApi(){
        return (SportsmanServiceApi) getCrudServiceApi();
    }

}
