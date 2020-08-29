package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Consumer;

public class CompetitionInputFormBuilder extends AbstractEntityInputFormBuilder<Competition> {

    public CompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(Competition::new, ServiceFactory.getCompetitionService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Competition entity, FormType formType, boolean isContextWindow, EntityInputFormController<Competition> controller) {
        controller.addTextField(
                "Название",
                "",
                entity::setName

        );
        controller.addDateField(
                "Дата начала",
                entity.getBeginningDate(),
                entity::setBeginningDate

        );
        controller.addDateField(
                "Дата окончания",
                entity.getFinishDate(),
                entity::setFinishDate
                );
        /*нет смысла менять вид спорта у уже назначенного соревнования, поэтому поле появляется только при создании*/
        if (formType == FormType.CREATION_FORM)
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
