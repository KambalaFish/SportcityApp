package sportcityApp.gui.forms.filtering;

import javafx.scene.Node;
import sportcityApp.entities.Entity;
import sportcityApp.services.filters.Filter;

public interface FilterBoxBuilder<T extends Entity> {
    Node buildFilterBox(Filter<T> filter);
}
