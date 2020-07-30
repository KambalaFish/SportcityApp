package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.types.Sport;
import sportcityApp.utils.LocalDateFormatter;

import java.util.*;

@Getter
@Setter
public class Competition extends Entity{

    private String name;
    private Date date;
    private Sport sport;

    private List<Sportsman> sportsmen = new ArrayList<>();
    private List<Organizer> organizers = new ArrayList<>();

    public void addNewOrganizer(Organizer organizer){
        organizers.add(organizer);
    }

    public void removeOrganizer(Organizer organizer){
        organizers.remove(organizer);
    }

    public void removeOrganizerById(Integer id){
        organizers.removeIf( organizer -> organizer.getId().intValue() == id);
    }

    private String dateProperty;
    private String sportProperty;

    @Override
    public Competition clone(){
        return (Competition) super.clone();
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
        sportProperty = Sport.toLocalizedString(sport);
        dateProperty = LocalDateFormatter.getFormattedDate(date);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Название");
        propertyNames.put("dateProperty", "Дата проведения");
        propertyNames.put("sportProperty", "Вид спорта");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Название");
        sortPropertyNames.put("date", "Дата проведения соревнования");
    }

    public static Map<String, String> getPropertyNames(){
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames(){
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
