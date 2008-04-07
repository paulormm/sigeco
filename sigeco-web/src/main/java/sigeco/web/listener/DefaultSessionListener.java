package sigeco.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Monitors the creation and destruction of Session elements.
 * 
 * This class adds required utilities to a new user Session and
 * destroys them if necessary.
 * 
 * @author julien
 */
public class DefaultSessionListener implements HttpSessionListener {

	/**
	 * Occurs when a session is created.
	 * 
	 * @param se HttpSessionEvent
	 */
	public void sessionCreated(final HttpSessionEvent se) {
		//nothing for now
	}

	/**
	 * Occurs when the session is destroyed
	 * 
	 * @param se HttpSessionEvent
	 */
	public void sessionDestroyed(final HttpSessionEvent se) {
		//nothing for now
	}

}
