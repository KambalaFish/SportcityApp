package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Club;
import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

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
        controller.addChoiceBox("Клуб", sportsman.getClub(), sportsman::setClub, null, choiceItemSupplier);
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
