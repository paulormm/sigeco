package sigeco.business.search;

import java.util.List;

import sigeco.model.User;
import sigeco.model.search.SearchExpression;
import sigeco.utils.junit.SpringTestCase;


/**
 * Tests for SearchManagerImpl
 *
 * @author julien
 */
public class SearchManagerImplTest extends SpringTestCase {

	private List<SearchExpression> expressionsUserA; //1 public 1 non-public
	private List<SearchExpression> expressionsUserB; //1 public 1 non-public
	private User userA;
	private User userB;
	private SearchManager searchManager;

	/**
	 * C'tor
	 */
	public SearchManagerImplTest() {
		super("test-business-beans.xml");
	}

	/**
	 * Tests that public expressions are returned correctly
	 */
	public void testGetPublicExpressions() {
		this.prepare();
		List<SearchExpression> expressions = this.searchManager.getPublicExpressions();
		
		assertEquals(2, expressions.size());
		for (SearchExpression e : expressions) {
			assertTrue(e.isPublish());
			assertTrue(this.expressionsUserA.contains(e) || this.expressionsUserB.contains(e));
		}
	}
	
	/**
	 * Tests that an user's expressions are returned correctly
	 */
	public void testGetExpressionsFromUser() {
		this.prepare();
		
		List<SearchExpression> expressions = this.searchManager.getExpressions(this.userA);
		assertEquals(2, expressions.size());
		
		for (SearchExpression e : expressions) {
			assertEquals(e.getUser(), this.userA);
			assertTrue(this.expressionsUserA.contains(e));
		}
		
		expressions = this.searchManager.getExpressions(this.userB);
		assertEquals(2, expressions.size());
		
		for (SearchExpression e : expressions) {
			assertEquals(e.getUser(), this.userB);
			assertTrue(this.expressionsUserB.contains(e));
		}
	}

	/**
	 * Saves all required entities before running the test
	 */
	private void prepare() {
		assertEquals(0, this.getSession().createCriteria(User.class).list().size());
		
		this.getSession().save(this.userA);
		this.getSession().save(this.userB);

		for (SearchExpression e : this.expressionsUserA) {
			e.setUser(this.userA);
			this.searchManager.saveExpression(e);
		}
		
		for (SearchExpression e : this.expressionsUserB) {
			e.setUser(this.userB);
			this.searchManager.saveExpression(e);
		}
	}


}
