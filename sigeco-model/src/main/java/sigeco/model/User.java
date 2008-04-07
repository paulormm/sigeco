package sigeco.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sigeco.utils.IdentifiedEntity;

/**
 * Represents a User in the system.
 *
 * @author julien
 * @author pitta
 */
@Entity
@Table(name = "users", uniqueConstraints = {
		@UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
@NamedQueries({
		@NamedQuery(
				name = "usersFromConditionGreater",
				query = "select v.user from CellValue v "
					  + "where v.cell.matrix = ? and v.cell.knowledge = ? and v.cell.ability = ? and v.grade > ? "),
		@NamedQuery(
				name = "usersFromConditionGreaterEquals",
				query = "select v.user from CellValue v "
					  +	"where v.cell.matrix = ? and v.cell.knowledge = ? and v.cell.ability = ? and v.grade >= ? "),
		@NamedQuery(
				name = "usersFromConditionEquals",
				query = "select v.user from CellValue v "
					  + "where v.cell.matrix = ? and v.cell.knowledge = ? and v.cell.ability = ? and v.grade = ? "),
		@NamedQuery(
				name = "usersFromConditionLesserEquals",
				query = "select v.user from CellValue v "
					  + "where v.cell.matrix = ? and v.cell.knowledge = ? and v.cell.ability = ? and v.grade <= ? "),
		@NamedQuery(
				name = "usersFromConditionLesser",
				query = "select v.user from CellValue v "
					  + "where v.cell.matrix = ? and v.cell.knowledge = ? and v.cell.ability = ? and v.grade < ? "),
		@NamedQuery(
				name = "usersWithPermissionManager",
				query = "select u from User u " 
					  + "where u.permission IN (2, 3, 6, 7)") })
public class User implements IdentifiedEntity {

	private long id;
	private String name;

	private String username;
	private byte[] password;

	private String email;

	/**
	 * Permission according to the levels related in the class Permissions.
	 */
	private int permission;
	
	/**
	 * Additional grants like ability to fill own matrix.
	 */
	private int grants;
	
	private List<UserGroup> managingGroups = new ArrayList<UserGroup>();

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	/**
	 * @return the name
	 */
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * @return the username
	 */
	@Column(nullable = false)
	public String getUsername() {
		return this.username;
	}

	/**
	 * @return the password
	 */
	@Column(nullable = false)
	public byte[] getPassword() {
		return this.password;
	}

	/**
	 * @return the email
	 */
	@Column(nullable = false)
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(final String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(final byte[] password) {
		this.password = password;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 *
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.username == null) ? 0 : this.username.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 *
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final User other = (User) obj;
		if (this.username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!this.username.equals(other.username)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a String representation of the user.
	 *
	 * @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * @return the permission
	 */
	@Column(nullable = false)
	public int getPermission() {
		return this.permission;
	}

	/**
	 * @param permission the permission to set
	 */
	public void setPermission(final int permission) {
		this.permission = permission;
	}

	/**
	 * @return the managingGroups
	 */
	@ManyToMany(mappedBy = "managers")
	public List<UserGroup> getManagingGroups() {
		return managingGroups;
	}

	/**
	 * @param managingGroups the managingGroups to set
	 */
	public void setManagingGroups(final List<UserGroup> managingGroups) {
		this.managingGroups = managingGroups;
	}

	/**
	 * 
	 * @return grants
	 */
	@Column
	public int getGrants() {
		return grants;
	}

	/**
	 * @param grants grants
	 */
	public void setGrants(final int grants) {
		this.grants = grants;
	}
}
