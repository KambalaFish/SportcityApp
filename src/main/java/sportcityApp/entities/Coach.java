package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.types.Sport;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Coach extends Entity{

    private String name;
    private Sport sport;

    private String sportNameProperty;

    @Override
    public Coach clone(){
        var clone = (Coach) super.clone();
        return clone;
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
        sportNameProperty = Sport.toLocalizedString(sport);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name","ФИО тренера");
        propertyNames.put("sportNameProperty","Вид спорта");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "ФИО тренера");
        sortPropertyNames.put("sportPropertyNames", "Вид спорта");
    }

    public static Map<String, String> getPropertyNames(){
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames(){
        return Collections.unmodifiableMap(sortPropertyNames);
    }
}
