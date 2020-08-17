package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Club extends Entity{
    private String name;
    private List<Sportsman> sportsmen = new ArrayList<>();


    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Название");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Название клуба");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
