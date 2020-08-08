package sportcityApp.gui.forms.filtering.impl;

import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.SneakyThrows;
import sportcityApp.entities.Entity;
import sportcityApp.gui.controllers.FilterBoxController;
import sportcityApp.gui.controllers.FxmlLoaderFactory;
import sportcityApp.gui.forms.filtering.FilterBoxBuilder;
import sportcityApp.services.filters.Filter;

public abstract class AbstractFilterBoxBuilder<T extends Entity> implements FilterBoxBuilder<T> {

    @Override
    @SneakyThrows
    public Node buildFilterBox(Filter<T> filter) {
        var fxmlLoader = FxmlLoaderFactory.createFilterBoxLoader();
        Parent rootNode = fxmlLoader.load();
        FilterBoxController<T> controller = fxmlLoader.getController();
        controller.init();
        fillFilterBox(controller, filter);
        return rootNode;
    }
    protected abstract void fillFilterBox(FilterBoxController<T> controller, Filter<T> filter);
}
