package sigeco.business.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import sigeco.business.UserManager;
import sigeco.model.CellValue;
import sigeco.model.Permission;
import sigeco.model.User;
import sigeco.model.UserGroup;
import sigeco.utils.crypto.MD5Helper;
import sigeco.utils.dao.Dao;

/**
 * Dao based implementation of UserManager.
 *
 */
public class UserManagerImpl implements UserManager {

	private Dao dao;

	/**
	 * Authenticates a user in the system.
	 * 
	 * @param username String
	 * @param password String
	 * 
	 * @return The User or null
	 */
	public User authenticate(final String username, final String password) {
		byte[] md5 = MD5Helper.computeMD5(password);
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class).add(Restrictions.eq("username", username));
		List<User> list = this.dao.get(User.class, criteria);
		if (list.size() > 0) {
			User user = list.get(0);
			if (Arrays.equals(user.getPassword(), md5)) {
				return user;
			}
			//Todo trocar para null.
			return null;
		}
		return null;
	}

	/**
	 * Deletes the given user
	 * 
	 * @param user User
	 */
	@SuppressWarnings("unchecked")
	public void deleteUser(final User user) {
		final User managedUser = this.dao.get(User.class, user.getId());
		final List<UserGroup> managingGroups = new ArrayList<UserGroup>(managedUser.getManagingGroups());
		for (final UserGroup group : managingGroups) {
			group.getManagers().remove(managedUser);
			managedUser.getManagingGroups().remove(group);
			if (group.getManagers().isEmpty()) {
				this.dao.remove(group);
			} else {
				this.dao.save(group);
			}
		}
		
		final List<UserGroup> groupsParticipating = 
			this.dao.query("UserGroup.findGroupWithUser", UserGroup.class, managedUser);
		for (final UserGroup group : groupsParticipating) {
			group.getUsers().remove(managedUser);
		}
		this.dao.save((List) groupsParticipating);
		
		final List values = this.dao.query("CellValue.findByUser", CellValue.class, managedUser);
		this.dao.remove(values);
		this.dao.remove(managedUser);
	}

	/**
	 * Gets the user by id 
	 * @param id long
	 * 
	 * @return User
	 */
	public User getUserById(final long id) {
		return this.dao.get(User.class, id);
	}

	/**
	 * Gets the user by username
	 * 
	 * @param username String
	 * 
	 * @return User
	 */
	public User getUserByUsername(final String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class).add(Restrictions.eq("username", username));
		List<User> list = this.dao.get(User.class, criteria);
		if (list.size() > 0) {
			return (User) list.get(0);
		}
		return null;
	}

	/**
	 * List all users
	 * 
	 * @return list
	 */
	public List<User> list() {
		return this.dao.list(User.class);
	}
	
	/**
	 * Given a user, find its subordinates.
	 * @param user user
	 * @return List.
	 */
	public List<User> list(final User user) {
		final User managedUser = this.dao.get(User.class, user.getId());
		final List<User> subordinates = new ArrayList<User>();
		final HashSet<UserGroup> allGroups = new HashSet<UserGroup>(managedUser.getManagingGroups());
		final LinkedList<UserGroup> groups = new LinkedList<UserGroup>(allGroups);
		while (!groups.isEmpty()) {
			final UserGroup group = groups.getFirst();
			groups.removeFirst();
			for (final User u : group.getUsers()) {
				if (!u.equals(user) && !subordinates.contains(u)) {
					subordinates.add(u);
					
					for (final UserGroup g : u.getManagingGroups()) {
						if (!allGroups.contains(g)) {
							allGroups.add(g);
							groups.add(g);
						}
					}
				}
			}
		}
		
		return subordinates;
	}
	
	/**
	 * Lists all managers.
	 * @return list
	 */
	public List<User> listManagers() {
		return this.dao.query("usersWithPermissionManager", User.class);
	}

	/**
	 * Saves the given user
	 * @param user User 
	 */
	@SuppressWarnings("unchecked")
	public void saveUser(final User user) {
		this.dao.save(user);
		final User u = this.dao.get(User.class, user.getId());
		if (!Permission.MANAGER.isRole(u.getPermission())) {
			final List<UserGroup> groups = new ArrayList<UserGroup>(u.getManagingGroups());
			for (final UserGroup group : groups) {
				group.getManagers().remove(u);
				u.getManagingGroups().remove(group);
				if (group.getManagers().isEmpty()) {
					this.dao.remove(group);
				} else {
					this.dao.save(group);
				}
			}
			
			this.dao.save(u);
		}
		if (!Permission.USER.isRole(u.getPermission())) {
			final List<CellValue> values = this.dao.query("CellValue.findByUser", CellValue.class, u);
			this.dao.remove((List) values);
		}
	}

	
	/**
	 * Saves the profile of given user
	 * @param user User 
	 */
	/*
	@SuppressWarnings("unchecked")
	public void saveProfile(final User user) {
		this.dao.save(user);
	}
	*/

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
}
