package sigeco.business.search;

import java.util.List;
import java.util.Set;

import sigeco.model.User;
import sigeco.model.search.SearchExpression;


/**
 * Interface for a SearchManager
 * 
 * @author julien
 */
public interface SearchManager {

	/**
	 * Returns a given user's expressions
	 * @param user User
	 * @return List
	 */
	List<SearchExpression> getExpressions(User user);
	
	/**
	 * Returns all public expressions.
	 * @return List
	 */
	List<SearchExpression> getPublicExpressions();
	
	/**
	 * Removes the given expression.
	 * @param expression SearchExpression
	 */
	void removeExpression(SearchExpression expression);
	
	/**
	 * Saves the given expression
	 * @param expression SearchExpression
	 */
	void saveExpression(SearchExpression expression);
	
	/**
	 * Returns the users found by the given SearchExpression.
	 * @param expression SearchExpression
	 * @return Set
	 */
	Set<User> getUsers(SearchExpression expression);
}
