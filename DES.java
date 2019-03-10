import javax.swing.*;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random ;
class DES {
	byte[] skey = new byte[1000];
	String skeyString;
	static byte[] raw;
	static int control;
	private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 };
	String inputMessage,encryptedData,decryptedMessage;


public DES(String input,int control,String key) {
	this.control=control;
try {
	
	generateSymmetricKey(key);
	
	//SecretKey key = KeyGenerator.getInstance("DES").generateKey();
	inputMessage=input;
	
	byte[] ibyte = inputMessage.getBytes();
	
	
	byte[] ebyte=encrypt(raw, ibyte);
	String encryptedData = new String(ebyte);
	this.encryptedData=encryptedData;
	//System.out.println("Encrypted message "+encryptedData);
	
	
	byte[] dbyte= decrypt(raw,ebyte);
	String decryptedMessage = new String(dbyte);
	//System.out.println("Decrypted message "+decryptedMessage);
	this.decryptedMessage=decryptedMessage;
	
	
	//JOptionPane.showMessageDialog(null,"Encrypted Data "+"\n"+encryptedData);

	
	//JOptionPane.showMessageDialog(null,"Decrypted Data "+"\n"+decryptedMessage);
}
	catch(Exception e) {
	System.out.println(e);
	}

}
public String Show(int EncryptOrDecrypt) {
	if(EncryptOrDecrypt==0) {
		return encryptedData;
	}
	else {
	return	decryptedMessage;
	}
}
void generateSymmetricKey(String key) {
	try {
		
		String knum = key;
		byte[] knumb = knum.getBytes();
		skey=getRawKey(knumb);
		skeyString = new String(skey);
		//System.out.println("DES Symmetric key = "+skeyString);
	}
	catch(Exception e) {
		System.out.println(e);
	}
}

private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("DES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(56, sr);
		SecretKey skey = kgen.generateKey();
		raw = skey.getEncoded();
	return raw;
	}
private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
	AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
	SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
	if(control==0) {
		Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, skeySpec, paramSpec);
		byte[] encrypted = encryptCipher.doFinal(clear);
		return encrypted;
	}
	else {
		Cipher encryptCipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, skeySpec, paramSpec);
		byte[] encrypted = encryptCipher.doFinal(clear);
		return encrypted;
	}
	
}
private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
		if(control==0) {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, paramSpec);
			byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
		}
		else {
			Cipher cipher = Cipher.getInstance("DES/OFB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, paramSpec);
			byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
		}
	//	SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
		
}








}