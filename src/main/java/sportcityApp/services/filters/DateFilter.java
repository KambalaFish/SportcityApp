package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DateFilter implements Filter{
    private Date minPeriod;
    private Date maxPeriod;
}
