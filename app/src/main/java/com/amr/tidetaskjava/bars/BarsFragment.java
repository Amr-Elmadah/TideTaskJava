package com.amr.tidetaskjava.bars;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.amr.tidetaskjava.BaseNetworkFragment;
import com.amr.tidetaskjava.R;
import com.amr.tidetaskjava.bars.list.BarsListFragment;
import com.amr.tidetaskjava.bars.map.MapFragment;
import com.amr.tidetaskjava.data.PlacesRepository;
import com.amr.tidetaskjava.data.models.Bar;
import com.amr.tidetaskjava.utils.GPSTracker;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import info.hoang8f.android.segmented.SegmentedGroup;

public class BarsFragment extends BaseNetworkFragment implements BarsContract.View {
    private static final int REQUEST_CODE_PERMISSION = 2;
    private static String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @BindView(R.id.srl_bars)
    SwipeRefreshLayout swipeRefreshLayoutRecyclerView;
    @BindView(R.id.srl_bars_emptyView)
    SwipeRefreshLayout swipeRefreshLayoutEmptyView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.rb_list)
    RadioButton rbList;
    @BindView(R.id.rb_map)
    RadioButton rbMap;
    @BindView(R.id.sg_switch)
    SegmentedGroup sgSwitch;
    private TabsPagerAdapter tabsAdapter;

    private BarsContract.Presenter mPresenter;
    private Snackbar snackbar;
    private GPSTracker gpsTracker;

    public BarsFragment() {
        // Required empty public constructor
    }

    public static BarsFragment newInstance() {
        BarsFragment barsFragment = new BarsFragment();
        Bundle args = new Bundle();
        barsFragment.setArguments(args);
        return barsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_bars, container, false);
        ButterKnife.bind(this, mView);
        setupSwipeRefreshLayout(swipeRefreshLayoutRecyclerView);
        setupSwipeRefreshLayout(swipeRefreshLayoutEmptyView);

        if (mPresenter == null) {
            mPresenter = new BarsPresenter(getString(R.string.google_maps_key), new PlacesRepository(), this);
        }
        setupViewPager(viewPager);

        if (savedInstanceState == null) {
            mPresenter.start();
        }
        sgSwitch.setTintColor(getResources().getColor(R.color.colorAccent));


        return mView;
    }

    @OnCheckedChanged(R.id.rb_map)
    void rbMapClicked() {
        if (rbMap.isChecked()) {
            viewPager.setCurrentItem(1);
        }
    }

    @OnCheckedChanged(R.id.rb_list)
    void rbListClicked() {
        if (rbList.isChecked()) {
            viewPager.setCurrentItem(0);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPager.setOffscreenPageLimit(3);
        tabsAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(tabsAdapter);
        viewPager.setCurrentItem(0);
    }

    private void setupSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkLocationPermission()) {
                    mPresenter.loadBars(getDeviceLocation());
                } else {
                    setLoadingIndicator(false);
                }
            }
        });
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (swipeRefreshLayoutRecyclerView != null && swipeRefreshLayoutEmptyView != null) {
            swipeRefreshLayoutRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayoutRecyclerView.setRefreshing(active);
                    // Set refreshing to false on the empty view's SwipeRefreshLayout if it's active
                    if (swipeRefreshLayoutEmptyView.isRefreshing() && !active) {
                        swipeRefreshLayoutEmptyView.setRefreshing(false);
                    }
                }
            });
        } else {
            startActivity(new Intent(getActivity(), BarsActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public boolean checkLocationPermission() {
        try {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showRationalePermission();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            PERMISSIONS_LOCATION,
                            REQUEST_CODE_PERMISSION);
                }
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public LatLng getDeviceLocation() {
        gpsTracker = new GPSTracker(getActivity());
        return new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkLocationPermission()) getDeviceLocation();
                } else {
                    showRationalePermission();
                }
                break;
        }
    }

    @Override
    public void showBars(List<Bar> bars) {
        swipeRefreshLayoutRecyclerView.setVisibility(bars.isEmpty() ? View.GONE : View.VISIBLE);
        swipeRefreshLayoutEmptyView.setVisibility(bars.isEmpty() ? View.VISIBLE : View.GONE);
        tabsAdapter.onBarsLoaded(bars);
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_INDEFINITE).setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLocationPermission()) mPresenter.loadBars(getDeviceLocation());
            }
        });
        snackbar.show();
    }

    @Override
    public void showRationalePermission() {
        Snackbar.make(getView(), R.string.must_location_permission, Snackbar.LENGTH_INDEFINITE).setAction(R.string.settings, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }).show();
    }

    @Override
    public void showClickedBar(Bar bar) {

    }

    @Override
    public void setPresenter(BarsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private class TabsPagerAdapter extends FragmentStatePagerAdapter {
        BarsListFragment barsListFragment;
        MapFragment mapFragment;

        TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    barsListFragment = new BarsListFragment();
                    return barsListFragment;
                case 1:
                    mapFragment = new MapFragment();
                    return mapFragment;
                default:
                    fragment = new BarsListFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        public void onBarsLoaded(List<Bar> bars) {
            Bar bar;
            Location location = new Location("Location1");
            LatLng myLocation = getDeviceLocation();
            location.setLatitude(myLocation.latitude);
            location.setLongitude(myLocation.longitude);
            Location locationDestination = new Location("Location2");
            for (int i = 0; i < bars.size(); i++) {
                bar = bars.get(i);
                locationDestination.setLatitude(bar.getGeometry().getLocation().getLatitude());
                locationDestination.setLongitude(bar.getGeometry().getLocation().getLongitude());
                bars.get(i).setDistance(location.distanceTo(locationDestination));
            }
            barsListFragment.showBars(bars);
            mapFragment.showBars(bars, myLocation);
        }
    }
}