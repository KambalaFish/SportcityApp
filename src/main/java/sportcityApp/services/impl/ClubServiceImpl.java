package sportcityApp.services.impl;

import sportcityApp.entities.Club;
import sportcityApp.services.ClubService;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.filters.DateFilter;
import sportcityApp.services.impl.api.ClubServiceApi;

public class ClubServiceImpl extends AbstractCrudServiceImpl<Club> implements ClubService {

    public ClubServiceImpl(String baseUrl){
        super(ClubServiceApi.class, Club.class, baseUrl, "club");
    }

    @Override
    public ServiceResponse<Integer> getNumberOfSportsmanInTheClubDuringPeriod(Integer clubId, DateFilter filter) {
        System.out.println(gson.toJsonTree(filter));
        System.out.println(clubId);
        var call = getServiceApi().getNumberOfSportsmanInTheClubDuringPeriod(clubId, gson.toJsonTree(filter));
        return getServerResponse(call);
    }

    private ClubServiceApi getServiceApi(){
        return (ClubServiceApi) getCrudServiceApi();
    }
}
