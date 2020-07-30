package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class CompetitionForSportsmanInputFormBuilder extends AbstractLinkingInputFormBuilder<Sportsman>{

    public CompetitionForSportsmanInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getSportsmanService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить соревнование";
    }

    @Override
    protected void fillInputForm(Sportsman entity, EntityInputFormController<Sportsman> controller) {
        ChoiceItemSupplier<Competition> competitionSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCompetitionService(),
                competition -> new ChoiceItem<>(competition, competition.getName()),
                "Не удалось загрузить список соревнований"
        );
        controller.addChoiceBox("Соревнование", null, entity::addNewCompetition, entity::removeCompetition, competitionSupplier);
    }
}
