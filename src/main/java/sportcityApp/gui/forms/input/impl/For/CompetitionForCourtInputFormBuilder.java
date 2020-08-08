package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Court;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilderForOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class CompetitionForCourtInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Competition, Court> {

    public CompetitionForCourtInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить новое соревнование";
    }

    @Override
    protected void fillInputForm(Court entity, EntityInputFormController<Competition> controller) {

        Predicate<Competition> predicate = competition ->{
            if(competition.getSport() == Sport.tennis){
                boolean result;
                result = competition.getSportFacilities().stream().anyMatch(sportFacility -> sportFacility.getId().intValue() == entity.getId().intValue());
                return !result;
            }
            return false;
        };

        ChoiceItemSupplierForM2MOwned<Competition, Court> competitionForCourtSupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getCompetitionService(),
                predicate,
                competition -> new ChoiceItemForM2MOwned<>(competition, competition.getName(), competition::addNewCourt, competition::removeCourt),
                "Не удалось загрузить список соревнований"
                );
        controller.addChoiceBoxForM2MOwned("Соревнование", null, entity, competitionForCourtSupplier);
    }
}
