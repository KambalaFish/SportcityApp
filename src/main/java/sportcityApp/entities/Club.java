package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Club extends Entity{
    private String name;
    private Integer amount_of_members = 0;
    private List<Sportsman> sportsmen = new ArrayList<>();


    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Название");
        propertyNames.put("amount_of_members", "Общее число членов клуба");
        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Названию клуба");
        sortPropertyNames.put("amount_of_members", "Числу членов");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
