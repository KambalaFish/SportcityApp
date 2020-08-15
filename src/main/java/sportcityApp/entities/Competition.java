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
    private Date beginningDate;
    private Date finishDate;
    private Sport sport;

    private List<Sportsman> sportsmen = new ArrayList<>();
    private List<Organizer> organizers = new ArrayList<>();
    private List<SportFacility> sportFacilities = new ArrayList<>();
    private List<Sportsman> prizeWinners = new ArrayList<>();

    public boolean isThereSportFacilityWithSuchId(int id){
        return sportFacilities.stream().anyMatch(sportFacility -> sportFacility.getId() == id);
    }

    public void addNewOrganizer(Organizer organizer){
        organizers.add(organizer);
    }

    public void removeOrganizer(Organizer organizer){
        organizers.remove(organizer);
    }


    public void removeOrganizerById(Integer id){
        organizers.removeIf( organizer -> organizer.getId().intValue() == id);
    }

    public void removeSportFacilityById(Integer id){
        sportFacilities.removeIf( sportFacility -> sportFacility.getId().intValue() == id.intValue());
    }

    public void addNewSportFacility(SportFacility sportFacility){
        sportFacilities.add(sportFacility);
    }

    public void removeSportFacility(SportFacility sportFacility){
        sportFacilities.remove(sportFacility);
    }

    public void addNewCourt(Court court){
        sportFacilities.add(court.getSportFacility());
    }

    public void removeCourt(Court court){
        sportFacilities.remove(court.getSportFacility());
    }

    public void addNewStadium(Stadium stadium){
        sportFacilities.add(stadium.getSportFacility());
    }

    public void removeStadium(Stadium stadium){
        sportFacilities.remove(stadium.getSportFacility());
    }

    public void addNewIceArena(IceArena iceArena){
        sportFacilities.add(iceArena.getSportFacility());
    }

    public void removeIceArena(IceArena iceArena){
        sportFacilities.remove(iceArena.getSportFacility());
    }

    public void addNewPrizeWinner(Sportsman prizeWinner){
        if(prizeWinners.stream().noneMatch(prizeWinner1 -> prizeWinner1.getId().intValue() == prizeWinner.getId().intValue()))
            prizeWinners.add(prizeWinner);
    }

    public void removePrizeWinner(Sportsman prizeWinner){
        prizeWinners.removeIf(prizeWinner1 -> prizeWinner1.getId().intValue() == prizeWinner.getId().intValue());
    }

    public void removePrizeWinnerById(Integer id){
        prizeWinners.removeIf( prizeWinner -> prizeWinner.getId().intValue() == id.intValue());
    }

    public void addNewVolleyballArena(VolleyballArena volleyballArena){
        sportFacilities.add(volleyballArena.getSportFacility());
    }

    public void removeVolleyballArena(VolleyballArena volleyballArena){
        sportFacilities.remove(volleyballArena.getSportFacility());
    }

    private String beginningDateProperty;
    private String finishDateProperty;
    private String sportProperty;

    @Override
    public Competition clone(){
        return (Competition) super.clone();
    }

    @Override
    public void calculateProperties(){
        super.calculateProperties();
        sportProperty = Sport.toLocalizedString(sport);
        beginningDateProperty = LocalDateFormatter.getFormattedDate(beginningDate);
        finishDateProperty = LocalDateFormatter.getFormattedDate(finishDate);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Название");
        propertyNames.put("beginningDateProperty", "Дата начала");
        propertyNames.put("finishDateProperty", "Дата окончания");
        propertyNames.put("sportProperty", "Вид спорта");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Название");
        sortPropertyNames.put("beginningDate", "Дата начала");
        sortPropertyNames.put("finishDate", "Дата окончания");
    }

    public static Map<String, String> getPropertyNames(){
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames(){
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}

