/**
 *
 */
package sigeco.web.jsf.beans;

import java.util.List;

import javax.faces.event.ActionEvent;

import sigeco.business.UserGroupManager;
import sigeco.business.UserManager;
import sigeco.model.User;
import sigeco.model.UserGroup;

/**
 * Managed bean responsible for the edition of user groups.
 */
public class UserGroupEditionBean {
	private UserGroup chosenGroup;
	private UserManager userManager;
	private UserGroupManager userGroupManager;
	private List<User> users;
	private List<User> managers;

	/**
	 * Prepares the managed bean for an new group inclusion.
	 * @param event event.
	 */
	public void startNewGroupInclusion(final ActionEvent event) {
		this.chosenGroup = new UserGroup();
		this.users = this.userManager.list();
		this.managers = this.userManager.listManagers();
	}

	/**
	 * Prepares the managed bean for an group edition.
	 * @param event ActionEvent
	 */
	public void startGroupEdition(final ActionEvent event) {
		this.users = this.userManager.list();
		this.users.removeAll(this.chosenGroup.getUsers());
		this.managers = this.userManager.listManagers();
		this.managers.removeAll(this.chosenGroup.getManagers());
	}

	/**
	 * Invoked when a group should be removed.
	 * @param event ActionEvent
	 */
	public void removeGroup(final ActionEvent event) {
		this.userGroupManager.deleteUserGroup(this.chosenGroup);
	}

	/**
	 * Saves the user group being added/edited.
	 * @param event event.
	 */
	public void saveUserGroup(final ActionEvent event) {
		if (this.chosenGroup != null) {
			this.userGroupManager.saveUserGroup(this.chosenGroup);
		}
	}

	/**
	 * @return the chosenGroup
	 */
	public UserGroup getChosenGroup() {
		return this.chosenGroup;
	}

	/**
	 * @param chosenGroup the chosenGroup to set
	 */
	public void setChosenGroup(final UserGroup chosenGroup) {
		this.chosenGroup = chosenGroup;
	}

	/**
	 * @param userGroupManager the userGroupManager to set
	 */
	public void setUserGroupManager(final UserGroupManager userGroupManager) {
		this.userGroupManager = userGroupManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(final UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return this.users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(final List<User> users) {
		this.users = users;
	}

	/**
	 * @return the managers
	 */
	public List<User> getManagers() {
		return this.managers;
	}

	/**
	 * @param managers the managers to set
	 */
	public void setManagers(final List<User> managers) {
		this.managers = managers;
	}
}
