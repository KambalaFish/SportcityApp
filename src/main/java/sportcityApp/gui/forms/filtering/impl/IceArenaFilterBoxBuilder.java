package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.filters.IceArenaFilter;

public class IceArenaFilterBoxBuilder extends AbstractFilterBoxBuilder{
    @Override
    protected void fillFilterBox(FilterBoxController controller, Filter filter) {
        IceArenaFilter iceArenaFilter = (IceArenaFilter) filter;

        controller.setNumberOfRows(1);
        controller.setNumberOfCols(5);

        controller.addLabel("Площадь:  от", 0, 0, 2);
        //controller.addLabel("от",2, 0, 1);
        controller.addDoubleField(iceArenaFilter::setMinSquare, 2, 0, 1);
        controller.addLabel("до:", 3, 0, 1);
        controller.addDoubleField(iceArenaFilter::setMaxSquare, 4, 0, 1);
    }
}
