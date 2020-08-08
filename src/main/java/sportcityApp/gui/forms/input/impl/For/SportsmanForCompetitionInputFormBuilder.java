package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilderForOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class SportsmanForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Sportsman, Competition> {

    public SportsmanForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить спортсмена";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Sportsman> controller) {
        Predicate<Sportsman> predicate = sportsman -> {
            boolean firstCondition = sportsman.getAbilities().stream().anyMatch(ability -> ability.getSport() == entity.getSport());
            boolean secondCondition = entity.getSportsmen().stream().noneMatch(sportsman1 -> sportsman1.getId().intValue() == sportsman.getId().intValue());
            return firstCondition & secondCondition;
        };

        ChoiceItemSupplierForM2MOwned<Sportsman, Competition> sportsmanForCompetitionSupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getSportsmanService(),
                predicate,
                sportsman -> new ChoiceItemForM2MOwned<>(sportsman, sportsman.getName(), sportsman::addNewCompetition, sportsman::removeCompetition),
                "Не удалось загрузить список спортсменов"
        );
        controller.addChoiceBoxForM2MOwned("Спортсмен", null, entity, sportsmanForCompetitionSupplier);
    }
}
