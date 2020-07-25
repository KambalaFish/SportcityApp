package sportcityApp.utils;

import lombok.experimental.UtilityClass;
import sportcityApp.AppProperties;
import sportcityApp.services.AbilityService;
import sportcityApp.services.CoachService;
import sportcityApp.services.SportsmanService;
import sportcityApp.services.impl.AbilityServiceImpl;
import sportcityApp.services.impl.CoachServiceImpl;
import sportcityApp.services.impl.SportsmanServiceImpl;

@UtilityClass
public class ServiceFactory {

    public CoachService getCoachService(){
        return new CoachServiceImpl(AppProperties.getServerHostname());
    }

    public SportsmanService getSportsmanService(){
        return new SportsmanServiceImpl(AppProperties.getServerHostname());
    }

    public AbilityService getAbilityService(){
        return new AbilityServiceImpl(AppProperties.getServerHostname());
    }
}
