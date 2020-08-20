package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.IceArena;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;
import sportcityApp.utils.SportFacilitySupplier;

public class IceArenaInputFormBuilder extends AbstractEntityInputFormBuilder<IceArena>{

    public IceArenaInputFormBuilder(RequestExecutor requestExecutor){
        super(SportFacilitySupplier.getIceArenaSupplier(), ServiceFactory.getIceArenaService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(IceArena entity, FormType formType, boolean isContextWindow, EntityInputFormController<IceArena> controller) {
        controller.addDoubleField("Площадь", 0d, entity::setSquare);
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новую ледовую арену";
    }

    @Override
    protected String getEditFormWindowTitle(IceArena entity) {
        return String.format("Изменить ледовую арену номер %s", entity.getId());
    }
}
