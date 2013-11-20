package net.myanmarhub.collabra.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import net.myanmarhub.collabra.R;

/**
 * Tin Htoo Aung (Myanmar Hub) on 18/10/13.
 */
public class ETextView extends TextView {

    public ETextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                    context.getString(R.string.default_font)));
        }
    }

    public ETextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            setFont(context.getString(R.string.default_font));
        }
    }

    public ETextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            setFont(context.getString(R.string.default_font));
        }
    }

    public void setFont(String font) {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), font));
    }

}
