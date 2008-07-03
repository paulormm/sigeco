package sigeco.web.jsf.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.richfaces.component.html.HtmlColumn;
import org.richfaces.component.html.HtmlDataTable;

import sigeco.business.CellValueManager;
import sigeco.business.MatrixManager;
import sigeco.business.UserManager;
import sigeco.business.security.UserLocator;
import sigeco.model.Ability;
import sigeco.model.CellValue;
import sigeco.model.Knowledge;
import sigeco.model.KnowledgeGroup;
import sigeco.model.Matrix;
import sigeco.model.Permission;
import sigeco.model.User;
import sigeco.model.Row;

/**
 * Auxiliary class for solving our problem
 * @author fabio & marum
 */
public class RenderableMatrixBean {
	private String matrixName;
	private HtmlDataTable matrixDataTable;
	private List<Row> matrixRows;

	public RenderableMatrixBean() { 
		matrixName = new String();
		matrixDataTable = new HtmlDataTable();
		matrixRows = new ArrayList<Row>();
	}

	/**
	 * @return the matrixDataTable
	 */
	public HtmlDataTable getMatrixDataTable() {
		return this.matrixDataTable;
	}

	/**
	 * @param matrixDataTable the matrixDataTable to set
	 */
	public void setMatrixDataTable(final HtmlDataTable matrixDataTable) {
		this.matrixDataTable = matrixDataTable;
	}

	/**
	 * @return the matrixRows
	 */
	public List<Row> getMatrixRows() {
		return this.matrixRows;
	}

	/**
	 * @param matrixRows the matrixRows to set
	 */
	public void setMatrixRows(final List<Row> matrixRows) {
		this.matrixRows = matrixRows;
	}

	/**
	 * @return the matrixName
	 */
	public String getMatrixName() {
		return this.matrixName;
	}

	/**
	 * @param matrixName the matrixName to set
	 */
	public void setMatrixName(final String matrixName) {
		this.matrixName = matrixName;
	}
}

