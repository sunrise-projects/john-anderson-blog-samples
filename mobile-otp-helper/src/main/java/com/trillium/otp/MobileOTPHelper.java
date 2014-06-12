package com.trillium.otp;

public class MobileOTPHelper {

	//Java function to authenticate Mobile-OTP tokens on web servers with Java.
	//Mobile One Time Passwords -- Mobile-OTP -- strong, two-factor authentication with mobile phones
	//http://motp.sourceforge.net/
	//test this on https://kelvin.nu/potato/index.php
	public static void main(String[] args) {
		String initsecret = "1234567890123456";
		String pin = "1234";
		String otp = "f44527";		
		System.out.println(generateOTP(initsecret, pin));		
		boolean result = checkOTP("joe", otp, initsecret, pin, 0);
		System.out.println(result);
	}
	
	// Function to generate an OTP based on the init-secret and the PIN 43980e
	public static String generateOTP(String initsecret, String pin) {		
		long time = System.currentTimeMillis() / 1000L; 
		String tm = String.valueOf(time);
		return ((MD5(tm.substring(0, tm.length()-1)+initsecret+pin)).substring(0,6));
	}
	
	/*******************************
	 * Function checkOTP($user,$otp,$initsecret,$pin,$offset = 0)
	 * $user       : username
	 * $otp        : one-time-password that is to be checked
	 * $initsecret : init-secret from token
	 * $pin        : user PIN
	 * $offset     : time difference between token and server in 10 seconds increments (360 = 1 hour)
	 *
	 * This function returns 0 if the OTP is accepted, and different error codes otherwise (the same as the shell version)
	 */
	
	public static boolean checkOTP(String user, String otp, String initsecret, String pin, long offset) {
		
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
	public static String MD5(String md5) {
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
}
