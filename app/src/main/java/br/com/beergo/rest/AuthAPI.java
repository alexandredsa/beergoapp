package br.com.beergo.rest;

import java.util.List;

import br.com.beergo.domain.dto.MapsResultsModel;
import br.com.beergo.domain.dto.UserCredentials;
import br.com.beergo.domain.dto.UserDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by alexandre.alves on 07/12/2016.
 */

public interface AuthAPI {
    @POST("/auth")
    Call<UserDetail> login(@Body UserCredentials credentials);
}
