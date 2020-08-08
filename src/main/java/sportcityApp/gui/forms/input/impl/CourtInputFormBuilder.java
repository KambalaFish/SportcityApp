package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Court;
import sportcityApp.entities.types.CoverageType;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;
import sportcityApp.utils.SportFacilitySupplier;

public class CourtInputFormBuilder extends AbstractEntityInputFormBuilder<Court> {

    public CourtInputFormBuilder(RequestExecutor requestExecutor){
        super(SportFacilitySupplier.getCourtSupplier(), ServiceFactory.getCourtService(), requestExecutor);
    }


    @Override
    protected void fillInputForm(Court entity, FormType formType, boolean isContextWindow, EntityInputFormController<Court> controller) {
        controller.addChoiceBox(
                "Тип покрытия",
                entity.getCoverageType(),
                entity::setCoverageType,
                null,
                CoverageType::getChoiceItems
        );
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый корт";
    }

    @Override
    protected String getEditFormWindowTitle(Court entity) {
        return String.format("Изменить корт номер %s", entity.getId());
    }
}
