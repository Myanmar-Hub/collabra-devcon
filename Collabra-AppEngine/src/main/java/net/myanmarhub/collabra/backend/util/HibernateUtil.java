package net.myanmarhub.collabra.backend.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Tin Htoo Aung (Myanmar Hub) on 22/09/13.
 */
public class HibernateUtil {

    private static SessionFactory factory;

    static {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getFactory() {
        return factory;
    }

    public static Session getSession() {
        return factory.openSession();
    }

    public static void shutdown() {
        getFactory().close();
    }
}
