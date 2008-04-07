package sigeco.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sigeco.utils.NamedEntity;

/**
 * Ability Class.
 *
 * Represents an Ability that can be associated to
 * a Knowledge forming a Cell for which a Person
 * may have a Competency level.
 *
 * @author julien
 */
@Entity
@Table(name = "abilities")
public class Ability implements Comparable<Ability>, NamedEntity {

	private String name;
	private Matrix matrix;
	private long id;

	/**
	 * C'tor
	 */
	public Ability() {
	}

	/**
	 * C'tor
	 * @param name String
	 * @param matrix Matrix
	 */
	public Ability(final String name, final Matrix matrix) {
		this.name = name;
		this.matrix = matrix;
	}

	/**
	 * Gets the name
	 * @return String
	 */
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name
	 * @param name String
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the Matrix
	 * @return Matrix
	 */
	@ManyToOne(optional = false)
	public Matrix getMatrix() {
		return this.matrix;
	}

	/**
	 * Sets the Matrix
	 * @param matrix Matrix
	 */
	public void setMatrix(final Matrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * Gets the ID
	 * @return long
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	/**
	 * Sets the id
	 * @param id long
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @param other Ability
	 *
	 * @return int
	 */
	public int compareTo(final Ability other) {
		return this.name.compareTo(other.name);
	}

	/**
	 * Hashcode.
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.matrix == null) ? 0 : this.matrix.hashCode());
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/**
	 * Equals.
	 * @param obj obj
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
		final Ability other = (Ability) obj;
		if (this.matrix == null) {
			if (other.matrix != null) {
				return false;
			}
		} else if (!this.matrix.equals(other.matrix)) {
			return false;
		}
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
