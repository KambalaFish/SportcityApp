package sportcityApp.gui.forms.input;

import javafx.stage.Stage;
import sportcityApp.gui.controllers.interfaces.SuccessAction;
import sportcityApp.entities.Entity;

public interface EntityInputFormBuilder <E extends Entity>{
    Stage buildCreationFormWindow(SuccessAction onSuccessAction);

    Stage buildEditFormWindow(E entity, SuccessAction onSuccessAction);

    Stage buildContextCreationFormWindow(E entity, SuccessAction onSuccessAction);

    Stage buildContextEditFormWindow(E entity, SuccessAction onSuccessAction);
}
