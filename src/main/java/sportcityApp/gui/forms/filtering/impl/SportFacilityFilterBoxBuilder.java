package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.custom.ValidationInfo;
import sportcityApp.services.filters.CompetitionOfSFFilter;
import sportcityApp.services.filters.Filter;

public class SportFacilityFilterBoxBuilder extends AbstractFilterBoxBuilder{
    private CompetitionOfSFFilter competitionOfSFFilter;
    @Override
    protected void fillFilterBox(FilterBoxController controller, Filter filter) {
        competitionOfSFFilter = (CompetitionOfSFFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(competitionOfSFFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(competitionOfSFFilter::setMaxPeriod, 6, 0, 2);
    }

    public ValidationInfo validate(){
        ValidationInfo info;
        if ( (competitionOfSFFilter.getMinPeriod() == null & competitionOfSFFilter.getMaxPeriod()!=null) | (competitionOfSFFilter.getMaxPeriod() == null & competitionOfSFFilter.getMinPeriod() != null) )
            info = new ValidationInfo(false, "Одно из значений периода не заполнено", "Заполните оба значения периода в фильтре или сбросьте, чтобы узнать подробную информацию");
        else if (competitionOfSFFilter.getMinPeriod() != null & competitionOfSFFilter.getMaxPeriod()!=null){
            if (competitionOfSFFilter.getMinPeriod().after(competitionOfSFFilter.getMaxPeriod()))
                info = new ValidationInfo(false, "Дата начала периода позже даты конца периода", "Введите корректные значения");
            else
                info = new ValidationInfo(true, "", "");
        }
        else
            info = new ValidationInfo(true, "", "");
        return info;
    }

}
