package net.myanmarhub.collabra.backend.dao;

import net.myanmarhub.collabra.backend.domain.User;

/**
 * Tin Htoo Aung (Myanmar Hub) on 20/11/13.
 */
public interface IUserDAO {

    public User getByUserName(String userName);
}
