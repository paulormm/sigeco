package sigeco.web.jsf.beans;

import java.util.List;

import javax.faces.event.ActionEvent;

import sigeco.business.search.SearchManager;
import sigeco.business.security.UserLocator;
import sigeco.model.search.SearchExpression;


/**
 * Bean for search listing operations.
 *
 * @author julien
 */
public class ListSearchBean {

	private List<SearchExpression> expressions;

	private SearchManager searchManager;

	private SearchExpression expressionToRemove;

	private boolean publicSearches = false;

	public String listOwnSearches() {
		this.expressions = this.searchManager.getExpressions(UserLocator.getUser());
		this.publicSearches = false;
		return "list searches";
	}

	public String listPublicSearches() {
		this.expressions = this.searchManager.getPublicExpressions();
		this.publicSearches = true;
		return "list searches";
	}

	public void removeExpression(final ActionEvent event) {
		if (this.expressionToRemove != null) {
			this.searchManager.removeExpression(this.expressionToRemove);
			this.expressions = this.searchManager.getExpressions(UserLocator.getUser());
		}
	}


	/**
	 * @return the expressions
	 */
	public List<SearchExpression> getExpressions() {
		return this.expressions;
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
	 * @return the publicSearches
	 */
	public boolean isPublicSearches() {
		return this.publicSearches;
	}

	/**
	 * @return the expressionToRemove
	 */
	public SearchExpression getExpressionToRemove() {
		return this.expressionToRemove;
	}

	/**
	 * @param expressionToRemove the expressionToRemove to set
	 */
	public void setExpressionToRemove(final SearchExpression expressionToRemove) {
		this.expressionToRemove = expressionToRemove;
	}

}
