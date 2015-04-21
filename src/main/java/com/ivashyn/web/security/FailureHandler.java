package com.ivashyn.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ickis on 4/21/15.
 */

public class FailureHandler implements AuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {
        LOGGER.info("Authentication failure", e);
        httpServletRequest.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, e);
        httpServletResponse.sendRedirect("login.jsf");
    }
}
