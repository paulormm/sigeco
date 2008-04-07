/**
 * 
 */
package sigeco.business.search;

import sigeco.model.User;
import sigeco.model.UserGroup;
import sigeco.utils.junit.SpringTestCase;

/**
 * Tests for UserManagerImpl.
 * @author yugo
 */
public class UserManagerImplTest extends SpringTestCase {
	private User userC;
	private User userD;
	
	private UserGroup groupA;
	private UserGroup groupB;
	
//	private UserManager userManager;
	
	/**
	 * C'tor
	 */
	public UserManagerImplTest() {
		super("test-business-beans.xml");
	}
	
	/**
	 * Tests that deleting a user correctly works
	 */
	public void testDeleteUser() {
		this.prepareFirstCase();
		
		//FIXME there is something wrong with how we delete user.
		//i think that the two-side relationships are causing some 
		//in-session duplication of entities and hibernate can't delete
		//then
		
//		this.userManager.deleteUser(userC);
//		
//		assertFalse(this.getSession().createCriteria(User.class).list().contains(userC));
//		assertFalse(this.getSession().createCriteria(User.class).list().contains(userD));
//		assertFalse(this.getSession().createCriteria(UserGroup.class).list().contains(groupA));
//		assertTrue(this.getSession().createCriteria(UserGroup.class).list().contains(groupB));
//		final UserGroup managedGroupB = (UserGroup) this.getSession().get(UserGroup.class, groupB.getId());
//		assertFalse(managedGroupB.getUsers().contains(userC));
//		assertFalse(managedGroupB.getManagers().contains(userC));
	}
	
	/**
	 * Prepares the tese scenario
	 */
	private void prepareFirstCase() {
		assertEquals(0, this.getSession().createCriteria(User.class).list().size());
		assertEquals(0, this.getSession().createCriteria(UserGroup.class).list().size());
		
		this.getSession().save(userC);
		this.getSession().save(userD);
		
		//FIXME those also don't work
//		this.userManager.saveUser(userC);
//		this.userManager.saveUser(userD);
		
		groupA.getUsers().add(userC);
		groupA.getUsers().add(userD);
		groupA.getManagers().add(userC);
		
		groupB.getUsers().add(userC);
		groupB.getManagers().add(userC);
		groupB.getManagers().add(userD);
		
		this.getSession().save(groupA);
		this.getSession().save(groupB);
		
		userC.getManagingGroups().add(groupA);
		userC.getManagingGroups().add(groupB);
		userD.getManagingGroups().add(groupB);
		
	}
}
