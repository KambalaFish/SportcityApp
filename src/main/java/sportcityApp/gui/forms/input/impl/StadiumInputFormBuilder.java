package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Stadium;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;
import sportcityApp.utils.SportFacilitySupplier;

public class StadiumInputFormBuilder extends AbstractEntityInputFormBuilder<Stadium>{

    public StadiumInputFormBuilder(RequestExecutor requestExecutor){
        super(SportFacilitySupplier.getStadiumSupplier(), ServiceFactory.getStadiumService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Stadium entity, FormType formType, boolean isContextWindow, EntityInputFormController<Stadium> controller) {
        controller.addIntegerField("Вместимость", 0, entity::setCapacity);
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый стадион";
    }

    @Override
    protected String getEditFormWindowTitle(Stadium entity) {
        return String.format("Изменить стадион номер %s",entity.getId());
    }
}
