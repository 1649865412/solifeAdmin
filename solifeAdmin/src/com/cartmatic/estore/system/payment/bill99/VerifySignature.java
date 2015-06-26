package com.cartmatic.estore.system.payment.bill99;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class VerifySignature {
private static VerifySignature verifySignature=null;
	
	private String vpos_cp_cer; //快钱公钥地址
	private String signature; //快钱返回的加密串参数signature
	private String data_sign; //快钱返回的其他参数值的拼接串
	
	public static VerifySignature initVerifySignature(){
		if(verifySignature==null){
			verifySignature=new VerifySignature();
		}
		return verifySignature;
	}
	
	/**
	 * 获取公钥
	 * 
	 * @throws IOException
	 * @throws CertificateException
	 * */
	private PublicKey getPublicKey() {		
		InputStream is = null; // 输入流
		URLConnection conn = null;// 公钥连接
		try {
			Resource resource = new ClassPathResource(vpos_cp_cer);			
			// 通过加密算法获取公钥
			Certificate cert = null;
			try {
				is = resource.getInputStream();
				CertificateFactory cf = CertificateFactory.getInstance("X.509"); // 指定证书类型
				cert = cf.generateCertificate(is); // 获取证书
				return cert.getPublicKey(); // 获得公钥
			} finally {
				if (is != null) {
					is.close();
				}
			}
		} catch (IOException e) {
			System.out.println("无法获取url连接");
			e.printStackTrace();
		} catch (CertificateException e) {
			System.out.println("无法获取url连接");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 验签
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public boolean verifySign(String charset) {
		if (charset == null || signature == null)
			return false;
		PublicKey publicKey = getPublicKey(); // 加载公钥
		
		// 验签
		Signature sig;
		byte[] signed;
		try {
			byte[] signData = signature.getBytes(charset); // 对签名字符串进行编码
			byte[] data = data_sign.getBytes(charset); // 对待签名字符串进行编码
			//开始进行验签
			sig = Signature.getInstance("SHA1WithRSA"); //初始化加密算法
			signed = Base64.decodeBase64(signData);     //数据加密
			sig.initVerify(publicKey);     //初始化公钥用于验证的对象
			sig.update(data);  //验证数据
			return sig.verify(signed);  //验证传入的签名
		}catch (UnsupportedEncodingException e) {
			System.out.println("编码错误");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("初始化加密算法时报错");
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			System.out.println("初始化公钥时报错");
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("验证数据时报错");
			e.printStackTrace();
		}
		return false;
	}

	public void setVpos_cp_cer(String vpos_cp_cer) {
		this.vpos_cp_cer = vpos_cp_cer;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setData_sign(String data_sign) {
		this.data_sign = data_sign;
	}
}
