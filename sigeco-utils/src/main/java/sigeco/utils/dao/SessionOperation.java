package sigeco.utils.dao;

import org.hibernate.Session;

/**
 * Interface for a Session operarion,
 * 
 * @author julien
 */
public interface SessionOperation {

	/**
	 * Executes receiving the current session.
	 * @param session Session
	 */
	void execute(final Session session);
	
}
