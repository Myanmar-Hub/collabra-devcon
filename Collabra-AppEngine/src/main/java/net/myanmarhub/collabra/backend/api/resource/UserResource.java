package net.myanmarhub.collabra.backend.api.resource;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.BadRequestException;

import net.myanmarhub.collabra.backend.api.Config;
import net.myanmarhub.collabra.backend.dao.impl.UserDAOImpl;
import net.myanmarhub.collabra.backend.domain.MessageData;
import net.myanmarhub.collabra.backend.domain.User;
import net.myanmarhub.collabra.backend.util.GCMConstant;
import net.myanmarhub.collabra.backend.util.HibernateUtil;
import net.myanmarhub.collabra.backend.util.RS;
import net.myanmarhub.collabra.backend.util.Utils;

import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

import javax.inject.Named;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
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
public class UserResource {


    @ApiMethod(
            name = "user.getById",
            path = "user/{userId}",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public User getById(@Named("userId") Long userId) {
        Session session = HibernateUtil.getSession();
        try {
            return new UserDAOImpl(session).getById(userId);
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name = "user.getAll",
            path = "user",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public List<User> getAll() {
        Session session = HibernateUtil.getSession();
        try {
            return new UserDAOImpl(session).getAll(null, null);
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name = "user.getByUsername",
            path = "user/with/{username}",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public User getByUserName(@Named("username") String userName) {
        Session session = HibernateUtil.getSession();
        try {
            return new UserDAOImpl(session).getByUserName(userName);
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name = "user.insert",
            path = "user",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public HashMap<String, Long> insert(User user) throws BadRequestException {
        Session session = HibernateUtil.getSession();
        try {
            UserDAOImpl dao = new UserDAOImpl(session);
            if (dao.getByUserName(user.getUsername()) == null) {
                dao.insert(user);
            } else {
                throw new BadRequestException(RS.ERROR_USERNAME_EXIST);
            }
            HashMap<String, Long> result = new HashMap<String, Long>();
            result.put("id", user.getId());
            Utils.GCMNotify(new MessageData(String.valueOf(user.getId()),
                    GCMConstant.KIND_USER, GCMConstant.TYPE_INSERT));
            return result;
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name = "user.delete",
            path = "user/{userId}",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("userId") Long userId) {
        Session session = HibernateUtil.getSession();
        try {
            new UserDAOImpl(session).delete(userId);
            Utils.GCMNotify(new MessageData(String.valueOf(userId),
                    GCMConstant.KIND_USER, GCMConstant.TYPE_DELETE));
        } finally {
            session.close();
        }
    }

}
