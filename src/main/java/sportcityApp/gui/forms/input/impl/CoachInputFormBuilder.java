package sportcityApp.gui.forms.input.impl;

import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.entities.Coach;
import sportcityApp.entities.types.Sport;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class CoachInputFormBuilder extends AbstractEntityInputFormBuilder<Coach> {

    public CoachInputFormBuilder(RequestExecutor requestExecutor){
        super(Coach::new, ServiceFactory.getCoachService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Coach coach, FormType formType, boolean isContextWindow, EntityInputFormController<Coach> controller) {
        controller.addTextField(
                "ФИО тренера",
                coach.getName(),
                coach::setName
                );
        controller.addChoiceBox(
                "Вид спорта",
                coach.getSport(),
                coach::setSport,
                null,
                Sport::getChoiceItems
                );
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить нового тренера";
    }

    @Override
    protected String getEditFormWindowTitle(Coach coach) {
        return String.format("Тренера %s - изменить", coach.getName());
    }
}
