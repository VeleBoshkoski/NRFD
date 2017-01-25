package com.example.vele_.nearbyrestaurantsfoodanddrinks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vele_.nearbyrestaurantsfoodanddrinks.Places.GetPlaces;
import com.example.vele_.nearbyrestaurantsfoodanddrinks.Places.Result;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by veleb on 22.01.2017.
 */

public class listFragment extends Fragment{
    View fragmentView;
    TextView tv;
    ArrayList<Result> ar;
    RecyclerView Rv;
    PlacesAdapter placesAdapter;
    LocationManager mLocationManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.list_layout,container,false);
        tv = (TextView) fragmentView.findViewById(R.id.textList);
        Rv = (RecyclerView) fragmentView.findViewById(R.id.RV);
        Rv.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        String location="";
        try{
            mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            if ( !mLocationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                Toast.makeText(getActivity().getBaseContext(),"Please turn on gps",
                        Toast.LENGTH_SHORT).show();
            }else {
                Location myLocation = getLastKnownLocation();
                location = myLocation.getLatitude() + "," + myLocation.getLongitude();
                Log.e("My coordinets", " " + myLocation.getLatitude() + ", " + myLocation.getLongitude());
                startService(location);

            }
        }catch (Exception e){
            Log.e("Greska",e.getMessage());
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(fragmentView.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragmentView.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void startService(String location1){
        String location;
        if(!location1.equals(""))
            location = location1;
        else
            location = "41.99,21.42";
        String key = "AIzaSyC-wZzXGEjcUPxCqOQD5H-3N8NlEf6Xh-4";
        String radius = "5000";
        String types = "restaurant|food|liquor_store|cafe";
        String types1 = "";
        final File out = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NRFD.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(out));
            String line;
            while ((line=br.readLine())!=null){
                String [] line1 = line.split(":");
                if(line1[0].equals("radius")){
                    radius = line1[1];
                }else if(line1[0].equals("restaurants")){
                    if(line1[1].equals("true")){
                        types1+="restaurant|";
                    }else{

                    }
                }else if(line1[0].equals("food")){
                    if(line1[1].equals("true")){
                        types1+="food|";
                    }else{

                    }
                }else if(line1[0].equals("liquor_store")){
                    if(line1[1].equals("true")){
                        types1+="liquor_store|";
                    }else{

                    }
                }else if(line1[0].equals("cafe")){
                    if(line1[1].equals("true")){
                        types1+="cafe";
                    }else{

                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        types=types1;

        GetPlacesAPI.PlacesFactory.getInstance().getPlaces(location,key,radius,types).enqueue(new Callback<GetPlaces>() {
            @Override
            public void onResponse(Call<GetPlaces> call, Response<GetPlaces> response) {

                ar=(ArrayList<Result>) response.body().getResults();
                for (Result r : ar){
                    Log.d("Ime na mesto",r.getName());
                    tv.append(r.getName()+"\n");
                }
                placesAdapter = new PlacesAdapter(fragmentView.getContext(),ar);

                Rv.setAdapter(placesAdapter);

                synchronized (Rv) {
                    Rv.notifyAll();
                }

            }

            @Override
            public void onFailure(Call<GetPlaces> call, Throwable t) {
                Log.e("listFragmentError",t.getMessage());
            }
        });
    }


}
