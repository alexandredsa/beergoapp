package br.com.beergo.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.ui.IconGenerator;

import br.com.beergo.R;
import br.com.beergo.adapter.BeerInfoWindowAdapter;
import br.com.beergo.content.UserPreferences;
import br.com.beergo.domain.dto.MapsDTO;
import br.com.beergo.domain.dto.MapsLocation;
import br.com.beergo.domain.dto.UserDetail;
import br.com.beergo.provider.LocationProvider;
import br.com.beergo.provider.OnLocationChanged;
import br.com.beergo.rest.GoogleMapsRestService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static br.com.beergo.R.id.map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private GoogleMapsRestService mapsRestService;
    private LocationProvider locationProvider;
    private LatLng currentLocation = null;
    private IconGenerator iconFactory;
    private UserPreferences userPreferences;
    private TextView txtStatus;
    private UserDetail user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        mapFragment.getMapAsync(this);
        userPreferences = new UserPreferences(PreferenceManager.getDefaultSharedPreferences(this), new Gson());
        iconFactory = new IconGenerator(this);
        locationProvider = new LocationProvider(this);
        mapsRestService = new GoogleMapsRestService(this);
        user = userPreferences.getUser();
    }

    @Override
    protected void onStart() {
        initStatusPlayer();
        super.onStart();
    }

    private void initStatusPlayer() {
        user = userPreferences.getUser();
        String status = user.getName();
        status += " | Level: " + user.getLevel();
        status += "| XP: " + user.getExperience();
        txtStatus.setText(status);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.code:
                callCodeActivity();
                return true;
            case R.id.logout:
                callLoginActivity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void callLoginActivity() {
        Intent i = new Intent(MapsActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void callCodeActivity() {
        Intent i = new Intent(this, CodeActivity.class);
        startActivity(i);
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
        styleMap(googleMap);
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new BeerInfoWindowAdapter(this));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            finish();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
        locationProvider.subscribe(new OnLocationChanged() {
            @Override
            public void result(MapsLocation location) {
                mapsRestService.getBaresProximos(location, userPreferences.getUser(), new Callback<MapsDTO[]>() {
                    @Override
                    public void onResponse(Call<MapsDTO[]> call, Response<MapsDTO[]> response) {
                        for (MapsDTO mapsResult : response.body())
                            addMarker(mapsResult);
                    }

                    @Override
                    public void onFailure(Call<MapsDTO[]> call, Throwable t) {

                    }
                });

                if (currentLocation == null)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLat(), location.getLng())));

                currentLocation = new LatLng(location.getLat(), location.getLng());
            }
        });
    }

    private void styleMap(GoogleMap googleMap) {
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.pokemon_go_maps_style));
    }

    private void addMarker(MapsDTO mapsResult) {
        iconFactory.setTextAppearance(R.style.iconMarkerTextAppearence);
        iconFactory.setBackground(getResources().getDrawable(R.drawable.ic_beer));
        LatLng location = new LatLng(mapsResult.getLocation().getLat(), mapsResult.getLocation().getLng());
        mMap.addMarker(new MarkerOptions().position(location).title(mapsResult.getName()).snippet(mapsResult.snippet())
                .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mapsResult.getPercentOff() + "%"))))
                .showInfoWindow();
    }

}
