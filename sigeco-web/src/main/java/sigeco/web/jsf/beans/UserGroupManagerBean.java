/**
 *
 */
package sigeco.web.jsf.beans;

import java.util.List;

import sigeco.business.UserGroupManager;
import sigeco.model.UserGroup;

/**
 * Managed bean implementation of UserGroupManager which delegates to a Spring
 * controlled UserGroupManager.
 *
 */
public class UserGroupManagerBean implements UserGroupManager {
	private UserGroupManager userGroupManager;

	/**
	 * @return all user groups.
	 */
	public List<UserGroup> list() {
		return this.userGroupManager.list();
	}

	/**
	 * Gets all user groups.
	 * @return all user groups.
	 */
	public List<UserGroup> getAll() {
		return this.list();
	}

	/**
	 * Saves a user group.
	 * @param userGroup user group.
	 */
	public void saveUserGroup(final UserGroup userGroup) {
		this.userGroupManager.saveUserGroup(userGroup);
	}

	/**
	 * Delete user group.
	 * @param userGroup UserGroup.
	 */
	public void deleteUserGroup(final UserGroup userGroup) {
		this.userGroupManager.deleteUserGroup(userGroup);
	}

	/**
	 * @param userGroupManager the userGroupManager to set
	 */
	public void setUserGroupManager(final UserGroupManager userGroupManager) {
		this.userGroupManager = userGroupManager;
	}

}
