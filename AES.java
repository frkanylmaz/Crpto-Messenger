import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES
{
    private byte[] key;
    public int ivSize;
    private static final String ALGORITHM = "AES";

    public AES(byte[] key)
    {
        this.key = key;
        this.ivSize=16;
    }
  
    	
      
  
    /**
     * Encrypts the given plain text
     *
     * @param plainText The plain text to encrypt
     */
    public byte[] encrypt(String plainText,String key,int control) throws Exception
{		
    	byte[] clean = plainText.getBytes();
    	byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
         
    	byte[] s;
    	MessageDigest digest = MessageDigest.getInstance("SHA-256");
    	digest.update(key.getBytes("UTF-8"));
        byte[] keyBytes = new byte[16];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        //SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
       
    	if(control==0) {
    			
    		  	SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
    	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	        
    	        cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);

    	        s=cipher.doFinal(clean);
    	}
    	else {
    		SecretKeySpec secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
	        Cipher cipher = Cipher.getInstance("AES/OFB/PKCS5Padding");
	       // IvParameterSpec iv=getIV();
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey,ivParameterSpec);

	        s=cipher.doFinal(clean);
    	}
    	//System.out.println(s);
    	byte[] encryptedIVAndText = new byte[ivSize + s.length];
    	
        	System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
        	System.arraycopy(s, 0, encryptedIVAndText, ivSize, s.length);
        return encryptedIVAndText;
      
    }

    /**
     * Decrypts the given byte array
     *
     * @param cipherText The data to decrypt
     */
    public String decrypt(byte[] cipherText,String key,int control) throws Exception
    {
    	int ivSize = 16;
        int keySize = 16;

        // Extract IV.
        byte[] iv = new byte[ivSize];
        System.arraycopy(cipherText, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract encrypted part.
        int encryptedSize = cipherText.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(cipherText, ivSize, encryptedBytes, 0, encryptedSize);

        // Hash key.
        byte[] keyBytes = new byte[keySize];
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(key.getBytes());
        System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        if(control==0) {
        	 Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
             cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
             byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);
             return new String(decrypted);
        }
        else {
        	 Cipher cipherDecrypt = Cipher.getInstance("AES/OFB/PKCS5Padding");
             cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
             byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);
             return new String(decrypted);
        }
       

       
    }
}