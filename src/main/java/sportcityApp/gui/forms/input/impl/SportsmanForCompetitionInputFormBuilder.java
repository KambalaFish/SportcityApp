package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2M;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class SportsmanForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Sportsman, Competition>{

    public SportsmanForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить спортсмена";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Sportsman> controller) {
        ChoiceItemSupplierForM2M<Sportsman, Competition> sportsmanForCompetitionSupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getSportsmanService(),
                sportsman -> new ChoiceItemForM2MOwned<>(sportsman, sportsman.getName(), sportsman::addNewCompetition, sportsman::removeCompetition),
                "Не удалось загрузить список спортсменов"
        );
        controller.addChoiceBoxForM2MOwned("Спортсмен", null, entity, sportsmanForCompetitionSupplier);
    }
}
