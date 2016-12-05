package br.com.beergo.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.beergo.R;
import br.com.beergo.domain.dto.BarMapsRequest;
import br.com.beergo.domain.dto.MapsLocation;
import br.com.beergo.domain.dto.MapsResult;
import br.com.beergo.domain.dto.MapsResultsModel;
import br.com.beergo.provider.LocationProvider;
import br.com.beergo.provider.OnLocationChanged;
import br.com.beergo.rest.GoogleMapsRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GoogleMapsRestService mapsRestService;
    private LocationProvider locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationProvider = new LocationProvider(this);
        mapsRestService = new GoogleMapsRestService(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        locationProvider.subscribe(new OnLocationChanged() {
            @Override
            public void result(MapsLocation location) {
                mapsRestService.getBaresProximos(new BarMapsRequest(location, 2000), new Callback<MapsResultsModel>() {
                    @Override
                    public void onResponse(Call<MapsResultsModel> call, Response<MapsResultsModel> response) {
                        for (MapsResult mapsResult : response.body().getResults())
                            addMarker(mapsResult);
                    }

                    @Override
                    public void onFailure(Call<MapsResultsModel> call, Throwable t) {

                    }
                });

                LatLng currentLocation = new LatLng(location.getLat(), location.getLng());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            }
        });
    }

    private void addMarker(MapsResult mapsResult) {
        LatLng location = new LatLng(mapsResult.getCoordenadas().getLatitude(), mapsResult.getCoordenadas().getLongitude());
        mMap.addMarker(new MarkerOptions().position(location).title(mapsResult.getNome()));
    }

}
