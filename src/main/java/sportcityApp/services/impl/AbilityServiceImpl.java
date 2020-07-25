package sportcityApp.services.impl;

import sportcityApp.entities.Ability;
import sportcityApp.services.AbilityService;
import sportcityApp.services.impl.api.AbilityServiceApi;

public class AbilityServiceImpl extends AbstractCrudServiceImpl<Ability> implements AbilityService {
    public AbilityServiceImpl(String baseUrl){
        super(AbilityServiceApi.class, Ability.class, baseUrl, "ability");
    }
}
