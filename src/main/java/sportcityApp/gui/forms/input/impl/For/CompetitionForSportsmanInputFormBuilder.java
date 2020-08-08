package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class CompetitionForSportsmanInputFormBuilder extends AbstractLinkingInputFormBuilder<Sportsman> {

    public CompetitionForSportsmanInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить соревнование";
    }

    @Override
    protected void fillInputForm(Sportsman entity, EntityInputFormController<Sportsman> controller) {
        Predicate<Competition> predicate = competition -> {
            boolean firstCondition = entity.getAbilities().stream().anyMatch(ability -> ability.getSport() == competition.getSport());
            boolean secondCondition = entity.getCompetitions().stream().noneMatch(competition1 -> competition1.getId().intValue() == competition.getId().intValue());
            return firstCondition & secondCondition;
        };

        ChoiceItemSupplier<Competition> competitionSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCompetitionService(),
                predicate,
                competition -> new ChoiceItem<>(competition, competition.getName()),
                "Не удалось загрузить список соревнований"
        );
        controller.addChoiceBox("Соревнование", null, entity::addNewCompetition, entity::removeCompetition, competitionSupplier);
    }
}
