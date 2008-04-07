package sigeco.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import sigeco.utils.NamedEntity;

/**
 * Knowledge Class.
 *
 * Represents and Knwoledge Area for which a person may have a Competency
 * given a related Ability.
 *
 * @author julien
 */
@Entity
@Table(name = "knowledges")
public class Knowledge implements CellMappeable, NamedEntity {

	private String name;
	private KnowledgeGroup knowledgeGroup;
	private long id;

	private List<Cell> cells = new ArrayList<Cell>();
	private Map<String, Cell> mappedCells;

	/**
	 * C'tor
	 */
	public Knowledge() {
	}

	/**
	 * C'tor
	 * @param name The Knowledge name
	 * @param knowledgeGroup The owning Group
	 */
	public Knowledge(final String name, final KnowledgeGroup knowledgeGroup) {
		this.name = name;
		this.knowledgeGroup = knowledgeGroup;
	}

	/**
	 * Gets the owning group
	 * @return KnowledgeGroup
	 */
	@ManyToOne(optional = false)
	public KnowledgeGroup getKnowledgeGroup() {
		return this.knowledgeGroup;
	}

	/**
	 * Sets the owning group
	 * @param knowledgeGroup KnowledgeGroup
	 */
	public void setKnowledgeGroup(final KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
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
	 * Gets the id
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
	 * String representation of this object
	 * @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Gets the mapped cells
	 * @return Map
	 */
	@Transient
	public Map<String, Cell> getMappedCells() {
		if (this.mappedCells == null) {
			this.fillMappedCells();
		}
		return this.mappedCells;
	}

	/**
	 * Fills the Mapped Cells
	 */
	private void fillMappedCells() {
		this.mappedCells = new HashMap<String, Cell>();

		for (Cell cell : this.cells) {
			this.mappedCells.put(new Long(cell.getAbility().getId()).toString(), cell);
		}
	}

	/**
	 * Gets the cells
	 * @return List
	 */
	@OneToMany(mappedBy = "knowledge")
	public List<Cell> getCells() {
		return this.cells;
	}

	/**
	 * Sets the cells
	 * @param cells List
	 */
	public void setCells(final List<Cell> cells) {
		this.cells = cells;
	}

	/**
	 * Hashcode.
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/**
	 * Equals.
	 * @param obj obj
	 * @return boolean.
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
		final Knowledge other = (Knowledge) obj;
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
