package br.com.beergo.provider;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import br.com.beergo.domain.dto.MapsLocation;

public class LocationProvider implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "LocationProvider";

    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private OnLocationChanged onLocationChanged;

    public LocationProvider(Context mContext) {
        this.mContext = mContext;
        callConnection();
    }

    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void stopLocationUpdate() {
        if (mGoogleApiClient != null)
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }


    @Override
    public void onConnected(Bundle bundle) {
        Location l = LocationServices
                .FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        startLocationUpdate();
    }


    public void startLocationUpdate() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            initLocationRequest();
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "onConnectionSuspended(" + i + ")");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "onConnectionFailed(" + connectionResult + ")");
    }

    @Override
    public void onLocationChanged(Location location) {
        onLocationChanged.result(new MapsLocation(location.getLatitude(), location.getLongitude()));
    }

    public void subscribe(OnLocationChanged onLocationChanged){
        this.onLocationChanged = onLocationChanged;
    }
}
