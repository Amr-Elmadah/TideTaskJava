package com.amr.tidetaskjava.bars.list;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amr.tidetaskjava.BaseNetworkFragment;
import com.amr.tidetaskjava.R;
import com.amr.tidetaskjava.customviews.DividerItemDecoration;
import com.amr.tidetaskjava.customviews.RecyclerViewEmptySupport;
import com.amr.tidetaskjava.data.models.Bar;
import com.amr.tidetaskjava.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.amr.tidetaskjava.utils.Utils.checkNotNull;

public class BarsListFragment extends BaseNetworkFragment implements BarsListContract.View {

    @BindView(R.id.rv_bars)
    RecyclerViewEmptySupport recyclerView;
    private BarsAdapter mAdapter;
    private long mLastClickTime = System.currentTimeMillis();

    private BarsListContract.Presenter mPresenter;

    public BarsListFragment() {
        // Required empty public constructor
    }

    public static BarsListFragment newInstance() {
        BarsListFragment barsListFragment = new BarsListFragment();
        Bundle args = new Bundle();
        barsListFragment.setArguments(args);
        return barsListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new BarsAdapter(getActivity(), new ArrayList<Bar>(), new BarsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bar bar) {
                long now = System.currentTimeMillis();
                if (now - mLastClickTime < Constants.CLICK_TIME_INTERVAL) {
                    return;
                }
                mLastClickTime = now;
                mPresenter.onBarClicked(bar);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_bars_list, container, false);
        ButterKnife.bind(this, mView);

        setupRecyclerView(recyclerView);
        if (mPresenter == null) {
            mPresenter = new BarsListPresenter(new ArrayList<Bar>(), this);
        }

        if (savedInstanceState == null) {
            mPresenter.start();
        }

        return mView;
    }

    private void setupRecyclerView(RecyclerViewEmptySupport recyclerView) {
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showBars(List<Bar> bars) {
        recyclerView.setLoaded(true);
        mAdapter.replaceData(bars);
    }

    @Override
    public void showClickedBar(Bar bar) {
        String uri = "http://maps.google.com/maps?q=loc:" + bar.getLatLng().latitude + "," + bar.getLatLng().longitude + " (" + bar.getName() + ")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    @Override
    public void setPresenter(BarsListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public static class BarsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ITEM = 1;

        private List<Bar> mBars;
        private OnItemClickListener mOnItemClickListener;
        private Context mContext;

        private BarsAdapter(Context context, List<Bar> bars, OnItemClickListener onItemClickListener) {
            mContext = checkNotNull(context);
            mOnItemClickListener = checkNotNull(onItemClickListener);
            setList(bars);
        }

        public void replaceData(List<Bar> bars) {
            setList(bars);
            notifyDataSetChanged();
        }

        public void updateItem(int position, Bar bar) {
            mBars.remove(position);
            mBars.add(position, bar);
            notifyDataSetChanged();
        }

        private void setList(List<Bar> bars) {
            mBars = checkNotNull(bars);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bar, viewGroup, false);
                return new ItemViewHolder(view, new ItemViewHolder.OnItemInteractionListener() {
                    @Override
                    public void onItemClick(int position) {
                        mOnItemClickListener.onItemClick(mBars.get(position));
                    }
                });
            } else {
                throw new IllegalStateException("Not a valid type");
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            if (viewHolder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                Bar bar = mBars.get(position);
                itemViewHolder.tvName.setText(bar.getName());
                itemViewHolder.tvDistance.setText(mContext.getString(R.string.distance) + " " + bar.getDistance() + " m");
            }
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }

        @Override
        public int getItemCount() {
            return (null != mBars ? mBars.size() : 0);
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_distance)
            TextView tvDistance;

            private OnItemInteractionListener mOnItemInteractionListener;

            public ItemViewHolder(View view, OnItemInteractionListener onItemInteractionListener) {
                super(view);
                ButterKnife.bind(this, itemView);
                mOnItemInteractionListener = onItemInteractionListener;
            }

            @OnClick(R.id.ll_list_item)
            void itemClick() {
                mOnItemInteractionListener.onItemClick(getAdapterPosition());
            }

            interface OnItemInteractionListener {
                void onItemClick(int position);
            }
        }

        private interface OnItemClickListener {
            void onItemClick(Bar bar);
        }
    }
}