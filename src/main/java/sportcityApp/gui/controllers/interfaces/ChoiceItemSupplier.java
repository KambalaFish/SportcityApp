package sportcityApp.gui.controllers.interfaces;

import sportcityApp.gui.custom.ChoiceItem;

import java.util.Collection;

public interface ChoiceItemSupplier<X> {
    Collection<ChoiceItem<X>> getItems() throws Exception;
}
