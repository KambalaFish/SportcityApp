package sportcityApp.services.filters;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolleyballArenaFilter implements Filter{
    private Double minNetHeight;
    private Double maxNetHeight;
    private Double minNetWidth;
    private Double maxNetWidth;
}
