package com.amr.tidetaskjava.data;

import com.amr.tidetaskjava.data.callbacks.LoadPlacesCallback;
import com.amr.tidetaskjava.data.models.Bar;
import com.amr.tidetaskjava.network.Callback;
import com.amr.tidetaskjava.network.ServiceGenerator;
import com.amr.tidetaskjava.network.interfaces.PlacesService;
import com.amr.tidetaskjava.utils.Constants;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PlacesRepository implements PlacesDataSource {


    @Override
    public void loadBars(LatLng location, String appMapsKey, final LoadPlacesCallback loadPlacesCallback) {
        PlacesService placesService = ServiceGenerator.createService(PlacesService.class);
        Call<JsonElement> call = placesService.getPlaces(location.latitude + "," + location.longitude, Constants.RADIUS, false, Constants.PLACES_TYPE, Constants.SERVER_KEY);
        call.enqueue(new Callback<JsonElement>(loadPlacesCallback) {

            @Override
            public void onResponseSuccess(Call<JsonElement> call, Response<JsonElement> response) {
                String status = response.body().getAsJsonObject().get("status").getAsString();
                if (status.equals("OK") || status.equals("ZERO_RESULTS")) {
                    Gson gson = new Gson();
                    loadPlacesCallback.onBarsLoaded(gson.<List<Bar>>fromJson(response.body().getAsJsonObject().get("results"), new TypeToken<List<Bar>>() {
                    }.getType()));
                } else {
                    loadPlacesCallback.onError(response.body().getAsJsonObject().get("error_message").getAsString());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
}