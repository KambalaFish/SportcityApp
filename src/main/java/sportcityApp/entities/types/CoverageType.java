package sportcityApp.entities.types;

import sportcityApp.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum  CoverageType {
    grass, clay;

    public static String toLocalizedString(CoverageType coverageType){
        switch (coverageType){
            case grass:
                return "трава";
            case clay:
                return "грунт";
            default:
                return "";
        }
    }

    public static List<ChoiceItem<CoverageType>> getChoiceItems(){
        return Arrays.stream(CoverageType.values()).map(с -> new ChoiceItem<>(с, CoverageType.toLocalizedString(с))).collect(Collectors.toList());
    }

}
