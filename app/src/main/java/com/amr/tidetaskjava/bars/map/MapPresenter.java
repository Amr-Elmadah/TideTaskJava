package com.amr.tidetaskjava.bars.map;

import com.amr.tidetaskjava.data.models.Bar;

import java.util.List;

import static com.amr.tidetaskjava.utils.Utils.checkNotNull;

public class MapPresenter implements MapContract.Presenter {
    private MapContract.View mView;
    private List<Bar> mBars;

    public MapPresenter(List<Bar> bars, MapContract.View view) {
        mBars = checkNotNull(bars);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showBars(mBars, null);
    }
}
