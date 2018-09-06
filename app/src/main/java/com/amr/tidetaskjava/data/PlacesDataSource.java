package com.amr.tidetaskjava.data;


import com.amr.tidetaskjava.data.callbacks.LoadPlacesCallback;
import com.google.android.gms.maps.model.LatLng;

public interface PlacesDataSource {
    void loadBars(LatLng location,String appMapsKey, LoadPlacesCallback loadPlacesCallback);
}
