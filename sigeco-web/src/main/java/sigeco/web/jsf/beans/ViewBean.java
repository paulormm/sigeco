package sigeco.web.jsf.beans;

import sigeco.model.search.SearchExpression;

/**
 * Simple bean for viewing a SearchExpression.
 *
 * @author julien
 */
public class ViewBean {

	private SearchExpression expression;

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

}
