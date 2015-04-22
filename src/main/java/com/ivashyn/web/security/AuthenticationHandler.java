package com.ivashyn.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ickis on 4/22/15.
 */

public class AuthenticationHandler implements AuthenticationSuccessHandler {

    private static final String ADMIN_ROLE_NAME = "Admin";
    private static final String ADMIN_SUCCESS_URL = "./pages/admin/userList.jsf";
    private static final String USER_SUCCESS_URL = "./pages/user/home.jsf";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

//        RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher(USER_SUCCESS_URL);
        List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
            for (GrantedAuthority authority : authList) {
                if (ADMIN_ROLE_NAME.equals(authority.getAuthority())) {
                    httpServletResponse.sendRedirect(ADMIN_SUCCESS_URL);
                    return;
                }
            }

        httpServletResponse.sendRedirect(USER_SUCCESS_URL);
    }
}
