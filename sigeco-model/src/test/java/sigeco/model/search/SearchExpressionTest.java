package sigeco.model.search;

import java.util.List;

import sigeco.model.Ability;
import sigeco.model.Grade;
import sigeco.model.Knowledge;
import sigeco.model.Matrix;
import sigeco.model.User;
import sigeco.utils.junit.AbstractCRUDTestCase;

/**
 * CRUD tests for a SearchExpression.
 *
 * @author julien
 */
public class SearchExpressionTest extends AbstractCRUDTestCase<SearchExpression> {

	private SearchExpression expression;
	private List<SearchExpression> validExpressions;
	private List<SearchExpression> invalidExpressions;
	private User user;
	private Matrix matrix;

	/**
	 * C'tor
	 */
	public SearchExpressionTest() {
		super("test-model-beans.xml");
	}

	/**
	 * Setup
	 * @throws Exception ex
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.getSession().save(this.user);
		this.getSession().save(this.matrix);

		Ability ability = this.matrix.getAbilities().iterator().next();
		Grade grade = this.matrix.getGrades().iterator().next();
		Knowledge knowledge = this.matrix.getKnowledgeGroups().iterator().next().getKnowledges().iterator().next();

		SearchElement element =
			this.expression.getSearchTerms().iterator().next().getSearchElements().iterator().next();

		element.setAbility(ability);
		element.setGrade(grade);
		element.setMatrix(this.matrix);
		element.setKnowledge(knowledge);
		element.setOperator(Operator.EQUALS);
		
		this.getSession().flush();
	}

	/**
	 * Returns an entity that will be saved before the updateEntity callback is called.
	 *
	 * @return SearchExpression
	 */
	@Override
	protected SearchExpression getEntityToBeUpdated() {
		return this.expression;
	}

	/**
	 * Return invalid entities
	 *
	 * @return List
	 */
	@Override
	protected List<SearchExpression> getInvalidEntities() {
		return this.invalidExpressions;
	}

	/**
	 * Return valid entities
	 * @return List
	 */
	@Override
	protected List<SearchExpression> getValidEntities() {
		return this.validExpressions;
	}

	/**
	 * Callback asking for an entity to be updated
	 *
	 * @param exp SearchExpression
	 */
	@Override
	protected void updateEntity(final SearchExpression exp) {
		exp.setName(exp.getName() + "modified");
	}



}
