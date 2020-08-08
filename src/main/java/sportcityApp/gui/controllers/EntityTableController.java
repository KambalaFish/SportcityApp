package sportcityApp.gui.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sportcityApp.entities.SportFacility;
import sportcityApp.gui.AlertDialogFactory;
import sportcityApp.gui.controllers.interfaces.ContextMenuAction;
import sportcityApp.gui.controllers.interfaces.ContextWindowBuilder;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.entities.Entity;
import sportcityApp.gui.forms.input.LinkingInputFormBuilder;
import sportcityApp.gui.forms.input.LinkingInputFormBuilderForOwned;
import sportcityApp.services.ServiceResponse;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.services.pagination.PageSort;
import sportcityApp.utils.RequestExecutor;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EntityTableController <T extends Entity, X extends Entity> {

    public interface EntitySource<E extends Entity>{
        ServiceResponse<Page<E>> getEntities(PageInfo pageInfo) throws Exception;
    }

    public interface EntityRemover<E extends Entity>{
        ServiceResponse<Void> deleteEntity(Integer id) throws Exception;
    }

    public interface LinkRemover{
        ServiceResponse<Void> removeLink(Integer sourceId, Integer destinationId);
    }

    public interface FrontendSideLinkRemover{
        void removeLink(Integer idOfEntityToRemove);
    }

    public void setLinkRemover(LinkRemover linkRemover){
        this.linkRemover = linkRemover;
    }

    public void setFrontendSideLinkRemover(FrontendSideLinkRemover frontendSideLinkRemover){
        this.frontendSideLinkRemover = frontendSideLinkRemover;
    }

    private EntitySource<T> entitySource;
    private EntityRemover<T> entityRemover;
    private RequestExecutor requestExecutor;
    private ContextWindowBuilder<T> infoWindowBuilder;
    private Supplier<T> newEntitySupplier;
    private Supplier<X> supplierForM2M; /*например открываю сначала спортсмена, подробнее -> получаю окно тренеров. так вот здесь будет выдавать спортсмена*/

    private LinkRemover linkRemover;
    private FrontendSideLinkRemover frontendSideLinkRemover;

    private EntityInputFormBuilder<T> inputFormBuilder;
    private LinkingInputFormBuilder<X> linkingInputFormBuilder;
    private LinkingInputFormBuilderForOwned<X> linkingInputFormBuilderForOwned;
    private Consumer<String> statusBarMessageAcceptor;
    private boolean isContextWindow;
    private boolean isLinkingWindow;
    private boolean isOwnedWindow;
    private boolean isChangeItemVisible;
    public void setIsLinkingWindow(boolean value){
        isLinkingWindow = value;
    }

    public void setIsOwnedWindow(boolean value){
        isOwnedWindow = value;
    }


    public void setInfoWindowBuilder(ContextWindowBuilder<T> infoWindowBuilder) {
        this.infoWindowBuilder = infoWindowBuilder;
    }

    public void setRequestExecutor(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void setEntitySource(EntitySource<T> entitySource) {
        this.entitySource = entitySource;
    }

    public void setEntityRemover(EntityRemover<T> entityRemover) {
        this.entityRemover = entityRemover;
    }

    public void setIsChangeItemVisible(boolean isChangeItemVisible){
        this.isChangeItemVisible = isChangeItemVisible;
    }

    /*метод для правого клика на строку в TableView и дальнейшего выбора опций подробнее изменить удалить. пока что скипаем*/

    private void fillContextMenu() {
        contextMenu = new ContextMenu();

        MenuItem infoItem = new MenuItem("Подробнее");
        MenuItem changeItem = new MenuItem("Изменить");
        MenuItem deleteItem = new MenuItem("Удалить");

        infoItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                openWindow(
                        () -> infoWindowBuilder.buildWindow(entity),
                        String.format("Не удалось открыть информацию о сущности №%d", entity.getId())
                );
            }
        });

        changeItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                final T entityClone = (T) entity.clone();
                entityClone.calculateProperties();
                SuccessAction successAction = () -> refreshTableContents("Успешно изменено");
                Supplier<Stage> windowBuilder = () -> {
                    if (isContextWindow) {
                        return inputFormBuilder.buildContextEditFormWindow(entityClone, successAction);
                    }
                    return inputFormBuilder.buildEditFormWindow(entityClone, successAction);
                };
                openWindow(
                        windowBuilder,
                        String.format("Не удалось открыть форму изменения сущности №%d", entity.getId())
                );
            }
        });

        deleteItem.setOnAction(event -> {
            if(isLinkingWindow){
                if(!isOwnedWindow)
                    if(frontendSideLinkRemover!=null)
                        frontendSideLinkRemover.removeLink(entityTable.getSelectionModel().getSelectedItem().getId());
                removeLink(supplierForM2M.get() ,entityTable.getSelectionModel().getSelectedItem());
            } else {
                T entity = entityTable.getSelectionModel().getSelectedItem();
                if (entity != null) {
                    deleteEntity(entity);
                }
            }
        });

        if (infoWindowBuilder != null) {
            contextMenu.getItems().add(infoItem);
        }

        if(isChangeItemVisible)
            contextMenu.getItems().add(changeItem);

        contextMenu.getItems().add(deleteItem);
        entityTable.setContextMenu(contextMenu);
    }


    public void addContextMenuWindowAction(String actionName, ContextWindowBuilder<T> windowBuilder) {
        addContextMenuAction(
                actionName,
                entity -> openWindow(
                        () -> windowBuilder.buildWindow(entity),
                        String.format("Не удалось выполнить действие \"%s\"", actionName)
                )
        );
    }

    public void addContextMenuAction(String actionName, ContextMenuAction<T> menuAction) {
        MenuItem menuItem = new MenuItem(actionName);
        menuItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                entity = (T) entity.clone();
                entity.calculateProperties();
                menuAction.run(entity);
            }
        });

        contextMenu.getItems().add(0, menuItem);
    }




    private ContextMenu contextMenu;

    @FXML
    private VBox rootVBox;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<T> entityTable;

    @FXML
    private ComboBox<ChoiceItem<String>> sortChoiceBox;

    @FXML
    private VBox filteringVBox;

    @FXML
    private Label pageSizeLabel;

    @FXML
    private Label totalSizeLabel;

    @FXML
    private HBox searchBox;

    @FXML
    private VBox sortingBox;

    @FXML
    private Button searchButton;

    @FXML
    private Button createButton;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();

    private PageInfo pageInfo;
    private PageSort pageSort;
    private final Label emptyTablePlaceholder = new Label();
    private final Label promptTablePlaceholder = new Label(
            "Для отображения данных, нажмите \"Обновить\""
    );

    @FXML
    public void openSearchBox() {
        searchBox.setVisible(!searchBox.isVisible());
        searchButton.setText(searchBox.isVisible() ?
                "Закрыть параметры поиска" : "Открыть параметры поиска"
        );
    }

    public void setEntityInputFormBuilder(EntityInputFormBuilder<T> entityInputFormBuilder){
        this.inputFormBuilder = entityInputFormBuilder;
    }

    public void setNewEntitySupplier(Supplier<T> newEntitySupplier){
        this.newEntitySupplier = newEntitySupplier;
    }

    public void setLinkingInputFormBuilder(LinkingInputFormBuilder<X> linkingInputFormBuilder){
        this.linkingInputFormBuilder = linkingInputFormBuilder;
    }

    public void setLinkingInputFormBuilderForOwned(LinkingInputFormBuilderForOwned<X> linkingInputFormBuilderForOwned){
        this.linkingInputFormBuilderForOwned = linkingInputFormBuilderForOwned;
    }

    public void setSupplierForM2M(Supplier<X> supplierForM2M){
        this.supplierForM2M = supplierForM2M;
    }

    public void disableCreateButton(){
        //createButton.setDisable(true);
        createButton.setVisible(false);
    }


    public void init(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            boolean isContextWindow,
            Consumer<String> statusBarMessageAcceptor,
            Node filterBox
    ) {
        isLinkingWindow = false;
        isOwnedWindow = false;
        searchBox.managedProperty().bind(searchBox.visibleProperty());
        searchBox.setVisible(false);

        sortingBox.setStyle(
                "-fx-padding: 10;" +
                        "-fx-border-style: solid inside;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-insets: 5;" +
                        "-fx-border-radius: 5;" +
                        "-fx-border-color: #3c3f4b;"
        );

        searchButton.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
            if (isFocused) {
                rootVBox.requestFocus();
            }
        });

        this.isContextWindow = isContextWindow;
        this.statusBarMessageAcceptor = statusBarMessageAcceptor;

        if (filterBox != null) {
            filteringVBox.getChildren().add(filterBox);
        }

        pageSort = new PageSort();
        pageInfo = new PageInfo(0L, 25L, pageSort);

        pagination.pageCountProperty().setValue(1);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newPageNumber = (int) newValue;
            pageInfo.setPageNumber((long) newPageNumber);
            refreshTableContents();
        });

        entityTable.placeholderProperty().setValue(emptyTablePlaceholder);

        List<ChoiceItem<String>> sortFieldList = entitySortPropertyNames
                .entrySet()
                .stream()
                .map(e -> new ChoiceItem<>(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        ChoiceItem<String> defaultSortField = new ChoiceItem<>("id", "№");
        sortFieldList.add(0, defaultSortField);
        sortChoiceBox.setValue(defaultSortField);
        sortChoiceBox.getItems().addAll(sortFieldList);
        pageSort.addField(defaultSortField.getValue());

        sortChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            pageSort.removeAllFields();
            pageSort.addField(newValue.getValue());
            refreshTableContents();
        });

        List<TableColumn<T, String>> columns = entityPropertyNames
                .entrySet()
                .stream()
                .map(e -> {
                    TableColumn<T, String> tableColumn = new TableColumn<>(e.getValue());
                    tableColumn.setCellValueFactory(new PropertyValueFactory<>(e.getKey()));
                    tableColumn.setSortable(false);
                    tableColumn.setEditable(false);
                    return tableColumn;
                }).collect(Collectors.toList());
        entityTable.getColumns().addAll(columns);
        entityTable.setItems(entityObservableList);

        fillContextMenu();

        Platform.runLater(this::refreshTableContents);
    }

    @FXML
    public void refreshTableContents() {
        refreshTableContents("Данные успешно загружены");
    }

    public void refreshTableContents(String successMessage) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entitySource.getEntities(pageInfo))
                .setOnSuccessAction(page -> {
                    var entities = page.getElementList();
                    entities.forEach(Entity::calculateProperties);

                    Platform.runLater(() -> {
                        pagination.pageCountProperty().setValue(
                                page.getTotalPages().equals(0L) ? 1 : page.getTotalPages()
                        );

                        pageSizeLabel.setText(String.format(
                                "На странице: %d", page.getNumberOfElements()
                        ));

                        totalSizeLabel.setText(String.format(
                                "Всего: %d", page.getTotalElements()
                        ));

                        entityObservableList.clear();
                        entityObservableList.addAll(entities);
                        statusBarMessageAcceptor.accept(successMessage);
                        entityTable.setPlaceholder(emptyTablePlaceholder);
                    });
                })
                .setOnFailureAction(errorMessage -> Platform.runLater(() -> {
                    AlertDialogFactory.showErrorAlertDialog(
                            "Не удалось загрузить информацию",
                            errorMessage
                    );
                    entityTable.setPlaceholder(promptTablePlaceholder);
                }))
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();
    }

    private void disableComponent() {
        rootVBox.setDisable(true);
    }

    private void enableComponent() {
        rootVBox.setDisable(false);
    }

    @FXML
    private void setAscendingSortOrder() {
        pageSort.setOrder(PageSort.Order.ASC);
        refreshTableContents();
    }

    @FXML
    private void setDescendingSortOrder() {
        pageSort.setOrder(PageSort.Order.DESC);
        refreshTableContents();
    }

    @FXML
    private void openCreateWindow() {
        SuccessAction successAction = () -> refreshTableContents("Успешно изменено");
        Supplier<Stage> windowBuilder = () -> {
            if (isContextWindow) {
                if(isLinkingWindow){
                    if(isOwnedWindow){
                        return linkingInputFormBuilderForOwned.buildLinkingWindow(supplierForM2M.get(), successAction);
                    }
                    return linkingInputFormBuilder.buildLinkingWindow(supplierForM2M.get(), successAction);/*#*/
                }
                return inputFormBuilder.buildContextCreationFormWindow(
                        newEntitySupplier.get(), successAction
                );
            }
            return inputFormBuilder.buildCreationFormWindow(successAction);
        };
        openWindow(
                windowBuilder,
                "Не удалось открыть форму добавления"
        );
    }

    private void openWindow(Supplier<Stage> windowBuilder, String errorDialogHeader) {
        try {
            if (windowBuilder != null) {
                Stage contextWindow = windowBuilder.get();
                contextWindow.show();
            }
        } catch (Exception e) {
            Platform.runLater(() ->
                    AlertDialogFactory.showErrorAlertDialog(
                            errorDialogHeader,
                            e.getLocalizedMessage()
                    )
            );
            e.printStackTrace();
        }
    }

    private void removeLink(X entityToSave, T entityToRemove){

        disableComponent();
        requestExecutor
                .makeRequest(() -> linkRemover.removeLink(entityToSave.getId(), entityToRemove.getId()))
                .setOnSuccessAction(responseBody -> Platform.runLater(
                        () -> refreshTableContents("Успешно удалено")
                ))
                .setOnFailureAction(errorMessage -> {
                    Platform.runLater(() -> {
                        enableComponent();
                        AlertDialogFactory.showErrorAlertDialog(
                                String.format("Не удалось удалить сущность № %d!", entityToSave.getId()),
                                errorMessage
                        );
                    });
                })
                .submit();

    }

    private void deleteEntity(T entity) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entityRemover.deleteEntity(entity.getId()))
                .setOnSuccessAction(responseBody -> Platform.runLater(
                        () -> refreshTableContents("Успешно удалено")
                ))
                .setOnFailureAction(errorMessage -> {
                    Platform.runLater(() -> {
                        enableComponent();
                        AlertDialogFactory.showErrorAlertDialog(
                                String.format("Не удалось удалить сущность № %d!", entity.getId()),
                                errorMessage
                        );
                    });
                })
                .submit();
    }

}
