package com.amr.tidetaskjava;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseNetworkFragment extends Fragment implements NetworkView {

    private Snackbar mSnackBarNoConnection;

    @Override
    public void showNoConnection(final Action retryAction) {
        if (retryAction != null) {
            mSnackBarNoConnection = Snackbar.make(getView(), R.string.check_connection, retryAction == null ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_INDEFINITE);
            mSnackBarNoConnection.setAction(R.string.retry, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    retryAction.perform();
                }
            });
        } else {
            mSnackBarNoConnection = Snackbar.make(getView(), R.string.check_connection, retryAction == null ? Snackbar.LENGTH_SHORT : Snackbar.LENGTH_LONG);
        }
        mSnackBarNoConnection.show();
    }

    @Override
    public void hideNoConnection() {
        if (mSnackBarNoConnection != null && mSnackBarNoConnection.isShown()) {
            mSnackBarNoConnection.dismiss();
        }
    }

    @Override
    public void showServerError() {
        Snackbar.make(getView(), R.string.server_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showTimeOut() {
        Snackbar.make(getView(), R.string.timeout, Snackbar.LENGTH_LONG)
                .show();
    }
}
