package com.cartmatic.estore.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptUtil
{

	private static final CryptUtil instance = new CryptUtil();

	private CryptUtil()
	{

	}

	public static CryptUtil getInstance() {
		return instance;
	}

	private Key initKeyForAES(String key) throws NoSuchAlgorithmException {
		if (null == key || key.length() == 0)
		{
			throw new NullPointerException("key not is null");
		}
		SecretKeySpec key2 = null;
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			key2 = new SecretKeySpec(enCodeFormat, "AES");
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new NoSuchAlgorithmException();
		}
		return key2;

	}

	/**
	 * AES加密算法，不受密钥长度限制
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public String encryptAES(String content, String key) {
		try
		{
			SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return asHex(result); // 加密
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * aes解密算法，不受密钥长度限制
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public String decryptAES(String content, String key) {
		try
		{
			SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
			byte[] result = cipher.doFinal(asBytes(content));
			return new String(result); // 加密
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将2进制数值转换为16进制字符串
	 * 
	 * @param buf
	 * @return
	 */
	public String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++)
		{
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	/**
	 * 将16进制转换
	 * 
	 * @param hexStr
	 * @return
	 */
	public byte[] asBytes(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++)
		{
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) {
		CryptUtil crypt = CryptUtil.getInstance();
		String content = "shanfeng929@163.com";
		System.out.println(crypt.encryptAES(content, "mindo"));
		String dcontent = crypt.encryptAES(content, "mindo");
		System.out.println(crypt.decryptAES(dcontent, "mindo"));
	}
}
