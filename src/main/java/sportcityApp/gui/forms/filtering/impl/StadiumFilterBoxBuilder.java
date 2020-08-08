package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Stadium;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.filters.StadiumFilter;

public class StadiumFilterBoxBuilder extends AbstractFilterBoxBuilder<Stadium>{

    @Override
    protected void fillFilterBox(FilterBoxController<Stadium> controller, Filter<Stadium> filter) {
        StadiumFilter stadiumFilter = (StadiumFilter) filter;
        controller.setNumberOfRows(1);
        controller.setNumberOfCols(12);

        controller.addLabel("Вместимость(min):", 0, 0, 3);
        controller.addIntegerField(stadiumFilter::setMinCapacity, 3, 0, 3);
        controller.addLabel("Вместимость(max):", 6, 0, 3);
        controller.addIntegerField(stadiumFilter::setMaxCapacity, 9, 0, 3);
    }
}
