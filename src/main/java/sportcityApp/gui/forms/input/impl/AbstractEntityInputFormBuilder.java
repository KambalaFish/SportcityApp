package sportcityApp.gui.forms.input.impl;

import javafx.scene.Parent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import sportcityApp.gui.AlertDialogFactory;
import sportcityApp.gui.controllers.EntityInputFormController;
import sportcityApp.gui.controllers.FxmlLoaderFactory;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.StageFactory;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.entities.Entity;
import sportcityApp.services.Service;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;
import sportcityApp.utils.RequestExecutor;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractEntityInputFormBuilder <E extends Entity> implements EntityInputFormBuilder<E> {

    private final RequestExecutor requestExecutor;
    private final Supplier<E> newEntitySupplier;
    private final Service<E> entityService;

    protected enum FormType{
        EDIT_FORM,
        CREATION_FORM
    }

    protected AbstractEntityInputFormBuilder(Supplier<E> newEntitySupplier, Service<E> entityService, RequestExecutor requestExecutor){
        this.newEntitySupplier = newEntitySupplier;
        this.entityService = entityService;
        this.requestExecutor = requestExecutor;
    }

    public Stage buildCreationFormWindow(SuccessAction onSuccessAction){
        E entity = newEntitySupplier.get();
        return buildInputFormWindow(entity, FormType.CREATION_FORM, false, onSuccessAction);
    }

    @Override
    public Stage buildEditFormWindow(E entity, SuccessAction onSuccessAction) {
        return buildInputFormWindow(
                entity, FormType.EDIT_FORM, false, onSuccessAction
        );
    }

    public Stage buildContextCreationFormWindow(E entity, SuccessAction onSuccessAction) {
        return buildInputFormWindow(
                entity, FormType.CREATION_FORM, true, onSuccessAction
        );
    }

    public Stage buildContextEditFormWindow(E entity, SuccessAction onSuccessAction) {
        return buildInputFormWindow(
                entity, FormType.EDIT_FORM, true, onSuccessAction
        );
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

    protected abstract void fillInputForm(E entity, FormType formType, boolean isContextWindow, EntityInputFormController<E> controller);

    protected abstract String getCreationFormWindowTitle();

    protected abstract String getEditFormWindowTitle(E entity);

    @SneakyThrows
    private Stage buildInputFormWindow(E entity, FormType formType, boolean isContextWindow, SuccessAction onSuccessAction){
        var fxmlLoader = FxmlLoaderFactory.createEntityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        EntityInputFormController<E>  controller = fxmlLoader.getController();

        switch (formType){
            case CREATION_FORM:
                controller.init(entity,
                        entityService::create,
                        onSuccessAction,
                        errorMessage -> AlertDialogFactory.showErrorAlertDialog("Произошла ошибка при добавлении новой сущности!", errorMessage),
                        requestExecutor);
                break;
            case EDIT_FORM:
                controller.init(entity,
                        entityService::save,
                        onSuccessAction,
                        errorMessage -> AlertDialogFactory.showErrorAlertDialog(
                                String.format("Произошла ошибка при изменении сущности %d!", entity.getId()),
                                errorMessage
                        ),
                        requestExecutor);
                break;
        }

        fillInputForm(entity, formType, isContextWindow, controller);
        String windowTitle = (formType == FormType.EDIT_FORM)? getEditFormWindowTitle(entity) : getCreationFormWindowTitle();
        return StageFactory.createStage(rootNode, windowTitle);
    }

}
