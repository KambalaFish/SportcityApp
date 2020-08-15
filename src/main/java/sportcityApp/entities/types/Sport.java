package sportcityApp.entities.types;

import sportcityApp.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Sport {
    football, tennis, hockey, volleyball, figureSkating, athletics;

    public static String toLocalizedString(Sport sport){
        switch (sport){
            case football:
                return "футбол";
            case tennis:
                return "теннис";
            case hockey:
                return "хоккей";
            case volleyball:
                return "воллейбол";
            case figureSkating:
                return "фигурное катание";
            case athletics:
                return "легкая атлетика";
            default:
                return "";
        }
    }

    public static List<ChoiceItem<Sport>> getChoiceItems(){
        return Arrays.stream(Sport.values()).map(s -> new ChoiceItem<>(s, Sport.toLocalizedString(s))).collect(Collectors.toList());
    }
}
