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


/**
 * Managed Bean for Viewing Matrix Feeds.
 *
 * @author julien
 */
public class ViewMatrixFeedBean {

	private UserManager userManager;

	private MatrixManager matrixManager;

	private CellValueManager cellValueManager;

	private User selectedUser;

	private Matrix selectedMatrix;

	private List<Matrix> matrices;

	private HtmlDataTable matrixDataTable;

	private List<Row> matrixRows;

	private Map<Long, String> emptyMap;

	/**
	 * Returns the list of users.
	 * @return List
	 */
	public List<SelectItem> getUsers() {
		User user = UserLocator.getUser();
		List<User> users = new ArrayList<User>();
		//= Permission.MANAGER.isRole(user.getPermission()) ? this.userManager.list(user) : Arrays.asList(user);
		if (Permission.USER.isRole(user.getPermission())) {
			users.add(user);
		}
		if (Permission.MANAGER.isRole(user.getPermission())) {
			users.addAll(this.userManager.list(user));
		}

		List<SelectItem> wrap = this.wrapAsGUIList(users);
		User fake = new User();
		fake.setId(-1L);
		SelectItem item = new SelectItem(fake, this.resolve("global.form.choose"));
		wrap.add(0, item);
		return wrap;
	}

	/**
	 * Fires when the user is changed
	 * @param event ValueChangeEvent
	 */
	public void selectedUserChanged(final ValueChangeEvent event) {
		User user = (User) event.getNewValue();
		if (user != null && user.getId() < 1) {
			this.selectedUser = null;
			this.matrices = new ArrayList<Matrix>();
		} else {
			this.selectedUser = user;
			this.matrices = this.matrixManager.getFilledMatrices(this.selectedUser);
		}
	}

	/**
	 * Populates the list of rows according to the current user and matrix.
	 */
	private void populateRows() {
		this.matrixRows = new ArrayList<Row>();

		//need to iterate thru the knowledge groups and create
		//row instances accordingly
		for (KnowledgeGroup group : this.selectedMatrix.getKnowledgeGroups()) {
			this.matrixRows.addAll(this.getRowsFor(group, 0));
		}
	}

	/**
	 * Returns the rows for the given group.
	 * @param group KnowledgeGroup
	 * @param n int
	 * @return List
	 */
	private List<Row> getRowsFor(final KnowledgeGroup group, final int n) {
		List<Row> rows = new ArrayList<Row>();
		rows.add(new Row(this.getSpacer(n) + group.getTitle(), this.getEmptyMap(), RowType.KNOWLEDGE_GROUP));

		for (Knowledge k : group.getKnowledges()) {
			rows.add(new Row(this.getSpacer(n + 1) + k.getName(), this.getValueMap(k), RowType.KNOWLEDGE));
		}

		for (KnowledgeGroup g : group.getChildren()) {
			rows.addAll(this.getRowsFor(g, n + 1));
		}

		return rows;
	}

	/**
	 * Gets a spacer of the given size.
	 * @param n int
	 * @return String
	 */
	private String getSpacer(final int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}

	/**
	 * Returns the level Map for the current matrix for the current use
	 * for the given Knowledge
	 * @param k Knowledge
	 * @return Map
	 */
	private Map<Long, String> getValueMap(final Knowledge k) {

		Map<Long, String> map = new HashMap<Long, String>();

		for (Ability a : this.selectedMatrix.getAbilities()) {
			CellValue cv = this.cellValueManager.getCellValue(
					this.selectedUser,
					this.selectedMatrix,
					k,
					a);
			if (cv != null) {
				if (cv.getGrade() == null) {
					if (cv.getCell().isActive()) {
						map.put(a.getId(), this.resolve("grades.none"));
					} else {
						map.put(a.getId(), "");
					}
				} else {
					map.put(a.getId(), cv.getGrade().getName()+" "+cv.dateAsString());
				}
			} else {
				map.put(a.getId(), "");
			}
		}

		return map;
	}

	/**
	 * Returns an empty level Map for the current matrix
	 * @return Map
	 */
	private Map<Long, String> getEmptyMap() {
		if (this.emptyMap == null) {
			this.emptyMap = new HashMap<Long, String>();
			for (Ability a : this.selectedMatrix.getAbilities()) {
				this.emptyMap.put(Long.valueOf(a.getId()), "");
			}
		}
		return this.emptyMap;
	}

	/**
	 * Populates the Matrix with the cells of the selected matrix for the selected user.
	 */
	@SuppressWarnings("unchecked")
	private void populateMatrix() {

		//since associated session may be closed we search for the matrix again
		this.selectedMatrix = this.matrixManager.get(this.selectedMatrix.getId());

		this.populateRows();

		this.matrixDataTable = new HtmlDataTable();
		this.createHeader();

		for (Ability ability : this.selectedMatrix.getAbilities()) {

			HtmlColumn abilityNameColumn = new HtmlColumn();
			HtmlOutputText headerOutputText = new HtmlOutputText();
			headerOutputText.setStyleClass("field");
			headerOutputText.setValue(ability.getName());
			abilityNameColumn.setHeader(headerOutputText);
			abilityNameColumn.setValueBinding(
					"style", 
					this.createValueExpression(
							"text-align: center; background-color:#{row.bgcolor[" + ability.getId() + "]};"));

			HtmlOutputText abilityOutputText = new HtmlOutputText();
			abilityOutputText.setValueBinding(
					"value", 
					this.createValueExpression("#{row.levels[" + ability.getId() + "]}"));
			abilityNameColumn.getChildren().add(abilityOutputText);
			this.matrixDataTable.getChildren().add(abilityNameColumn);

		}

	}


	/**
	 * Creates the header of the Data Table.
	 */
	@SuppressWarnings("unchecked")
	private void createHeader() {
		HtmlColumn knowledgeTitleColumn = new HtmlColumn();
		knowledgeTitleColumn.setStyleClass("field");
		HtmlOutputText knowledgeTitleHeader = new HtmlOutputText();
		knowledgeTitleHeader.setStyleClass("field");
		knowledgeTitleHeader.setValueBinding(
				"value", 
				this.createValueExpression("#{msgs['matrices.form.abilities.title']}"));
		knowledgeTitleColumn.setHeader(knowledgeTitleHeader);
		knowledgeTitleColumn.setStyle("background-color: lightgray;");

		HtmlOutputText titleOutputText = new HtmlOutputText();
		titleOutputText.setEscape(false);
		titleOutputText.setValueBinding("value", this.createValueExpression("#{row.item}"));
		knowledgeTitleColumn.getChildren().add(titleOutputText);
		this.matrixDataTable.getChildren().add(knowledgeTitleColumn);
	}


	/**
	 * Creates an EL Value Binding
	 * @param valueExpression String
	 * @return ValueBinding
	 */
	private ValueBinding createValueExpression(final String valueExpression) {
        return FacesContext.getCurrentInstance().getApplication().createValueBinding(valueExpression);
    }

	/**
	 *
	 *
	 * Return the matrices for the selected user.
	 * @return List
	 */
	public List<Matrix> getMatrices() {
		return this.matrices;
	}

	/**
	 * Wraps a list of objects as a list of select items to be used in jsf list components.
	 * @param list List
	 * @return List
	 */
    private List<SelectItem> wrapAsGUIList(final List< ? extends Object> list) {
        List<SelectItem> items = new ArrayList<SelectItem>();

        for (Iterator< ? extends Object> iter = list.iterator(); iter.hasNext();) {
        	Object next = iter.next();
            SelectItem item = new SelectItem(next, next.toString());
            items.add(item);
        }

        return items;
	}

    /**
     * Enum for defining a row type.
     * 
     * @author julien
     */
    public enum RowType {
    	KNOWLEDGE("white"),
    	KNOWLEDGE_GROUP("whitesmoke");

    	private String color;

    	/**
    	 * C'tor 
    	 * @param color The html color this row type should be colored with
    	 */
    	private RowType(final String color) {
    		this.color = color;
    	}

    	/**
    	 * Returns what color the row should be represented with
    	 * @return String
    	 */
    	public String getColor() {
    		return this.color;
    	}
    }

    /**
     * Auxiliary class for displaying feeds
     * @author julien
     */
    public class Row {

    	private RowType type;
    	private String item;
    	private Map<Long, String> levels;
    	private Map<Long, String> bgcolor;

    	/**
    	 * C'tor
    	 */
    	public Row() {
			super();
		}

		/**
    	 * C'tor
    	 * @param item String
    	 * @param levels Map
    	 * @param type RowType
    	 */
		public Row(final String item, final Map<Long, String> levels, final RowType type) {
			super();
			this.item = item;
			this.levels = levels;
			this.type = type;
			
			this.bgcolor = new HashMap<Long, String>();
			for (Map.Entry<Long, String> entry : levels.entrySet()) {
				if (entry.getValue().equals("")) {
					this.bgcolor.put(entry.getKey(), "whitesmoke");
				} else {
					this.bgcolor.put(entry.getKey(), this.type.color);
				}
			}
		}

		/**
		 * Returns the background color map
		 * @return Map
		 */
		public Map<Long, String> getBgcolor() {
			return this.bgcolor;
		}
		
		/**
		 * @return the levels
		 */
		public Map<Long, String> getLevels() {
			return this.levels;
		}
		/**
		 * @param levels the levels to set
		 */
		public void setLevels(final Map<Long, String> levels) {
			this.levels = levels;
		}
		/**
		 * @return the item
		 */
		public String getItem() {
			return this.item;
		}
		/**
		 * @param item the item to set
		 */
		public void setItem(final String item) {
			this.item = item;
		}

		/**
		 * @return the type
		 */
		public RowType getType() {
			return this.type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(final RowType type) {
			this.type = type;
		}



    }


	/**
	 * @return the userManager
	 */
	public UserManager getUserManager() {
		return this.userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(final UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * Resolves a bundleKey
	 * @param bundleKey String
	 * @return String
	 */
	private String resolve(final String bundleKey) {
		return ResourceBundle.getBundle("messages").getString(bundleKey);
	}

	/**
	 * @return the selectedUser
	 */
	public User getSelectedUser() {
		return this.selectedUser;
	}

	/**
	 * @param selectedUser the selectedUser to set
	 */
	public void setSelectedUser(final User selectedUser) {
		this.selectedUser = selectedUser;
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
	 * @return the selectedMatrix
	 */
	public Matrix getSelectedMatrix() {
		return this.selectedMatrix;
	}

	/**
	 * @param selectedMatrix the selectedMatrix to set
	 */
	public void setSelectedMatrix(final Matrix selectedMatrix) {
		this.selectedMatrix = selectedMatrix;
		this.emptyMap = null;
		this.populateMatrix();
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



}
