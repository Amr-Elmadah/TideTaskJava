package com.amr.tidetaskjava.network;



import com.amr.tidetaskjava.data.callbacks.BaseNetworkCallback;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Response;

import static com.amr.tidetaskjava.utils.Utils.checkNotNull;

public abstract class Callback<T> implements retrofit2.Callback<T> {

    private BaseNetworkCallback mNetworkCallback;

    public Callback() {
    }

    public Callback(BaseNetworkCallback callback) {
        mNetworkCallback = checkNotNull(callback);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onResponseSuccess(call, response);
        } else {
            onResponseFailure(call, response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (t instanceof SocketTimeoutException) {
            onTimeOut(call, t);
        } else if (t instanceof IOException) {
            onNoConnection(call, t);
        } else {
            onNoConnection(call, t);
        }
    }

    public abstract void onResponseSuccess(Call<T> call, Response<T> response);

    public void onResponseFailure(Call<T> call, Response<T> response) {
        if (mNetworkCallback != null) {
            mNetworkCallback.onResponseError(response.code());
        }
    }

    public void onNoConnection(Call<T> call, Throwable t) {
        if (mNetworkCallback != null) {
            mNetworkCallback.onNoConnection();
        }
    }

    public void onTimeOut(Call<T> call, Throwable t) {
        if (mNetworkCallback != null) {
            mNetworkCallback.onTimeOut();
        }
    }
}
