package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.filters.Filter;

public class SportFacilityFilterBoxBuilder extends AbstractFilterBoxBuilder{
    @Override
    protected void fillFilterBox(FilterBoxController controller, Filter filter) {
        CompetitionOfSFFilter competitionOfSFFilter = (CompetitionOfSFFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(competitionOfSFFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(competitionOfSFFilter::setMaxPeriod, 6, 0, 2);
    }
}
