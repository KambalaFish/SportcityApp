package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.filters.VolleyballArenaFilter;

public class VolleyballArenaFilterBoxBuilder extends AbstractFilterBoxBuilder{
    @Override
    protected void fillFilterBox(FilterBoxController controller, Filter filter) {
        VolleyballArenaFilter volleyballArenaFilter = (VolleyballArenaFilter) filter;
        controller.setNumberOfRows(2);
        controller.setNumberOfCols(5);

        controller.addLabel("Высота сетки:  от", 0, 0, 2);
        //controller.addLabel("от",2, 0, 1);
        controller.addDoubleField(volleyballArenaFilter::setMinNetHeight, 2, 0, 1);
        controller.addLabel("до:", 3, 0, 1);
        controller.addDoubleField(volleyballArenaFilter::setMaxNetHeight, 4, 0, 1);

        controller.addLabel("Длина сетки:  от", 0, 1, 2);
        //controller.addLabel("от",2, 0, 1);
        controller.addDoubleField(volleyballArenaFilter::setMinNetWidth, 2, 1, 1);
        controller.addLabel("до:", 3, 1, 1);
        controller.addDoubleField(volleyballArenaFilter::setMaxNetWidth, 4, 1, 1);
    }
}
