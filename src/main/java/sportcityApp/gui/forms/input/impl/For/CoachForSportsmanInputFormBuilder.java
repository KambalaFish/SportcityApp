package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Coach;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

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
        Predicate<Coach> predicate = coach -> {
            boolean firstCondition = entity.getAbilities().stream().anyMatch(ability -> ability.getSport() == coach.getSport());
            boolean secondCondition = entity.getCoaches().stream().noneMatch(coach1 -> coach1.getId().intValue() == coach.getId().intValue());
            return firstCondition & secondCondition;
        };


        ChoiceItemSupplier<Coach> coachSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCoachService(),
                predicate,
                c -> new ChoiceItem<>(c, c.getName()),
                "Не удалось загрузить список тренеров");
        controller.addChoiceBox("Тренер", null, entity::addNewCoach, entity::removeCoach, coachSupplier);
    }


}
