package sportcityApp.gui.forms.input;

import javafx.stage.Stage;
import sportcityApp.entities.Entity;
import sportcityApp.gui.controllers.interfaces.SuccessAction;

public interface LinkingInputFormBuilderForOwned<T extends Entity> {
    Stage buildLinkingWindow(T entity, SuccessAction successAction);
}
