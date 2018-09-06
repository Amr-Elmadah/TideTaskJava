package com.amr.tidetaskjava.bars.map;

import com.amr.tidetaskjava.BasePresenter;
import com.amr.tidetaskjava.BaseView;
import com.amr.tidetaskjava.NetworkView;
import com.amr.tidetaskjava.data.models.Bar;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface MapContract {

    interface View extends BaseView<Presenter>, NetworkView {
        boolean isActive();

        void showBars(List<Bar> bars, LatLng myLocation);
    }

    interface Presenter extends BasePresenter {
    }
}
