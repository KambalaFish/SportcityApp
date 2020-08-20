package sportcityApp.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;
import sportcityApp.entities.*;
import sportcityApp.entities.types.CoverageType;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.utils.RequestExecutor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class SportFacilityInputFormController {

    @FXML
    private VBox contentBox;

    @FXML
    private GridPane grid;

    private final Map<ComboBox, ChoiceItem> choiceBoxes = new LinkedHashMap<>();

    private RequestExecutor requestExecutor;

    public void init(RequestExecutor requestExecutor/*, SportFacility sportFacility*/){
        this.requestExecutor = requestExecutor;
        //this.sportFacility = sportFacility;
    }

    @FXML
    public void submit(javafx.event.ActionEvent actionEvent) {
        disableComponent();
        requestExecutor.submit(() -> {
            Platform.runLater(() -> {
                Node sourceNode = (Node) actionEvent.getSource();
                Stage stage = (Stage) sourceNode.getScene().getWindow();
                stage.close();
            });
        });
        enableComponent();
    }

    public interface FieldSetter<X>{
        void setField(X value);
    }


    public void enableComponent() {
        contentBox.setDisable(false);
    }

    public void disableComponent() {
        contentBox.setDisable(true);
    }

    private final List<TextField> integerFields = new ArrayList<>();

    private final List<TextField> doubleFields = new ArrayList<>();



    public void inputCourt(SportFacility sportFacility){
        Court court = new Court();
        sportFacility.setCourt(court);
        court.setId(sportFacility.getId());
        addChoiceBox("Тип покрытия", CoverageType.clay, court::setCoverageType, CoverageType::getChoiceItems);
    }

    public void inputStadium(SportFacility sportFacility){
        Stadium stadium = new Stadium();
        sportFacility.setStadium(stadium);
        stadium.setId(sportFacility.getId());
        addIntegerField("Вместимость", 0, stadium::setCapacity);
    }

    public void inputIceArena(SportFacility sportFacility){
        IceArena iceArena = new IceArena();
        sportFacility.setIceArena(iceArena);
        iceArena.setId(sportFacility.getId());
        addDoubleField("Площадь", 0d, iceArena::setSquare);
    }

    public void inputVolleyballArena(SportFacility sportFacility){
        VolleyballArena volleyballArena = new VolleyballArena();
        sportFacility.setVolleyballArena(volleyballArena);
        volleyballArena.setId(sportFacility.getId());
        addDoubleField("Высота сетки", 0d, volleyballArena::setNet_height);
        addDoubleField("Ширина сетки", 0d, volleyballArena::setNet_width);
    }

    private void addIntegerField(String name, Integer initFieldValue, EntityInputFormController.EntityFieldSetter<Integer> fieldSetter){
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

    private void addDoubleField(String name, Double initFieldValue, EntityInputFormController.EntityFieldSetter<Double> fieldSetter){
        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String newText = change.getControlNewText();
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

    @SneakyThrows
    private <X> void addChoiceBox(String name, X initFieldValue, FieldSetter<X> fieldSetter, ChoiceItemSupplier<X> itemSupplier) {
        ChoiceItem<X> defaultItem = new ChoiceItem<>(null, "Не указано");
        var items = itemSupplier.getItems();
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
        });
        choiceBoxes.put(choiceBox, defaultItem);
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

    @FXML
    private void clearFields() {

        for (var integerField: integerFields) {
            integerField.setText("");
        }

        for(var doubleField : doubleFields){
            doubleField.setText("");
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ComboBox<ChoiceItem<?>> choiceBox = rawChoiceBox;
            ChoiceItem<?> defaultItem = choiceBoxes.get(rawChoiceBox);
            choiceBox.setValue(defaultItem);
        }

    }

}
