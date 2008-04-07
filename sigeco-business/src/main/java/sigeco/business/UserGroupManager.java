/**
 * 
 */
package sigeco.business;

import java.util.List;

import sigeco.business.security.annotation.Secured;
import sigeco.model.UserGroup;

/**
 * CRUD <code>UserGroup</code> operations.
 */
public interface UserGroupManager {
	/**
	 * List all user groups.
	 * @return all users groups
	 */
	@Secured
	List<UserGroup> list();
	
	/**
	 * Saves a <code>UserGroup</code>.
	 * @param userGroup user group
	 */
	@Secured
	void saveUserGroup(final UserGroup userGroup);
	
	/**
	 * Deletes a <code>UserGroup</code>.
	 * @param userGroup user group.
	 */
	@Secured
	void deleteUserGroup(final UserGroup userGroup);
}
