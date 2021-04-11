package ucc.utils;

import org.apache.commons.codec.binary.Base64;

public class EncryptDecrypt {
	
	
	public String encodedValue(String str) {
	// Encode data on your side using BASE64
	byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
	return new String(bytesEncoded);
	}
	
	public String decodedValue(String bytesEncoded) {
	// Decode data on other side, by processing encoded data
	byte[] valueDecoded = Base64.decodeBase64(bytesEncoded);
	return new String(valueDecoded);
	}
	
}
