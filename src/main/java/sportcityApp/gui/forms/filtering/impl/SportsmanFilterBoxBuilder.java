package sportcityApp.gui.forms.filtering.impl;

import sportcityApp.entities.Entity;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
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

    @Override
    protected void fillFilterBox(FilterBoxController/*<Sportsman>*/ controller, Filter/*Filter<Sportsman>*/ filter) {
        SportsmanFilter sportsmanFilter = (SportsmanFilter) filter;
        controller.setNumberOfRows(6);
        controller.setNumberOfCols(9);
        //controller.setNumberOfCols(6);
        controller.addLabel("Вид спорта:", 0, 0, 2);
        controller.addChoiceBox(sportsmanFilter::setSport, Sport::getChoiceItems, 1, 0, 3);

        controller.addLabel("Минимальный разряд:", 0, 1, 2);
        controller.addIntegerField(sportsmanFilter::setMinLevel, 2, 1, 1);
        controller.addLabel("Максимальный разряд:", 3, 1, 3);
        controller.addIntegerField(sportsmanFilter::setMaxLevel, 5, 1, 1);

        ChoiceItemSupplier<Integer> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCoachService(),
                coach -> new ChoiceItem<>(coach.getId(), coach.getName()),
                "Не удалось загрузить тренеров"
        );
        controller.addLabel("Тренер:", 0, 2, 1);
        controller.addChoiceBox(sportsmanFilter::setCoachId, choiceItemSupplier, 1, 2, 3);

        controller.addLabel("Виды спорта:", 0, 3, 2);
        controller.addCheckBox("футбол", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.football, 2, 3, 1);
        controller.addCheckBox("теннис", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.tennis, 3, 3, 1);
        controller.addCheckBox("воллейбол", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.volleyball, 4, 3, 2);
        controller.addCheckBox("хоккей", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.hockey, 2, 4, 1);
        controller.addCheckBox("легкая атлетика", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.athletics, 3, 4, 2);
        controller.addCheckBox("фигурное катание", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.figureSkating, 5, 4, 2);

        controller.addLabel("Не учавствовал в период:", 0, 5, 3);
        controller.addLabel("от", 3, 5, 1);
        controller.addDateField(sportsmanFilter::setMinPeriod, 4, 5, 2);
        controller.addLabel("до", 6, 5, 1);
        controller.addDateField(sportsmanFilter::setMaxPeriod, 7, 5, 2);
    }

}
