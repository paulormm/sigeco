package sigeco.business.impl;

import java.util.List;

import sigeco.business.CellValueManager;
import sigeco.model.Ability;
import sigeco.model.CellValue;
import sigeco.model.Knowledge;
import sigeco.model.Matrix;
import sigeco.model.User;
import sigeco.utils.dao.Dao;

/**
 * Implementation of a CellValueManager using a DAO.
 *
 * @author julien
 */
public class CellValueManagerImpl implements CellValueManager {

	private Dao dao;

	/**
	 * Returns the list of cellvalues from this matrix
	 *
	 * @param matrix Matrix
	 *
	 *  @return List
	 */
	public List<CellValue> getCellValues(final Matrix matrix) {
		return this.dao.query("CellValue.findByMatrix", CellValue.class, matrix);
	}

	/**
	 * Gets all the CellValue's for a User in a Matrix
	 * @param user User
	 * @param matrix Matrix
	 * @return List
	 */
	public List<CellValue> getCellValues(final User user, final Matrix matrix) {
		return this.dao.query("CellValue.findByUserAndMatrix", CellValue.class, matrix, user);
	}


	/**
	 * Save all the CellValues in the supplied List
	 * @param cellValues List
	 */
	@SuppressWarnings("unchecked")
	public void saveAllCellValue(final List<CellValue> cellValues) {
		this.dao.save((List) cellValues);
	}

	/**
	 * Save a CellValue
	 * @param cellValue CellValue
	 */
	public void saveCellValue(final CellValue cellValue) {
		this.dao.save(cellValue);
	}

	/**
	 * Gets the CellValue for the given user in the given matrix for the given knowledge
	 * and the given ability
	 * @param user User
	 * @param matrix Matrix
	 * @param knowledge Knowledge
	 * @param ability Ability
	 * @return CellValue
	 */
	public CellValue getCellValue(
			final User user, 
			final Matrix matrix, 
			final Knowledge knowledge, 
			final Ability ability) {
		List<CellValue> list = this.dao.query("CellValue.findByAll", CellValue.class, matrix, knowledge, ability, user);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}



	/**
	 * Gets the dao
	 * @return Dao
	 */
	public Dao getDao() {
		return this.dao;
	}

	/**
	 * Sets the Dao
	 * @param dao Dao
	 */
	public void setDao(final Dao dao) {
		this.dao = dao;
	}
}
