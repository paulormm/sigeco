package sigeco.business;

import java.util.List;

import sigeco.model.Ability;
import sigeco.model.CellValue;
import sigeco.model.Knowledge;
import sigeco.model.Matrix;
import sigeco.model.User;

/**
 * Interface for a CellValue Manager.
 * 
 * @author julien
 */
public interface CellValueManager {
	/**
	 * Gets all the CellValue's for a Matrix.
	 * @param matrix Matrix
	 * @return List
	 */
	List<CellValue> getCellValues(Matrix matrix);
	
	/**
	 * Gets all the CellValue's for a User in a Matrix
	 * @param user User
	 * @param matrix Matrix
	 * @return List
	 */
	List<CellValue> getCellValues(User user, Matrix matrix);
	
	/**
	 * Gets the CellValue for the given user in the given matrix for the given knowledge
	 * and the given ability
	 * @param user User
	 * @param matrix Matrix
	 * @param knowledge Knowledge
	 * @param ability Ability
	 * @return CellValue
	 */
	CellValue getCellValue(User user, Matrix matrix, Knowledge knowledge, Ability ability);
	
	/**
	 * Save a CellValue 
	 * @param cellValue CellValue
	 */
	void saveCellValue(final CellValue cellValue);
	
	/**
	 * Save all the CellValues in the supplied List
	 * @param cellValues List
	 */
	void saveAllCellValue(final List<CellValue> cellValues);
}
