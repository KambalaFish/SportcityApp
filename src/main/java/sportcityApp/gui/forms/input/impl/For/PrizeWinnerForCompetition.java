package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Organizer;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.Date;
import java.util.function.Predicate;

public class PrizeWinnerForCompetition extends AbstractLinkingInputFormBuilder<Competition> {

    public PrizeWinnerForCompetition(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить призера";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {

        Predicate<Sportsman> predicate = prizeWinner -> {
            boolean firstCondition = prizeWinner.getCompetitions().stream().anyMatch(
                    competition ->
                            (
                                    competition.getId().intValue() == entity.getId().intValue() &
                                    competition.getBeginningDate().before(new Date(System.currentTimeMillis()))
                            )
            );
            boolean secondCondition = prizeWinner.getWonCompetitions().stream().noneMatch(competition -> competition.getId().intValue() == entity.getId().intValue());
            return firstCondition & secondCondition;
        };


        ChoiceItemSupplier<Sportsman> prizeWinnerSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getSportsmanService(),
                predicate,
                prizeWinner -> new ChoiceItem<>(prizeWinner, prizeWinner.getName()),
                "Не удалось загрузить список организаторов"
        );
        controller.addChoiceBox("Призер", null, entity::addNewPrizeWinner, entity::removePrizeWinner, prizeWinnerSupplier);

    }
}
