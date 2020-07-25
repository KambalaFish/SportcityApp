package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class CoachForSportsmanInputFormBuilder extends AbstractLinkingInputFormBuilder<Sportsman> {

    public CoachForSportsmanInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить тренера";
    }

    @Override
    protected void fillInputForm(Sportsman entity, EntityInputFormController<Sportsman> controller) {
        ChoiceItemSupplier<Coach> coachSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCoachService(),
                c -> new ChoiceItem<>(c, c.getName()),
                "Не удалось загрузить список тренеров");

        controller.addChoiceBox("Тренер", null, entity::addNewCoach/*coach -> entity.addNewCoach(coach)*/, entity::removeCoach, coachSupplier);
    }
}
