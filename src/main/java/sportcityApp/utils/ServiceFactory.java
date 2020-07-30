package sportcityApp.utils;

import lombok.experimental.UtilityClass;
import sportcityApp.AppProperties;
import sportcityApp.services.*;
import sportcityApp.services.impl.*;

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

    public CompetitionService getCompetitionService(){
        return new CompetitionServiceImpl(AppProperties.getServerHostname());
    }

    public OrganizerService getOrganizerService(){
        return new OrganizerServiceImpl(AppProperties.getServerHostname());
    }
}
