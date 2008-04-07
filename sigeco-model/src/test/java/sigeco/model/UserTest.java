package sigeco.model;

import java.util.List;

import sigeco.utils.junit.AbstractCRUDTestCase;

/**
 * CRUD Tests for User class.
 *
 * @author julien
 */
public class UserTest extends AbstractCRUDTestCase<User> {

	private List<User> validUsers;
	
	private List<User> invalidUsers;
	
	private User user;
	
	/**
	 * C'tor
	 */
	public UserTest() {
		super("test-model-beans.xml");
	}

	/**
	 * Returns an User to be updated.
	 *
	 * @return User
	 */
	@Override
	protected User getEntityToBeUpdated() {
		return this.user;
	}

	/**
	 * Returns a List of invalid Users
	 *
	 * @return List
	 */
	@Override
	protected List<User> getInvalidEntities() {
		return this.invalidUsers;
	}

	/**
	 * Returns a List of valid Users
	 *
	 * @return List
	 */
	@Override
	protected List<User> getValidEntities() {
		return this.validUsers;
	}

	/**
	 * Updates the given User
	 *
	 * @param entity User
	 */
	@Override
	protected void updateEntity(final User entity) {
		entity.setUsername(entity.getUsername() + " modified!");
	}

}
