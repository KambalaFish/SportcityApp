package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Competition;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.custom.ValidationInfo;
import sportcityApp.services.filters.CompetitionFilter;
import sportcityApp.services.filters.Filter;
import sportcityApp.utils.ServiceFactory;

public class CompetitionFilterBoxBuilder extends AbstractFilterBoxBuilder/*<Competition>*/{

    private CompetitionFilter competitionFilter;

    @Override
    protected void fillFilterBox(FilterBoxController/*<Competition>*/ controller, Filter/*Filter<Competition>*/ filter) {
        competitionFilter = (CompetitionFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(8);

        controller.addLabel("Период проведения:", 0, 0, 2);
        controller.addLabel("от", 2, 0, 1);
        controller.addDateField(competitionFilter::setMinPeriod, 3, 0, 2);
        controller.addLabel("до", 5, 0, 1);
        controller.addDateField(competitionFilter::setMaxPeriod, 6, 0, 2);


        ChoiceItemSupplier<Integer> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getOrganizerService(),
                organizer -> new ChoiceItem<>(organizer.getId(), organizer.getName()),
                "Не удалось загрузить организаторов"
                );

        controller.addLabel("Организатор:", 0, 1, 2);
        controller.addChoiceBox(competitionFilter::setOrganizerId, choiceItemSupplier, 2, 1, 3);

        controller.addLabel("Вид спорта:", 0, 2, 2);
        controller.addChoiceBox(competitionFilter::setSport, Sport::getChoiceItems, 2, 2, 3);
    }

    public ValidationInfo validate(){
        ValidationInfo info;
        if ( (competitionFilter.getMinPeriod() == null & competitionFilter.getMaxPeriod()!=null) | (competitionFilter.getMaxPeriod() == null & competitionFilter.getMinPeriod() != null) )
            info = new ValidationInfo(false, "Одно из значений периода не заполнено", "Заполните оба значения периода в фильтре или сбросьте, чтобы обновить");
        else if (competitionFilter.getMinPeriod() != null & competitionFilter.getMaxPeriod()!=null){
            if (competitionFilter.getMinPeriod().after(competitionFilter.getMaxPeriod()))
                info = new ValidationInfo(false, "Дата начала периода позже даты конца периода", "Введите корректные значения");
            else
                info = new ValidationInfo(true, "", "");
        }
        else
            info = new ValidationInfo(true, "", "");
        return info;
    }
}
