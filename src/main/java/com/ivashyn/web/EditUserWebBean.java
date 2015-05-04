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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.util.regex.Pattern;

/**
 * Created by ickis on 5/4/15.
 */

@Component
@Scope("request")
public class EditUserWebBean extends BaseWebBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditUserWebBean.class);
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9\\d._-]{1,100}+@([a-zA-Z]+.){1,2}([a-zA-Z]{2,4})";

    @Autowired
    private UserDao userDao;

    private User user;

    @PostConstruct
    private void init() {
        Long id = getIdFromSession(UserListWebBean.USER_ID);
        if (id != null) {
            try {
                user = userDao.findById(id);
            } catch (Exception e) {
                LOGGER.error("Can't get user by id", e);
            }
        }
    }

    public String save() {
        try {
            userDao.update(user);
        } catch (DaoException e) {
            LOGGER.error("Can't update user", e);
            FacesContext.getCurrentInstance().addMessage(null, newFacesErrorMessage("cant.update.user"));
            return "";
        }
        return "goToUserList";
    }

    public void emailValidator(FacesContext context, UIComponent component, Object value) throws Exception {

        if (value != null && !value.toString().isEmpty()) {

            if (!matchPattern(value.toString(), EMAIL_PATTERN))
                throw new ValidatorException(newFacesErrorMessage("validation.message.invalid.email"));


            String oldEmail = userDao.findById(user.getId()).getEmail();
            if (!oldEmail.equals(value.toString()) && userDao.findByEmail(value.toString()) != null)
                throw new ValidatorException(newFacesErrorMessage("message.email.not.unique"));
        }
    }

    private boolean matchPattern(String value, String pattern) {
        return Pattern.compile(pattern, Pattern.UNICODE_CASE).matcher(value).matches();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
