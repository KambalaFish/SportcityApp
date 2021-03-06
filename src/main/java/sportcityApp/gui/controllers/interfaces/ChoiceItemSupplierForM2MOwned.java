package sportcityApp.gui.controllers.interfaces;

import sportcityApp.gui.custom.ChoiceItemForM2MOwned;

import java.util.Collection;

public interface ChoiceItemSupplierForM2MOwned<X, T> {
    Collection<ChoiceItemForM2MOwned<X, T>> getItems() throws Exception;
}
