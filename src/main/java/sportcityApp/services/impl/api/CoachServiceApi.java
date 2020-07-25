package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Sportsman;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface CoachServiceApi extends CrudServiceApi {

    @GET("/coach/{id}/sportsmen")
    Call<Page<Sportsman>> getSportsmen(@Path("id") Integer coachId, @QueryMap Map<String, Object> pageInfo);

}
