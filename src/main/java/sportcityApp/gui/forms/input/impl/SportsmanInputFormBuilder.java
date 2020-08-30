package sportcityApp.gui.forms.input.impl;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sportcityApp.entities.Club;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.Collection;
import java.util.function.Predicate;

public class SportsmanInputFormBuilder extends AbstractEntityInputFormBuilder<Sportsman>{

    public SportsmanInputFormBuilder(RequestExecutor requestExecutor){
        super(Sportsman::new, ServiceFactory.getSportsmanService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Sportsman sportsman, FormType formType, boolean isContextWindow, EntityInputFormController<Sportsman> controller) {
        ChoiceItemSupplier<Club> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getClubService(),
                club -> new ChoiceItem<>(club, club.getName()),
                "Не удалось загрузить список клубов"
        );

        controller.addTextField("ФИО спортсмена", sportsman.getName(), sportsman::setName);
        ComboBox<ChoiceItem<Club>> comboBox = controller.addChoiceBox("Клуб", sportsman.getClub(), sportsman::setClub, null, choiceItemSupplier);


        Runnable runnable = () ->{
            EntityInputFormBuilder<Club> entityInputFormBuilder = new ClubInputFormBuilder(getRequestExecutor());
            SuccessAction successAction = () -> {
                Platform.runLater(() -> {
                    try {
                        comboBox.getItems().clear();
                        Collection<ChoiceItem<Club>> items = null;
                        items = makeChoiceItemSupplierFromEntities(
                                ServiceFactory.getClubService(),
                                club -> new ChoiceItem<>(club, club.getName()),
                                "Не удалось загрузить список клубов"
                        ).getItems();
                        ChoiceItem<Club> defaultItem = new ChoiceItem<>(null, "Не указано");
                        items.add(defaultItem);
                        comboBox.getItems().addAll(items);
                        comboBox.setValue(defaultItem);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            };
            Stage stage = entityInputFormBuilder.buildCreationFormWindow(successAction);
            stage.show();
        };
        controller.getContentBox().setMinWidth(690);
        controller.addButton("Добавить клуб", runnable);
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить нового спортсмена";
    }

    @Override
    protected String getEditFormWindowTitle(Sportsman sportsman) {
        return String.format("Спортсмена %s - изменить", sportsman.getName());
    }
}
