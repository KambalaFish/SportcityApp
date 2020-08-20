package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Stadium;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.filters.StadiumFilter;

public class StadiumFilterBoxBuilder extends AbstractFilterBoxBuilder/*<Stadium>*/{

    @Override
    protected void fillFilterBox(FilterBoxController/*<Stadium>*/ controller, Filter/*Filter<Stadium>*/ filter) {
        StadiumFilter stadiumFilter = (StadiumFilter) filter;
        controller.setNumberOfRows(1);
        controller.setNumberOfCols(5);

        controller.addLabel("Вместимость:  от", 0, 0, 2);
        //controller.addLabel("от",2, 0, 1);
        controller.addIntegerField(stadiumFilter::setMinCapacity, 2, 0, 1);
        controller.addLabel("до:", 3, 0, 1);
        controller.addIntegerField(stadiumFilter::setMaxCapacity, 4, 0, 1);
    }
}
