package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Sportsman;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class SportsmanInputFormBuilder extends AbstractEntityInputFormBuilder<Sportsman>{

    public SportsmanInputFormBuilder(RequestExecutor requestExecutor){
        super(Sportsman::new, ServiceFactory.getSportsmanService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Sportsman sportsman, FormType formType, boolean isContextWindow, EntityInputFormController<Sportsman> controller) {
        controller.addTextField("ФИО спортсмена", sportsman.getName(), sportsman::setName);
        controller.addTextField("Спортивный клуб", sportsman.getClub_name(), sportsman::setClub_name);
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
