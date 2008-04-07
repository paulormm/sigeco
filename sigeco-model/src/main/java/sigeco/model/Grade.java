package sigeco.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sigeco.utils.NamedEntity;

/**
 * Represents the grade of an ability in an
 * knowledge.
 * @author YW
 */
@Entity
@Table(name = "grades")
public class Grade implements Comparable<Grade>, NamedEntity {
	private long id;
	private Matrix matrix;
	private String name;
	private int gradeOrder;

	/**
	 * C'tor.
	 */
	public Grade() {
	}

	/**
	 * C'tor.
	 * @param name name
	 * @param matrix matrix
	 */
	public Grade(final String name, final Matrix matrix) {
		this.name = name;
		this.matrix = matrix;
		this.gradeOrder = matrix.getGrades().size();
	}

	/**
	 * Id.
	 * @return long
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	/**
	 * Id.
	 * @param id long
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * Matrix it's associated with.
	 * @return matrix
	 */
	@ManyToOne(optional = false)
	public Matrix getMatrix() {
		return this.matrix;
	}

	/**
	 * Matrix.
	 * @param matrix matrix
	 */
	public void setMatrix(final Matrix matrix) {
		this.matrix = matrix;
	}

	/**
	 * Name.
	 * @return String
	 */
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * Name.
	 * @param name name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Represents the order of the grade.
	 * The higher the better.
	 * @return order
	 */
	@Column(nullable = false)
	public int getGradeOrder() {
		return this.gradeOrder;
	}

	/**
	 * Order.
	 * @param gradeOrder order
	 */
	public void setGradeOrder(final int gradeOrder) {
		this.gradeOrder = gradeOrder;
	}

	/**
	 * HashCode.
	 * @return hashcode.
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
	 * equals.
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
		final Grade other = (Grade) obj;
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

	/**
	 * Compares the grade according to their gradeOrder member.
	 * @param o Grade
	 * @return int
	 */
	public int compareTo(final Grade o) {
		return this.gradeOrder - o.gradeOrder;
	}
}
