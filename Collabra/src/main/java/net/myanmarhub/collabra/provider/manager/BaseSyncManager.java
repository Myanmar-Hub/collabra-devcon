package net.myanmarhub.collabra.provider.manager;

import android.content.ContentProviderClient;

import com.appspot.myanhub_collabra.collabra.Collabra;

import net.myanmarhub.collabra.util.Utils;

/**
 * Tin Htoo Aung (Myanmar Hub) on 11/11/13.
 */
public abstract class BaseSyncManager<T> {

    protected Collabra mCollabra;
    protected ContentProviderClient mProviderClient;

    protected BaseSyncManager(ContentProviderClient providerClient) {
        this.mProviderClient = providerClient;
        this.mCollabra = Utils.getAPIService();
    }

    protected abstract void processData(T object);

    public abstract void delete(Long id);

    public abstract void deleteAll();

}
