package sigeco.business.security;

import sigeco.model.User;

/**
 * Holds an instance of a User per Thread.
 *  
 * @author julien
 */
public final class UserLocator {

	private static ThreadLocal<User> userInThread = new ThreadLocal<User>();
	
	/**
	 * C'tor
	 */
	private UserLocator() {
	}
	
	/**
	 * Sets the user for this Thread.
	 * @param user User
	 */
	public static void setUser(final User user) {
		userInThread.set(user);
	}
	
	/**
	 * Returns the User for this Thread. May be null.
	 * @return User
	 */
	public static User getUser() {
		return userInThread.get();
	}
	
	/**
	 * Resets the User for this Thread.
	 */
	public static void resetUser() {
		userInThread.remove();
	}
	
}
