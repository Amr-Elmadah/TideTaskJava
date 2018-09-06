package com.amr.tidetaskjava.network.interfaces;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlacesService {
    @GET("maps/api/place/search/json")
    Call<JsonElement> getPlaces(@Query("location") String location
            , @Query("radius") int radius
            , @Query("sensor") boolean sensor
            , @Query("types") String type
            , @Query("key") String appMapsKey);
}
