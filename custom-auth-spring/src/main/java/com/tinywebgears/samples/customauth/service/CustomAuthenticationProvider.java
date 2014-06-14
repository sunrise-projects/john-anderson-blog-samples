package com.tinywebgears.samples.customauth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider implements AuthenticationProvider {
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        if (name.equals("user") && password.equals("user")) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new GrantedAuthorityImpl("ROLE_USER"));            
            UserDetails userDetails = new User(name, password, true, true, true, true, authorities);            
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
            return auth;
        } else if (name.equals("admin") && password.equals("admin")) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
            authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
            UserDetails userDetails = new User(name, password, true, true, true, true, authorities);            
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, password, authorities);
            return auth;
        } else {
        	UserDetails userDetails = new User(name, password, false, false, false, false, new ArrayList<GrantedAuthority>());
        	Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, password, new ArrayList<GrantedAuthority>());
        	auth.setAuthenticated(false);        	
        	return auth;
        }
    }
 
    private boolean checkOTP(String otp, String initsecret, String pin, long offset) {
        
        // Here is the allowed +/- timeframe in minutes
        long timeframe = 3;
        long maxperiod = timeframe * 60; // maxperiod in seconds = +/- 3 minutes
        long time = System.currentTimeMillis() / 1000L; // time in seconds
       
        for(long i = time + (offset * 10) - maxperiod; i <= time + (offset * 10) + maxperiod; i++)
        {
                String tm = String.valueOf(i);
                String md5 = ((MD5(tm.substring(0, tm.length()-1)+initsecret+pin)).substring(0,6));
                if(md5.equals(otp)) return true;
        }      
        return false;
       
    }
    
    private String MD5(String md5) {
        try {
             java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
             byte[] array = md.digest(md5.getBytes());
             StringBuffer sb = new StringBuffer();
             for (int i = 0; i < array.length; ++i) {
               sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
             return sb.toString();
         } catch (java.security.NoSuchAlgorithmException e) {
         }
         return null;
    }     
    
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}