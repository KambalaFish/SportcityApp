package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2M;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class SportsmanForCoachInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Sportsman, Coach>{

    public SportsmanForCoachInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить спортсмена";
    }

    @Override
    protected void fillInputForm(Coach entity, EntityInputFormController<Sportsman> controller){
        ChoiceItemSupplierForM2M<Sportsman, Coach> sportsmanForCoachSupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getSportsmanService(),
                sportsman -> new ChoiceItemForM2MOwned<>(sportsman, sportsman.getName(), sportsman::addNewCoach, sportsman::removeCoach),
                "не удалось загрузить список спортсменов"
        );
        controller.addChoiceBoxForM2MOwned("Спортсмен", null, entity,  sportsmanForCoachSupplier);
    }

}
