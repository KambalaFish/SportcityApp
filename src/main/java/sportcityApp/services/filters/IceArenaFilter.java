package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IceArenaFilter implements Filter{
    private Double minSquare;
    private Double maxSquare;
}
