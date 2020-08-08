package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class SportFacility extends Entity{

    private Court court;
    private Stadium stadium;
    private IceArena iceArena;
    private VolleyballArena volleyballArena;
    private List<Competition> competitions = new ArrayList<>();

    @Override
    public SportFacility clone(){
        var clone = (SportFacility) super.clone();
        if (court!=null)
            clone.setCourt(court.clone());
        if (stadium!=null)
            clone.setStadium(stadium.clone());
        if (iceArena!=null)
            clone.setIceArena(iceArena.clone());
        if (volleyballArena!=null)
            clone.setVolleyballArena(volleyballArena.clone());
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

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
