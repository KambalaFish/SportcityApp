package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.VolleyballArena;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class VolleyballArenaForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilder<Competition> {

    public VolleyballArenaForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить новую ледовую арену";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {
        Predicate<VolleyballArena> predicate = volleyballArena -> entity.getSportFacilities().stream().noneMatch(sportFacility -> sportFacility.getId().intValue() == volleyballArena.getId().intValue());

        ChoiceItemSupplier<VolleyballArena> volleyballArenaSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getVolleyballArenaService(),
                predicate,
                volleyballArena -> new ChoiceItem<>(volleyballArena, volleyballArena.getId().toString()),
                "Не удалось загрузить id воллейбольных арен"
        );
        controller.addChoiceBox("Волейбольная арена", null, entity::addNewVolleyballArena, entity::removeVolleyballArena, volleyballArenaSupplier);
    }
}
