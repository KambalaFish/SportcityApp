package sportcityApp.gui.custom;

import lombok.Getter;

@Getter
public class ValidationInfo {
    private final boolean isValid;
    private final String headerText;
    private final String contextText;

    public ValidationInfo(boolean isValid, String headerText, String contextText){
        this.isValid = isValid;
        this.headerText = headerText;
        this.contextText = contextText;
    }

}
