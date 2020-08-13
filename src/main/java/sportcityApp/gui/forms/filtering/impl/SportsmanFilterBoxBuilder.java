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

public class SportsmanFilterBoxBuilder extends AbstractFilterBoxBuilder<Sportsman>{

    @Override
    protected void fillFilterBox(FilterBoxController<Sportsman> controller, Filter<Sportsman> filter) {
        SportsmanFilter sportsmanFilter = (SportsmanFilter) filter;
        controller.setNumberOfRows(4);
        controller.setNumberOfCols(8);
        //controller.setNumberOfCols(6);
        controller.addLabel("Вид спорта", 0, 0, 2);
        controller.addChoiceBox(sportsmanFilter::setSport, Sport::getChoiceItems, 2, 0, 4);

        controller.addLabel("Разряд(min):", 0, 1, 1);
        controller.addIntegerField(sportsmanFilter::setMinLevel, 1, 1, 2);
        controller.addLabel("Разряд(max)", 3, 1, 1);
        controller.addIntegerField(sportsmanFilter::setMaxLevel, 4, 1, 2);

        ChoiceItemSupplier<Integer> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCoachService(),
                coach -> new ChoiceItem<>(coach.getId(), coach.getName()),
                "Не удалось загрузить тренеров"
        );
        controller.addLabel("Тренер:", 0, 2, 2);
        controller.addChoiceBox(sportsmanFilter::setCoachId, choiceItemSupplier, 2, 2, 4);

        controller.addLabel("Виды спорта", 0, 3, 1);
        controller.addCheckBox("футбол", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.football, 1, 3, 2);
        controller.addCheckBox("теннис", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.tennis, 3, 3, 2);
        controller.addCheckBox("воллейбол", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.volleyball, 5, 3, 2);
        controller.addCheckBox("хоккей", sportsmanFilter::addSport, sportsmanFilter::removeSport, Sport.hockey, 7, 3, 2);
    }

}
