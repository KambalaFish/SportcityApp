package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class SportsmanForCoachInputFormBuilder extends AbstractLinkingInputFormBuilder<Coach>{

    public SportsmanForCoachInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCoachService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить спортсмена";
    }

    @Override
    protected void fillInputForm(Coach entity, EntityInputFormController<Coach> controller) {
        ChoiceItemSupplier<Sportsman> sportsmanSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getSportsmanService(),
                sportsman -> new ChoiceItem<>(sportsman, sportsman.getName()),
                "не удалось загрузить список спортсменов"
                );
        controller.addChoiceBox("Спортсмен", null, entity::addNewSportsman, entity::removeSportsman, sportsmanSupplier);
    }
}
