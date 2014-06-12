package com.trillium.otp;

import junit.framework.TestCase;

public class MobileOTPHelperTest  extends TestCase {

	public void testOTP() {	
		String initsecret = "1234567890123456";
		String pin = "1234";
		String otp = "f44527";		
		System.out.println(MobileOTPHelper.generateOTP(initsecret, pin));		
		boolean result = MobileOTPHelper.checkOTP("joe", otp, initsecret, pin, 0);
		System.out.println(result);
	}
	
	public void testOTP2() {	
		String initsecret = "1234567890123456";
		String pin = "1234";	
		String otp = MobileOTPHelper.generateOTP(initsecret, pin);		
		boolean result = MobileOTPHelper.checkOTP("joe", otp, initsecret, pin, 0);
		assertEquals(result, true);
		
	}	
}
