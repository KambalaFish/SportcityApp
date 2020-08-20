package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.Stadium;

@Getter
@Setter
public class StadiumFilter implements Filter/*<Stadium>*/ {
    Integer minCapacity;
    Integer maxCapacity;
}
