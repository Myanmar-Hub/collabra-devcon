package net.myanmarhub.collabra.provider;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import net.myanmarhub.collabra.provider.manager.UserSyncManager;
import net.myanmarhub.collabra.util.GCMConstant;

import java.io.IOException;

/**
 * Tin Htoo Aung (Myanmar Hub) on 24/10/13.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s,
                              ContentProviderClient contentProviderClient, SyncResult syncResult) {
        if (bundle != null) {
            int type = Integer.MIN_VALUE, kind = Integer.MIN_VALUE;
            Long message = null;
            try {
                type = Integer.parseInt(bundle.getString("type"));
                kind = Integer.parseInt(bundle.getString("kind"));
                message = Long.parseLong(bundle.getString("message"));
            } catch (NumberFormatException e) {
            }

            switch (kind) {
                case GCMConstant.KIND_USER:
                    switch (type) {
                        case GCMConstant.TYPE_INSERT:
                            try {
                                UserSyncManager.getInstance(getContext(), contentProviderClient).fetchItem(message);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case GCMConstant.TYPE_DELETE:
                            UserSyncManager.getInstance(getContext(), contentProviderClient).delete(message);
                            break;
                    }
                    break;
                default:
                    try {
                        UserSyncManager.getInstance(getContext(), contentProviderClient).fetchAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

}
