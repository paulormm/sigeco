package sigeco.web.jsf.beans;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sigeco.business.UserManager;
import sigeco.model.Permission;
import sigeco.model.User;

public class LoginBean {
	public static final String USER_SESSION_KEY = "user";

	private String username;
	private String password;

	private UserManager userManager;
	
	private String userIdentification;

	public static LoginBean getInstance(FacesContext context) {
		return (LoginBean)context.getApplication().createValueBinding("#{" + "loginBean" + "}").getValue(context);
	}
	
	public String login() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		User bd = this.userManager.authenticate(username, password);
		if (bd != null) {
			HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
			req.getSession().setAttribute(USER_SESSION_KEY, bd);
			return "login successful";
		}
		
		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		
		FacesMessage message = new FacesMessage();
		message.setDetail(bundle.getString("login.form.error"));
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		ctx.addMessage("loginForm", message);
		return "login error";
	}
	
	public String logout() {
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		req.getSession().invalidate();
		return "logout";
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return this.userManager;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(final UserManager userManager) {
		this.userManager = userManager;
	}

	public User getCurrentUser() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return (User)req.getSession().getAttribute(USER_SESSION_KEY);
	}
	
	/**
	 * Returns the user identification that is displayed on the top-right
	 * corner of the page.
	 * 
	 * The format is "name (role(s))".
	 * 
	 * @return String
	 */
	public String getUserIdentification() {
		if (this.userIdentification == null) {
			HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			WebApplicationContext wac = 
				WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
			ResourceBundleMessageSource ms = (ResourceBundleMessageSource) wac.getBean("messageSource");
			
			User u = this.getCurrentUser();
			if (u != null) {
				String ret = u.getName();
				ret += " (";
				String sep = "";
				for (Permission p : Permission.values()) {
					if (p.isRole(u.getPermission())) {
						ret += sep + ms.getMessage(p.getBundleKey(), null, Locale.getDefault());
						sep = ", ";
					}
				}
				ret += ")";
				this.userIdentification = ret;
			} else {
				this.userIdentification = "";
			}
		}
		
		return this.userIdentification;
	}
}
