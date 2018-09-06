package com.amr.tidetaskjava.data.callbacks;

public interface BaseNetworkCallback {
    void onResponseError(int responseCode);

    void onNoConnection();

    void onTimeOut();
}
