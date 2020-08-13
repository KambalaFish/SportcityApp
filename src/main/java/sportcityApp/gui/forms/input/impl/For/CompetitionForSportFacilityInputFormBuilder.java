package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.*;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilderForOwned;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class CompetitionForSportFacilityInputFormBuilder extends AbstractLinkingInputFormBuilderForOwned<Competition, SportFacility> {


    public CompetitionForSportFacilityInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить соревнование";
    }

    @Override
    protected void fillInputForm(SportFacility entity, EntityInputFormController<Competition> controller) {
        Predicate<Competition> predicate = competition -> {

            if (entity.getVolleyballArena()!=null & competition.getSport() == Sport.volleyball)
                return competition.getSportFacilities().stream().noneMatch(sportFacility ->  sportFacility.getId().intValue() == entity.getId().intValue());

            if (entity.getCourt()!=null & competition.getSport() == Sport.tennis)
                return competition.getSportFacilities().stream().noneMatch(sportFacility ->  sportFacility.getId().intValue() == entity.getId().intValue());

            if (entity.getIceArena()!=null & competition.getSport() == Sport.hockey)
                return competition.getSportFacilities().stream().noneMatch(sportFacility ->  sportFacility.getId().intValue() == entity.getId().intValue());

            if (entity.getStadium()!=null & competition.getSport()==Sport.football)
                return competition.getSportFacilities().stream().noneMatch(sportFacility ->  sportFacility.getId().intValue() == entity.getId().intValue());

            return false;
        };

        ChoiceItemSupplierForM2MOwned<Competition, SportFacility> competitionForSportFacilitySupplier = makeChoiceItemSupplierFromEntitiesForM2MOwned(
                ServiceFactory.getCompetitionService(),
                predicate,
                competition -> new ChoiceItemForM2MOwned<>(competition, competition.getName(), competition::addNewSportFacility, competition::removeSportFacility),
                "Не удалось загрузить список соревнований"
        );
        controller.addChoiceBoxForM2MOwned("Соревнование", null, entity, competitionForSportFacilitySupplier);
    }
}
