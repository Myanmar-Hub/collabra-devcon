package net.myanmarhub.collabra.backend.util;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.response.UnauthorizedException;

import net.myanmarhub.packntrade.backend.dao.impl.AccountDAOImpl;
import net.myanmarhub.packntrade.backend.dao.impl.BaseDAOImpl;
import net.myanmarhub.packntrade.backend.domain.Account;
import net.myanmarhub.packntrade.backend.gcm.GCMDevice;
import net.myanmarhub.packntrade.backend.gcm.MessageData;
import net.myanmarhub.packntrade.backend.gcm.dao.GCMDeviceImpl;

import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Tin Htoo Aung (Myanmar Hub) on 23/10/13.
 */
public class Utils {
    private static final String API_KEY = "AIzaSyA42uEYUE24OZzodLMysDfeTYK55LcKy3w";

    public static Account authorizedUser(com.google.appengine.api.users.User authUser)
            throws UnauthorizedException {
        if (authUser == null) {
            throw new UnauthorizedException(RS.ERROR_UNAUTHORIZED);
        }
        Session session = HibernateUtil.getSession();
        try {
            AccountDAOImpl accountDAOImpl = new AccountDAOImpl(session);
            Account account = accountDAOImpl.getByEmail(authUser.getEmail());
            if (account == null) {
                throw new UnauthorizedException(RS.ERROR_UNAUTHORIZED);
            }
            return account;
        } finally {
            session.close();
        }
    }

    public static Account authorizedAdmin(com.google.appengine.api.users.User authUser)
            throws UnauthorizedException {
        if (authUser == null) {
            throw new UnauthorizedException(RS.ERROR_UNAUTHORIZED);
        }
        Session session = HibernateUtil.getSession();
        try {
            AccountDAOImpl accountDAOImpl = new AccountDAOImpl(session);
            Account account = accountDAOImpl.getByEmail(authUser.getEmail());
            if (account == null || account.getIsAdmin() == 1) {
                throw new UnauthorizedException(RS.ERROR_UNAUTHORIZED);
            }
            return account;
        } finally {
            session.close();
        }
    }

    public static void GCMNotify(MessageData messageData) {
        Session session = HibernateUtil.getSession();
        ArrayList<GCMDevice> devices;
        BaseDAOImpl<MessageData> dao = new BaseDAOImpl<MessageData>(session, MessageData.class);
        try {
            Integer offset = 0;
            Integer limit = 20;
            do {
                devices = new ArrayList<GCMDevice>(new BaseDAOImpl<GCMDevice>(session, GCMDevice.class)
                        .getAll(limit, offset));
                for (GCMDevice device : devices) {
                    doSendViaGcm(messageData, device, messageData.getMessage());
                    messageData.setGcmDevice(device);
                }
                dao.insert(messageData);
                offset += limit;
                devices = new ArrayList<GCMDevice>(new BaseDAOImpl<GCMDevice>(session, GCMDevice.class)
                        .getAll(limit, offset));
            } while (devices.size() != 0);
        } finally {
            session.close();
        }

    }

    private static Result doSendViaGcm(MessageData messageData, GCMDevice deviceInfo, String key) {
        Session session = HibernateUtil.getSession();
        Result result = null;
        try {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("message", messageData.getMessage());
            data.put("kind", String.valueOf(messageData.getKind()));
            data.put("type", String.valueOf(messageData.getType()));

            Message msg = new Message.Builder()
                    .setData(data)
                    .collapseKey(key)
                    .build();
            result = new Sender(API_KEY).send(msg, deviceInfo.getDeviceRegistrationID(),
                    5);
            if (result.getMessageId() != null) {
                String canonicalRegId = result.getCanonicalRegistrationId();
                if (canonicalRegId != null) {
                    deviceInfo.setDeviceRegistrationID(canonicalRegId);
                    new GCMDeviceImpl(session).update(deviceInfo);
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    new GCMDeviceImpl(session).delete(deviceInfo.getDeviceRegistrationID());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }


}
