package net.myanmarhub.collabra.backend.api.resource;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import net.myanmarhub.collabra.backend.api.Config;
import net.myanmarhub.collabra.backend.dao.impl.BaseDAOImpl;
import net.myanmarhub.collabra.backend.domain.GCMDevice;
import net.myanmarhub.collabra.backend.util.HibernateUtil;

import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;

/**
 * Tin Htoo Aung (Myanmar Hub) on 30/10/13.
 */
@Api(
        name = "collabra",
        version = Config.VERSION,
        description = Config.DESCRIPTION,
        scopes = Config.SCOPE_EMAIL,
        audiences = {Config.AUDIENCE_DEBUG},
        clientIds = {Config.CLIENT_WEB, Config.CLIENT_ANDROID_DEBUG, Config.CLIENT_API_EXPLORER},
        defaultVersion = AnnotationBoolean.TRUE
)
public class DeviceInfoResource {

    @ApiMethod(
            name="gcm.device.insert",
            path = "device",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public HashMap<String, String> insert(GCMDevice deviceInfo)
            throws UnauthorizedException {

        Session session = HibernateUtil.getSession();
        try {
            new BaseDAOImpl<GCMDevice>(session, GCMDevice.class).insert(deviceInfo);
            HashMap<String, String> result = new HashMap<String, String>();
            result.put("id", deviceInfo.getDeviceRegistrationID());
            return result;
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name="gcm.device.delete",
            path = "device/{regId}",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("regId") String regId)
            throws UnauthorizedException {
        Session session = HibernateUtil.getSession();
        try {
            new BaseDAOImpl<GCMDevice>(session, GCMDevice.class).delete(regId);
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name="gcm.device.getAll",
            path = "device",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public List<GCMDevice> getAll(@Named("limit") @Nullable Integer limit,
                                  @Named("offset") @Nullable Integer offset)
            throws UnauthorizedException {
        Session session = HibernateUtil.getSession();
        try{
            if(limit == null)limit = 20;
            return new BaseDAOImpl<GCMDevice>(session, GCMDevice.class).getAll(limit, offset);
        }finally {
            session.close();
        }
    }
}
