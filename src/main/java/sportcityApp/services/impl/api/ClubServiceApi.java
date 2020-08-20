package sportcityApp.services.impl.api;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ClubServiceApi extends CrudServiceApi{

    @POST("club/{id}/numberOfSportsmanInTheClubDuringPeriod")
    Call<Integer> getNumberOfSportsmanInTheClubDuringPeriod(@Path("id") Integer id, @Body JsonElement filter);


}
