package sportcityApp.gui.forms.filtering.impl;

import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.SneakyThrows;
import sportcityApp.entities.Entity;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.controllers.FxmlLoaderFactory;
import sportcityApp.gui.controllers.interfaces.ChoiceItemSupplier;
import sportcityApp.gui.custom.ChoiceItem;
import sportcityApp.gui.forms.filtering.FilterBoxBuilder;
import sportcityApp.services.Service;
import sportcityApp.services.filters.Filter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractFilterBoxBuilder/*<T extends Entity>*/ implements FilterBoxBuilder/*<T>*/ {
    /*new*/
    protected FilterBoxController controller;

    @Override
    @SneakyThrows
    public Node buildFilterBox(Filter/*<T>*/ filter) {
        var fxmlLoader = FxmlLoaderFactory.createFilterBoxLoader();
        Parent rootNode = fxmlLoader.load();
        //FilterBoxController/*<T>*/ controller = fxmlLoader.getController();
        controller = fxmlLoader.getController();
        controller.init();
        fillFilterBox(controller, filter);
        return rootNode;
    }
    protected abstract void fillFilterBox(FilterBoxController/*<T>*/ controller, Filter/*Filter<T>*/ filter);

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
