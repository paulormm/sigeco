package sigeco.business;

import java.util.List;

import sigeco.business.security.annotation.Secured;
import sigeco.model.Permission;
import sigeco.model.User;

/**
 * Interface for a User Manager.
 * 
 * Englobes CRUD operations as well as user Authentication.
 * 
 * @author julien
 * @author pitta
 *
 */
public interface UserManager {
	/**
	 * Saves or updates a User.
	 * @param user User
	 */
	@Secured
	void saveUser(final User user);
	
	/**
	 * Deletes a User
	 * @param user User
	 */
	@Secured
	void deleteUser(final User user);
	
	/**
	 * Lists all Users
	 * @return List
	 */
	@Secured
	List<User> list();
	
	/**
	 * Given a user, find its subordinates.
	 * @param user user
	 * @return List.
	 */
	@Secured(Permission.MANAGER)
	List<User> list(final User user);
	
	/**
	 * Lists all users with manager permission.
	 * @return List
	 */
	@Secured
	List<User> listManagers();
	
	/**
	 * Returns a User given its id
	 * @param id long
	 * @return User
	 */
	@Secured(Permission.MANAGER)
	User getUserById(final long id);
	
	/**
	 * Returns a User given its username
	 * @param username String
	 * @return User
	 */
	@Secured
	User getUserByUsername(final String username);
	
	/**
	 * Returns the User with the given username and the given password.
	 * 
	 * Will return null if the username does not exist or if the password 
	 * is incorrect.
	 * 
	 * As a security measure users will not know which error it was if the
	 * above happens.
	 * 
	 * @param username String
	 * @param password String
 	 * @return User or null
	 */
	User authenticate(final String username, final String password);
}
