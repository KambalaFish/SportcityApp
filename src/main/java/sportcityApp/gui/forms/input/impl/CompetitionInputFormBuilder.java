package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class CompetitionInputFormBuilder extends AbstractEntityInputFormBuilder<Competition> {

    public CompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(Competition::new, ServiceFactory.getCompetitionService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Competition entity, FormType formType, boolean isContextWindow, EntityInputFormController<Competition> controller) {
        controller.addDateField(
                "Дата проведения",
                entity.getDate(),
                entity::setDate
        );
        controller.addChoiceBox(
                "Вид спорта",
                entity.getSport(),
                entity::setSport,
                null,
                Sport::getChoiceItems
        );
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новое соревнование";
    }

    @Override
    protected String getEditFormWindowTitle(Competition entity) {
        return String.format("изменить соревнование");
    }
}
