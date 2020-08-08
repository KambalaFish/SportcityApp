package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Court;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface CourtServiceApi extends CrudServiceApi{

    @GET("/court/pageWithCourtById/{id}")
    Call<Page<Court>> getPageWithCourtById(@Path("id") Integer courtId, @QueryMap Map<String, Object> pageInfo);

}
