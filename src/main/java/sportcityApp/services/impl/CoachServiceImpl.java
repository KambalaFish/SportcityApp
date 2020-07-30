package sportcityApp.services.impl;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.services.CoachService;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.impl.api.CoachServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public class CoachServiceImpl extends AbstractCrudServiceImpl<Coach> implements CoachService {

    public CoachServiceImpl(String baseUrl){
        super(CoachServiceApi.class, Coach.class, baseUrl, "coach");
    }

    @Override
    public ServiceResponse<Page<Sportsman>> getSportsmen(Integer coachId, PageInfo pageInfo) {
        var call = getServiceApi().getSportsmen(coachId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeSportsmanFromCoach(Integer coachId, Integer sportsmanId) {
        var call = getServiceApi().removeSportsmanFromCoach(coachId, sportsmanId);
        return getServerResponse(call);
    }


    private CoachServiceApi getServiceApi(){
        return (CoachServiceApi) getCrudServiceApi();
    }
}
