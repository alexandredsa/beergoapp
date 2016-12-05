package br.com.beergo.rest;

import android.content.Context;

import java.util.Arrays;

import br.com.beergo.R;
import br.com.beergo.domain.dto.BarMapsRequest;
import br.com.beergo.domain.dto.MapsResultsModel;
import retrofit2.Call;
import retrofit2.Callback;

public class GoogleMapsRestService {
    private Context mContext;

    public GoogleMapsRestService(Context mContext) {
        this.mContext = mContext;
    }

    public void getBaresProximos(BarMapsRequest barMapsRequest, Callback<MapsResultsModel> callback) {
        Call<MapsResultsModel> resultsModelCall;
        GoogleMapsAPI mapsAPI = (GoogleMapsAPI) RetrofitRestService.initGoogleMapsService(GoogleMapsAPI.class);

        resultsModelCall = mapsAPI.getResults(barMapsRequest.locationToString(),
                barMapsRequest.getRadius(), Arrays.asList("bar"),
                mContext.getResources().getString(R.string.google_maps_key));

        resultsModelCall.enqueue(callback);
    }
}
