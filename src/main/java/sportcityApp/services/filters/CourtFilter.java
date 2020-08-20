package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;
import sportcityApp.entities.types.CoverageType;

@Getter
@Setter
public class CourtFilter implements Filter{
    private CoverageType coverageType;
}
