package sportcityApp.gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;
import sportcityApp.entities.SportFacility;
import sportcityApp.entities.types.FacilityType;
import sportcityApp.gui.AlertDialogFactory;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.controllers.interfaces.ErrorAction;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.StageFactory;
import sportcityApp.services.ServiceResponse;
import sportcityApp.utils.LocalDateFormatter;
import sportcityApp.utils.RequestExecutor;

import java.text.ParseException;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class EntityInputFormController<T> {

    public interface SubmitAction<E> {
        ServiceResponse<E> submit(E entity) throws Exception;
    }

    public interface EntityFieldSetter<X>{
        void setField(X value);
    }

    public interface EntityFieldPreviousRemover<X>{
        void removeField(X value);
    }


    private SubmitAction<T> submitAction;
    private SuccessAction onSuccessAction;
    private ErrorAction onErrorAction;
    private RequestExecutor requestExecutor;

    private final List<TextField> textFields = new ArrayList<>();
    private final List<TextField> integerFields = new ArrayList<>();
    private final List<TextField> doubleFields = new ArrayList<>();
    private final List<TextField> dateTimeFields = new ArrayList<>();
    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final Map<ComboBox, ChoiceItem> choiceBoxes = new LinkedHashMap<>();
    private final Map<ComboBox, ChoiceItemForM2MOwned> choiceBoxesM2MOwned = new LinkedHashMap<>();

    @FXML
    private VBox contentBox;

    public VBox getContentBox(){
        return contentBox;
    }

    @FXML
    private GridPane grid;

    private T entity;

    public T getEntity(){
        return entity;
    }

    public void setEntity(T entity){
        this.entity = entity;
    }

    public void init(T entity, SubmitAction<T> submitAction, SuccessAction onSuccessAction, ErrorAction onErrorAction, RequestExecutor requestExecutor){
        this.entity = entity;
        this.submitAction = submitAction;
        this.requestExecutor = requestExecutor;
        this.onSuccessAction = onSuccessAction;
        this.onErrorAction = onErrorAction;
    }

    public void addTextField(String name, String initFieldValue, EntityFieldSetter<String> fieldSetter){
        TextField textField = new TextField();
        textField.textProperty().addListener( (observable, oldValue, newValue) ->{
            fieldSetter.setField(newValue.trim());
        });

        initFieldValue = Objects.requireNonNullElse(initFieldValue, "");
        fieldSetter.setField(initFieldValue);
        textField.setText(initFieldValue);

        addField(name, textField);
        textFields.add(textField);

    }

    public void addCheckBox(String name, Boolean initFieldValue, EntityFieldSetter<Boolean> fieldSetter){
        CheckBox checkBox = new CheckBox();
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue);
        });

        initFieldValue = Objects.requireNonNullElse(initFieldValue, false);
        fieldSetter.setField(initFieldValue);
        checkBox.setSelected(initFieldValue);

        addField(name, checkBox);
        checkBoxes.add(checkBox);
    }

    public void addIntegerField(String name, Integer initFieldValue, EntityFieldSetter<Integer> fieldSetter){
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("0|([1-9][0-9]{0,8})?")) {
                return change;
            }
            return null;
        };

        TextField integerField = new TextField();
        integerField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), initFieldValue, integerFilter));

        integerField.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue.isEmpty() ? null : Integer.valueOf(newValue));
        });

        addField(name, integerField);
        integerFields.add(integerField);
    }

    public void addDoubleField(String name, Double initFieldValue, EntityInputFormController.EntityFieldSetter<Double> fieldSetter){
        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.equals(""))
                return change;
            try {
                Double num = Double.parseDouble(newText);
                return change;
            } catch (Exception e){
                return null;
            }
        };

        TextField doubleField = new TextField();
        doubleField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), initFieldValue, doubleFilter));

        doubleField.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue.isEmpty()? null : Double.valueOf(newValue));
        });

        addField(name, doubleField);
        doubleFields.add(doubleField);
    }

    public void addDateField(String name, Date initFieldValue, EntityFieldSetter<Date> fieldSetter) {
        addDateTimePicker(
                name,
                initFieldValue,
                fieldSetter,
                true
        );
    }

    public void addDateTimeField(String name, Date initFieldValue, EntityFieldSetter<Date> fieldSetter) {
        addDateTimePicker(
                name,
                initFieldValue,
                fieldSetter,
                false
        );
    }

    private void addDateTimePicker(String name, Date initFieldValue, EntityFieldSetter<Date> fieldSetter, final boolean isDateOnly) {
        TextField dateTimeField = new TextField();
        Date currentDate = initFieldValue == null ? Date.from(Instant.now()) : initFieldValue;
        fieldSetter.setField(currentDate);

        String currentDateText = isDateOnly ?
                LocalDateFormatter.getFormattedDate(currentDate) :
                LocalDateFormatter.getFormattedDateTime(currentDate);
        dateTimeField.setText(currentDateText);

        dateTimeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    String dateText = dateTimeField.getText().trim();
                    dateTimeField.setText(dateText);
                    Date date = isDateOnly ?
                            LocalDateFormatter.parseDate(dateText)
                            : LocalDateFormatter.parseDateTime(dateText);
                    fieldSetter.setField(date);
                } catch (ParseException e) {
                    dateTimeField.setText("");
                    fieldSetter.setField(null);
                }
            }
        });

        addField(name, dateTimeField);
        dateTimeFields.add(dateTimeField);
    }

    @SneakyThrows
    public <X> ComboBox<ChoiceItem<X>> addChoiceBox(String name, X initFieldValue, EntityFieldSetter<X> fieldSetter, EntityFieldPreviousRemover<X> fieldRemover, ChoiceItemSupplier<X> itemSupplier) {
        ChoiceItem<X> defaultItem;
        var items = itemSupplier.getItems();
        if (items.isEmpty()){
            defaultItem = new ChoiceItem<>(null, "Нет доступных вариантов");
        } else
            defaultItem = new ChoiceItem<>(null, "Не указано");
        items.add(defaultItem);

        ChoiceItem<X> selectedItem = items.stream()
                .filter(item -> item.getValue() != null &&
                        item.getValue().equals(initFieldValue))
                .findAny()
                .orElse(defaultItem);

        ComboBox<ChoiceItem<X>> choiceBox = new ComboBox<>();
        choiceBox.setValue(selectedItem);
        choiceBox.getItems().addAll(items);
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue.getValue());
            if (oldValue.getValue()!=null & fieldRemover != null) /*нужно для M2M, когда выбрал сначала один айтем, потом другой, чтобы не добавлялось оба айтема*/
                fieldRemover.removeField(oldValue.getValue());
        });
        choiceBoxes.put(choiceBox, defaultItem);
        addField(name, choiceBox);
        return choiceBox;
    }

    public void addChoiceBoxForSportFacilityTypeCreation(SportFacility sportFacility){
        ChoiceItem<FacilityType> defaultItem = new ChoiceItem<>(null, "Не указано");
        var items = FacilityType.getChoiceItems();
        items.add(defaultItem);

        ComboBox<ChoiceItem<FacilityType>> choiceBoxOfFacilityTypes = new ComboBox<>();
        choiceBoxOfFacilityTypes.setValue(defaultItem);
        choiceBoxOfFacilityTypes.getItems().addAll(items);
        choiceBoxOfFacilityTypes.valueProperty().addListener((observable, oldValue, newValue) -> {
                switch (newValue.getValue()) {
                    case stadium:
                        createStadiumInput(sportFacility);
                        break;
                    case court:
                        createCourtInput(sportFacility);
                        break;
                    case iceArena:
                        createIceArena(sportFacility);
                        break;
                    case volleyballArena:
                        createVolleyballArena(sportFacility);
                        break;
                }
                if (oldValue.getValue() != null){
                    switch (oldValue.getValue()){
                        case volleyballArena:
                            sportFacility.setVolleyballArena(null);
                            break;
                        case stadium:
                            sportFacility.setStadium(null);
                            break;
                        case iceArena:
                            sportFacility.setIceArena(null);
                            break;
                        case court:
                            sportFacility.setCourt(null);
                            break;
                    }
                }
        });
        choiceBoxes.put(choiceBoxOfFacilityTypes, defaultItem);
        addField("Тип сооружения", choiceBoxOfFacilityTypes);
    }

    @SneakyThrows
    private void createStadiumInput(SportFacility sportFacility){
        var fxmlLoader = FxmlLoaderFactory.createSportFacilityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        SportFacilityInputFormController controller = fxmlLoader.getController();
        controller.init(requestExecutor);
        controller.inputStadium(sportFacility);

        Supplier<Stage> supplier = () -> StageFactory.createStage(rootNode, "Добавить стадион");
        openWindow(supplier, "Открытие формы для создания стадион не удалось");
    }

    @SneakyThrows
    private void createCourtInput(SportFacility sportFacility){
        var fxmlLoader = FxmlLoaderFactory.createSportFacilityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        SportFacilityInputFormController controller = fxmlLoader.getController();
        controller.init(requestExecutor);
        controller.inputCourt(sportFacility);

        Supplier<Stage> supplier = () -> StageFactory.createStage(rootNode, "Добавить корт");
        openWindow(supplier, "Открытие формы для создания корта не удалось");
    }

    @SneakyThrows
    private void createIceArena(SportFacility sportFacility){
        var fxmlLoader = FxmlLoaderFactory.createSportFacilityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        SportFacilityInputFormController controller = fxmlLoader.getController();
        controller.init(requestExecutor);
        controller.inputIceArena(sportFacility);

        Supplier<Stage> supplier = () -> StageFactory.createStage(rootNode, "Добавить ледовую арену");
        openWindow(supplier, "Открытие формы для создания ледовой арены не удалось");
    }

    @SneakyThrows
    private void createVolleyballArena(SportFacility sportFacility){
        var fxmlLoader = FxmlLoaderFactory.createSportFacilityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        SportFacilityInputFormController controller = fxmlLoader.getController();
        controller.init(requestExecutor);
        controller.inputVolleyballArena(sportFacility);

        Supplier<Stage> supplier = () -> StageFactory.createStage(rootNode, "Добавить волейбольную арену");
        openWindow(supplier, "Открытие формы для создания волейбольной арены не удалось");
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

    @SneakyThrows
    public <X> void addChoiceBoxForM2MOwned(String name, X initFieldValue, X ownedEntityToSave, ChoiceItemSupplierForM2MOwned<T, X> itemSupplier)  {
        ChoiceItemForM2MOwned<T, X> defaultItem;
        var items = itemSupplier.getItems();
        if (items.isEmpty())
            defaultItem = new ChoiceItemForM2MOwned<>(null, "Нет доступных вариантов", value -> {}, value -> {});
        else
            defaultItem = new ChoiceItemForM2MOwned<>(null, "Не указано", value -> {}, value -> {});
        items.add(defaultItem);

        ChoiceItemForM2MOwned<T, X> selectedItem = items.stream()
                .filter(item -> item.getValue() != null &&
                        item.getValue().equals(initFieldValue))
                .findAny()
                .orElse(defaultItem);

        ComboBox<ChoiceItemForM2MOwned<T, X>> choiceBox = new ComboBox<>();
        choiceBox.setValue(selectedItem);
        choiceBox.getItems().addAll(items);

        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            newValue.fieldSetter.setField(ownedEntityToSave);
            if(oldValue!=null) {
                if (oldValue.fieldRemover == null){
                    System.err.println("field remover in addChoiceBoxForM2MOwned is null, could leave to invalid changes");
                    System.exit(13);
                }
                oldValue.fieldRemover.removeField(ownedEntityToSave);
            }
            entity = newValue.getValue();
        });

        choiceBoxesM2MOwned.put(choiceBox, defaultItem);
        addField(name, choiceBox);
    }

    private void addField(String name, Control field){
        field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label label = new Label(String.format("%s:", name));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        GridPane.setHalignment(field, HPos.CENTER);
        GridPane.setValignment(field, VPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.CENTER);

        int rowsNumber = grid.getRowCount();
        grid.add(label, 0, rowsNumber, 2, 1);
        grid.add(field, 2, rowsNumber, 3, 1);
    }

    public void addButton(String name, Runnable runnable){
        Button button = new Button(name);
        button.setOnAction( actionEvent -> {
            runnable.run();
        });
        grid.add(button, 5, grid.getRowCount() - 1, 2,1);
    }

    private boolean validateFields() {
        for (var textField: textFields) {
            var text = textField.getText().trim();
            if (text.isEmpty()) {
                textField.setText("");
                textField.requestFocus();
                return false;
            }
        }

        for (var integerField: integerFields) {
            var value = integerField.getText();
            if (value == null || value.isEmpty()) {
                integerField.requestFocus();
                return false;
            }
        }

        for (var doubleField : doubleFields){
            var value = doubleField.getText();
            if (value == null || value.isEmpty()){
                doubleField.requestFocus();
                return false;
            }
        }

        for (var dateTimeField: dateTimeFields) {
            var dateText = dateTimeField.getText().trim();
            if (dateText.isEmpty()) {
                dateTimeField.requestFocus();
                return false;
            }
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ComboBox<ChoiceItem<?>> choiceBox = rawChoiceBox;
            ChoiceItem<?> choiceItem = choiceBox.valueProperty().getValue();
            if (choiceItem.getValue() == null) {
                choiceBox.requestFocus();
                return false;
            }
        }

        for (var rawChoiceBox: choiceBoxesM2MOwned.keySet()) {
            ComboBox<ChoiceItemForM2MOwned<?,?>> choiceBoxForM2M = rawChoiceBox;
            ChoiceItemForM2MOwned<?, ?> choiceItem = choiceBoxForM2M.valueProperty().getValue();
            if (choiceItem.getValue() == null) {
                choiceBoxForM2M.requestFocus();
                return false;
            }
        }

        return true;
    }

    @FXML
    private void clearFields() {
        for (var textField: textFields) {
            textField.setText("");
        }

        for (var integerField: integerFields) {
            integerField.setText("");
        }

        for (var doubleField : doubleFields){
            doubleField.setText("");
        }

        for (var dateTimeField: dateTimeFields) {
            dateTimeField.setText("");
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ComboBox<ChoiceItem<?>> choiceBox = rawChoiceBox;
            ChoiceItem<?> defaultItem = choiceBoxes.get(rawChoiceBox);
            choiceBox.setValue(defaultItem);
        }

        for (var rawChoiceBox: choiceBoxesM2MOwned.keySet()) {
            ComboBox<ChoiceItemForM2MOwned<?, ?>> choiceBoxForM2MOwned = rawChoiceBox;
            ChoiceItemForM2MOwned<?, ?> defaultItem = choiceBoxesM2MOwned.get(rawChoiceBox);
            choiceBoxForM2MOwned.setValue(defaultItem);
        }

        for (var checkBox: checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    @FXML
    private void submit(ActionEvent event) {
        boolean fieldsAreValid = validateFields();
        if (!fieldsAreValid) {
            return;
        }
        disableComponent();
        requestExecutor
                .makeRequest(() ->{
                    return submitAction.submit(entity);
                })
                .setOnSuccessAction(createdEntity -> {
                    Platform.runLater(() -> {
                        Node sourceNode = (Node) event.getSource();
                        Stage stage = (Stage) sourceNode.getScene().getWindow();
                        stage.close();
                    });
                    if (onSuccessAction != null) {
                        onSuccessAction.run();
                    }
                })
                .setOnFailureAction(errorMessage -> {
                    if (onErrorAction != null) {
                        onErrorAction.run(errorMessage);
                    }
                })
                .setFinalAction(this::enableComponent)
                .submit();
    }

    public void enableComponent() {
        contentBox.setDisable(false);
    }

    public void disableComponent() {
        contentBox.setDisable(true);
    }

}
