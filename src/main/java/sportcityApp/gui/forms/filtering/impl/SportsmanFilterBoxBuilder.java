package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Club;
import sportcityApp.entities.Entity;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.custom.ValidationInfo;
import sportcityApp.services.Service;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.filters.SportsmanFilter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.ServiceFactory;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SportsmanFilterBoxBuilder extends AbstractFilterBoxBuilder/*<Sportsman>*/{
    SportsmanFilter sportsmanFilter;
    @Override
    protected void fillFilterBox(FilterBoxController/*<Sportsman>*/ controller, Filter/*Filter<Sportsman>*/ filter) {
        sportsmanFilter = (SportsmanFilter) filter;


        controller.setNumberOfRows(6);
        controller.setNumberOfCols(9);
        //controller.setNumberOfCols(6);
        int rowIndex = 0;
        controller.addLabel("ФИО спортсмена:", 0, rowIndex, 2);
        controller.addTextField(sportsmanFilter::setName, 2, rowIndex, 4);

        controller.addLabel("Клуб:", 6, rowIndex, 1);
        ChoiceItemSupplier<Integer> clubSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getClubService(),
                club -> new ChoiceItem<>(club.getId(), club.getName()),
                "Не удалось загрузить клубы"
        );
        controller.addChoiceBox(sportsmanFilter::setClubId, clubSupplier, 7, rowIndex, 2);

        rowIndex++;

        controller.addLabel("Минимальный разряд:", 0, rowIndex, 2);
        controller.addIntegerField(sportsmanFilter::setMinLevel, 2, rowIndex, 1);
        controller.addLabel("Максимальный разряд:", 3, rowIndex, 3);
        controller.addIntegerField(sportsmanFilter::setMaxLevel, 5, rowIndex, 1);

        rowIndex++;

        ChoiceItemSupplier<Integer> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCoachService(),
                coach -> new ChoiceItem<>(coach.getId(), coach.getName()),
                "Не удалось загрузить тренеров"
        );
        controller.addLabel("Тренер:", 0, rowIndex, 1);
        controller.addChoiceBox(sportsmanFilter::setCoachId, choiceItemSupplier, 1, rowIndex, 3);

        /*
        controller.addLabel("Показать спортсменов, занимающиеся более двумя видами спорта:", 0, 3, 6);
        controller.addCheckBox("",sportsmanFilter::setSportsmenWithOverOneSport, 6, 3, 1);
        */
        rowIndex++;
        controller.addLabel("Виды спорта:", 0, rowIndex, 2);
        controller.addCheckBox("футбол", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.football, 2, rowIndex, 1);
        controller.addCheckBox("теннис", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.tennis, 3, rowIndex, 1);
        controller.addCheckBox("воллейбол", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.volleyball, 4, rowIndex, 2);
        rowIndex++;
        controller.addCheckBox("хоккей", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.hockey, 2, rowIndex, 1);
        controller.addCheckBox("легкая атлетика", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.athletics, 3, rowIndex, 2);
        controller.addCheckBox("фигурное катание", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.figureSkating, 5, rowIndex, 2);
        rowIndex++;
        controller.addLabel("Не учавствовал в период:", 0, rowIndex, 3);
        controller.addLabel("от", 3, rowIndex, 1);
        controller.addDateField(sportsmanFilter::setMinPeriod, 4, rowIndex, 2);
        controller.addLabel("до", 6, rowIndex, 1);
        controller.addDateField(sportsmanFilter::setMaxPeriod, 7, rowIndex, 2);
    }

    public ValidationInfo validate(){
        ValidationInfo info;
        if ( (sportsmanFilter.getMinPeriod() == null & sportsmanFilter.getMaxPeriod()!=null) | (sportsmanFilter.getMaxPeriod() == null & sportsmanFilter.getMinPeriod() != null) )
            info = new ValidationInfo(false, "Одно из значений периода не заполнено", "Заполните оба значения периода в фильтре или сбросьте, чтобы обновить");
        else if (sportsmanFilter.getMinPeriod() != null & sportsmanFilter.getMaxPeriod()!=null){
            if (sportsmanFilter.getMinPeriod().after(sportsmanFilter.getMaxPeriod()))
                info = new ValidationInfo(false, "Дата начала периода позже даты конца периода", "Введите корректные значения");
            else
                info = new ValidationInfo(true, "", "");
        }
        else
            info = new ValidationInfo(true, "", "");
        return info;
    }

}
