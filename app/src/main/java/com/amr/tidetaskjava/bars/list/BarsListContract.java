package com.amr.tidetaskjava.bars.list;

import com.amr.tidetaskjava.BasePresenter;
import com.amr.tidetaskjava.BaseView;
import com.amr.tidetaskjava.NetworkView;
import com.amr.tidetaskjava.data.models.Bar;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface BarsListContract {

    interface View extends BaseView<Presenter>, NetworkView {

        boolean isActive();

        void showBars(List<Bar> bars);

        void showClickedBar(Bar bar);
    }

    interface Presenter extends BasePresenter {
        void onBarClicked(Bar bar);
    }
}
