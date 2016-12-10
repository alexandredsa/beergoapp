package br.com.beergo.rest;

import br.com.beergo.domain.dto.UserCredentials;
import br.com.beergo.domain.dto.UserDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by alexandre.alves on 07/12/2016.
 */

public interface AuthAPI {
    @POST("/auth/")
    Call<UserDetail> login(@Body UserCredentials credentials);
    @POST("/auth/signup")
    Call<UserDetail> signUp(@Body UserCredentials credentials);
}
