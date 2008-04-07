package sigeco.model;

import junit.framework.TestCase;

/**
 * Tests for permissions.
 * 
 * @author julien
 */
public class PermissionTest extends TestCase {

	private static final int COMB_USER_MANAGER = 3;
	private int p1;
	private int p2;
	private int p3;
	
	private int p4;
	private int p5;
	private int p6;
	
	private int p7;

	/**
	 * Setup
	 * 
	 * @throws Exception ex
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		p1 = Permission.USER.getPermission();
		p2 = Permission.MANAGER.getPermission();
		p3 = Permission.ADMIN.getPermission();
		
		p4 = Permission.combine(Permission.USER, Permission.MANAGER);
		p5 = Permission.combine(Permission.USER, Permission.ADMIN);
		p6 = Permission.combine(Permission.MANAGER, Permission.ADMIN);
		
		p7 = Permission.combine(Permission.USER, Permission.MANAGER, Permission.ADMIN);
	}

	/**
	 * Tests all possible permission combinations and
	 * asserts that permissions are correctly recognized.
	 */
	public void testUserCases() {
		
		assertTrue(Permission.USER.isRole(p1));
		assertTrue(Permission.USER.isRole(p4));
		assertTrue(Permission.USER.isRole(p5));
		assertTrue(Permission.USER.isRole(p7));
		assertFalse(Permission.USER.isRole(p2));
		assertFalse(Permission.USER.isRole(p3));
		assertFalse(Permission.USER.isRole(p6));
		
	}
	
	/**
	 * Tests all possible permission combinations and
	 * asserts that permissions are correctly recognized.
	 */
	public void testManagerCases() {
		
		assertTrue(Permission.MANAGER.isRole(p2));
		assertTrue(Permission.MANAGER.isRole(p4));
		assertTrue(Permission.MANAGER.isRole(p6));
		assertTrue(Permission.MANAGER.isRole(p7));
		assertFalse(Permission.MANAGER.isRole(p1));
		assertFalse(Permission.MANAGER.isRole(p3));
		assertFalse(Permission.MANAGER.isRole(p5));
		
	}
	
	/**
	 * Tests all possible permission combinations and
	 * asserts that permissions are correctly recognized.
	 */
	public void testAdminCases() {
		
		assertTrue(Permission.ADMIN.isRole(p3));
		assertTrue(Permission.ADMIN.isRole(p5));
		assertTrue(Permission.ADMIN.isRole(p6));
		assertTrue(Permission.ADMIN.isRole(p7));
		assertFalse(Permission.ADMIN.isRole(p1));
		assertFalse(Permission.ADMIN.isRole(p2));
		assertFalse(Permission.ADMIN.isRole(p4));
		
	}
	
	/**
	 * Tests the combine method.
	 */
	public void testCombine() {
		
		assertEquals(COMB_USER_MANAGER, Permission.combine(Permission.USER, Permission.MANAGER));
		assertEquals(COMB_USER_MANAGER, Permission.combine(new String[] {"USER", "MANAGER"}));
		
	}
	
}
