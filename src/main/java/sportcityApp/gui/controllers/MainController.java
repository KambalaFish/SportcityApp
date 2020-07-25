package sportcityApp.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import sportcityApp.entities.Ability;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.interfaces.ContextWindowBuilder;
import sportcityApp.gui.forms.filtering.FilterBoxBuilder;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.gui.forms.input.LinkingInputFormBuilder;
import sportcityApp.gui.forms.input.impl.*;
import sportcityApp.entities.Coach;
import sportcityApp.entities.Entity;
import sportcityApp.services.AbilityService;
import sportcityApp.services.CoachService;
import sportcityApp.services.Service;
import sportcityApp.services.SportsmanService;
import sportcityApp.services.filters.Filter;
import sportcityApp.utils.LocalDateFormatter;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainController {

    private RequestExecutor requestExecutor;

    @FXML
    private TabPane contentTabPane;

    @FXML
    private Label statusBarLabel;

    @FXML
    private Tab defaultTab;

    @SneakyThrows
    public void init(RequestExecutor requestExecutor){
        this.requestExecutor = requestExecutor;
        contentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }


    @FXML
    @SneakyThrows
    void openCoaches(){
        CoachService coachService = ServiceFactory.getCoachService();

        ContextWindowBuilder<Coach> infoWindowBuilder = coach -> {
            var sportsmanPropertyNames = new LinkedHashMap<>(Sportsman.getPropertyNames());
            var sportsmanSortPropertyNames = new LinkedHashMap<>(Sportsman.getSortPropertyNames());

            Node sportsmenOfCoachTable = createInfoWindowEntityTableForM2M(
                    sportsmanPropertyNames,
                    sportsmanSortPropertyNames,
                    pageInfo -> coachService.getSportsmen(coach.getId(), pageInfo),
                    new SportsmanInputFormBuilder(requestExecutor),
                    new SportsmanForCoachInputFormBuilder(requestExecutor),
                    () -> coach,
                    null
                    );

            return EntityInfoWindowBuilder.newInfoWindow(coach.getName()).addTab(sportsmenOfCoachTable, "Спортсмены").build();
        };


        createEntityTable(
                "Тренеры",
                Coach.getPropertyNames(),
                Coach.getSortPropertyNames(),
                coachService,
                new CoachInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null,
                null
        );

    }

    @FXML
    @SneakyThrows
    void openSportsmen(){
        SportsmanService sportsmanService = ServiceFactory.getSportsmanService();
        AbilityService abilityService = ServiceFactory.getAbilityService();


        ContextWindowBuilder<Sportsman> infoWindowBuilder = sportsman -> {
            var abilityPropertyNames = new LinkedHashMap<>(Ability.getPropertyNames());
            abilityPropertyNames.remove("sportsmanName");
            var abilitySortPropertyNames = new LinkedHashMap<>(Ability.getSortPropertyNames());
            abilitySortPropertyNames.remove("sportsmanName");

            var coachPropertyNames = new LinkedHashMap<>(Coach.getPropertyNames());
            var coachSortPropertyNames = new LinkedHashMap<>(Coach.getSortPropertyNames());


            Node abilitiesOfSportsmanTable = createInfoWindowEntityTable(
                    abilityPropertyNames,
                    abilitySortPropertyNames,
                    pageInfo -> sportsmanService.getAbilities(sportsman.getId(), pageInfo),
                    abilityService::deleteById,
                    new AbilityInputFormBuilder(requestExecutor),
                    () -> {
                        Ability ability = new Ability();
                        ability.getSportsman().setId(sportsman.getId());
                        return ability;
                    }
            );

            Node coachesOfSportsmanTable = createInfoWindowEntityTableForM2M(
                    coachPropertyNames,
                    coachSortPropertyNames,
                    pageInfo -> sportsmanService.getCoaches(sportsman.getId(), pageInfo),
                    new CoachInputFormBuilder(requestExecutor),
                    new CoachForSportsmanInputFormBuilder(requestExecutor),
                    () -> sportsman,
                    sportsmanService::removeCoachFromSportsman
            );

            return EntityInfoWindowBuilder.newInfoWindow(sportsman.getName()).addTab(abilitiesOfSportsmanTable, "Способности").addTab(coachesOfSportsmanTable, "Тренеры").build();
        };

        createEntityTable(
                "Спортсмены",
                Sportsman.getPropertyNames(),
                Sportsman.getSortPropertyNames(),
                sportsmanService,
                new SportsmanInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null,
                null
                );

    }

    @SneakyThrows
    private <T extends Entity, X extends Entity> EntityTableController<T, X> createEntityTable(
            String tableName,
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            Service<T> entityService,
            EntityInputFormBuilder<T> inputFormBuilder,
            ContextWindowBuilder<T> infoWindowBuilder,
            FilterBoxBuilder<T> filterBoxBuilder,
            Supplier<Filter<T>> newFilterSupplier)
    {
        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        Tab tableTab = new Tab(tableName);
        tableTab.setContent(table);
        tableTab.setOnClosed(event -> {
            if (contentTabPane.getTabs().isEmpty()) {
                contentTabPane.getTabs().add(defaultTab);
            }
        });

        contentTabPane.getTabs().remove(defaultTab);
        contentTabPane.getTabs().add(tableTab);
        contentTabPane.getSelectionModel().select(tableTab);

        EntityTableController<T, X> controller = tableLoader.getController();
        controller.setInfoWindowBuilder(infoWindowBuilder);

        controller.setEntityRemover(entityService::deleteById);

        Node filterBox = null;
        if (filterBoxBuilder != null && newFilterSupplier != null) {
            Filter<T> filter = newFilterSupplier.get();
            filterBox = filterBoxBuilder.buildFilterBox(filter);
            controller.setEntitySource(pageInfo -> entityService.search(filter, pageInfo));
        } else {
            controller.setEntitySource(entityService::getAll);
        }

        controller.setRequestExecutor(requestExecutor);

        controller.setEntityInputFormBuilder(inputFormBuilder);

        controller.init(
                entityPropertyNames,
                entitySortPropertyNames,
                false,
                this::setStatusBarMessage,
                filterBox
        );

        return controller;

    } 

    private void setStatusBarMessage(String message) {
        Platform.runLater(() -> {
            String messageTime = LocalDateFormatter.getFormattedDateTime(Instant.now().toEpochMilli());
            String messageWithTime = String.format("%s: %s", messageTime, message);
            statusBarLabel.textProperty().setValue(messageWithTime);
        });
    }



    @SneakyThrows
    private <T extends Entity, X extends Entity> Node createInfoWindowEntityTable(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityTableController.EntityRemover<T> entityRemover,
            EntityInputFormBuilder<T> inputFormBuilder,
            Supplier<T> newEntitySupplier
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T, X> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(entityRemover);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.setEntityInputFormBuilder(inputFormBuilder);

        entityTableController.setNewEntitySupplier(newEntitySupplier);

        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                true,
                this::setStatusBarMessage,
                null
        );

        return table;
    }

    @SneakyThrows
    private <T extends Entity, X extends Entity> Node createInfoWindowEntityTableForM2M(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityInputFormBuilder<T> entityInputFormBuilder,
            LinkingInputFormBuilder<X> linkingInputFormBuilder,
            Supplier<X> supplier,
            EntityTableController.LinkRemover<T> linkRemover
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T, X> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(null);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.setEntityInputFormBuilder(entityInputFormBuilder);
        entityTableController.setLinkingInputFormBuilder(linkingInputFormBuilder);
        entityTableController.setSupplierForM2M(supplier);

        entityTableController.setLinkRemover(linkRemover);

        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                true,
                this::setStatusBarMessage,
                null
        );
        entityTableController.setIsLinkingWindow(true);

        return table;
    }

}
