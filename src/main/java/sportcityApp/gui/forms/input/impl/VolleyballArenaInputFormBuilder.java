package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.VolleyballArena;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class VolleyballArenaInputFormBuilder extends AbstractEntityInputFormBuilder<VolleyballArena>{

    public VolleyballArenaInputFormBuilder(RequestExecutor requestExecutor){
        super(VolleyballArena::new, ServiceFactory.getVolleyballArenaService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(VolleyballArena entity, FormType formType, boolean isContextWindow, EntityInputFormController<VolleyballArena> controller) {
        controller.addIntegerField("ширина сетки", entity.getNet_width(), entity::setNet_width);
        controller.addIntegerField("высота сетки", entity.getNet_height(), entity::setNet_height);
    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новую воллейбольную арену";
    }

    @Override
    protected String getEditFormWindowTitle(VolleyballArena entity) {
        return String.format("Изменить воллейбольную арену номер %s", entity.getId());
    }
}
