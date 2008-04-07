package sigeco.web.jsf.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import sigeco.model.Permission;
import sigeco.model.User;

/**
 * Provides support for verifying that a given user has a required permission.
 * 
 * @author julien
 */
public class PermissionBean {

	/**
	 * Returns true if the current user is an Administrator.
	 * @return boolean
	 */
	public boolean isAdmin() {
		return mustBe(Permission.ADMIN);
	}
	
	/**
	 * Returns true if the current user is a Manager.
	 * @return boolean
	 */
	public boolean isManager() {
		return mustBe(Permission.MANAGER);
	}

	
	/**
	 * Returns true if the current user is an User.
	 * @return boolean
	 */
	public boolean isUser() {
		return mustBe(Permission.USER);
	}
	
	/**
	 * Returns current user.
	 * @return current user.
	 */
	public User getUser() {
		HttpServletRequest request = 
			(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return (User) request.getSession().getAttribute(LoginBean.USER_SESSION_KEY);
	}
	
	/**
	 * Returns true if the current user has the given permission.
	 * @param permission Permission
	 * @return boolean
	 */
	private boolean mustBe(final Permission permission) {
		User user = getUser();
		
		if (user != null) {
			return permission.isRole(user.getPermission());
		}
		return false;
	}
	
}
