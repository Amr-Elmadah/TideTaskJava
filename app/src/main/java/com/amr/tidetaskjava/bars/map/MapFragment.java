package com.amr.tidetaskjava.bars.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amr.tidetaskjava.BaseNetworkFragment;
import com.amr.tidetaskjava.R;
import com.amr.tidetaskjava.data.models.Bar;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.ButterKnife;

public class MapFragment extends BaseNetworkFragment implements MapContract.View, OnMapReadyCallback {

    private MapContract.Presenter mPresenter;

    private GoogleMap mMap;

    private List<Bar> mBars;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_bars_map, container, false);
        ButterKnife.bind(this, mView);

        initMap();
        return mView;
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mBars != null) {
            showBars(mBars, null);
        }
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showBars(List<Bar> bars, LatLng myLocation) {
        if (mMap != null) {
            mMap.clear();
            for (Bar bar : bars) {
                mMap.addMarker(new MarkerOptions().position(bar.getLatLng()).title(bar.getName()).snippet(getString(R.string.distance) + " " + bar.getDistance() + " m"))
                ;
            }
            if (myLocation != null) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        myLocation, 14.0f);
                mMap.animateCamera(cameraUpdate);
            }
        }
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
