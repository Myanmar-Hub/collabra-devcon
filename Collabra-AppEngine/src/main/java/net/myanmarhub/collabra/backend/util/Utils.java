package net.myanmarhub.collabra.backend.util;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import net.myanmarhub.collabra.backend.dao.DAOException;
import net.myanmarhub.collabra.backend.dao.impl.BaseDAOImpl;
import net.myanmarhub.collabra.backend.domain.GCMDevice;
import net.myanmarhub.collabra.backend.domain.MessageData;

import org.hibernate.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Tin Htoo Aung (Myanmar Hub) on 23/10/13.
 */
public class Utils {
    private static final String API_KEY = "AIzaSyCCiu76PYBzcO0FaO5P8FiiJf9oooIJx64";

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
        BaseDAOImpl<GCMDevice> dao = new BaseDAOImpl<GCMDevice>(session, GCMDevice.class);
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
                    try {
                        dao.update(deviceInfo);
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                String error = result.getErrorCodeName();
                if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                    dao.delete(deviceInfo.getDeviceRegistrationID());
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
