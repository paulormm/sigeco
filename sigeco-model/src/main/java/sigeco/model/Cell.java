package sigeco.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Cell Class.
 *
 * Represents a Cell in a Competency Matrix that associates
 * a Knowledge to an Ability.
 *
 * @author julien
 */
@Entity
@Table(name = "cells")
public class Cell {

	private Matrix matrix;
	private Knowledge knowledge;
	private Ability ability;
	private boolean active;
	private long id;

	/**
	 * C'tor
	 */
	public Cell() {
		super();
	}

	/**
	 * C'tor
	 * @param knowledge Knowledge
	 * @param ability Ability
	 */
	public Cell(final Knowledge knowledge, final Ability ability) {
		super();
		this.knowledge = knowledge;
		this.ability = ability;
	}

	/**
	 * Gets the Knowledge
	 * @return Knowledge
	 */
	@ManyToOne(optional = false)
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	/**
	 * Sets the Knowledge Knowledge
	 * @param knowledge Knowledge
	 */
	public void setKnowledge(final Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	/**
	 * Gets the Ability
	 * @return Ability
	 */
	@ManyToOne(optional = false)
	public Ability getAbility() {
		return this.ability;
	}

	/**
	 * Sets the Ability
	 * @param ability Ability
	 */
	public void setAbility(final Ability ability) {
		this.ability = ability;
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
	 * hashcode
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.ability == null) ? 0 : this.ability.hashCode());
		result = prime * result
				+ ((this.knowledge == null) ? 0 : this.knowledge.hashCode());
		result = prime * result + ((this.matrix == null) ? 0 : this.matrix.hashCode());
		return result;
	}

	/**
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
		final Cell other = (Cell) obj;
		if (this.ability == null) {
			if (other.ability != null) {
				return false;
			}
		} else if (!this.ability.equals(other.ability)) {
			return false;
		}
		if (this.knowledge == null) {
			if (other.knowledge != null) {
				return false;
			}
		} else if (!this.knowledge.equals(other.knowledge)) {
			return false;
		}
		if (this.matrix == null) {
			if (other.matrix != null) {
				return false;
			}
		} else if (!this.matrix.equals(other.matrix)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(final boolean active) {
		this.active = active;
	}
}
