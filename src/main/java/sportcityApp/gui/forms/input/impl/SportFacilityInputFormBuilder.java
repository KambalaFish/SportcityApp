package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.SportFacility;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;
import sportcityApp.utils.SportFacilitySupplier;

public class SportFacilityInputFormBuilder extends AbstractEntityInputFormBuilder<SportFacility>{

    public SportFacilityInputFormBuilder(RequestExecutor requestExecutor){
        super(SportFacilitySupplier.getSportFacilitySupplier(), ServiceFactory.getSportFacilityService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(SportFacility entity, FormType formType, boolean isContextWindow, EntityInputFormController<SportFacility> controller) {
        if (formType == FormType.CREATION_FORM)
            controller.addChoiceBoxForSportFacilityTypeCreation(entity);
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новое сооружение";
    }

    @Override
    protected String getEditFormWindowTitle(SportFacility entity) {
        return "Изменить id сооружения";
    }
}
