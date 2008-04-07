package sigeco.web.jsf.beans;

import java.util.ArrayList;
import java.util.List;

import sigeco.business.search.SearchManager;
import sigeco.model.User;
import sigeco.model.search.SearchExpression;


/**
 * Bean for viewing search results.
 *
 * @author julien
 */
public class ResultBean {

	private static final String ACTION_BACK_LIST = "back to list";
	private static final String ACTION_BACK_VIEW = "back to view";
	private static final String ACTION_BACK_FORM = "back to form";

	private SearchManager searchManager;
	private String backAction = ACTION_BACK_LIST;
	private SearchExpression expression;
	private List<User> users;

	/**
	 * Goes back to the previous page
	 * @return String
	 */
	public String back() {
		return this.backAction;
	}

	/**
	 * Indicates that we came from the list
	 * @return String
	 */
	public String fromList() {
		this.backAction = ACTION_BACK_LIST;
		this.search();
		return "show result";
	}

	/**
	 * Indicates that we came from the expression form
	 * @return String
	 */
	public String fromForm() {
		this.backAction = ACTION_BACK_FORM;
		this.search();
		return "show result";
	}

	/**
	 * Indicates that we came from the expression view page
	 * @return String
	 */
	public String fromView() {
		this.backAction = ACTION_BACK_VIEW;
		this.search();
		return "show result";
	}

	/**
	 * Fires the search.
	 */
	private void search() {
		this.users = new ArrayList<User>();
		if (this.expression != null) {
			this.users.addAll(this.searchManager.getUsers(this.expression));
		}
	}

	/**
	 * @return the searchManager
	 */
	public SearchManager getSearchManager() {
		return this.searchManager;
	}

	/**
	 * @param searchManager the searchManager to set
	 */
	public void setSearchManager(final SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	/**
	 * @return the expression
	 */
	public SearchExpression getExpression() {
		return this.expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(final SearchExpression expression) {
		this.expression = expression;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return this.users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(final List<User> users) {
		this.users = users;
	}




}
