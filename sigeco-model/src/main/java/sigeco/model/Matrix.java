	package sigeco.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import sigeco.utils.NamedEntity;

/**
 * Matrix Class.
 *
 * Represents a Competency Matrix.
 *
 * A Competency Matrix defines a number of Knowledge Areas and related
 * Abilities.
 *
 * A Competency Matrix has Unique title for identification.
 *
 * A Competency Matrix defines its Cells that are pairs of Knowledge and Ability.
 *
 * @author julien
 */
@Entity
@Table(name = "matrices", uniqueConstraints = {@UniqueConstraint(columnNames = { "name" }) })
public class Matrix implements NamedEntity {

	private long id;
	private String name;
	private List<Ability> abilities = new ArrayList<Ability>();
	private List<Grade> grades = new ArrayList<Grade>();
	private List<KnowledgeGroup> knowledgeGroups = new ArrayList<KnowledgeGroup>();
	private List<Cell> cells = new ArrayList<Cell>();

	/**
	 * Auxiliary transient map for location of cells within the Matrix.
	 */
	private Map<Knowledge, Map<Ability, Cell>> auxiliaryCellMap;

	/**
	 * Returns the name.
	 * @return String
	 */
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name
	 * @param nome String
	 */
	public void setName(final String nome) {
		this.name = nome;
	}

	/**
	 * Returns the List of Abilities.
	 * @return List
	 */
	@OneToMany(targetEntity = Ability.class, mappedBy = "matrix", cascade = { CascadeType.ALL })
	public List<Ability> getAbilities() {
		return this.abilities;
	}

	/**
	 * Sets the List of Abilities
	 * @param habilidades List
	 */
	public void setAbilities(final List<Ability> habilidades) {
		for (Ability ability : habilidades) {
			ability.setMatrix(this);
		}
		this.abilities = habilidades;
	}

	/**
	 * Gets the Knowledge Groups.
	 * @return List
	 */
	@OneToMany(targetEntity = KnowledgeGroup.class, cascade = { CascadeType.ALL })
	public List<KnowledgeGroup> getKnowledgeGroups() {
		return this.knowledgeGroups;
	}

	/**
	 * Sets the Knowledge Groups.
	 * @param grupos List
	 */
	public void setKnowledgeGroups(final List<KnowledgeGroup> grupos) {
		for (KnowledgeGroup knowledgeGroup : grupos) {
			knowledgeGroup.setMatrix(this);
		}
		this.knowledgeGroups = grupos;
	}

	/**
	 * Gets the Cells.
	 * @return List
	 */
	@OneToMany(targetEntity = Cell.class, mappedBy = "matrix", cascade = { CascadeType.ALL })
	public List<Cell> getCells() {
		return this.cells;
	}

	/**
	 * Sets the Cells.
	 * @param celulas List
	 */
	public void setCells(final List<Cell> celulas) {
		this.cells = celulas;
	}

	/**
	 * Returns the ID.
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
	 * Adds all the given Cells to this Matrix.
	 *
	 * @param cellsToAdd Cells
	 */
	public void addCells(final List<Cell> cellsToAdd) {
		for (Cell cell : cellsToAdd) {
			this.addCell(cell);
		}
	}

	/**
	 * Adds the cell to this Matrix' cells setting itself as owner.
	 *
	 * @param cell Cell
	 */
	private void addCell(final Cell cell) {
		cell.setMatrix(this);
		this.getCells().add(cell);
	}

	/**
	 * String representation of this object,
	 * @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Returns all the knowledges navigating through the Groups.
	 * @return List
	 */
	@Transient
	public List<Object> getAllKnowledges() {
		List<Object> result = new ArrayList<Object>();
		for (KnowledgeGroup group : this.getKnowledgeGroups()) {
			this.getKnowledges(result, group);
		}

		return result;
	}

	/**
	 * Puts all Knowledges from the Group into the supplied List.
	 * @param result List
	 * @param group KnowledgeGroup
	 */
	@Transient
	private void getKnowledges(final List<Object> result, final KnowledgeGroup group) {
		result.add(group);
		result.addAll(group.getKnowledges());

		for (KnowledgeGroup knowledgeGroup : group.getChildren()) {
			this.getKnowledges(result, knowledgeGroup);
		}
	}

	/**
	 * Gets all cells from a Knowledge
	 * @param knowledge Knowledge
	 * @return List
	 */
	@Transient
	public List<Cell> getCells(final Knowledge knowledge) {
		List<Cell> result = new ArrayList<Cell>();
		for (Cell cell : this.getCells()) {
			if (cell.getKnowledge().getId() == knowledge.getId()) {
				result.add(cell);
			}
		}
		return result;
	}

	/**
	 * @return int
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/**
	 * @param obj Object
	 * @return boolean
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
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
		final Matrix other = (Matrix) obj;
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
	 * Grades associated to this matrix.
	 * @return list of grades
	 */
	@OneToMany(targetEntity = Grade.class, mappedBy = "matrix", cascade = { CascadeType.ALL })
	public List<Grade> getGrades() {
		return this.grades;
	}

	/**
	 * Grades associated to this matrix.
	 * @param grades list of grades
	 */
	public void setGrades(final List<Grade> grades) {
		for (Grade grade : grades) {
			grade.setMatrix(this);
		}
		this.grades = grades;
	}


	/**
	 * Returns a Cell given its Ability and its Knowledge.
	 * @param ability {@link Ability}
	 * @param knowledge {@link Knowledge}
	 * @return {@link Cell}
	 */
	public Cell getCellFor(final Ability ability, final Knowledge knowledge) {
		if (this.auxiliaryCellMap == null) {
			this.initAuxiliaryCellMap();
		}
		return this.auxiliaryCellMap.get(knowledge).get(ability);
	}

	/**
	 * Cosntructs the auxiliary cell map
	 */
	private void initAuxiliaryCellMap() {
		this.auxiliaryCellMap = new HashMap<Knowledge, Map<Ability, Cell>>();
		for (Cell cell : this.cells) {
			Map<Ability, Cell> map = this.auxiliaryCellMap.get(cell.getKnowledge());
			if (map == null) {
				map = new HashMap<Ability, Cell>();
				this.auxiliaryCellMap.put(cell.getKnowledge(), map);
			}
			
			map.put(cell.getAbility(), cell);
		}
	}

	/**
	 * @return the auxiliaryCellMap
	 */
	@Transient
	public Map<Knowledge, Map<Ability, Cell>> getAuxiliaryCellMap() {
		return this.auxiliaryCellMap;
	}
}
