package net.myanmarhub.collabra.backend.dao.impl;

import net.myanmarhub.collabra.backend.dao.IUserDAO;
import net.myanmarhub.collabra.backend.domain.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
public class UserDAOImpl extends BaseDAOImpl<User> implements IUserDAO {


    public UserDAOImpl(Session session) {
        super(session, User.class);
    }

    @Override
    public User getByUserName(String userName) {
        Criteria criteria = mSession.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", userName));
        List<User> users = criteria.list();
        if (users != null && users.size() != 0) {
            return users.get(0);
        }
        return null;
    }

}
