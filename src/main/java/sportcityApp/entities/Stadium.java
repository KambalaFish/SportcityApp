package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Stadium extends Entity{

    private SportFacility sportFacility;

    private Integer capacity;

    /*new*/
    public void addNewCompetition(Competition competition){
        sportFacility.addNewCompetition(competition);
    }
    /*new*/
    public void removeCompetition(Competition competition){
        sportFacility.removeCompetition(competition);
    }

    @Override
    public Stadium clone(){
        var clone = (Stadium) super.clone();
        if(sportFacility!=null)
            clone.setSportFacility(sportFacility.clone());
        return clone;
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
        //sportFacility.setId(super.getId());
        //sportFacility.setStadium(this);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("capacity", "Вместимость");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("capacity", "Вместимости");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }
}
