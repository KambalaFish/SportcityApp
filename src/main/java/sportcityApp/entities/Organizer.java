package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Organizer extends Entity{

    private String name;

    private List<Competition> competitions = new ArrayList<>();

    /*new*/
    public void addNewCompetition(Competition competition){
        if (competitions.stream().noneMatch(competition1 -> competition1.getId().intValue() == competition.getId().intValue()))
            competitions.add(competition);

    }
    /*new*/
    public void removeCompetition(Competition competition){
        competitions.removeIf(competition1 -> competition1.getId().intValue() == competition.getId().intValue());
    }

    @Override
    public Organizer clone(){
        var clone = (Organizer) super.clone();
        return clone;
    }

    @Override
    public void calculateProperties() {
        super.calculateProperties();
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "ФИО организатора");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "ФИО организатора");
    }

    public static Map<String, String> getPropertyNames(){
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames(){
        return Collections.unmodifiableMap(sortPropertyNames);
    }
}
