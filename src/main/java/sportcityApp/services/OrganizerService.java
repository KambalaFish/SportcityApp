package sportcityApp.services;

import sportcityApp.entities.Competition;
import sportcityApp.entities.Organizer;
import sportcityApp.services.filters.DateFilter;
import sportcityApp.services.pagination.Page;
import sportcityApp.services.pagination.PageInfo;

public interface OrganizerService extends Service<Organizer>{
    ServiceResponse<Page<Competition>> getCompetitions(Integer organizerId, PageInfo pageInfo);

    ServiceResponse<Void> removeCompetitionFromOrganizer(Integer organizerId, Integer competitionId);

    ServiceResponse<Integer> getNumberOfCompetitonsForPeriod(Integer organizerId, DateFilter organizerFilter);
}
