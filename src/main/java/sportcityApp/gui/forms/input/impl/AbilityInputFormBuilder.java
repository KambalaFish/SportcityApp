package sportcityApp.gui.forms.input.impl;

import sportcityApp.entities.Ability;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.services.SportsmanService;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.List;

public class AbilityInputFormBuilder extends AbstractEntityInputFormBuilder<Ability> {
    Sportsman sportsman;
    SportsmanService sportsmanService;
    public AbilityInputFormBuilder(RequestExecutor requestExecutor, Sportsman sportsman){
        super(Ability::new, ServiceFactory.getAbilityService(), requestExecutor);
        this.sportsman = sportsman;
        sportsmanService = ServiceFactory.getSportsmanService();
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
        /*
        controller.addChoiceBox(
                "Вид спорта",
                ability.getSport(),
                ability::setSport,
                null,
                Sport::getChoiceItems
        );
        */
        controller.addChoiceBox(
                "Вид спорта",
                ability.getSport(),
                ability::setSport,
                null,
                () -> {
                    List<ChoiceItem<Sport>> list = Sport.getChoiceItems();
                    list.removeIf(sportChoiceItem -> sportsmanService.getAbilities(sportsman.getId(), PageInfo.getUnlimitedPageInfo()).getBody().getElementList().stream().anyMatch(ability1 -> ability1.getSport() == sportChoiceItem.getValue()));
                    return list;
                }
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
