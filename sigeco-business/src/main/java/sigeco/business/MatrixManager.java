package sigeco.business;

import java.io.Serializable;
import java.util.List;

import sigeco.business.security.annotation.Secured;
import sigeco.model.Matrix;
import sigeco.model.Permission;
import sigeco.model.User;

/**
 * Interface that defines common CRUD {@link Matrix} operations.
 * 
 * @author julien
 */
public interface MatrixManager {

	/**
	 * Saves the given Matrix.
	 * 
	 * @param matrix The Matrix to be saved.
	 */
	@Secured(Permission.MANAGER)
	void saveMatrix(final Matrix matrix);
	
	/**
	 * Gets the Matrix with the given id or null.
	 * @param id The id
	 * @return The found Matrix or null
	 */
	Matrix get(final Serializable id);

	
	/**
	 * Returns the list of Matrices that already were filled by the given
	 * user.
	 * @param user User
	 * @return list
	 */
	@Secured({Permission.MANAGER, Permission.USER })
	List<Matrix> getFilledMatrices(final User user);
	
	/**
	 * Return all Matrices.
	 * 
	 * @return The List of Matrices
	 */
//	@Secured(Permission.MANAGER)
	List<Matrix> getAll();
	
	/**
	 * Returns all Matrices from start until (start + amount - 1).
	 * 
	 * @param start The index of the first Matrix to be returned
	 * @param amount The max amount of Matrices returned 
	 * @return The List of Matrices
	 */
	@Secured(Permission.ADMIN)
	List<Matrix> getAll(final int start, final int amount);
	
	/**
	 * Removes the given Matrix.
	 * 
	 * @param matrix The Matrix to be removed
	 */
	@Secured(Permission.ADMIN)
	void removeMatrix(final Matrix matrix);
	
}
