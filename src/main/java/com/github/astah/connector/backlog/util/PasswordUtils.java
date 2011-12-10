package com.github.astah.connector.backlog.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PasswordUtils {
	private static final String ALGORITHM = "Blowfish";
	
	public static byte[] encrypt(String key, String text) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		javax.crypto.spec.SecretKeySpec sksSpec = new javax.crypto.spec.SecretKeySpec(key.getBytes(), ALGORITHM);
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, sksSpec);
		byte[] encrypted = cipher.doFinal(text.getBytes());
		return encrypted;
	}

	public static String decrypt(String key, byte[] encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		javax.crypto.spec.SecretKeySpec sksSpec = new javax.crypto.spec.SecretKeySpec(key.getBytes(), ALGORITHM);
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, sksSpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return new String(decrypted);
	}
	
	public static void main(String[] args) throws Throwable {
		byte[] encrypt = PasswordUtils.encrypt("Backlog", "developer");
		String es = new String(encrypt, "iso-8859-1");
		String decrypt = PasswordUtils.decrypt("Backlog", es.getBytes("iso-8859-1"));
		System.out.println(decrypt);
	}
}
