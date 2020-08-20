package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.DateFilter;
import sportcityApp.services.filters.Filter;

public class DateFilterBoxBuilder extends AbstractFilterBoxBuilder/*<Organizer>*/{
    @Override
    protected void fillFilterBox(FilterBoxController/*<Organizer>*/ controller, Filter/*Filter<Organizer>*/ filter) {
        DateFilter organizerFilter = (DateFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(organizerFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(organizerFilter::setMaxPeriod, 6, 0, 2);
    }
}
