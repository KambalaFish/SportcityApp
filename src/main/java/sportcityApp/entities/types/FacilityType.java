package sportcityApp.entities.types;

import sportcityApp.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum  FacilityType {

    court, stadium, iceArena, volleyballArena;

    public static String toLocalizedString(FacilityType facilityType){
        switch (facilityType){
            case court:
                return "корт";
            case stadium:
                return "стадион";
            case iceArena:
                return "ледовый дворец";
            case volleyballArena:
                return "воллейбольная арена";
            default:
                return "";
        }
    }

    public static List<ChoiceItem<FacilityType>> getChoiceItems(){
        return Arrays.stream(FacilityType.values()).map(facilityType -> new ChoiceItem<>(facilityType, FacilityType.toLocalizedString(facilityType))).collect(Collectors.toList());
    }

}
