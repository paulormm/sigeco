package sigeco.utils.crypto;

import org.springframework.beans.factory.FactoryBean;

/**
 * Factory Bean that returns MD5 hashes.
 *
 * @author julien
 */
public class MD5FactoryBean implements FactoryBean {

	private String message;


	/**
	 * Returns the MD5 Hash of the message.
	 * 
	 * @return byte[]
	 * 
	 * @throws Exception If some weird error occurs.
	 */
	public Object getObject() throws Exception {
		return MD5Helper.computeMD5(this.message);
	}

	/**
	 * Returns the class of the generated object. In this case a byte[].
	 * 
	 * @return Class
	 */
	public Class getObjectType() {
		return byte[].class;
	}

	/**
	 * Wether this hash is a singleton. It can safely be since it won't change given the message.
	 * 
	 *  @return boolean
	 */
	public boolean isSingleton() {
		return true;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

}
