package br.com.beergo.rest;

import br.com.beergo.domain.dto.UserCredentials;
import br.com.beergo.domain.dto.UserDetail;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by alexandre.alves on 07/12/2016.
 */

public class AuthRestService {

    public void login(UserCredentials userCredentials, Callback<UserDetail> callback) {
        Call<UserDetail> call;
        AuthAPI authAPI = (AuthAPI) RetrofitRestService.initBeerGOService(AuthAPI.class);
        call = authAPI.login(userCredentials);
        call.enqueue(callback);
    }
    public void signUp(UserCredentials userCredentials, Callback<UserDetail> callback) {
        Call<UserDetail> call;
        AuthAPI authAPI = (AuthAPI) RetrofitRestService.initBeerGOService(AuthAPI.class);
        call = authAPI.signUp(userCredentials);
        call.enqueue(callback);
    }
}
