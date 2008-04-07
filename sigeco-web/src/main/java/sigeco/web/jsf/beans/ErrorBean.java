package sigeco.web.jsf.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import sigeco.business.security.PermissionDeniedException;

/**
 * Resolver a request error. 
 * @author julien
 */
public class ErrorBean {

	/**
	 * Looks for an error in the current request attributes and resolves it.
	 * @return String
	 */
	public String getResolvedError() {
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String error = "";
		
		Throwable ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
		if (ex != null) {
			while (ex.getCause() != null) {
				ex = ex.getCause();
			}
			
			if (ex instanceof PermissionDeniedException) {
				error = ((PermissionDeniedException) ex).toString(request);
			} else {
				error = ex.toString();
			}
		}
		
		return error;
	}
	
	
}
