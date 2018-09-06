package com.amr.tidetaskjava.customviews;

import android.content.Context;
import android.util.AttributeSet;

public class CTextView extends android.support.v7.widget.AppCompatTextView {
    public CTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CFont.init(this, context, attrs);
    }

    public CTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CFont.init(this, context, attrs);
    }

    public CTextView(Context context) {
        super(context);
        CFont.init(this, context, null);
    }
}