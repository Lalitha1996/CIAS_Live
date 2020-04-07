package com.cias.utility;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {
	private static final String ALGORITHM = "AES";
	private static final String KEY = "1Hbfh667adfDEJ78";
	private static final Logger logger = Logger.getLogger(AES.class);

	@SuppressWarnings("restriction")
	public static String encrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(AES.ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
		return new BASE64Encoder().encode(encryptedByteValue);

	}

	public static String decrypt(String value) throws Exception {
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(AES.ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedValue64 = new BASE64Decoder().decodeBuffer(value);
		byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
		return new String(decryptedByteValue, "utf-8");

	}

	private static Key generateKey() {
		return new SecretKeySpec(AES.KEY.getBytes(), AES.ALGORITHM);
	}

	public static String getMD5EncryptedValue(String password) {
		final byte[] defaultBytes = password.getBytes();
		try {
			final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
			md5MsgDigest.reset();
			md5MsgDigest.update(defaultBytes);
			final byte[] messageDigest = md5MsgDigest.digest();
			final StringBuilder hexString = new StringBuilder();
			for (final byte element : messageDigest) {
				final String hex = Integer.toHexString(0xFF & element);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			password = hexString + "";
		} catch (final NoSuchAlgorithmException nsae) {
			logger.error(nsae.getMessage());
		}
		return password;
	}

}