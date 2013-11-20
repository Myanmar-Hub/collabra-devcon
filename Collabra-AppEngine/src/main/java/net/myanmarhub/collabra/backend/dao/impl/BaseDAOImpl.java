package net.myanmarhub.collabra.backend.dao.impl;

import net.myanmarhub.collabra.backend.dao.DAOException;
import net.myanmarhub.collabra.backend.dao.IBaseDAO;

import org.hibernate.Criteria;
import org.hibernate.Session;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Tin Htoo Aung (Myanmar Hub) on 22/10/13.
 */
public class BaseDAOImpl<T> implements IBaseDAO<T> {

    private Class mClazz;
    protected Session mSession;

    public BaseDAOImpl(Session session, Class<T> clazz) {
        this.mClazz = clazz;
        this.mSession = session;
    }

    @Override
    public List<T> getAll(Integer limit, Integer offset) {
        Criteria criteria = mSession.createCriteria(mClazz);
        //Manage Paging
        if (offset != null) {
            criteria.setFirstResult(offset);
        }
        if (limit != null) {
            criteria.setMaxResults(limit);
        }
        return criteria.list();
    }

    @Override
    public T getById(Long id) {
        return (T) mSession.get(mClazz, id);
    }

    @Override
    public void delete(Long id) {
        T object = getById(id);
        mSession.getTransaction().begin();
        mSession.delete(object);
        mSession.getTransaction().commit();
    }

    @Override
    public void delete(T object) throws DAOException {
        try {
            Long id = (Long) object.getClass().getDeclaredMethod("getId", new Class[]{})
                    .invoke(object, null);
            if (id == null) throw new DAOException(DAOException.ID_NOT_FOUND);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        mSession.getTransaction().begin();
        mSession.delete(object);
        mSession.getTransaction().commit();
    }

    @Override
    public void insert(T object) {
        mSession.getTransaction().begin();
        mSession.persist(object);
        mSession.getTransaction().commit();
    }

    @Override
    public void update(T object) throws DAOException {
        try {
            Long id = (Long) object.getClass().getDeclaredMethod("getId", new Class[]{})
                    .invoke(object, null);
            if (id == null) throw new DAOException(DAOException.ID_NOT_FOUND);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        mSession.getTransaction().begin();
        mSession.update(object);
        mSession.getTransaction().commit();
    }
}
