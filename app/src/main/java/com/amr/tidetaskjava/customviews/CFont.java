package com.amr.tidetaskjava.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.amr.tidetaskjava.R;


class CFont {
    public static void init(TextView textView, Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextFonts,
                0, 0);
        try {
            int mFontStyle = typedArray.getInteger(R.styleable.TextFonts_textStyleFont, 0);
            switch (mFontStyle) {
                case 0:
                    textView.setTypeface(null, Typeface.NORMAL);
                    break;
                case 1:
                    textView.setTypeface(null, Typeface.BOLD);
                    break;
                default:
                    textView.setTypeface(null, Typeface.NORMAL);
                    break;
            }
        } finally {
            typedArray.recycle();
        }

    }
}
