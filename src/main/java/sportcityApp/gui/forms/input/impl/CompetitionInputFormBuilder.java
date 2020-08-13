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
        controller.addChoiceBox(
                "Вид спорта",
                entity.getSport(),
                entity::setSport,
                null,
                Sport::getChoiceItems
        );
        /*то есть выходит, если что-то поменяли в соревновании, например вид спорта, то все связи со спортивными сооружениями и спортсменами удаляются*/
        if (isContextWindow) {
            Consumer<Competition> preparation = competition -> {
                competition.getSportFacilities().clear();
                competition.getSportsmen().clear();
            };
            controller.setPreparation(preparation);
        }
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
