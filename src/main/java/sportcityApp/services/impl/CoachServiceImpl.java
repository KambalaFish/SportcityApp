package sportcityApp.services.impl;

import sportcityApp.entities.Coach;
import sportcityApp.services.CoachService;
import sportcityApp.services.impl.api.CoachSeviceApi;

public class CoachServiceImpl extends AbstractCrudServiceImpl<Coach> implements CoachService {

    public CoachServiceImpl(String baseUrl){
        super(CoachSeviceApi.class, Coach.class, baseUrl, "coach");
    }

}
