package sportcityApp.gui.forms.input.impl.For;

import sportcityApp.entities.Competition;
import sportcityApp.entities.SportFacility;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.input.impl.AbstractLinkingInputFormBuilder;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.util.function.Predicate;

public class SportFacilityForCompetitionInputFormBuilder extends AbstractLinkingInputFormBuilder<Competition> {

    public SportFacilityForCompetitionInputFormBuilder(RequestExecutor requestExecutor){
        super(requestExecutor, ServiceFactory.getCompetitionService());
    }

    @Override
    protected String getLinkingWindowTitle() {
        return "Добавить спортивное сооружение";
    }

    @Override
    protected void fillInputForm(Competition entity, EntityInputFormController<Competition> controller) {
        Predicate<SportFacility> predicate = sportFacility -> {
            switch (entity.getSport()){
                case football:
                    if (sportFacility.getStadium()!=null){
                        //return true;
                        return !entity.isThereSportFacilityWithSuchId(sportFacility.getId());
                    }
                    break;
                case hockey:
                    if(sportFacility.getIceArena()!=null){
                        //return true;
                        return !entity.isThereSportFacilityWithSuchId(sportFacility.getId());
                    }
                    break;
                case tennis:
                    if(sportFacility.getCourt()!=null){
                        return !entity.isThereSportFacilityWithSuchId(sportFacility.getId());
                        //return true;
                    }
                    break;
                case volleyball:
                    if(sportFacility.getVolleyballArena()!=null){
                        //return true;
                        return !entity.isThereSportFacilityWithSuchId(sportFacility.getId());
                    }
                    break;
            }
            return false;
        };

        ChoiceItemSupplier<SportFacility> sportFacilitySupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getSportFacilityService(),
                predicate,
                sportFacility -> new ChoiceItem<>(sportFacility, sportFacility.getId().toString()),
                "Не удалось загрузить список спортивных сооружений"
        );
        controller.addChoiceBox("Спортивное сооружение", null, entity::addNewSportFacility, entity::removeSportFacility, sportFacilitySupplier);
    }
}
