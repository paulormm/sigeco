package sigeco.business.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import sigeco.business.security.annotation.Secured;
import sigeco.model.Permission;
import sigeco.model.User;

/**
 * Method Interceptor for the Secured Proxy
 * @author julien
 */
public class SecuredMethodInterceptor implements MethodInterceptor {

	/**
	 * Searchs for the Secured annotation and if present verifies that the current
	 * user has the required permission.
	 *
	 * If it doesn't a SecurityException is thrown.
	 *
	 * @param method MethodInvocation
	 *
	 * @return Object
	 * 
	 * @throws Throwable SecurityException if the User doesn't have the required permission 
	 * 					 or any other exception thrown by the invoked method
	 */
	public Object invoke(final MethodInvocation method) throws Throwable {
		
		Secured secured = method.getMethod().getAnnotation(Secured.class);
		if (secured != null) {
			Permission[] perm = secured.value();
			User user = UserLocator.getUser();
			
			if (perm != null && user != null) {
				for (Permission p : perm) {
					if (p.isRole(user.getPermission())) {
						return method.proceed();
					}
				}
				throw new PermissionDeniedException("global.security.denied", (Object[]) perm);
			} else {
				throw new IllegalStateException("Neither the User nor the Permission should be null!");
			}
		}
		return method.proceed();
	}

}
