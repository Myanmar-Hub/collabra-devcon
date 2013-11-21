package net.myanmarhub.collabra.provider.manager;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;

import com.appspot.myanhub_collabra.collabra.model.User;

import net.myanmarhub.collabra.dao.UserDAO;
import net.myanmarhub.collabra.provider.CollabraKind;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Tin Htoo Aung (Myanmar Hub) on 10/11/13.
 */
public class UserSyncManager extends BaseSyncManager<User> {

    private UserDAO mUserDAO;
    private static UserSyncManager INSTANCE;

    public static UserSyncManager getInstance(Context context, ContentProviderClient providerClient) {
        if (INSTANCE == null) {
            INSTANCE = new UserSyncManager(context, providerClient);
        }
        return INSTANCE;
    }

    private UserSyncManager(Context context, ContentProviderClient providerClient) {
        super(providerClient);
        this.mUserDAO = new UserDAO(context);
    }

    public void fetchItem(Long userId) throws IOException {
        User user = mCollabra.user().getById(userId).execute();
        if (user != null) processData(user);
    }

    public void fetchAll() throws IOException {
        Collection<User> users = mCollabra.user().getAll().execute().getItems();
        if (users != null) {
            mUserDAO.deleteAll();
            Iterator<User> iterator = users.iterator();
            while (iterator.hasNext()) {
                processData(iterator.next());
            }
        }
    }

    @Override
    protected void processData(User user) {
        if (mUserDAO.getById(user.getId()) == null) {
            insert(user);
        } else {
            update(user);
        }
    }

    private void insert(User object) {
        try {
            mProviderClient.insert(CollabraKind.User.CONTENT_URI,
                    mUserDAO.toContentValues(object));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void update(User object) {
        try {
            mProviderClient.update(Uri.parse(CollabraKind.User.CONTENT_URI + "/" +
                    object.getId()), mUserDAO.toContentValues(object), null, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            try {
                mProviderClient.delete(Uri.parse(CollabraKind.User.CONTENT_URI + "/" + id),
                        null, null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteAll() {
        try {
            mProviderClient.delete(CollabraKind.User.CONTENT_URI, null, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
