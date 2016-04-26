package com.tcs.webservices;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptionUtil {

	static Cipher cipher;

	
	public static String decrypt(String valueToDecrypt,  String password) throws Exception {		

		int len = valueToDecrypt.length();     
		byte[] data = new byte[len / 2];    
		for (int i = 0; i < len; i += 2) {        
			data[i / 2] = (byte) ((Character.digit(valueToDecrypt.charAt(i), 16) << 4) 
					+ Character.digit(valueToDecrypt.charAt(i+1), 16));    
		} 

		byte[] keyBytes = password.getBytes("UTF-8");
		byte[] ivBytes = keyBytes;
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec spec = new SecretKeySpec(keyBytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");		
		cipher.init(Cipher.DECRYPT_MODE, spec, ivSpec);
		data = cipher.doFinal(data);
		return(new String(data));

	}
	

		
}
