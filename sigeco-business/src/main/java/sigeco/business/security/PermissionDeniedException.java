package sigeco.business.security;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import sigeco.model.Permission;

/**
 * Exception for denied permissions.
 *
 * @author julien
 */
public class PermissionDeniedException extends RuntimeException {

	private static final long serialVersionUID = 6253102677354658874L;
	
	private String bundleKey;
	private Object[] arguments;

	/**
	 * C'tor
	 * @param bundleKey String
	 * @param arguments Object[]
	 */
	public PermissionDeniedException(final String bundleKey, final Object... arguments) {
		super();
		this.bundleKey = bundleKey;
		this.arguments = arguments;
	}

	/**
	 * @return the bundleKey
	 */
	public String getBundleKey() {
		return this.bundleKey;
	}

	/**
	 * @return the arguments
	 */
	public Object[] getArguments() {
		return this.arguments;
	}

	/**
	 * @param bundleKey the bundleKey to set
	 */
	public void setBundleKey(final String bundleKey) {
		this.bundleKey = bundleKey;
	}

	/**
	 * @param arguments the arguments to set
	 */
	public void setArguments(final Object[] arguments) {
		this.arguments = arguments;
	}

	/**
	 * Finds a message resolver in the current request and gets the i18n message for this exception.
	 * @param request {@link HttpServletRequest}
	 * @return String
	 */
	public String toString(final HttpServletRequest request) {
		WebApplicationContext wac = 
			WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		ResourceBundleMessageSource ms = (ResourceBundleMessageSource) wac.getBean("messageSource");
		Object[] args = new Object[this.arguments.length];
		for (int i = 0; i < args.length; i++) {
			Object obj = this.arguments[i];
			if (obj instanceof Permission) {
				args[i] = ms.getMessage(((Permission) obj).getBundleKey(), null, Locale.getDefault());
			} else {
				args[i] = this.arguments[i];
			}
		}
		
		return ms.getMessage(this.bundleKey, args, Locale.getDefault());
	}
	
}
