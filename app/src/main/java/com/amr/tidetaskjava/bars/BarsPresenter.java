package com.amr.tidetaskjava.bars;

import com.amr.tidetaskjava.Action;
import com.amr.tidetaskjava.data.PlacesRepository;
import com.amr.tidetaskjava.data.callbacks.LoadPlacesCallback;
import com.amr.tidetaskjava.data.models.Bar;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import static com.amr.tidetaskjava.utils.Utils.checkNotNull;

public class BarsPresenter implements BarsContract.Presenter {


    private PlacesRepository mPlacesRepository;
    private BarsContract.View mView;
    private String mAppMapsKey;

    public BarsPresenter(String appMapsKey, PlacesRepository placesRepository, BarsContract.View view) {
        mAppMapsKey = checkNotNull(appMapsKey);
        mPlacesRepository = checkNotNull(placesRepository);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void loadBars(final LatLng location) {
        mView.setLoadingIndicator(true);
        mPlacesRepository.loadBars(location, mAppMapsKey
                , new LoadPlacesCallback() {
                    @Override
                    public void onBarsLoaded(List<Bar> bars) {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false);
                            mView.showBars(bars);
                        }
                    }

                    @Override
                    public void onError(String message) {
                        if (mView.isActive()) {
                            mView.showError(message);
                            mView.setLoadingIndicator(false);
                        }
                    }

                    @Override
                    public void onResponseError(int responseCode) {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false);
                            mView.showServerError();
                        }
                    }

                    @Override
                    public void onNoConnection() {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false);
                            mView.showNoConnection(new Action() {
                                @Override
                                public void perform() {
                                    if (mView.isActive()) {
                                        if (mView.checkLocationPermission()) {
                                            loadBars(mView.getDeviceLocation());
                                        }
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onTimeOut() {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false);
                            mView.showTimeOut();
                        }
                    }
                });
    }

    @Override
    public void onBarClicked(Bar bar) {
        mView.showClickedBar(bar);
    }

    @Override
    public void start() {
        if (mView.checkLocationPermission()) loadBars(mView.getDeviceLocation());
    }
}
