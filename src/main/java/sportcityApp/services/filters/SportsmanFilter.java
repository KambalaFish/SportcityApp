package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SportsmanFilter implements Filter<Sportsman>{
    private Sport sport;
    private Integer minLevel;
    private Integer maxLevel;
    private Integer coachId;
    private List<Sport> sportsOfSportsman = new ArrayList<>();
    public void addSport(Sport sport){
        if (sportsOfSportsman.stream().noneMatch(sport1 -> sport1 == sport))
            sportsOfSportsman.add(sport);
    }
    public void removeSport(Sport sport){
        sportsOfSportsman.removeIf(sport1 -> sport1 == sport);
    }
}
