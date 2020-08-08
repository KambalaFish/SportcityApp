package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Organizer;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilderForOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class CompetitionForOrganizerInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Competition, Organizer> {

    public CompetitionForOrganizerInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить соревнование";
    }

    @Override
    protected void fillInputForm(Organizer entity, EntityInputFormController<Competition> controller) {
        ChoiceItemSupplierForM2MOwned<Competition, Organizer> competitionForOrganizerSupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getCompetitionService(),
                competition -> new ChoiceItemForM2MOwned<>(competition, competition.getName(), competition::addNewOrganizer, competition::removeOrganizer),
                "Не удалось загрузить список соревнований"
        );
        controller.addChoiceBoxForM2MOwned("Соревнование", null, entity, competitionForOrganizerSupplier);
    }
}
