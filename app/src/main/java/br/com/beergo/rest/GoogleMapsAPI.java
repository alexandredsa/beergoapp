package br.com.beergo.rest;

import java.util.List;

import br.com.beergo.domain.dto.MapsResultsModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsAPI {
    @GET("maps/api/place/nearbysearch/json")
    Call<MapsResultsModel> getResults(@Query("location") String latLng, @Query("radius") long radius,
                                      @Query("types") List<String> types, @Query("key") String key);
}
