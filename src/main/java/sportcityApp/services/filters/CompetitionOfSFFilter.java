package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Competition;
import sportcityApp.entities.types.Sport;

import java.util.Date;

@Getter
@Setter
public class CompetitionOfSFFilter implements Filter/*<Competition>*/{

    private Integer sportFacilityID;
    private Sport sport;
    private Date minPeriod;
    private Date maxPeriod;

}
