package Service;

import java.util.ArrayList;
import Model.UserObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Oriaje on 3/7/2017.
 */
public interface LagDevClient {

    @GET("/search/users")
    Call<UserObject.SearchResult> searchUser(@Query(value = "q",encoded = true) String query);
}
