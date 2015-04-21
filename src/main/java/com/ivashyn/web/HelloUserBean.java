package com.ivashyn.web;

/**
 * Created by ickis on 4/19/15.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "helloUser", eager = true)
public class HelloUserBean {

    private String username;

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloUserBean.class);

    public HelloUserBean() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        username = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
    }

    public String getMessage() {
        return "Hello " + username + "!";
    }
}