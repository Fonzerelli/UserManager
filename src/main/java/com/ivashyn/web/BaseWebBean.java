package com.ivashyn.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by ickis on 5/4/15.
 */
public class BaseWebBean {

    protected void putIdInSession(String key, Long id) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put(key, id);
    }

    protected Long getIdFromSession(String key) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        return (Long) sessionMap.get(key);
    }


    protected FacesMessage newFacesErrorMessage(String messageText) {

        ResourceBundle resourceBundle = ResourceBundle.
                getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        return new FacesMessage(FacesMessage.SEVERITY_ERROR,
                resourceBundle.getString(messageText),
                resourceBundle.getString(messageText));
    }

}
