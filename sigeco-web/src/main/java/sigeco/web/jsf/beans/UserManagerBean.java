package sigeco.web.jsf.beans;

import java.util.List;

import javax.faces.context.FacesContext;

import sigeco.business.UserManager;
import sigeco.model.User;

/**
 * JSF Managed Bean that delegates to a spring controlled MatrixManager
 *
 * Please refer to the {@link MatrixManager} interface for further documentation.
 *
 * @author julien
 */
public class UserManagerBean implements UserManager {

	private UserManager userManager;
	public static final String BEAN_NAME = "userManagerBean";

	/**
	 * Static getter method.
	 * @param context FacesContext
	 * @return UserManagerBean
	 */
	public static UserManagerBean getInstance(final FacesContext context) {
		return (UserManagerBean) context.getApplication().createValueBinding("#{" + BEAN_NAME + "}").getValue(context);
	}

	/**
	 * Authenticates a user into the system
	 *
	 * @param username String
	 * @param password String
	 *
	 * @return User or null
	 */
	public User authenticate(final String username, final String password) {
		return this.userManager.authenticate(username, password);
	}

	/**
	 * Deletes a user
	 * @param user User
	 */
	public void deleteUser(final User user) {
		this.userManager.deleteUser(user);
	}


	/**
	 * Returns a user given his id
	 *
	 * @param id long
	 * @return User or null
	 */
	public User getUserById(final long id) {
		return this.userManager.getUserById(id);
	}


	/**
	 * Returns a uer given his username
	 *
	 * @param username String
	 *
	 * @return user or null
	 */
	public User getUserByUsername(final String username) {
		return this.userManager.getUserByUsername(username);
	}


	/**
	 * Returns the list of all users
	 *
	 * @return List
	 */
	public List<User> list() {
		return this.userManager.list();
	}

	/**
	 * Returns the list of all managers
	 *
	 * @return List
	 */
	public List<User> listManagers() {
		return this.userManager.listManagers();
	}

	/**
	 * Returns the list of all users
	 * @return List
	 */
	public List<User> getAll() {
		return this.list();
	}

	/**
	 * gets manager list.
	 * @return List
	 */
	public List<User> getManagers() {
		return this.listManagers();
	}


	/**
	 * Saves a user
	 *
	 * @param user User
	 */
	public void saveUser(final User user) {
		this.userManager.saveUser(user);
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return this.userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(final UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * Lists a user's subordinates
	 *
	 * @param user User
	 *
	 * @return list
	 */
	public List<User> list(final User user) {
		return this.userManager.list(user);
	}


}
