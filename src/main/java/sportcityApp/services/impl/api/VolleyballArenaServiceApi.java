package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.VolleyballArena;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface VolleyballArenaServiceApi extends CrudServiceApi{

    @GET("volleyballArena/pageWithVolleyballArenaById/{id}")
    Call<Page<VolleyballArena>> getPageWithVolleyballArenaById(@Path("id") Integer volleyballArenaId, @QueryMap Map<String, Object> pageInfo);

}
