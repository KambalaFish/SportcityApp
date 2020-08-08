package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class IceArena extends Entity{

    private SportFacility sportFacility;

    private Integer square;

    @Override
    public IceArena clone(){
        var clone = (IceArena) super.clone();
        //if(sportFacility!=null)
            //clone.setSportFacility(sportFacility.clone());
        return clone;
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("square", "Площадь");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("square", "Площадь");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }
}
