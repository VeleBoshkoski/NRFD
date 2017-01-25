package com.example.vele_.nearbyrestaurantsfoodanddrinks;

import com.example.vele_.nearbyrestaurantsfoodanddrinks.Places.GetPlaces;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by veleb on 22.01.2017.
 */

public interface GetPlacesAPI {

    String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    @GET("json?")
    //@GET("json?location={lat},{lon}&key=AIzaSyC-wZzXGEjcUPxCqOQD5H-3N8NlEf6Xh-4&radius={radius}&types={types}")
    Call<GetPlaces> getPlaces(@Query("location") String lat, @Query("key") String key, @Query("radius") String radius, @Query("types") String types);

    class PlacesFactory {
        private static GetPlacesAPI service;


        public static GetPlacesAPI getInstance() {

            if (service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(GetPlacesAPI.class);
                return service;
            } else {
                return service;
            }

        }
    }
}
