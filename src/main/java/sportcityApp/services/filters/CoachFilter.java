package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Coach;
import sportcityApp.entities.types.Sport;

@Getter
@Setter
public class CoachFilter implements Filter{
    private Sport sport;
}
