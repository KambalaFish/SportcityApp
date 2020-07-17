package sportcityApp.gui.controllers.interfaces;

import javafx.stage.Stage;
import sportcityApp.entities.Entity;

public interface ContextWindowBuilder <E extends Entity>{
    Stage buildWindow(E entity);
}
