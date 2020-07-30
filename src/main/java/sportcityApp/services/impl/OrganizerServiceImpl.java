package sportcityApp.services.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Organizer;
import sportcityApp.services.OrganizerService;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.impl.api.OrganizerServiceApi;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public class OrganizerServiceImpl extends AbstractCrudServiceImpl<Organizer> implements OrganizerService {
    public OrganizerServiceImpl(String baseUrl){
        super(OrganizerServiceApi.class, Organizer.class, baseUrl, "organizer");
    }

    @Override
    public ServiceResponse<Page<Competition>> getCompetitions(Integer organizerId, PageInfo pageInfo) {
        var call = getServiceApi().getCompetitions(organizerId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> removeCompetitionFromOrganizer(Integer organizerId, Integer competitionId) {
        var call = getServiceApi().removeCompetitionFromOrganizer(organizerId, competitionId);
        return getServerResponse(call);
    }

    private OrganizerServiceApi getServiceApi(){
        return (OrganizerServiceApi) getCrudServiceApi();
    }
}
