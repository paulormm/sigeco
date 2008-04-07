package sigeco.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sigeco.business.security.UserLocator;
import sigeco.model.User;
import sigeco.web.jsf.beans.LoginBean;


/**
 * Filter that checks that a User is properly logged in before viewing restricted pages.
 *
 *
 * @author julien
 * @author pitta
 *
 */
public class UserInSessionFilter implements Filter {

	/**
	 * Occurs when the filter is destroyed
	 */
	public void destroy() {
	}

	/**
	 * Action method. Verifies if the user is correctly logged in for views that require
	 * a logged user.
	 * 
	 * @param req {@link HttpServletRequest}
	 * @param res {@link HttpServletResponse}
	 * @param chain {@link FilterChain}
	 * 
	 * @throws IOException IO Error
	 * @throws ServletException Servlet Error
	 */
	public void doFilter(
			final ServletRequest req, 
			final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;		
		
        if (
        		request.getRequestURI().endsWith("faces")
        		&& !request.getRequestURI().contains("sigeco-web/a4j_3_1_0css")
        		&& !request.getRequestURI().endsWith("login.faces")) {
        	User user = (User) request.getSession().getAttribute(LoginBean.USER_SESSION_KEY);
        	if (user == null) {
    			response.sendRedirect(request.getContextPath() + "/login.faces");
        	} else {
        		UserLocator.setUser(user);
        		chain.doFilter(request, response);
        		UserLocator.resetUser();
        	}
        } else {
        	chain.doFilter(request, response);
        }
	}

	/**
	 * Initializes this Filter with parameters from web.xml
	 * 
	 * @param filterConfig FilterConfig
	 * 
	 * @throws ServletException Servlet Error 
	 */
	public void init(final FilterConfig filterConfig) throws ServletException {
	}
}
