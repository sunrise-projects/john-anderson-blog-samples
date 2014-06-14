package com.tinywebgears.samples.customauth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UmsUserDetailsService implements UserDetailsService
{
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMIN";

    private Logger logger = LoggerFactory.getLogger(UmsUserDetailsService.class);

    private ThreadLocal<User> currentUser = new ThreadLocal<User>();
    private ThreadLocal<String> currentPassword = new ThreadLocal<String>();

    private Map<String, String> usersToCookies = new HashMap<String, String>();
    private Map<String, String> cookiesToUsers = new HashMap<String, String>();
    private Map<String, Date> sessionValidity = new HashMap<String, Date>();

    public UmsUserDetailsService()
    {
    }

    public String createSSOSession(String username)
    {
        logger.debug("Creating SSO session for " + username);
        String newCookieValue = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + username;
        String oldCookie = usersToCookies.get(username);
        removeSSOSession(oldCookie);
        cookiesToUsers.put(newCookieValue, username);
        Date now = new Date();
        Date sessionValidityTime = new Date(now.getTime() + 30 * 1000);
        sessionValidity.put(newCookieValue, sessionValidityTime);
        return newCookieValue;
    }

    public void removeSSOSession(String cookie)
    {
        if (cookie != null)
        {
            logger.debug("Removing SSO session: " + cookie);
            String username = cookiesToUsers.get(cookie);
            if (username != null)
                usersToCookies.remove(username);
            cookiesToUsers.remove(cookie);
            sessionValidity.remove(cookie);
        }
    }

    public UserDetails loadUserByCookie(String cookie) throws UsernameNotFoundException, DataAccessException
    {
    	return null;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
    	return null;
    }

    public User getCurrentUser()
    {
        return currentUser.get();
    }

    public void setCurrentUser(User user)
    {
        currentUser.set(user);
    }

    public String getPassword()
    {
        return currentPassword.get();
    }

    public void setPassword(String password)
    {
        currentPassword.set(password);
    }
}
