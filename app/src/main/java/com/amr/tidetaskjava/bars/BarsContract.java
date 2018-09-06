package com.amr.tidetaskjava.bars;

import com.amr.tidetaskjava.BasePresenter;
import com.amr.tidetaskjava.BaseView;
import com.amr.tidetaskjava.NetworkView;
import com.amr.tidetaskjava.data.models.Bar;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface BarsContract {

    interface View extends BaseView<Presenter>, NetworkView {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        boolean checkLocationPermission();

        LatLng getDeviceLocation();

        void showBars(List<Bar> bars);

        void showError(String message);

        void showRationalePermission();

        void showClickedBar(Bar bar);
    }

    interface Presenter extends BasePresenter {
        void loadBars(LatLng location);

        void onBarClicked(Bar bar);
    }
}
