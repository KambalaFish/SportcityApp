package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SportsmanFilter implements Filter/*<Sportsman>*/{
    private Sport sport;
    private Integer minLevel;
    private Integer maxLevel;
    private Integer coachId;
    private Date minPeriod;
    private Date maxPeriod;
    private List<Sport> sportList = new ArrayList<>();
    //private boolean sportsmenWithOverOneSport;
    public void addSport(Sport sport){
        sportList.add(sport);
    }
    public void removeSport(Sport sport){
        sportList.remove(sport);
    }
}
