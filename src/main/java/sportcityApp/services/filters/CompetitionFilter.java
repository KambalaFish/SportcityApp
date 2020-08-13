package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Competition;

import java.util.Date;

@Getter
@Setter
public class CompetitionFilter implements Filter<Competition>{
    private Date minPeriod;
    private Date maxPeriod;
    private Integer organizerId;
}
