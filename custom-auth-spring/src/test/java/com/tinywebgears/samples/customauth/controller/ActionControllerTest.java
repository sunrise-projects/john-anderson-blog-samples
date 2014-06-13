package com.tinywebgears.samples.customauth.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import junit.framework.Assert;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tinywebgears.samples.customauth.controller.ActionController;


public class ActionControllerTest
{
    //@Test
    public void testController()
    {
        ActionController controller = new ActionController();
        Assert.assertEquals("fileview", controller.listFiles().getViewName());
    }
    
    //@Test
    public void test1() {
        String p = "12345";
        PasswordEncoder passwordEncoder = (PasswordEncoder) new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(p.subSequence(0, p.length()));
        System.out.println(encodedPassword);
          
    }
    
    @Test
    public void test2() throws NoSuchAlgorithmException {
        String password = "user";
    
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        sha1.reset();
        sha1.update(password.getBytes());
        Base64 encoder = new Base64();
        String passwordHash = new String(encoder.encode(sha1.digest()));     
        System.out.println(passwordHash);
    }    
    
    
    
    
    
}
