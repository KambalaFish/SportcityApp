package sportcityApp.services.impl.api;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Collection;
import java.util.Map;

public interface CrudServiceApi {

    @GET("{root}/count")
    Call<Integer> countAll(@Path("root") String root);

    @GET("{root}/{id}")
    Call<JsonElement> getById(
            @Path("root") String root,
            @Path("id") Integer id
    );

    @GET("{root}")
    Call<JsonElement> getAll(
            @Path("root") String root,
            @QueryMap Map<String, Object> pageInfo
    );

    @GET("{root}/collection")
    Call<JsonElement> getAllById(
            @Path("root") String root,
            @Query("id") Collection<Integer> idCollection
    );

    @POST("{root}/search")
    Call<JsonElement> search(
            @Path("root") String root,
            @QueryMap Map<String, Object> pageInfo,
            @Body JsonElement filter
    );

    @POST("{root}")
    Call<JsonElement> create(
            @Path("root") String root,
            @Body JsonElement entity
    );

    @PUT("{root}/{id}")
    Call<JsonElement> save(
            @Path("root") String root,
            @Path("id") Integer id,
            @Body JsonElement entity
    );

    @PUT("{root}/collection")
    Call<JsonElement> saveAll(
            @Path("root") String root,
            @Body JsonElement entityCollection
    );

    @DELETE("{root}/{id}")
    Call<Void> deleteById(
            @Path("root") String root,
            @Path("id") Integer id
    );

    @DELETE("{root}/collection")
    Call<Void> deleteAllById(
            @Path("root") String root,
            @Query("id") Collection<Integer> idCollection
    );

}
