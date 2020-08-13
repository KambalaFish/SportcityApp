package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Stadium;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class StadiumForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilder<Competition> {

    public StadiumForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить новый стадион";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {
        Predicate<Stadium> predicate = stadium -> entity.getSportFacilities().stream().noneMatch(sportFacility -> sportFacility.getId().intValue()==stadium.getId().intValue());
        ChoiceItemSupplier<Stadium> stadiumSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getStadiumService(),
                predicate,
                stadium -> new ChoiceItem<>(stadium, stadium.getId().toString()),
                "Не удалось загрузить id стадионов"
        );
        controller.addChoiceBox("Стадион", null, entity::addNewStadium, entity::removeStadium, stadiumSupplier);
    }
}
