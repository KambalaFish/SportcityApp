package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Club;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class ClubInputFormBuilder extends AbstractEntityInputFormBuilder<Club> {

    public ClubInputFormBuilder(RequestExecutor requestExecutor){
        super(Club::new, ServiceFactory.getClubService(), requestExecutor);
    }


    @Override
    protected void fillInputForm(Club entity, FormType formType, boolean isContextWindow, EntityInputFormController<Club> controller) {
        controller.addTextField(
                "Название клуба",
                entity.getName(),
                entity::setName
        );
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый клуб";
    }

    @Override
    protected String getEditFormWindowTitle(Club entity) {
        return String.format("Клуб номер %s - изменить", entity.getName());
    }
}
