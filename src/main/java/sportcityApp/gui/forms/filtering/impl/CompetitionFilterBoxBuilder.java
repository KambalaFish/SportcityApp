package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Competition;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.services.filters.CompetitionFilter;
import sportcityApp.services.filters.Filter;
import sportcityApp.utils.ServiceFactory;

public class CompetitionFilterBoxBuilder extends AbstractFilterBoxBuilder<Competition>{
    @Override
    protected void fillFilterBox(FilterBoxController<Competition> controller, Filter<Competition> filter) {
        CompetitionFilter competitionFilter = (CompetitionFilter) filter;
        controller.setNumberOfRows(2);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(competitionFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(competitionFilter::setMaxPeriod, 6, 0, 2);


        ChoiceItemSupplier<Integer> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getOrganizerService(),
                organizer -> new ChoiceItem<>(organizer.getId(), organizer.getName()),
                "Не удалось загрузить организаторов"
                );

        controller.addLabel("Организатор:", 0, 1, 2);
        controller.addChoiceBox(competitionFilter::setOrganizerId, choiceItemSupplier, 2, 1, 3);
    }
}
