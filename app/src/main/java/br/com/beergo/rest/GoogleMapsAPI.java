package br.com.beergo.rest;

import br.com.beergo.domain.dto.MapsDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsAPI {
    @GET("/maps")
    Call<MapsDTO[]> getResults(@Query("location") String latLng, @Query("id") long userId);
}
