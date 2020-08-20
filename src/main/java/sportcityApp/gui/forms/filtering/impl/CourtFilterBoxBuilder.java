package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.types.CoverageType;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.CourtFilter;
import sportcityApp.services.filters.Filter;

public class CourtFilterBoxBuilder extends AbstractFilterBoxBuilder{

    @Override
    protected void fillFilterBox(FilterBoxController controller, Filter filter) {
        CourtFilter courtFilter = (CourtFilter) filter;
        controller.setNumberOfRows(1);
        controller.setNumberOfRows(6);

        controller.addLabel("Тип покрытия:", 0, 0, 2);
        controller.addChoiceBox(courtFilter::setCoverageType, CoverageType::getChoiceItems, 2, 0, 4);
    }
}
