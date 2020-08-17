package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Club;

import java.util.Date;

@Getter
@Setter
public class ClubFilter implements Filter<Club>{
    private Date minPeriod;
    private Date maxPeriod;
}
