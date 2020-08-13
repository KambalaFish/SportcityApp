package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Organizer;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class OrganizerForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilder<Competition> {

    public OrganizerForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить организатора";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {
        Predicate<Organizer> predicate = organizer -> organizer.getCompetitions().stream().noneMatch(competition -> competition.getId().intValue() == entity.getId().intValue());

        ChoiceItemSupplier<Organizer> organizerSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getOrganizerService(),
                predicate,
                organizer -> new ChoiceItem<>(organizer, organizer.getName()),
                "Не удалось загрузить список организаторов"
        );

        controller.addChoiceBox("Организатор", null, entity::addNewOrganizer, entity::removeOrganizer, organizerSupplier);
    }
}
