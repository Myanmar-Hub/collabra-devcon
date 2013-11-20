package net.myanmarhub.collabra.backend.dao;

import java.util.Collection;

/**
 * Tin Htoo Aung (Myanmar Hub) on 22/10/13.
 * <p/>
 * Interface defined for BaseDAO requirements.
 */
public interface IBaseDAO<T> {

    /**
     * Get all objects of type T
     *
     * @param limit  number of objects to be query at a time
     * @param offset index to start query at
     * @return
     */
    public Collection<T> getAll(Integer limit, Integer offset);

    /**
     * Get object of type T by its unique ID
     *
     * @param id unique ID to query with
     * @return
     */
    public T getById(Long id);

    /**
     * Delete object of type T by ID
     *
     * @param id object with ID to delete
     */
    public void delete(Long id);

    /**
     * Delete given object of type T
     *
     * @param object object to be deleted. Id field must be set
     * @throws DAOException if ID field is not set or error encountered
     */
    public void delete(T object) throws DAOException;

    /**
     * Insert given object of type T
     *
     * @param object object to be inserted.
     */
    public void insert(T object);

    /**
     * Update given object of type T
     *
     * @param object object to be updated. ID field must be set
     * @throws DAOException if ID field is not set or error encountered
     */
    public void update(T object) throws DAOException;
}
