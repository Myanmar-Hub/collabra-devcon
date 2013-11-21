package net.myanmarhub.collabra;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import net.myanmarhub.collabra.util.Utils;

/**
 * Tin Htoo Aung (Myanmar Hub) on 21/11/13.
 */
public class BaseFragmentActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Utils.checkPlayService(this)) {
            Utils.registerGCM(this);
        }
    }
}
