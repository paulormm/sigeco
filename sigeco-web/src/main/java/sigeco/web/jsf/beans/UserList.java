/**
 * 
 */
package sigeco.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import sigeco.business.UserManager;
import sigeco.model.User;

/**
 * Managed bean responsible for the list of users visible to the current user.
 */
public class UserList {
	private UserManager userManager;
	private PermissionBean permissionBean;
	
	/**
	 * C'tor.
	 */
	public UserList() {
	}
	
	/**
	 * Lists visible users to current user.
	 * @return List.
	 */
	public List<User> getList() {
		if (permissionBean.isAdmin()) {
			return userManager.list();
		} else if (permissionBean.isManager()) {
			return userManager.list(permissionBean.getUser());
		}
		final List<User> currentUserList = new ArrayList<User>();
		currentUserList.add(permissionBean.getUser());
		return currentUserList;
	}

	/**
	 * Setter.
	 * @param userManager userManager.
	 */
	public void setUserManager(final UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * Setter.
	 * @param permissionBean permissionBean.
	 */
	public void setPermissionBean(final PermissionBean permissionBean) {
		this.permissionBean = permissionBean;
	}
	
	
}
