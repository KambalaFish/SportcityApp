package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.IceArena;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class IceArenaForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilder<Competition> {

    public IceArenaForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить новую ледовую арену";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {

        Predicate<IceArena> predicate = iceArena -> {
            boolean result;
            result = entity.getSportFacilities().stream().anyMatch(sportFacility -> sportFacility.getId().intValue()==iceArena.getId().intValue());
            return !result;
        };

        ChoiceItemSupplier<IceArena> iceArenaSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getIceArenaService(),
                predicate,
                iceArena -> new ChoiceItem<>(iceArena, iceArena.getId().toString()),
                "Не удалось загрузить id ледовых арен"
        );
        controller.addChoiceBox("Ледовая арена", null, entity::addNewIceArena, entity::removeIceArena, iceArenaSupplier);

    }
}
