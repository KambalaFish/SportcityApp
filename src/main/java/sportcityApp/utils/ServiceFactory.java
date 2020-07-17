package sportcityApp.utils;

import lombok.experimental.UtilityClass;
import sportcityApp.AppProperties;
import sportcityApp.services.CoachService;
import sportcityApp.services.impl.CoachServiceImpl;

@UtilityClass
public class ServiceFactory {

    public CoachService getCoachService(){
        return new CoachServiceImpl(AppProperties.getServerHostname());
    }
}
