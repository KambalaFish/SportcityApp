package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class VolleyballArena extends Entity{

    private SportFacility sportFacility;

    private Double net_height;
    private Double net_width;

    /*new*/
    public void addNewCompetition(Competition competition){
        sportFacility.addNewCompetition(competition);
    }
    /*new*/
    public void removeCompetition(Competition competition){
        sportFacility.removeCompetition(competition);
    }

    @Override
    public VolleyballArena clone(){
        var clone = (VolleyballArena) super.clone();
        if(sportFacility!=null)
            clone.setSportFacility(sportFacility.clone());
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
        propertyNames.put("net_height", "Высота сетки");
        propertyNames.put("net_width", "Ширина сетки");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("net_height", "Высота сетки");
        sortPropertyNames.put("net_width", "Ширина сетки");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
