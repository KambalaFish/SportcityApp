package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Sportsman;
import sportcityApp.entities.types.Sport;

@Getter
@Setter
public class SportsmanFilter implements Filter<Sportsman>{
    private Sport sport;
    private Integer minLevel;
    private Integer maxLevel;
}
