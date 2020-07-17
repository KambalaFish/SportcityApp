package sportcityApp.gui.controllers.interfaces;

import sportcityApp.entities.Entity;

public interface ContextMenuAction<E extends Entity>  {
    void run(E entity);
}
