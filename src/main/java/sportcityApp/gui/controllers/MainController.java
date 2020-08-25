package sportcityApp.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.SneakyThrows;
import sportcityApp.entities.*;
import sportcityApp.entities.types.Sport;
import sportcityApp.gui.AlertDialogFactory;
import sportcityApp.gui.controllers.interfaces.ContextWindowBuilder;
import sportcityApp.gui.custom.ValidationInfo;
import sportcityApp.gui.forms.filtering.FilterBoxBuilder;
import sportcityApp.gui.forms.filtering.impl.*;
import sportcityApp.gui.forms.input.EntityInputFormBuilder;
import sportcityApp.gui.forms.input.LinkingInputFormBuilder;
import sportcityApp.gui.forms.input.LinkingInputFormBuilderForOwned;
import sportcityApp.gui.forms.input.impl.*;
import sportcityApp.gui.forms.input.impl.For.*;
import sportcityApp.services.*;
import sportcityApp.services.filters.*;
import sportcityApp.utils.LocalDateFormatter;
import sportcityApp.utils.RequestExecutor;
import sportcityApp.utils.ServiceFactory;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MainController {

    private RequestExecutor requestExecutor;

    @FXML
    private TabPane contentTabPane;

    @FXML
    private Label statusBarLabel;

    @FXML
    private Tab defaultTab;

    @SneakyThrows
    public void init(RequestExecutor requestExecutor){
        this.requestExecutor = requestExecutor;
        contentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }


    @FXML
    @SneakyThrows
    void openCoaches(){
        CoachService coachService = ServiceFactory.getCoachService();

        ContextWindowBuilder<Coach> infoWindowBuilder = coach -> {
            var sportsmanPropertyNames = new LinkedHashMap<>(Sportsman.getPropertyNames());
            var sportsmanSortPropertyNames = new LinkedHashMap<>(Sportsman.getSortPropertyNames());

            Node sportsmenOfCoachTable = createInfoWindowEntityTableForM2MOwned(
                    sportsmanPropertyNames,
                    sportsmanSortPropertyNames,
                    pageInfo -> coachService.getSportsmen(coach.getId(), pageInfo),/*T - это спортсмен в ентитиТэйблКонтроллерере*/
                    new SportsmanInputFormBuilder(requestExecutor),
                    new SportsmanForCoachInputFormBuilder(requestExecutor),
                    () -> coach,
                    coachService::removeSportsmanFromCoach,
                    true,
                    null
            );


            return EntityInfoWindowBuilder.newInfoWindow(coach.getName()).addTab(sportsmenOfCoachTable, "Спортсмены").build();
        };


        createEntityTable(
                "Тренеры",
                Coach.getPropertyNames(),
                Coach.getSortPropertyNames(),
                coachService,
                new CoachInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                new CoachFilterBoxBuilder(),
                CoachFilter::new,
                true,
                true
        );

    }

    @FXML
    @SneakyThrows
    void openSportsmen(){
        SportsmanService sportsmanService = ServiceFactory.getSportsmanService();
        AbilityService abilityService = ServiceFactory.getAbilityService();

        ContextWindowBuilder<Sportsman> infoWindowBuilder = sportsman -> {
            var abilityPropertyNames = new LinkedHashMap<>(Ability.getPropertyNames());
            abilityPropertyNames.remove("sportsmanName");
            var abilitySortPropertyNames = new LinkedHashMap<>(Ability.getSortPropertyNames());
            abilitySortPropertyNames.remove("sportsmanName");


            Node abilitiesOfSportsmanTable = createInfoWindowEntityTable(
                    abilityPropertyNames,
                    abilitySortPropertyNames,
                    pageInfo -> sportsmanService.getAbilities(sportsman.getId(), pageInfo),
                    abilityService::deleteById,
                    new AbilityInputFormBuilder(requestExecutor, sportsman),
                    () -> {
                        Ability ability = new Ability();
                        ability.getSportsman().setId(sportsman.getId());
                        return ability;
                    },
                    true
            );

            Node coachesOfSportsmanTable = createInfoWindowEntityTableForM2M(
                    Coach.getPropertyNames(),
                    Coach.getSortPropertyNames(),
                    pageInfo -> sportsmanService.getCoaches(sportsman.getId(), pageInfo),
                    new CoachInputFormBuilder(requestExecutor),
                    new CoachForSportsmanInputFormBuilder(requestExecutor),
                    () -> sportsman,
                    sportsmanService::removeCoachFromSportsman,
                    sportsman::removeCoachById/*можно в принципе ставить null, но тогда нужно обновить перед добавлением, не удобно*/,
                    true
            );

            Node competitionsOfSportsmanTable = createInfoWindowEntityTableForM2M(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> sportsmanService.getCompetitions(sportsman.getId(), pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForSportsmanInputFormBuilder(requestExecutor),
                    () -> sportsman,
                    sportsmanService::removeCompetitionFromSportsman,
                    sportsman::removeCompetitionById,
                    true
            );

            return EntityInfoWindowBuilder.
                    newInfoWindow(sportsman.getName()).
                    addTab(abilitiesOfSportsmanTable, "Способности").
                    addTab(coachesOfSportsmanTable, "Тренеры").
                    addTab(competitionsOfSportsmanTable, "Соревнования").
                    build();
        };

        SportsmanFilterBoxBuilder filterBoxBuilder = new SportsmanFilterBoxBuilder();
        EntityTableController<Sportsman, Ability> controller = createEntityTable(
                "Спортсмены",
                Sportsman.getPropertyNames(),
                Sportsman.getSortPropertyNames(),
                sportsmanService,
                new SportsmanInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                filterBoxBuilder,
                SportsmanFilter::new,
                true,
                true
                );
        controller.setValidator(filterBoxBuilder::validate);
    }

    @FXML
    @SneakyThrows
    public void openCompetition(){
        CompetitionService competitionService = ServiceFactory.getCompetitionService();

        ContextWindowBuilder<Competition> infoWindowBuilder = competition ->{
            EntityInfoWindowBuilder.Builder builder = EntityInfoWindowBuilder.newInfoWindow(competition.getName());

            Node sportsmenOfTheCompetition = createInfoWindowEntityTableForM2MOwned(
                    Sportsman.getPropertyNames(),
                    Sportsman.getSortPropertyNames(),
                    pageInfo -> competitionService.getSportsmen(competition.getId(), pageInfo),
                    new SportsmanInputFormBuilder(requestExecutor),
                    new SportsmanForCompetitionInputFormBuilder(requestExecutor),
                    () -> competition,
                    competitionService::removeSportsmanFromCompetition,
                    true,
                    null
            );
            builder.addTab(sportsmenOfTheCompetition, "Спортсмены");

            Node organizersOfTheCompetition = createInfoWindowEntityTableForM2M(
                    Organizer.getPropertyNames(),
                    Organizer.getSortPropertyNames(),
                    pageInfo -> competitionService.getOrganizers(competition.getId(), pageInfo),
                    new OrganizerInputFormBuilder(requestExecutor),
                    new OrganizerForCompetitionInputFormBuilder(requestExecutor),
                    () -> competition,
                    competitionService::removeOrganizerFromCompetition,
                    competition::removeOrganizerById,
                    true
            );

            builder.addTab(organizersOfTheCompetition, "Организаторы");

            if (competition.getSport() == Sport.tennis){

                Node courtsOfTheCompetition = createInfoWindowEntityTableForM2M(
                        Court.getPropertyNames(),
                        Court.getSortPropertyNames(),
                        pageInfo -> competitionService.getCourts(competition.getId(), pageInfo),
                        new CourtInputFormBuilder(requestExecutor),
                        new CourtForCompetitionInputFormBuilder(requestExecutor),
                        () -> competition,
                        competitionService::removeSportFacilityFromCompetition,
                        competition::removeSportFacilityById,
                        true
                );
                builder.addTab(courtsOfTheCompetition, "Корты");
            }

            if(competition.getSport() == Sport.football | competition.getSport()==Sport.athletics){
                Node stadiumsOfTheCompetition = createInfoWindowEntityTableForM2M(
                        Stadium.getPropertyNames(),
                        Stadium.getSortPropertyNames(),
                        pageInfo -> competitionService.getStadiums(competition.getId(), pageInfo),
                        new StadiumInputFormBuilder(requestExecutor),
                        new StadiumForCompetitionInputFormBuilder(requestExecutor),
                        () -> competition,
                        competitionService::removeSportFacilityFromCompetition,
                        competition::removeSportFacilityById,
                        true
                );
                builder.addTab(stadiumsOfTheCompetition, "Стадионы");
            }

            if(competition.getSport() == Sport.hockey | competition.getSport() == Sport.figureSkating){
                Node iceArenasOfTheCompetition = createInfoWindowEntityTableForM2M(
                        IceArena.getPropertyNames(),
                        IceArena.getSortPropertyNames(),
                        pageInfo -> competitionService.getIceArenas(competition.getId(), pageInfo),
                        new IceArenaInputFormBuilder(requestExecutor),
                        new IceArenaForCompetitionInputFormBuilder(requestExecutor),
                        () -> competition,
                        competitionService::removeSportFacilityFromCompetition,
                        competition::removeSportFacilityById,
                        true
                );
                builder.addTab(iceArenasOfTheCompetition, "Ледовые арены");
            }

            if(competition.getSport()==Sport.volleyball){
                Node volleyballArenasOfTheCompetition = createInfoWindowEntityTableForM2M(
                        VolleyballArena.getPropertyNames(),
                        VolleyballArena.getSortPropertyNames(),
                        pageInfo -> competitionService.getVolleyballArenas(competition.getId(), pageInfo),
                        new VolleyballArenaInputFormBuilder(requestExecutor),
                        new VolleyballArenaForCompetitionInputFormBuilder(requestExecutor),
                        () -> competition,
                        competitionService::removeSportFacilityFromCompetition,
                        competition::removeSportFacilityById,
                        true
                );
                builder.addTab(volleyballArenasOfTheCompetition, "Волейбольные арены");
            }

            Node winnersOfTheCompetition = createInfoWindowEntityTableForM2M(
                    Sportsman.getPropertyNames(),
                    Sportsman.getSortPropertyNames(),
                    pageInfo -> competitionService.getWinners(competition.getId(), pageInfo),
                    new SportsmanInputFormBuilder(requestExecutor),
                    new PrizeWinnerForCompetition(requestExecutor),
                    () -> competition,
                    competitionService::removePrizeWinnerFromCompetition,
                    competition::removePrizeWinnerById,
                    true
            );

            builder.addTab(winnersOfTheCompetition, "Призеры");

            return builder.build();
        };
        CompetitionFilterBoxBuilder competitionFilterBoxBuilder = new CompetitionFilterBoxBuilder();
        EntityTableController<Competition, Sportsman> controller = createEntityTable(
                "Соревнования",
                Competition.getPropertyNames(),
                Competition.getSortPropertyNames(),
                competitionService,
                new CompetitionInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                competitionFilterBoxBuilder,
                CompetitionFilter::new,
                true,
                true
                );
        controller.setValidator(competitionFilterBoxBuilder::validate);
    }

    @FXML
    @SneakyThrows
    public void openOrganizer(){
        OrganizerService organizerService = ServiceFactory.getOrganizerService();

        DateFilterBoxBuilder filterBoxBuilder = new DateFilterBoxBuilder();
        DateFilter organizerFilter = new DateFilter();

        ContextWindowBuilder<Organizer> infoWindowBuilder = organizer -> {
            ValidationInfo info = filterBoxBuilder.validate();
            if (!info.isValid()) {
                AlertDialogFactory.showWarningAlertDialog(info.getHeaderText(), info.getContextText());
                return null;
            }
            else {
                EntityInfoWindowBuilder.Builder builder = EntityInfoWindowBuilder.newInfoWindow(organizer.getName());

                Node competitionsOfTheOrganizer = createInfoWindowEntityTableForM2MOwned(
                        Competition.getPropertyNames(),
                        Competition.getSortPropertyNames(),
                        pageInfo -> organizerService.getCompetitions(organizer.getId(), pageInfo),
                        new CompetitionInputFormBuilder(requestExecutor),
                        new CompetitionForOrganizerInputFormBuilder(requestExecutor),
                        () -> organizer,
                        organizerService::removeCompetitionFromOrganizer,
                        true,
                        null
                );
                builder.addTab(competitionsOfTheOrganizer, "Cоревнования");

                FXMLLoader entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
                Parent entityInfoRoot = null;
                try {
                    entityInfoRoot = entityInfoLoader.load();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                EntityInfoController controller = entityInfoLoader.getController();
                requestExecutor
                        .makeRequest(() -> organizerService.getNumberOfCompetitonsForPeriod(organizer.getId(), organizerFilter))
                        .setOnSuccessAction(numberOfCompetitions -> Platform.runLater(() -> {
                            if (organizerFilter.getMinPeriod() == null & organizerFilter.getMaxPeriod() == null) {
                                controller.addInfoLine(String.format("Число соревнований, которые провел %s за всё время: %d", organizer.getName(), numberOfCompetitions));
                            } else {
                                controller.addInfoLine(String.format("Число соревнований, которые провел %s в период от %s до %s: %d", organizer.getName(),
                                        LocalDateFormatter.getFormattedDate(organizerFilter.getMinPeriod()),
                                        LocalDateFormatter.getFormattedDate(organizerFilter.getMaxPeriod()),
                                        numberOfCompetitions));
                            }
                        }))
                        .setOnFailureAction(errorMessage -> AlertDialogFactory.showErrorAlertDialog(
                                "Не удалось загрузить информацию о числе соревнований в указанный период",
                                errorMessage
                        ))
                        .submit();
                builder.addTab(entityInfoRoot, "Инфо о соревнованиях");

                return builder.build();
            }
        };

        createEntityTable(
                "Организаторы",
                Organizer.getPropertyNames(),
                Organizer.getSortPropertyNames(),
                organizerService,
                new OrganizerInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                filterBoxBuilder,
                () -> organizerFilter,
                true,
                false
        );
    }

    @FXML
    @SneakyThrows
    public void openSportFacilities(){

        SportFacilityService sportFacilityService = ServiceFactory.getSportFacilityService();

        CompetitionOfSFFilter filter = new CompetitionOfSFFilter();
        SportFacilityFilterBoxBuilder filterBoxBuilder = new SportFacilityFilterBoxBuilder();

        ContextWindowBuilder<SportFacility> infoWindowBuilder = sportFacility ->{

            ValidationInfo info = filterBoxBuilder.validate();

            if (!info.isValid()){
                AlertDialogFactory.showWarningAlertDialog(info.getHeaderText(), info.getContextText());
                return null;
            } else {
                EntityInfoWindowBuilder.Builder builder = EntityInfoWindowBuilder.newInfoWindow(sportFacility.getId().toString());
                filter.setSportFacilityID(sportFacility.getId());
                Node filterBox = new CompetitionOfSFFilterBoxBuilder().buildFilterBox(filter);

                Node competitionsOfTheSportFacility = createInfoWindowEntityTableForM2MOwned(
                        Competition.getPropertyNames(),
                        Competition.getSortPropertyNames(),
                        pageInfo -> sportFacilityService.getCompetitionsByFilter(filter, pageInfo),
                        new CompetitionInputFormBuilder(requestExecutor),
                        new CompetitionForSportFacilityInputFormBuilder(requestExecutor),
                        () -> sportFacility,
                        sportFacilityService::removeCompetitionFromSportFacility,
                        true,
                        filterBox
                );
                builder.addTab(competitionsOfTheSportFacility, "Соревнования");

                if (sportFacility.getCourt() != null) {
                    CourtService courtService = ServiceFactory.getCourtService();
                    Node courtNode = createInfoWindowEntityTableCreateButtonDisabled(
                            Court.getPropertyNames(),
                            Court.getSortPropertyNames(),
                            pageInfo -> courtService.getPageWithCourtById(sportFacility.getId(), pageInfo),
                            courtService::deleteById,
                            new CourtInputFormBuilder(requestExecutor),
                            null,
                            true
                    );
                    builder.addTab(courtNode, "Корт");
                }
                if (sportFacility.getIceArena() != null) {
                    IceArenaService iceArenaService = ServiceFactory.getIceArenaService();
                    Node iceArenaNode = createInfoWindowEntityTableCreateButtonDisabled(
                            IceArena.getPropertyNames(),
                            IceArena.getSortPropertyNames(),
                            pageInfo -> iceArenaService.getPageWithIceArenaById(sportFacility.getId(), pageInfo),
                            iceArenaService::deleteById,
                            new IceArenaInputFormBuilder(requestExecutor),
                            null,
                            true
                    );
                    builder.addTab(iceArenaNode, "Ледовая арена");
                }
                if (sportFacility.getStadium() != null) {
                    StadiumService stadiumService = ServiceFactory.getStadiumService();
                    Node stadiumNode = createInfoWindowEntityTableCreateButtonDisabled(
                            Stadium.getPropertyNames(),
                            Stadium.getSortPropertyNames(),
                            pageInfo -> stadiumService.getPageWithStadiumById(sportFacility.getId(), pageInfo),
                            stadiumService::deleteById,
                            new StadiumInputFormBuilder(requestExecutor),
                            null,
                            true
                    );
                    builder.addTab(stadiumNode, "Стадион");
                }
                if (sportFacility.getVolleyballArena() != null) {
                    VolleyballArenaService volleyballArenaService = ServiceFactory.getVolleyballArenaService();
                    Node volleyballArenaNode = createInfoWindowEntityTableCreateButtonDisabled(
                            VolleyballArena.getPropertyNames(),
                            VolleyballArena.getSortPropertyNames(),
                            pageInfo -> volleyballArenaService.getPageWithVolleyballArenaById(sportFacility.getId(), pageInfo),
                            volleyballArenaService::deleteById,
                            new VolleyballArenaInputFormBuilder(requestExecutor),
                            null,
                            true
                    );
                    builder.addTab(volleyballArenaNode, "Воллейбольная арена");
                }
                return builder.build();
            }
        };

        createEntityTable(
                "Спортивные сооружения",
                SportFacility.getPropertyNames(),
                SportFacility.getSortPropertyNames(),
                sportFacilityService,
                new SportFacilityInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                filterBoxBuilder,
                () -> filter,
                false,
                false
        );

    }

    @FXML
    @SneakyThrows
    public void openCourts(){
        CourtService courtService = ServiceFactory.getCourtService();


        ContextWindowBuilder<Court> infoWindowBuilder = court -> {

            /*
            Node competitionsOfTheCourt = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> courtService.getCompetitionsOfTheCourt(court.getId(), pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForCourtInputFormBuilder(requestExecutor),
                    () -> court,
                    courtService::removeCompetitionFromCourt,
                    true,
                    null
            );
            */

            CompetitionOfSFFilter filter = new CompetitionOfSFFilter();
            filter.setSportFacilityID(court.getId());
            Node filterBox = new CompetitionOfSFFilterBoxBuilder().buildFilterBox(filter);

            Node competitionsOfTheCourt = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> courtService.getCompetitionsOfTheCourtByFilter(filter, pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForCourtInputFormBuilder(requestExecutor),
                    () -> court,
                    courtService::removeCompetitionFromCourt,
                    true,
                    filterBox
            );

            return EntityInfoWindowBuilder.newInfoWindow("Корт № " + court.getId().toString()).
                    addTab(competitionsOfTheCourt, "Соревнования").
                    build();
        };

        createEntityTable(
                "Корты",
                Court.getPropertyNames(),
                Court.getSortPropertyNames(),
                courtService,
                new CourtInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                new CourtFilterBoxBuilder(),
                CourtFilter::new,
                true,
                true
        );
    }

    @FXML
    public void openStadium(){
        StadiumService stadiumService = ServiceFactory.getStadiumService();

        ContextWindowBuilder<Stadium> infoWindowBuilder = stadium -> {
            /*
            Node competitionsOfTheStadium = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> stadiumService.getCompetitionsOfTheStadium(stadium.getId(), pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForStadiumInputFormBuilder(requestExecutor),
                    () -> stadium,
                    stadiumService::removeCompetitionFromStadium,
                    true,
                    null
            );
            */
            CompetitionOfSFFilter filter = new CompetitionOfSFFilter();
            filter.setSportFacilityID(stadium.getId());
            Node filterBox = new CompetitionOfSFFilterBoxBuilder().buildFilterBox(filter);
            Node competitionsOfTheStadium = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> stadiumService.getCompetitionsOfTheStadiumByFilter(filter, pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForStadiumInputFormBuilder(requestExecutor),
                    () -> stadium,
                    stadiumService::removeCompetitionFromStadium,
                    true,
                    filterBox
            );

            return EntityInfoWindowBuilder.newInfoWindow("Стадион № " + stadium.getId().toString()).
                    addTab(competitionsOfTheStadium, "Соревнования").
                    build();
        };

        createEntityTable(
                "Стадионы",
                Stadium.getPropertyNames(),
                Stadium.getSortPropertyNames(),
                stadiumService,
                new StadiumInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                new StadiumFilterBoxBuilder(),
                StadiumFilter::new,
                true,
                true
        );
    }

    @FXML
    public void openIceArena(){
        IceArenaService iceArenaService = ServiceFactory.getIceArenaService();

        ContextWindowBuilder<IceArena> infoWindowBuilder = iceArena -> {

            CompetitionOfSFFilter filter = new CompetitionOfSFFilter();
            filter.setSportFacilityID(iceArena.getId());
            Node filterBox = new CompetitionOfSFFilterBoxBuilder().buildFilterBox(filter);

            Node competitionsOfTheIceArena = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> iceArenaService.getCompetitionsOfTheIceArenaByFilter(filter, pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForIceArenaInputFormBuilder(requestExecutor),
                    () -> iceArena,
                    iceArenaService::removeCompetitionFromIceArena,
                    true,
                    filterBox
            );

            return EntityInfoWindowBuilder.newInfoWindow("Ледовая арена №" + iceArena.getId().toString()).
                    addTab(competitionsOfTheIceArena, "Соревнования").
                    build();
        };

        createEntityTable(
                "Ледовые арены",
                IceArena.getPropertyNames(),
                IceArena.getSortPropertyNames(),
                iceArenaService,
                new IceArenaInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                new IceArenaFilterBoxBuilder(),
                IceArenaFilter::new,
                true,
                true
        );

    }

    @FXML
    public void openVolleyballArena(){
        VolleyballArenaService volleyballArenaService = ServiceFactory.getVolleyballArenaService();

        ContextWindowBuilder<VolleyballArena> infoWindowBuilder = volleyballArena ->{
            /*
            Node competitionsOfTheVolleyballArena = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> volleyballArenaService.getCompetitionsOfTheVolleyballArena(volleyballArena.getId(), pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForVolleyballArenaInputFormBuilder(requestExecutor),
                    () -> volleyballArena,
                    volleyballArenaService::removeCompetitionFromVolleyballArena,
                    true,
                    null
            );
            */
            CompetitionOfSFFilter filter = new CompetitionOfSFFilter();
            filter.setSportFacilityID(volleyballArena.getId());
            Node filterBox = new CompetitionOfSFFilterBoxBuilder().buildFilterBox(filter);

            Node competitionsOfTheVolleyballArena = createInfoWindowEntityTableForM2MOwned(
                    Competition.getPropertyNames(),
                    Competition.getSortPropertyNames(),
                    pageInfo -> volleyballArenaService.getCompetitionsOfTheVolleyballArenaByFilter(filter, pageInfo),
                    new CompetitionInputFormBuilder(requestExecutor),
                    new CompetitionForVolleyballArenaInputFormBuilder(requestExecutor),
                    () -> volleyballArena,
                    volleyballArenaService::removeCompetitionFromVolleyballArena,
                    true,
                    filterBox
            );

            return EntityInfoWindowBuilder.
                    newInfoWindow("Волейбольная арена № " + volleyballArena.getId().toString()).
                    addTab(competitionsOfTheVolleyballArena, "Соревнования").
                    build();
        };
        createEntityTable(
                "Волейбольная арена",
                VolleyballArena.getPropertyNames(),
                VolleyballArena.getSortPropertyNames(),
                volleyballArenaService,
                new VolleyballArenaInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                new VolleyballArenaFilterBoxBuilder(),
                VolleyballArenaFilter::new,
                true,
                true
        );
    }

    @FXML
    public void openClubs(){
        ClubService clubService = ServiceFactory.getClubService();

        DateFilterBoxBuilder clubFilterBoxBuilder = new DateFilterBoxBuilder();
        DateFilter clubFilter = new DateFilter();

        ContextWindowBuilder<Club> infoWindowBuilder = club -> {
            ValidationInfo info = clubFilterBoxBuilder.validate();
            if (!info.isValid()){
                AlertDialogFactory.showWarningAlertDialog(info.getHeaderText(), info.getContextText());
                return null;
            } else {
            FXMLLoader entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
            Parent entityInfoRoot = null;
            try {
                entityInfoRoot = entityInfoLoader.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            EntityInfoController controller = entityInfoLoader.getController();
            requestExecutor
                    .makeRequest(() -> clubService.getNumberOfSportsmanInTheClubDuringPeriod(club.getId(), clubFilter))
                    .setOnSuccessAction(numberOfSportsmen -> Platform.runLater(() -> {

                        if (clubFilter.getMinPeriod() == null & clubFilter.getMaxPeriod() == null) {
                            controller.addInfoLine(String.format(
                                    "Число спортсменов клуба %s, участвовавших в соревнованиях за всё время: %d",
                                    club.getName(), numberOfSportsmen
                            ));
                        } else
                            controller.addInfoLine(String.format(
                                    "Число спортсменов клуба %s, участвовавших в период от %s до %s: %d",
                                    club.getName(), LocalDateFormatter.getFormattedDate(clubFilter.getMinPeriod()), LocalDateFormatter.getFormattedDate(clubFilter.getMaxPeriod()), numberOfSportsmen
                            ));

                    }))
                    .setOnFailureAction(errorMessage -> AlertDialogFactory.showErrorAlertDialog(
                            "Не удалось загрузить информацию о числе спортсменов",
                            errorMessage
                    ))
                    .submit();
                return EntityInfoWindowBuilder
                        .newInfoWindow(club.getName())
                        .addTab(entityInfoRoot, "Инфо о спортсменах")
                        .build();
        }

        };

        createEntityTable(
                "Клубы",
                Club.getPropertyNames(),
                Club.getSortPropertyNames(),
                clubService,
                new ClubInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                clubFilterBoxBuilder,
                () -> clubFilter,
                true,
                false
                );
    }

    @SneakyThrows
    private <T extends Entity, X extends Entity> /*void*/ EntityTableController<T, X> createEntityTable(
            String tableName,
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            Service<T> entityService,
            EntityInputFormBuilder<T> inputFormBuilder,
            ContextWindowBuilder<T> infoWindowBuilder,
            FilterBoxBuilder/*<T>*/ filterBoxBuilder,
            Supplier<Filter/*<T>*/> newFilterSupplier,
            boolean isChangeItemVisible,
            boolean isSearchSource
    )
    {
        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        Tab tableTab = new Tab(tableName);
        tableTab.setContent(table);
        tableTab.setOnClosed(event -> {
            if (contentTabPane.getTabs().isEmpty()) {
                contentTabPane.getTabs().add(defaultTab);
            }
        });

        contentTabPane.getTabs().remove(defaultTab);
        contentTabPane.getTabs().add(tableTab);
        contentTabPane.getSelectionModel().select(tableTab);

        EntityTableController<T, X> controller = tableLoader.getController();
        controller.setInfoWindowBuilder(infoWindowBuilder);

        controller.setIsChangeItemVisible(isChangeItemVisible);

        controller.setEntityRemover(entityService::deleteById);

        Node filterBox = null;
        if (filterBoxBuilder != null && newFilterSupplier != null) {
            if (isSearchSource) {
                Filter/*<T>*/ filter = newFilterSupplier.get();
                filterBox = filterBoxBuilder.buildFilterBox(filter);
                controller.setEntitySource(pageInfo -> entityService.search(filter, pageInfo));
            } else {
                Filter/*<T>*/ filter = newFilterSupplier.get();
                filterBox = filterBoxBuilder.buildFilterBox(filter);
                controller.setEntitySource(entityService::getAll);
            }
        } else {
            controller.setEntitySource(entityService::getAll);
        }

        controller.setRequestExecutor(requestExecutor);

        controller.setEntityInputFormBuilder(inputFormBuilder);

        controller.init(
                entityPropertyNames,
                entitySortPropertyNames,
                false,
                this::setStatusBarMessage,
                filterBox
        );

        return controller;

    }

    private void setStatusBarMessage(String message) {
        Platform.runLater(() -> {
            String messageTime = LocalDateFormatter.getFormattedDateTime(Instant.now().toEpochMilli());
            String messageWithTime = String.format("%s: %s", messageTime, message);
            statusBarLabel.textProperty().setValue(messageWithTime);
        });
    }



    @SneakyThrows
    private <T extends Entity, X extends Entity> Node createInfoWindowEntityTable(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityTableController.EntityRemover<T> entityRemover,
            EntityInputFormBuilder<T> inputFormBuilder,
            Supplier<T> newEntitySupplier,
            boolean isChangeItemVisible
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T, X> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(entityRemover);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.setEntityInputFormBuilder(inputFormBuilder);

        entityTableController.setNewEntitySupplier(newEntitySupplier);

        entityTableController.setIsChangeItemVisible(isChangeItemVisible);

        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                true,
                this::setStatusBarMessage,
                null
        );

        return table;
    }



    @SneakyThrows
    private <T extends Entity, X extends Entity> Node createInfoWindowEntityTableForM2M(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityInputFormBuilder<T> entityInputFormBuilder,
            LinkingInputFormBuilder<X> linkingInputFormBuilder,
            Supplier<X> supplier,
            EntityTableController.LinkRemover linkRemover,
            EntityTableController.FrontendSideLinkRemover frontendSideLinkRemover,
            boolean isChangeItemVisible
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T, X> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(null);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.setEntityInputFormBuilder(entityInputFormBuilder);
        entityTableController.setLinkingInputFormBuilder(linkingInputFormBuilder);
        entityTableController.setSupplierForM2M(supplier);

        entityTableController.setLinkRemover(linkRemover);
        entityTableController.setFrontendSideLinkRemover(frontendSideLinkRemover);

        entityTableController.setIsChangeItemVisible(isChangeItemVisible);

        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                true,
                this::setStatusBarMessage,
                null
        );
        entityTableController.setIsLinkingWindow(true);

        return table;
    }

    @SneakyThrows
    private <T extends Entity, X extends Entity> Node createInfoWindowEntityTableForM2MOwned(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityInputFormBuilder<T> entityInputFormBuilder,
            LinkingInputFormBuilderForOwned<X> linkingInputFormBuilder,
            Supplier<X> supplier,
            EntityTableController.LinkRemover linkRemover,
            boolean isChangeItemVisible,
            Node filterBox
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T, X> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(null);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.setEntityInputFormBuilder(entityInputFormBuilder);
        entityTableController.setLinkingInputFormBuilderForOwned(linkingInputFormBuilder);
        entityTableController.setSupplierForM2M(supplier);

        entityTableController.setLinkRemover(linkRemover);

        entityTableController.setIsChangeItemVisible(isChangeItemVisible);

        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                true,
                this::setStatusBarMessage,
                filterBox
        );
        entityTableController.setIsLinkingWindow(true);
        entityTableController.setIsOwnedWindow(true);
        return table;
    }


    @SneakyThrows
    private <T extends Entity, X extends Entity> Node createInfoWindowEntityTableCreateButtonDisabled(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityTableController.EntityRemover<T> entityRemover,
            EntityInputFormBuilder<T> inputFormBuilder,
            Supplier<T> newEntitySupplier,
            boolean isChangeItemVisible
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T, X> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(entityRemover);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.setEntityInputFormBuilder(inputFormBuilder);

        entityTableController.setNewEntitySupplier(newEntitySupplier);

        entityTableController.setIsChangeItemVisible(isChangeItemVisible);
        entityTableController.disableCreateButton();

        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                true,
                this::setStatusBarMessage,
                null
        );

        return table;
    }




}
