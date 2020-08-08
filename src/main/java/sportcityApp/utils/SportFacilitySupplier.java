package sportcityApp.utils;

import lombok.experimental.UtilityClass;
import sportcityApp.entities.Court;
import sportcityApp.entities.IceArena;
import sportcityApp.entities.SportFacility;
import sportcityApp.entities.Stadium;

import java.util.function.Supplier;

@UtilityClass
public class SportFacilitySupplier {

    private Integer getId(){
        return ServiceFactory.getSportFacilityService().getLastIdNumber().getBody() + 1;
    }

    public Supplier<Court> getCourtSupplier(){
        return () -> {
            Integer id = getId();
            Court court = new Court();
            court.setId(id);
            SportFacility sportFacility = new SportFacility();
            sportFacility.setId(id);
            court.setSportFacility(sportFacility);
            return court;
        };
    }

    public Supplier<SportFacility> getSportFacilitySupplier(){
        return () -> {
            Integer id = getId();
            SportFacility sportFacility = new SportFacility();
            sportFacility.setId(id);
            return sportFacility;
        };
    }

    public Supplier<Stadium> getStadiumSupplier(){
        return () -> {
            Integer id = getId();
            Stadium stadium = new Stadium();
            stadium.setId(id);
            SportFacility sportFacility = new SportFacility();
            sportFacility.setId(id);
            stadium.setSportFacility(sportFacility);
            return stadium;
        };
    }

    public Supplier<IceArena> getIceArenaSupplier(){
        return () -> {
          Integer id = getId();
          IceArena iceArena = new IceArena();
          iceArena.setId(id);
          SportFacility sportFacility = new SportFacility();
          sportFacility.setId(id);
          iceArena.setSportFacility(sportFacility);
          return iceArena;
        };
    }

}
