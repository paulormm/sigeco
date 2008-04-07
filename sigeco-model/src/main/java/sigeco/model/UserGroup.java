package sigeco.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Represents a group of users.
 *
 */
@Entity
@Table(name = "user_group", uniqueConstraints = {@UniqueConstraint(columnNames = {"name" }) })
@NamedQueries(
		{@NamedQuery(
				name = "UserGroup.findGroupWithUser", 
				query = "select g from UserGroup g where ? MEMBER OF g.users"),
		 @NamedQuery(
				name = "UserGroup.findGroupWithManager",
				query = "select g from UserGroup g where ? MEMBER OF g.managers")
		}
)
public class UserGroup {
	private long id;
	private String name;
	private List<User> users = new ArrayList<User>();
	private List<User> managers = new ArrayList<User>();

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	/**
	 * @param id the id
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the users
	 */
	@ManyToMany
	@JoinTable(name = "REL_USER_GROUP")
	public List<User> getUsers() {
		return this.users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(final List<User> users) {
		this.users = users;
	}

	/**
	 * @return the managers
	 */
	@ManyToMany
	@JoinTable(name = "REL_MANAGER_GROUP")
	public List<User> getManagers() {
		return this.managers;
	}

	/**
	 * @param managers the managers to set
	 */
	public void setManagers(final List<User> managers) {
		this.managers = managers;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/** (non-Javadoc)
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
		final UserGroup other = (UserGroup) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

}
