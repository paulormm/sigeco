package sigeco.business.search.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sigeco.business.search.SearchManager;
import sigeco.model.User;
import sigeco.model.search.Operator;
import sigeco.model.search.SearchElement;
import sigeco.model.search.SearchExpression;
import sigeco.model.search.SearchTerm;
import sigeco.utils.dao.Dao;

/**
 * Impl of SearchManager. Uses a DAO to access objects.
 *
 * @author julien
 */
public class SearchManagerImpl implements SearchManager {

	private Dao dao;

	private static final Map<Operator, String> QUERIES = new HashMap<Operator, String>();

	static {
		QUERIES.put(Operator.GREATER, "usersFromConditionGreater");
		QUERIES.put(Operator.GREATER_OR_EQUALS, "usersFromConditionGreaterEquals");
		QUERIES.put(Operator.EQUALS, "usersFromConditionEquals");
		QUERIES.put(Operator.LESSER_OR_EQUALS, "usersFromConditionLesserEquals");
		QUERIES.put(Operator.LESSER, "usersFromConditionLesser");
	}


	/**
	 * @return the dao
	 */
	public Dao getDao() {
		return this.dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(final Dao dao) {
		this.dao = dao;
	}

	/**
	 * Returns the given user's expressions
	 *
	 * @param user User
	 *
	 * @return List
	 */
	public List<SearchExpression> getExpressions(final User user) {
		return this.dao.query("expressionsFromUser", SearchExpression.class, user);
	}

	/**
	 * Returns all public expressions
	 *
	 * @return List
	 */
	public List<SearchExpression> getPublicExpressions() {
		return this.dao.query("publicExpressions", SearchExpression.class);
	}

	/**
	 * Returns the users found by this expression
	 *
	 * @param expression the expression
	 * @return Set
	 */
	public Set<User> getUsers(final SearchExpression expression) {
		Set<User> users = new HashSet<User>();

		for (SearchTerm term : expression) {
			users.addAll(this.getUsers(term));
		}

		return users;
	}

	/**
	 * Returns all users that match this term
	 * @param term SearchTerm
	 * @return Set
	 */
	private Set<User> getUsers(final SearchTerm term) {
		Set<User> users = new HashSet<User>();
		for (SearchElement element : term) {
			if (users.isEmpty()) {
				users.addAll(this.getUsers(element));
			} else {
				users.retainAll(this.getUsers(element));
			}
		}
		return users;
	}

	/**
	 * Returns all users that match this element
	 * @param element SearchElement
	 * @return Set
	 */
	private Set<User> getUsers(final SearchElement element) {
		List<User> query = this.dao.query(
				QUERIES.get(element.getOperator()),
				User.class,
				element.getMatrix(),
				element.getKnowledge(),
				element.getAbility(),
				element.getGrade());
		return new HashSet<User>(query);
	}

	/**
	 * Removes an expression
	 *
	 * @param expression the expression to remove
	 */
	public void removeExpression(final SearchExpression expression) {
		this.removeExpression(expression.getId());
	}
	
	/**
	 * Removes an expression given its id
	 * @param id long
	 */
	public void removeExpression(final long id) {
		this.dao.remove(this.dao.get(SearchExpression.class, id));
	}

	/**
	 * Saves an expression
	 *
	 * @param expression the expression to save
	 */
	public void saveExpression(final SearchExpression expression) {
		this.dao.save(expression);
	}

}
