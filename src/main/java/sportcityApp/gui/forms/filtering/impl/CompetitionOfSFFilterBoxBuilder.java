package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.filters.Filter;

public class CompetitionOfSFFilterBoxBuilder extends AbstractFilterBoxBuilder<Competition>{
    @Override
    protected void fillFilterBox(FilterBoxController<Competition> controller, Filter<Competition> filter) {
        CompetitionOfSFFilter competitionOfSFFilter = (CompetitionOfSFFilter) filter;
        controller.setNumberOfRows(1);
        controller.setNumberOfCols(5);

        controller.addLabel("Вид спорта:", 0, 0, 2);
        controller.addChoiceBox(competitionOfSFFilter::setSport, Sport::getChoiceItems, 2, 0, 3);
    }
}
