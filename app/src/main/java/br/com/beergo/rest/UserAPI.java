package br.com.beergo.rest;

import br.com.beergo.domain.dto.UserDetail;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by alexandre on 13/12/16.
 */

public interface UserAPI {
    @PUT("/user/id/{id}/code/{code}")
    Call<UserDetail> sendCode(@Path("id") long userId, @Path("code") String code);
}
