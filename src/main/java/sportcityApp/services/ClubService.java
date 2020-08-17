package sportcityApp.services;

import sportcityApp.entities.Club;
import sportcityApp.services.filters.ClubFilter;

public interface ClubService extends Service<Club>{
    ServiceResponse<Integer> getNumberOfSportsmanInTheClubDuringPeriod(Integer clubId, ClubFilter filter);
}
