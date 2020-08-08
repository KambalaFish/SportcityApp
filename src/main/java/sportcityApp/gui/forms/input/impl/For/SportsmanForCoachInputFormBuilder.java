package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilderForOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class SportsmanForCoachInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Sportsman, Coach> {

    public SportsmanForCoachInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить спортсмена";
    }

    @Override
    protected void fillInputForm(Coach entity, EntityInputFormController<Sportsman> controller){
        Predicate<Sportsman> predicate = sportsman -> {
            boolean firstCondition = sportsman.getAbilities().stream().anyMatch(ability -> ability.getSport() == entity.getSport());
            boolean secondCondition = sportsman.getCoaches().stream().noneMatch( coach -> coach.getId().intValue() == entity.getId().intValue());
            return firstCondition & secondCondition;
        };


        ChoiceItemSupplierForM2MOwned<Sportsman, Coach> sportsmanForCoachSupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getSportsmanService(),
                predicate,
                sportsman -> new ChoiceItemForM2MOwned<>(sportsman, sportsman.getName(), sportsman::addNewCoach, sportsman::removeCoach),
                "не удалось загрузить список спортсменов"
        );
        controller.addChoiceBoxForM2MOwned("Спортсмен", null, entity,  sportsmanForCoachSupplier);
    }

}
