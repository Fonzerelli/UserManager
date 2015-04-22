package com.ivashyn.web;

import com.ivashyn.db.dao.UserDao;
import com.ivashyn.db.entity.User;
import com.ivashyn.db.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by ickis on 4/22/15.
 */

@Component
@Scope("request")
public class UserListWebBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserListWebBean.class);

    @Autowired
    private UserDao userDao;

    private List<User> userList;

    @PostConstruct
    public  void init() {
        try {
            userList = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Can't get all uesrs", e);
        }
    }

    public List<User> getUserList() {
        return userList;
    }
}
