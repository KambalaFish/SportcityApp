package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.filters.SportsmanFilter;

public class SportsmanFilterBoxBuilder extends AbstractFilterBoxBuilder<Sportsman>{

    @Override
    protected void fillFilterBox(FilterBoxController<Sportsman> controller, Filter<Sportsman> filter) {
        SportsmanFilter sportsmanFilter = (SportsmanFilter) filter;
        controller.setNumberOfRows(2);
        controller.setNumberOfCols(6);
        controller.addLabel("Вид спорта", 0, 0, 2);
        controller.addChoiceBox(sportsmanFilter::setSport, Sport::getChoiceItems, 2, 0, 4);

        controller.addLabel("Разряд(min):", 0, 1, 1);
        controller.addIntegerField(sportsmanFilter::setMinLevel, 1, 1, 2);
        controller.addLabel("Разряд(max)", 3, 1, 1);
        controller.addIntegerField(sportsmanFilter::setMaxLevel, 4, 1, 2);
    }
}
