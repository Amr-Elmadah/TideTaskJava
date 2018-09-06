package com.amr.tidetaskjava.bars;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.amr.tidetaskjava.R;
import com.amr.tidetaskjava.utils.ActivityUtils;

import butterknife.ButterKnife;

public class BarsActivity extends AppCompatActivity {
    private BarsFragment barsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);
        ButterKnife.bind(this);
        barsFragment = (BarsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (barsFragment == null) {
            barsFragment = new BarsFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), barsFragment, R.id.container);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
