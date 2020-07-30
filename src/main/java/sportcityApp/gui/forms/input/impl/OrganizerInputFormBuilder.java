package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Organizer;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class OrganizerInputFormBuilder extends AbstractEntityInputFormBuilder<Organizer>{
    public OrganizerInputFormBuilder(RequestExecutor requestExecutor){
        super(Organizer::new, ServiceFactory.getOrganizerService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Organizer entity, FormType formType, boolean isContextWindow, EntityInputFormController<Organizer> controller) {
        controller.addTextField(
                "ФИО организатора",
                entity.getName(),
                entity::setName
        );
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить нового организатора";
    }

    @Override
    protected String getEditFormWindowTitle(Organizer entity) {
        return String.format("Организатора %s - изменить", entity.getName());
    }
}
