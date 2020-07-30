package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Ability;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

public class AbilityInputFormBuilder extends AbstractEntityInputFormBuilder<Ability> {

    public AbilityInputFormBuilder(RequestExecutor requestExecutor){
        super(Ability::new, ServiceFactory.getAbilityService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(Ability ability, FormType formType, boolean isContextWindow, EntityInputFormController<Ability> controller) {
        if (!isContextWindow){
            ChoiceItemSupplier<Sportsman> choiceItemSupplier = makeChoiceItemSupplierFromEntities(
                    ServiceFactory.getSportsmanService(),
                    sportsman -> new ChoiceItem<>(sportsman, sportsman.getName()),
                    "Не удалось загрузить спортсменов"
            );
            controller.addChoiceBox(
                    "Спортсмен",
                    ability.getSportsman(),
                    ability::setSportsman,
                    null,
                    choiceItemSupplier
            );
        }
        controller.addChoiceBox(
                "Вид спорта",
                ability.getSport(),
                ability::setSport,
                null,
                Sport::getChoiceItems
        );
        controller.addIntegerField("Разряд", ability.getLevel(), ability::setLevel);

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новую способность";
    }

    @Override
    protected String getEditFormWindowTitle(Ability entity) {
        return String.format("Способность спортсмена %s - изменить", entity.getSportsman().getName());
    }
}
