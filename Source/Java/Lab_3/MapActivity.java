package com.example.sravan.camlocation;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;
import java.util.Locale;

public class MapActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener
{

   public Bitmap bm;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleMap gMap;
    private GoogleApiClient gApiClient;
    private LocationRequest locRequest;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
        gApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        bm=getIntent().getParcelableExtra("ImageObject");

        locRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        gApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (gApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(gApiClient, this);
            gApiClient.disconnect();
        }
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (gMap != null) {
                setUpMap();
            }
        }
    }
    private void setUpMap() {
        gMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    private void handleNewLocation(Location location) {
        String straddr=markerPosition(location);
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(bm))
                .title(straddr);
        CameraUpdate center=CameraUpdateFactory.newLatLng(new LatLng(currentLatitude, currentLongitude));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(13);
        gMap.addMarker(options);
        gMap.moveCamera(center);
        gMap.animateCamera(zoom);
    }
    public String markerPosition(Location location)
    {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        String address = "";
        Geocoder geoCoder= new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geoCoder
                    .getFromLocation(currentLatitude, currentLongitude, 1);
            if (addressList != null) {
                android.location.Address returnedAddress = addressList.get(0);
                StringBuilder returnedAddrress = new StringBuilder("");
                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    returnedAddrress
                            .append(returnedAddress.getAddressLine(i)).append(
                            "\n");
                }
                address = returnedAddrress.toString();
            } else {
                Log.d("Error", "Address null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gApiClient);
        if (loc == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(gApiClient, locRequest, this);
        }
        else {
            handleNewLocation(loc);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
           Log.d("Error","Connection failed");
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }
}
