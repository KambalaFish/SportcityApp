package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Club;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.ClubFilter;
import sportcityApp.services.filters.Filter;

public class ClubFilterBoxBuilder extends AbstractFilterBoxBuilder<Club>{

    @Override
    protected void fillFilterBox(FilterBoxController<Club> controller, Filter<Club> filter) {
        ClubFilter clubFilter = (ClubFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(clubFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(clubFilter::setMaxPeriod, 6, 0, 2);
    }

}
