package com.ivashyn.db.dao;

import com.ivashyn.db.entity.User;
import com.ivashyn.db.exception.DaoException;

/**
 * Created by: Dima Ivashyn
 * Date: 13.03.15
 * Time: 12:50
 */
public interface UserDao extends GenericDao<User> {

    public User findByLogin(String login) throws DaoException;
    public User findByEmail(String email) throws DaoException;
}
