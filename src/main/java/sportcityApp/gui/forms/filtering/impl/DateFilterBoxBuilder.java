package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.custom.ValidationInfo;
import sportcityApp.services.filters.DateFilter;
import sportcityApp.services.filters.Filter;

import java.time.ZoneId;
import java.util.Date;

public class DateFilterBoxBuilder extends AbstractFilterBoxBuilder/*<Organizer>*/{
    private DateFilter dateFilter;
    @Override
    protected void fillFilterBox(FilterBoxController/*<Organizer>*/ controller, Filter/*Filter<Organizer>*/ filter) {
        //DateFilter dateFilter = (DateFilter) filter;
        dateFilter = (DateFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(dateFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(dateFilter::setMaxPeriod, 6, 0, 2);
    }

    public ValidationInfo validate(){
        ValidationInfo info;
        if ( (dateFilter.getMinPeriod() == null & dateFilter.getMaxPeriod()!=null) | (dateFilter.getMaxPeriod() == null & dateFilter.getMinPeriod() != null) )
            info = new ValidationInfo(false, "Одно из значений периода не заполнено", "Заполните оба значения периода в фильтре или сбросьте, чтобы узнать подробную информацию");
        else if (dateFilter.getMinPeriod() != null & dateFilter.getMaxPeriod()!=null){
            if (dateFilter.getMinPeriod().after(dateFilter.getMaxPeriod()))
                info = new ValidationInfo(false, "Дата начала периода позже даты конца периода", "Введите корректные значения");
            else
                info = new ValidationInfo(true, "", "");
        }
        else
            info = new ValidationInfo(true, "", "");
        return info;
    }
}
