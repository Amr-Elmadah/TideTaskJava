package com.amr.tidetaskjava.bars.list;

import com.amr.tidetaskjava.data.PlacesRepository;
import com.amr.tidetaskjava.data.models.Bar;

import java.util.List;

import static com.amr.tidetaskjava.utils.Utils.checkNotNull;

public class BarsListPresenter implements BarsListContract.Presenter {

    private BarsListContract.View mView;
    private List<Bar> mBars;

    public BarsListPresenter(List<Bar> bars, BarsListContract.View view) {
        mBars = checkNotNull(bars);
        mView = checkNotNull(view);
        mView.setPresenter(this);
    }

    @Override
    public void onBarClicked(Bar bar) {
        mView.showClickedBar(bar);
    }

    @Override
    public void start() {
        mView.showBars(mBars);
    }
}
