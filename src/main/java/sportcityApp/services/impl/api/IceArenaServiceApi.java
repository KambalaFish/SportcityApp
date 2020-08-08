package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.IceArena;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface IceArenaServiceApi extends CrudServiceApi{
    @GET("/iceArena/pageWithIceArenaById/{id}")
    Call<Page<IceArena>> getPageWithIceArenaById(@Path("id") Integer iceArenaId, @QueryMap Map<String, Object> pageInfo);
}
