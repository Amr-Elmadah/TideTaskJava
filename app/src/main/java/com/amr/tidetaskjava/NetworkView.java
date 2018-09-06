package com.amr.tidetaskjava;

public interface NetworkView {

    void showNoConnection(Action retryAction);

    void hideNoConnection();

    void showServerError();

    void showTimeOut();
}
