package com.amr.tidetaskjava.data.callbacks;

import com.amr.tidetaskjava.data.models.Bar;

import java.util.List;

public interface LoadPlacesCallback extends BaseNetworkCallback {
    void onBarsLoaded(List<Bar> bars);

    void onError(String message);
}
