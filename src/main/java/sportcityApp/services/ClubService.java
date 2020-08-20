package sportcityApp.services;

import sportcityApp.entities.Club;
import sportcityApp.services.filters.DateFilter;

public interface ClubService extends Service<Club>{
    ServiceResponse<Integer> getNumberOfSportsmanInTheClubDuringPeriod(Integer clubId, DateFilter filter);

}
