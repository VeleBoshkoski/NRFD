package com.example.vele_.nearbyrestaurantsfoodanddrinks.Places;

import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by veleb on 24.01.2017.
 */

public class LocationLis implements LocationListener {
    public double lat;
    public double lng;

    @Override
    public void onLocationChanged(Location location) {

        String longitude = "Longitude: " + location.getLongitude();
        //Log.e("longitude", longitude);
        String latitude = "Latitude: " + location.getLatitude();
        //Log.e("latitude", latitude);
    }
    public double getLat(){
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Log.e("Enable GPS","Enable GPS");
    }
}
