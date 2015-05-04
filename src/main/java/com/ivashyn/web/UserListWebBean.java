package com.ivashyn.web;

import com.ivashyn.db.dao.UserDao;
import com.ivashyn.db.entity.User;
import com.ivashyn.db.exception.DaoException;
import com.ivashyn.util.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ickis on 4/22/15.
 */

@Component
@Scope("request")
public class UserListWebBean extends BaseWebBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserListWebBean.class);
    public static final String USER_ID = "userId";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    private List<User> userList;

    private List<User> userListForEmail = new ArrayList<User>();

    private String message = "";
    private String subject = "";

    @PostConstruct
    public void init() {
        try {
            userList = userDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Can't get all users", e);
        }
    }

    public String editUser(User user) {
        putIdInSession(USER_ID, user.getId());
        return "goToUserDetails";
    }

    public synchronized void send() {

        if (CollectionUtils.isEmpty(userListForEmail)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No recipient has been chosen", "Select at least one recipient"));
            return;
        }
        final String[] recipients = parseTo();
        final String msg = message;
        final String subj = subject;
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    mailService.sendEmail("test@test.tes", subj, recipients, msg);
                }
            });

            thread.start();
        } catch (Exception e) {
            LOGGER.error("Can't send email");
        }
        userListForEmail.clear();
        message = "";
        subject = "";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "The email was successfully sent", "The email was successfully sent"));

    }

    private String[] parseTo() {
        String[] toArray = new String[userListForEmail.size()];
        for (int i = 0; i < userListForEmail.size(); i++) {
            toArray[i] = userListForEmail.get(i).getEmail();
        }
        return toArray;
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<User> getUserListForEmail() {
        return userListForEmail;
    }

    public void setUserListForEmail(List<User> userListForEmail) {
        this.userListForEmail = userListForEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
