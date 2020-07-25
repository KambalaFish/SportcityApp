package sportcityApp.gui.forms.input;

import javafx.stage.Stage;
import sportcityApp.entities.Entity;
import sportcityApp.gui.controllers.interfaces.SuccessAction;

public interface LinkingInputFormBuilder<E extends Entity> {
    Stage buildLinkingWindow(E entity, SuccessAction successAction);
}
