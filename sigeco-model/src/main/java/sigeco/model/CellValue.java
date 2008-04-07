package sigeco.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * CellValue entity.
 *
 * Represents the association between a Cell and a User valued by a Level.
 *
 * @author julien
 */
@Entity
@Table(name = "cell_values")
@NamedQueries(value = {
		@NamedQuery(
			name = "CellValue.findByUserAndMatrix",
			query = "from CellValue cv where cv.cell.matrix = ? and cv.user = ?"),
		@NamedQuery(
			name = "CellValue.findByAll",
			query 	= "from CellValue cv "
					+ "where cv.cell.matrix = ? and cv.cell.knowledge = ? and cv.cell.ability = ? and cv.user = ?"),
		@NamedQuery(
			name = "CellValue.findByMatrix",
			query = "from CellValue cv where cv.cell.matrix = ?"),
		@NamedQuery(
			name = "CellValue.findMatrixByUser",
			query = "select cv.cell.matrix from CellValue cv where cv.user = ? group by cv.cell.matrix"),
		@NamedQuery(
			name = "CellValue.findByUser",
			query = "from CellValue cv where cv.user = ?"
		)
	}
)
public class CellValue implements Comparable<CellValue> {

	private long id;
	private User user;
	private Cell cell;
	private Grade grade;
	private Level level;

	/**
	 * Gets the cell
	 * @return Cell
	 */
	@ManyToOne(optional = false, targetEntity = Cell.class)
	public Cell getCell() {
		return this.cell;
	}

	/**
	 * Sets the cell
	 * @param cell Cell
	 */
	public void setCell(final Cell cell) {
		this.cell = cell;
	}

	/**
	 * Gets the User
	 * @return User
	 */
	@ManyToOne(optional = false, targetEntity = User.class)
	public User getUser() {
		return this.user;
	}

	/**
	 * Sets the User
	 * @param user User
	 */
	public void setUser(final User user) {
		this.user = user;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * Compares this CellValue to another.
	 * @param other The other CellValue
	 *
	 * @return int
	 */
	public int compareTo(final CellValue other) {
		return this.cell.getAbility().compareTo(other.getCell().getAbility());
	}

	/**
	 * Gets the grade.
	 * @return grade
	 */
	@ManyToOne
	public Grade getGrade() {
		return this.grade;
	}

	/**
	 * Sets the grade.
	 * @param grade grade
	 */
	public void setGrade(final Grade grade) {
		this.grade = grade;
	}

	/**
	 * 
	 * @return Level
	 */
	public Level getLevel() {
		return this.level;
	}

	/**
	 * 
	 * @param level the Level
	 */
	public void setLevel(final Level level) {
		this.level = level;
	}

	/**
	 * hashcode
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.cell == null) ? 0 : this.cell.hashCode());
		result = prime * result + ((this.user == null) ? 0 : this.user.hashCode());
		return result;
	}

	/**
	 * equals
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
		final CellValue other = (CellValue) obj;
		if (this.cell == null) {
			if (other.cell != null) {
				return false;
			}
		} else if (!this.cell.equals(other.cell)) {
			return false;
		}
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
			return false;
		}
		return true;
	}


}
