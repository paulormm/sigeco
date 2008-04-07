/**
 * 
 */
package sigeco.business.impl;

import java.util.List;

import sigeco.business.UserGroupManager;
import sigeco.model.UserGroup;
import sigeco.utils.dao.Dao;

/**
 * DAO based implementation of <code>UserGroupManager</code>.
 *
 */
public class UserGroupManagerImpl implements UserGroupManager {
	private Dao dao;
	/**
	 * Lists all users.
	 * @return all users.
	 */
	public List<UserGroup> list() {
		return dao.list(UserGroup.class);
	}
	
	/**
	 * Save user group.
	 * @param userGroup UserGroup
	 */
	public void saveUserGroup(final UserGroup userGroup) {
		dao.save(userGroup);
	}
	
	/**
	 * Delete user group.
	 * @param userGroup UserGroup.
	 */
	public void deleteUserGroup(final UserGroup userGroup) {
		dao.remove(userGroup);
	}
	
	/**
	 * @return the dao
	 */
	public Dao getDao() {
		return dao;
	}
	
	/**
	 * @param dao the dao to set
	 */
	public void setDao(final Dao dao) {
		this.dao = dao;
	}
}
