package com.cartmatic.estore.system.payment.bill99;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PemKeySign {
	/*private String[] pemKeyStore = new String[] { "vpos/cert/10411004511118190.pem", "1041100451111819",
			"123456" };*/
	private String pemKeyPath=null;
	private String pemKeyAlias=null;
	private String pemKeyPassword=null;
	public PemKeySign(String pemKeyPath,String pemKeyAlias,String pemKeyPassword){
		this.pemKeyPath=pemKeyPath;
		this.pemKeyAlias=pemKeyAlias;
		this.pemKeyPassword=pemKeyPassword;
	}
	/**
	 * 获取PKCS12的KeyStore
	 * @param path
	 * @param alias
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static KeyStore readPKCS12KeyStore(String path, String alias, String pwd) throws Exception {
		Resource resource = new ClassPathResource(path);
		InputStream is = resource.getInputStream();
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(is, pwd.toCharArray());
			return keyStore;
		} finally {
			if (is != null)
				is.close();
		}
	}

	/**
	 * 获取PEM的KeyStore
	 * @param path
	 * @param alias
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static KeyPair readPEMKeyPair(String path, String alias, final String pwd) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		Resource resource = new ClassPathResource(path);
		
		Reader fRd = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		try {
			PEMReader pemRd = new PEMReader(fRd, new PasswordFinder() {
				public char[] getPassword() {
					return pwd.toCharArray();
				}
			});
			KeyPair pair = (KeyPair) pemRd.readObject();
			return pair;
		} finally {
			if (fRd != null)
				fRd.close();
		}
	}
	
	
	public String encrypt(String text) {
		try {
			// 待验签字符串
			byte[] data = text.getBytes("UTF-8");
			// 获取PEM的私钥
			KeyPair keyPair = PemKeySign.readPEMKeyPair(pemKeyPath, pemKeyAlias, pemKeyPassword);
			PrivateKey pemKey = keyPair.getPrivate();
			java.security.Signature sig = java.security.Signature.getInstance("SHA1WithRSA");
			sig.initSign(pemKey);
			sig.update(data);
			byte[] signed = Base64.encodeBase64(sig.sign());
			String signature = new String(signed);
			return signature;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	
	public void setPemKeyPath(String pemKeyPath) {
		this.pemKeyPath = pemKeyPath;
	}

	public void setPemKeyAlias(String pemKeyAlias) {
		this.pemKeyAlias = pemKeyAlias;
	}

	public void setPemKeyPassword(String pemKeyPassword) {
		this.pemKeyPassword = pemKeyPassword;
	}
	
	
}
