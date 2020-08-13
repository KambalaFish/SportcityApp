package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Court;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class CourtForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilder<Competition> {

    public CourtForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить новый корт";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {
        Predicate<Court> predicate = court -> entity.getSportFacilities().stream().noneMatch(sportFacility -> sportFacility.getId().intValue() == court.getId().intValue());
        ChoiceItemSupplier<Court> courtSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCourtService(),
                predicate,
                court -> new ChoiceItem<>(court, court.getId().toString()),
                "Не удалось загрузить id кортов"
        );
        controller.addChoiceBox("Корт", null, entity::addNewCourt, entity::removeCourt, courtSupplier);
    }
}
