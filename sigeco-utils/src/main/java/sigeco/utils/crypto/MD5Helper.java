package sigeco.utils.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class for MD5 computation
 * 
 * @author julien
 * @author pitta
 *
 */
public final class MD5Helper {

	private static final String MD5 = "MD5";

	/**
	 * To prevent instantiation.
	 */
	private MD5Helper() {
	}
	
	/**
	 * Computes the MD5 Hash of the given message.
	 * 
	 * @param message String
	 * @return byte[]
	 */
	public static byte[] computeMD5(final String message) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			return md.digest(message.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
