package sportcityApp.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Sportsman extends Entity {

    private String name;
    private Club club;
    private String clubName;

    private List<Coach> coaches = new ArrayList<>();
    private List<Competition> competitions = new ArrayList<>();
    private List<Ability> abilities = new ArrayList<>();/*new*/
    private List<Competition> wonCompetitions = new ArrayList<>();

    public void addNewCoach(Coach coach){
        if(!coaches.contains(coach))
            coaches.add(coach);
    }

    public void removeCoach(Coach coach){
            coaches.remove(coach);
    }

    public void removeCoachById(Integer id){
        coaches.removeIf(coach -> coach.getId().intValue() == id);
    }

    public void addNewCompetition(Competition competition){
        competitions.add(competition);
    }

    public void removeCompetition(Competition competition){
        competitions.remove(competition);
    }

    public void removeCompetitionById(Integer id){
        competitions.removeIf(competition -> competition.getId().intValue() == id);
    }

    @Override
    public Sportsman clone(){
        var clone = (Sportsman) super.clone();
        return clone;
    }

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        clubName = club.getName();
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "ФИО спортсмена");
        propertyNames.put("clubName", "Клуб");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Имени спортсмена");
        sortPropertyNames.put("club", "Имени клуба");
    }

    public static Map<String, String> getPropertyNames(){
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames(){
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
