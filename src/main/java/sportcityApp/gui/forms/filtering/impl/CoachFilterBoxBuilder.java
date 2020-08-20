package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Coach;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.CoachFilter;
import sportcityApp.services.filters.Filter;

public class CoachFilterBoxBuilder extends AbstractFilterBoxBuilder/*<Coach>*/{


    @Override
    protected void fillFilterBox(FilterBoxController/*<Coach>*/ controller, Filter/*Filter<Coach>*/ filter) {
        CoachFilter coachFilter = (CoachFilter) filter;
        controller.setNumberOfRows(1);
        controller.setNumberOfCols(6);

        controller.addLabel("Вид спорта:", 0, 0, 2);
        controller.addChoiceBox(coachFilter::setSport, Sport::getChoiceItems, 2, 0, 4);
    }
}
