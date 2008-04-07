package sigeco.business.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sigeco.business.CellValueManager;
import sigeco.business.MatrixManager;
import sigeco.model.Ability;
import sigeco.model.Cell;
import sigeco.model.Knowledge;
import sigeco.model.KnowledgeGroup;
import sigeco.model.Matrix;
import sigeco.model.User;
import sigeco.utils.dao.Dao;

/**
 * DAO based implementation of the MatrixManager interface.
 *
 * @author julien
 */
public class MatrixManagerImpl implements MatrixManager {
	private CellValueManager cellValueManager;
	private Dao dao;

	/**
	 * Gets the Matrix with the given id or null.
	 * @param id The id
	 * @return The found Matrix or null
	 */
	public Matrix get(final Serializable id) {
		return this.dao.get(Matrix.class, id);
	}

	/**
	 * Return all Matrices.
	 *
	 * @return The List of Matrices
	 */
	public List<Matrix> getAll() {
		return this.dao.list(Matrix.class);
	}

	/**
	 * Returns all Matrices from start until (start + amount - 1).
	 *
	 * @param start The index of the first Matrix to be returned
	 * @param amount The max amount of Matrices returned
	 * @return The List of Matrices
	 */
	public List<Matrix> getAll(final int start, final int amount) {
		return this.dao.list(Matrix.class, start, amount);
	}

	/**
	 * Removes the given Matrix.
	 *
	 * @param matrix The Matrix to be removed
	 */
	@SuppressWarnings("unchecked")
	public void removeMatrix(final Matrix matrix) {
		this.dao.remove((List<Object>) ((List< ? extends Object>) this.cellValueManager.getCellValues(matrix)));
		this.dao.remove(matrix);
	}

	/**
	 * Saves the given Matrix.
	 *
	 * @param matrix The Matrix to be saved.
	 */
	public void saveMatrix(final Matrix matrix) {
		//if this is a new matrix we create all cells
		if (matrix.getId() < 1) {
			this.createCells(matrix);
		}
		//we create a new Set reference with the same cell references as the matrix' cells
		Set<Cell> cells = new HashSet<Cell>(matrix.getCells());
		//we can then clear the matrix' cells before saving witouht loosing references to the cells
		matrix.getCells().clear();
		//we save without cells
		this.dao.save(matrix);
		//now we put the cells back into the matrix
		matrix.getCells().addAll(cells);
		//and save them...
		this.dao.save(matrix);
	}

	/**
	 * Creates all cells for a matrix
	 * @param matrix Matrix
	 */
	private void createCells(final Matrix matrix) {
		for (Ability ability : matrix.getAbilities()) {
			matrix.addCells(this.getCellsFor(matrix.getKnowledgeGroups(), ability));
		}
	}

	/**
	 * Returns Cells for the given ability for all Knowledges of the given KnowledgeGroups and their
	 * contained KnowledgeGroups.
	 *
	 * @param knowledgeGroups List
	 * @param ability Ability
	 * @return List
	 */
	private List<Cell> getCellsFor(final List<KnowledgeGroup> knowledgeGroups, final Ability ability) {

		List<Cell> cells = new ArrayList<Cell>();

		for (KnowledgeGroup knowledgeGroup : knowledgeGroups) {
			cells.addAll(this.getCellsFor(knowledgeGroup.getChildren(), ability));
			for (Knowledge knowledge : knowledgeGroup.getKnowledges()) {
				cells.add(this.getCellFor(knowledge, ability));
			}
		}

		return cells;
	}

	/**
	 * Returns a new Cell for a Knowledge and an Ability
	 * @param knowledge Knowledge
	 * @param ability Ability
	 * @return Cell
	 */
	private Cell getCellFor(final Knowledge knowledge, final Ability ability) {
		return new Cell(knowledge, ability);
	}

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

	/**
	 * @return the cellValueManager
	 */
	public CellValueManager getCellValueManager() {
		return this.cellValueManager;
	}

	/**
	 * @param cellValueManager the cellValueManager to set
	 */
	public void setCellValueManager(final CellValueManager cellValueManager) {
		this.cellValueManager = cellValueManager;
	}

	/**
	 * Returns the list of Matrices that already were filled by the given
	 * user.
	 * @param user User
	 * @return list
	 */
	public List<Matrix> getFilledMatrices(final User user) {
		return this.dao.query("CellValue.findMatrixByUser", Matrix.class, user);
	}




}
