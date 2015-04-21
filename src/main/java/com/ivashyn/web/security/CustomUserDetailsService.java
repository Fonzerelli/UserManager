package com.ivashyn.web.security;

import com.ivashyn.db.dao.UserDao;
import com.ivashyn.db.entity.Role;
import com.ivashyn.db.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by: Dima Ivashyn
 * Date: 27.03.15
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails user = null;

        try {

            User dbUser = userDao.findByLogin(username);

            user = new org.springframework.security.core.userdetails.User(
                    dbUser.getLogin(),
                    dbUser.getPassword(),
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(dbUser.getRole()));

        } catch (Exception e) {
            LOGGER.error("Error in retrieving user", e);
            throw new UsernameNotFoundException("Error in retrieving user");
        }
        return user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(role);
        return authList;
    }

//    public List<String> getRoles(Long role) {
//        List<String> roles = new ArrayList<String>();
//
//        if (role.intValue() == 1) {
//            roles.add("ROLE_ADMIN");
//
//        } else if (role.intValue() == 2) {
//            roles.add("ROLE_USER");
//        }
//
//        return roles;
//    }

    public static List<GrantedAuthority> getGrantedAuthorities(Role role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }
}
