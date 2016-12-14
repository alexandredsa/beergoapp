package br.com.beergo.rest;

import br.com.beergo.domain.dto.UserDetail;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by alexandre.alves on 07/12/2016.
 */

public class UserRestService {

    public void sendCode(UserDetail userDetail, String code, Callback<UserDetail> callback) {
        Call<UserDetail> call;
        UserAPI userAPI = (UserAPI) RetrofitRestService.initBeerGOService(UserAPI.class);
        call = userAPI.sendCode(userDetail.getId(), code.toUpperCase());
        call.enqueue(callback);
    }
}
