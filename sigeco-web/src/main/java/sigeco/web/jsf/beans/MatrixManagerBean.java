package sigeco.web.jsf.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;

import sigeco.business.MatrixManager;
import sigeco.model.Matrix;
import sigeco.model.User;

/**
 * JSF Managed Bean that delegates to a spring controlled MatrixManager
 *
 * Please refer to the {@link MatrixManager} interface for further documentation.
 *
 * @author julien
 */
public class MatrixManagerBean implements MatrixManager {

	private MatrixManager matrixManager;
	public static final String BEAN_NAME = "matrixManagerBean";

	/**
	 * Sttic getter.
	 * @param context FacesContext
	 * @return MatrixManagerBean
	 */
	public static MatrixManagerBean getInstance(final FacesContext context) {
		return (MatrixManagerBean) context.getApplication().createValueBinding(
				"#{" + BEAN_NAME + "}").getValue(context);
	}

	/**
	 * Gets the Matrix with the given id or null.
	 * @param id The id
	 * @return The found Matrix or null
	 */
	public Matrix get(final Serializable id) {
		return this.matrixManager.get(id);
	}

	/**
	 * Return all Matrices.
	 * 
	 * @return The List of Matrices
	 */
	public List<Matrix> getAll() {
		return this.matrixManager.getAll();
	}

	/**
	 * Returns all Matrices from start until (start + amount - 1).
	 * 
	 * @param start The index of the first Matrix to be returned
	 * @param amount The max amount of Matrices returned 
	 * @return The List of Matrices
	 */
	public List<Matrix> getAll(final int start, final int amount) {
		return this.matrixManager.getAll(start, amount);
	}
	/**
	 * Removes the given Matrix.
	 * 
	 * @param matrix The Matrix to be removed
	 */
	public void removeMatrix(final Matrix matrix) {
		this.matrixManager.removeMatrix(matrix);

	}
	
	/**
	 * Saves the given Matrix.
	 * 
	 * @param matrix The Matrix to be saved.
	 */
	public void saveMatrix(final Matrix matrix) {
		this.matrixManager.saveMatrix(matrix);
	}

	/**
	 * @return the matrixManager
	 */
	public MatrixManager getMatrixManager() {
		return this.matrixManager;
	}

	/**
	 * @param matrixManager the matrixManager to set
	 */
	public void setMatrixManager(final MatrixManager matrixManager) {
		this.matrixManager = matrixManager;
	}

	/**
	 * Returns the list of Matrices that already were filled by the given
	 * user.
	 * @param user User
	 * @return list
	 */
	public List<Matrix> getFilledMatrices(final User user) {
		return this.matrixManager.getFilledMatrices(user);
	}
}
