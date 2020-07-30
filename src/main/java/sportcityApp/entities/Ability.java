package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.types.Sport;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Ability extends Entity{
    private Sportsman sportsman = new Sportsman();

    private Sport sport;
    private Integer level;

    private String sportsmanName;
    private String sportProperty;

    @Override
    public Ability clone(){
        var clone = (Ability) super.clone();
        clone.setSportsman(sportsman.clone());
        return clone;
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
        sportsmanName = sportsman.getName();
        sportProperty = Sport.toLocalizedString(sport);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("sportsmanName", "Спортсмен");
        propertyNames.put("sportProperty","Вид спорта");
        propertyNames.put("level", "Разряд");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("sportsmanName", "Имя спортсмена");
        sortPropertyNames.put("sportProperty", "Спорт");
        sortPropertyNames.put("level", "Разряд");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
