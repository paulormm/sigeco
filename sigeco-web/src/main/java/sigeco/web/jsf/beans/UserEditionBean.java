package sigeco.web.jsf.beans;

import javax.faces.event.ActionEvent;

import sigeco.business.UserManager;
import sigeco.model.Grant;
import sigeco.model.Permission;
import sigeco.model.User;
import sigeco.utils.crypto.MD5Helper;

/**
 * Backing Bean for creating and editing users.
 *
 * @author julien
 */
public class UserEditionBean {
	private User userOnFocus;
	private UserManager userManager;
	private String password;
	private String[] permission;
	private String[] grants;
	private String permissionType;

	/**
	 * Starts the inclusion of a User.
	 *
	 * @param event ActionEvent
	 */
	public void startUserInclusion(final ActionEvent event) {
		this.userOnFocus = new User();
		this.permissionType = "SPECIAL";
		this.grants = new String[0];
		this.permission = new String[0];
	}

	/**
	 * @return the userOnFocus
	 */
	public User getUserOnFocus() {
		return this.userOnFocus;
	}

	/**
	 * @param userOnFocus the userOnFocus to set
	 */
	public void setUserOnFocus(final User userOnFocus) {
		this.userOnFocus = userOnFocus;
		this.permission = Permission.currentRoles(userOnFocus.getPermission());
		this.grants = Grant.currentGrants(userOnFocus.getGrants());
		if (permission.length == 0) {
			this.permissionType = "SPECIAL";
		} else {
			this.permissionType = "GENERAL";
		}
	}

	/**
	 * Invoked when the user should be saved.
	 * @param event ActionEvent
	 */
	public void save(final ActionEvent event) {
		byte[] pwdBytes = MD5Helper.computeMD5(this.password);
		this.userOnFocus.setPassword(pwdBytes);
		if (this.permissionType.equals("SPECIAL")) {
			this.grants = new String[0];
			this.permission = new String[0];
		}
		this.userOnFocus.setPermission(Permission.combine(this.permission));
		this.userOnFocus.setGrants(Grant.combine(this.grants));
		userManager.saveUser(this.userOnFocus);
		this.userOnFocus = null;
	}
	
	/**
	 * Removes chosen user.
	 * @param event event
	 */
	public void remove(final ActionEvent event) {
		if (this.userOnFocus != null) {
			userManager.deleteUser(this.userOnFocus);
			this.userOnFocus = null;
		}
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @return the permission
	 */
	public String[] getPermission() {
		return this.permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(final String[] permission) {
		this.permission = permission;
	}
	
	/**
	 * If current user is set as User with matrix.
	 * @return boolean.
	 */
	public boolean isUser() {
		if (this.permission != null) {
			for (final String p : permission) {
				if (p.equals("USER")) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * If current user is set as manager.
	 * @return boolean.
	 */
	public boolean isManager() {
		if (this.permission != null) {
			for (final String p : permission) {
				if (p.equals("MANAGER")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	
	/**
	 * @return grants
	 */
	public String[] getGrants() {
		return grants;
	}

	/**
	 * @param grants grants
	 */
	public void setGrants(final String[] grants) {
		this.grants = grants;
	}

	/**
	 * @return permissionType permissionType
	 */
	public String getPermissionType() {
		return permissionType;
	}

	/**
	 * @param permissionType permissionType
	 */
	public void setPermissionType(final String permissionType) {
		this.permissionType = permissionType;
	}

	/**
	 * @param userManager userManager.
	 */
	public void setUserManager(final UserManager userManager) {
		this.userManager = userManager;
	}
}
