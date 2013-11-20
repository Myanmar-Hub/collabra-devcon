package net.myanmarhub.collabra.backend.api.resource;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

import net.myanmarhub.collabra.backend.api.Config;
import net.myanmarhub.collabra.backend.dao.impl.BaseDAOImpl;
import net.myanmarhub.collabra.backend.domain.Conversation;
import net.myanmarhub.collabra.backend.domain.GCMConstant;
import net.myanmarhub.collabra.backend.domain.MessageData;
import net.myanmarhub.collabra.backend.util.HibernateUtil;
import net.myanmarhub.collabra.backend.util.Utils;

import org.hibernate.Session;

import java.util.HashMap;

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
public class ConversationResource {

    @ApiMethod(
            name = "conversation.getById",
            path = "conversation/{conversationId}",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public Conversation getById(@Named("conversationId") Long userId) {
        Session session = HibernateUtil.getSession();
        try {
            return new BaseDAOImpl<Conversation>(session, Conversation.class).getById(userId);
        } finally {
            session.close();
        }
    }

    @ApiMethod(
            name = "conversation.insert",
            path = "conversation",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public HashMap<String, Long> insert(Conversation conversation) {
        Session session = HibernateUtil.getSession();
        try {
            new BaseDAOImpl<Conversation>(session, Conversation.class).insert(conversation);
            HashMap<String, Long> result = new HashMap<String, Long>();
            result.put("id", conversation.getId());
            Utils.GCMNotify(new MessageData(String.valueOf(conversation.getId()),
                    GCMConstant.KIND_CONVERSATION, GCMConstant.TYPE_INSERT));
            return result;
        } finally {
            session.close();
        }
    }

}
