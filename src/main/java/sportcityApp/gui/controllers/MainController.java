package sportcityApp.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import sportcityApp.gui.controllers.interfaces.ContextWindowBuilder;
import sportcityApp.gui.forms.filtering.FilterBoxBuilder;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.gui.forms.input.impl.CoachInputFormBuilder;
import sportcityApp.entities.Coach;
import sportcityApp.entities.Entity;
import sportcityApp.services.CoachService;
import sportcityApp.services.Service;
import sportcityApp.services.filters.Filter;
import sportcityApp.utils.LocalDateFormatter;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.time.Instant;
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
        /*
        ContextWindowBuilder<Coach> infoWindowBuilder = coach -> {
        }
        */

        createEntityTable(
                "Тренеры",
                Coach.getPropertyNames(),
                Coach.getSortPropertyNames(),
                coachService,
                new CoachInputFormBuilder(requestExecutor),
                null,
                null,
                null
        );

    }


    @SneakyThrows
    private <T extends Entity> EntityTableController<T> createEntityTable(
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

        EntityTableController<T> controller = tableLoader.getController();
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

        controller.init(
                entityPropertyNames,
                entitySortPropertyNames,
                inputFormBuilder,
                null,
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

}
