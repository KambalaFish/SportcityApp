package sportcityApp.gui.forms.input.impl;

import javafx.scene.Parent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import sportcityApp.entities.Entity;
import sportcityApp.gui.AlertDialogFactory;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.FxmlLoaderFactory;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplierForM2MOwned;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.gui.custom.ChoiceItemForM2MOwned;
import sportcityApp.gui.forms.StageFactory;
import sportcityApp.gui.forms.input.LinkingInputFormBuilderForOwned;
import sportcityApp.services.Service;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.RequestExecutor;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*E - это владелец отношение(спортсмен), T - это замапленная сторона(тренера)*/
public abstract class AbstractLinkingInputFormBuilderForOwned <E extends Entity, T extends Entity> implements LinkingInputFormBuilderForOwned<T> {

    private final RequestExecutor requestExecutor;
    private final Service<E> entityService;

    protected AbstractLinkingInputFormBuilderForOwned(RequestExecutor requestExecutor, Service<E> entityService){
        this.requestExecutor = requestExecutor;
        this.entityService = entityService;
    }

    protected abstract String getLinkingWindowTitle();

    protected abstract void fillInputForm(T entity, EntityInputFormController<E> controller);

    @SneakyThrows
    public Stage buildLinkingWindow(T entity, SuccessAction onSuccessAction) {
        var fxmlLoader = FxmlLoaderFactory.createEntityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        EntityInputFormController<E> controller = fxmlLoader.getController();

        controller.init(null,
                entityService::save,
                onSuccessAction,
                errorMessage -> AlertDialogFactory.showErrorAlertDialog(
                        String.format("Произошла ошибка при изменении сущности %d!", entity.getId()),
                        errorMessage
                ),
                requestExecutor);

        fillInputForm(entity, controller);
        String windowTitle = getLinkingWindowTitle();
        return StageFactory.createStage(rootNode, windowTitle);
    }

    protected <E extends Entity, T extends Entity> ChoiceItemSupplierForM2MOwned<E, T> makeChoiceItemSupplierFromEntitiesForM2MOwned(
            Service<E> entityService,
            Function<E, ChoiceItemForM2MOwned<E, T>> entityToChoiceItemMapper,
            String errorMessage
    )
    {
        return makeChoiceItemSupplierFromEntitiesForM2MOwned(
                entityService,
                x -> true,
                entityToChoiceItemMapper,
                errorMessage
        );
    }

    protected <E extends Entity, T extends Entity> ChoiceItemSupplierForM2MOwned<E, T> makeChoiceItemSupplierFromEntitiesForM2MOwned(
            Service<E> entityService,
            Predicate<E> entityFilterPredicate,
            Function<E, ChoiceItemForM2MOwned<E, T>> entityToChoiceItemMapper,
            String errorMessage
    )
    {
        return () -> {
            try {
                Page<E> page = entityService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, errorMessage);

                return page.getElementList().stream()
                        .filter(entityFilterPredicate)
                        .map(entityToChoiceItemMapper)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException(errorMessage, e);
            }

        };
    }

}