package sportcityApp.gui.forms.input.impl;

import javafx.scene.Parent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import sportcityApp.entities.Entity;
import sportcityApp.gui.AlertDialogFactory;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.FxmlLoaderFactory;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.StageFactory;
import sportcityApp.gui.forms.input.LinkingInputFormBuilder;
import sportcityApp.services.Service;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.RequestExecutor;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractLinkingInputFormBuilder <E extends Entity> implements LinkingInputFormBuilder<E> {

    private final RequestExecutor requestExecutor;
    private final Service<E> entityService;


    protected AbstractLinkingInputFormBuilder(RequestExecutor requestExecutor, Service<E> entityService){
        this.requestExecutor = requestExecutor;
        this.entityService = entityService;
    }

    protected abstract String getLinkingWindowTitle();

    protected abstract void fillInputForm(E entity, EntityInputFormController<E> controller);


    @SneakyThrows
    public Stage buildLinkingWindow(E entity, SuccessAction onSuccessAction) {
        var fxmlLoader = FxmlLoaderFactory.createEntityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        EntityInputFormController<E> controller = fxmlLoader.getController();

        controller.init(entity,
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


    protected <X extends Entity, Y> ChoiceItemSupplier<Y> makeChoiceItemSupplierFromEntities(
            Service<X> entityService,
            Function<X, ChoiceItem<Y>> entityToChoiceItemMapper,
            String errorMessage
    ) {
        return makeChoiceItemSupplierFromEntities(
                entityService,
                x -> true,
                entityToChoiceItemMapper,
                errorMessage
        );
    }

    protected <X extends Entity, Y> ChoiceItemSupplier<Y> makeChoiceItemSupplierFromEntities(
            Service<X> entityService,
            Predicate<X> entityFilterPredicate,
            Function<X, ChoiceItem<Y>> entityToChoiceItemMapper,
            String errorMessage
    ) {
        return () -> {
            try {
                Page<X> page = entityService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
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
