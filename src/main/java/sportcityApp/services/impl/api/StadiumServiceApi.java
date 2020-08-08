package sportcityApp.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import sportcityApp.entities.Stadium;
import sportcityApp.services.pagination.Page;

import java.util.Map;

public interface StadiumServiceApi extends CrudServiceApi{
    @GET("/stadium/pageWithStadiumById/{id}")
    Call<Page<Stadium>> getPageWithStadiumById(@Path("id") Integer stadiumId, @QueryMap Map<String, Object> pageInfo);
}
