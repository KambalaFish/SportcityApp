package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.types.CoverageType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Court extends Entity{

    private SportFacility sportFacility;

    private CoverageType coverageType;
    private String coverageTypeProperty;

    @Override
    public Court clone(){
        var clone = (Court) super.clone();
        return clone;
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
        coverageTypeProperty = CoverageType.toLocalizedString(coverageType);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("coverageTypeProperty", "Тип покрытия");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("coverageType", "Тип покрытия");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
