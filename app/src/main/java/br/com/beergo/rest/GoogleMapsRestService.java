package br.com.beergo.rest;

import android.content.Context;

import br.com.beergo.domain.dto.MapsDTO;
import br.com.beergo.domain.dto.MapsLocation;
import br.com.beergo.domain.dto.UserDetail;
import retrofit2.Call;
import retrofit2.Callback;

public class GoogleMapsRestService {
    private Context mContext;

    public GoogleMapsRestService(Context mContext) {
        this.mContext = mContext;
    }

    public void getBaresProximos(MapsLocation location, UserDetail userDetail, Callback<MapsDTO[]> callback) {
        Call<MapsDTO[]> resultsModelCall;
        GoogleMapsAPI mapsAPI = (GoogleMapsAPI) RetrofitRestService.initBeerGOService(GoogleMapsAPI.class);
        resultsModelCall = mapsAPI.getResults(location.toString(), userDetail.getId());
        resultsModelCall.enqueue(callback);
    }
}
