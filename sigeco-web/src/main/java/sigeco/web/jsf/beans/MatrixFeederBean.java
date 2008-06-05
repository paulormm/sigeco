package sigeco.web.jsf.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.ajax4jsf.component.html.HtmlAjaxOutputPanel;
import org.richfaces.component.html.HtmlColumn;
import org.richfaces.component.html.HtmlDataTable;
import org.richfaces.component.html.HtmlDndParam;
import org.richfaces.component.html.HtmlDragSupport;
import org.richfaces.component.html.HtmlDropSupport;
import org.richfaces.component.html.HtmlPanel;
import org.richfaces.event.DropEvent;

import sigeco.business.CellValueManager;
import sigeco.business.MatrixManager;
import sigeco.business.UserManager;
import sigeco.business.security.UserLocator;
import sigeco.model.Cell;
import sigeco.model.CellValue;
import sigeco.model.Grade;
import sigeco.model.Grant;
import sigeco.model.Knowledge;
import sigeco.model.KnowledgeGroup;
import sigeco.model.Matrix;
import sigeco.model.Permission;
import sigeco.model.User;
import sigeco.web.utils.FeedElement;


/**
 * JSF Bean for feeding the matrices.
 *
 * @author yugo
 */
public class MatrixFeederBean {
	private UserManager userManager;
	private MatrixManager matrixManager;
	private CellValueManager cellValueManager;

	private User selectedUser;
	private Matrix matrixOnFocus;
	private List<FeedElement> elements;

	private List<CellValue> updatedElements;

	private HtmlDataTable dataTable;

	/**
	 * Returns the matrix being fed.
	 *
	 * @return Matrix
	 */
	public Matrix getMatrixOnFocus() {
		return this.matrixOnFocus;
	}

	/**
	 * Returns the users list wrapped as a SelectItem list.
	 * @return List
	 */
	public List<SelectItem> getUsers() {
		final User user = UserLocator.getUser();
		final List<User> users = new ArrayList<User>();

		if (Permission.USER.isRole(user.getPermission()) && Grant.FILL_OWN.isGrant(user.getGrants())) {
			users.add(user);
		}
		if (Permission.MANAGER.isRole(user.getPermission()) && Grant.FILL_OTHERS.isGrant(user.getGrants())) {
			users.addAll(this.userManager.list(user));
		}

		final List<SelectItem> wrap = this.wrapAsGUIList(users);
		return wrap;
	}

	/**
	 * Wraps a given list into a SelectItem list.
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
     * Sets that matrix that is being fed
     * @param matrixOnFocus Matrix
     */
	public void setMatrixOnFocus(final Matrix matrixOnFocus) {
		this.matrixOnFocus = matrixOnFocus;

		this.setElements(this.initializeElements(this.selectedUser, matrixOnFocus));
		this.populateMatrix();
	}

	/**
	 * ActionListener for saving the matrix and the fed values.
	 * @param event ActionEvent
	 */
	public void save(final ActionEvent event) {
		this.cellValueManager.saveAllCellValue(this.updatedElements);
		this.matrixManager.saveMatrix(this.matrixOnFocus);
	}

	/**
	 * Gets the matrixManager
	 * @return MatrixManager
	 */
	public MatrixManager getMatrixManager() {
		return this.matrixManager;
	}

	/**
	 * Sets the matrixManager
	 * @param matrixManager MatrixManager
	 */
	public void setMatrixManager(final MatrixManager matrixManager) {
		this.matrixManager = matrixManager;
	}

	/**
	 * Gets the cellValueManager
	 * @return CellValueManager
	 */
	public CellValueManager getCellValueManager() {
		return this.cellValueManager;
	}

	/**
	 * Sets the cellValueManager
	 * @param cellValueManager CellValueManager
	 */
	public void setCellValueManager(final CellValueManager cellValueManager) {
		this.cellValueManager = cellValueManager;
	}

	/**
	 * Gets the FeedElement list
	 * @return List
	 */
	public List<FeedElement> getElements() {
		return this.elements;
	}

	/**
	 * Sets the FeedElement list
	 * @param elements List
	 */
	public void setElements(final List<FeedElement> elements) {
		this.elements = elements;
	}

	/**
	 * Initialize the FeedElements.
	 * @param user User
	 * @param matrix Matrix
	 * @return List
	 */
	private List<FeedElement> initializeElements(final User user, final Matrix matrix) {
		HashMap<Long, CellValue> cellValueMap = new HashMap<Long, CellValue>();
		this.updatedElements = new ArrayList<CellValue>();

		for (CellValue cellValue : this.cellValueManager.getCellValues(user, matrix)) {
			cellValueMap.put(cellValue.getCell().getId(), cellValue);
		}

		List<FeedElement> feedElements = new ArrayList<FeedElement>();

		for (Object obj : matrix.getAllKnowledges()) {
			if (obj instanceof KnowledgeGroup) {
				FeedElement groupElement = new FeedElement((KnowledgeGroup) obj);
				feedElements.add(groupElement);
			} else if (obj instanceof Knowledge) {
				Knowledge knowledge = (Knowledge) obj;

				List<CellValue> cellValues = new ArrayList<CellValue>();

				for (Cell cell : matrix.getCells(knowledge)) {
					if (cell.isActive()) {
						CellValue cellValue;
						if (cellValueMap.containsKey(cell.getId())) {
							cellValue = cellValueMap.get(cell.getId());
						} else {
							cellValue = new CellValue();
							cellValue.setCell(cell);
							cellValue.setUser(user);
							cellValue.setGrade(null);
							
							cellValueMap.put(cellValue.getCell().getId(), cellValue);
						}
						this.updatedElements.add(cellValue);
						cellValues.add(cellValue);
					}
				}

				FeedElement knowledgeElement = new FeedElement(knowledge, cellValues);
				knowledgeElement.setGrades(this.matrixOnFocus.getGrades());
				feedElements.add(knowledgeElement);
			}
		}

		return feedElements;
	}

	/**
	 * Drop event.
	 * @param event DropEvent
	 */
	public void dropGradeListener(final DropEvent event) {
		CellValue cellValue = (CellValue) event.getDragValue();
		Grade grade = (Grade) event.getDropValue();
		cellValue.setGrade(grade);
		cellValue.setDate(new Date());
		if (!this.updatedElements.contains(cellValue)) {
			this.updatedElements.add(cellValue);
		}

		this.synchronize(cellValue);
		this.populateMatrix();
	}

	/**
	 * Syncs the CellValue
	 * @param cellValue CellValue
	 */
	private void synchronize(final CellValue cellValue) {
		for (FeedElement element : this.elements) {
			if (element.isRendered()) {
				element.synchronize(cellValue);
			}
		}
	}

	/**
	 * Gets the HtmlDataTable
	 * @return HtmlDataTable
	 */
	public HtmlDataTable getDataTable() {
		return this.dataTable;
	}

	/**
	 * Sets the HtmlDataTable
	 * @param dataTable HtmlDataTable
	 */
	public void setDataTable(final HtmlDataTable dataTable) {
		this.dataTable = dataTable;
	}

	/**
	 * populates the matrix
	 */
	@SuppressWarnings("unchecked")
	private void populateMatrix() {
		this.dataTable = new HtmlDataTable();
		this.dataTable.setId("elements");
		this.dataTable.setValueBinding("value", this.createValueExpression("#{matrixFeederBean.elements}"));
		this.dataTable.setVar("element");

		this.createHeader();

		List<Grade> grades = this.matrixOnFocus.getGrades();
		Collections.sort(grades);

		this.addDragDropColumn("#{msgs['grades.none']}", -1, true, null);

		for (Grade grade : grades) {
			this.addDragDropColumn(grade.getName(), grade.getGradeOrder(), false, grade);
		}
	}

	/**
	 * @param grade
	 */
	@SuppressWarnings("unchecked")
	private void addDragDropColumn(final String gradeName, final int gradeOrder, final boolean isValueExpression, final Grade grade) {
		//Creating column and its header.
		HtmlColumn gradeColumn = new HtmlColumn();
		HtmlOutputText gradeTitle = new HtmlOutputText();
		gradeTitle.setStyleClass("field");
		if (isValueExpression) {
			gradeTitle.setValueBinding("value", this.createValueExpression(gradeName));
		}
		else {
			gradeTitle.setValue(gradeName);
		}
		gradeColumn.setHeader(gradeTitle);

		//Creating rich panel
		HtmlPanel panel = new HtmlPanel();
		panel.setValueBinding("rendered", this.createValueExpression("#{element.rendered}"));
		gradeColumn.getChildren().add(panel);

		//Creating Drop Support
		HtmlDropSupport dropSupport = this.createComponent(HtmlDropSupport.class, HtmlDropSupport.COMPONENT_TYPE);
		dropSupport.setId("dropZone" + gradeOrder);
		dropSupport.setValueBinding("acceptedTypes", this.createValueExpression("#{element.name}"));
		dropSupport.setReRender("elements");
		dropSupport.setDropListener(this.createMethodExpression("#{matrixFeederBean.dropGradeListener}", new Class[]{DropEvent.class}));
		dropSupport.setDropValue(grade);
		panel.getChildren().add(dropSupport);

		//Creating second data table inside
		HtmlDataTable secondaryTable = new HtmlDataTable();
		secondaryTable.setId("secTable" + gradeOrder);
		secondaryTable.setValueBinding("value",	this.createValueExpression("#{element.cellValues[" + gradeOrder + "]}"));
		secondaryTable.setVar("cellValue");
		panel.getChildren().add(secondaryTable);

		//Creating Column
		HtmlColumn abilityColumn = new HtmlColumn();
		secondaryTable.getChildren().add(abilityColumn);

		//Ajax Output Panel.
		HtmlAjaxOutputPanel outputPanel = new HtmlAjaxOutputPanel();
		outputPanel.setId("ajaxPanel" + gradeOrder);
		abilityColumn.getChildren().add(outputPanel);

		//Drag Support.
		HtmlDragSupport dragSupport = this.createComponent(HtmlDragSupport.class, HtmlDragSupport.COMPONENT_TYPE);
		dragSupport.setId("dragZone" + gradeOrder);
		dragSupport.setValueBinding("dragType", this.createValueExpression("#{element.name}"));
		dragSupport.setDragIndicator(":indicator");
		dragSupport.setValueBinding("dragValue", this.createValueExpression("#{cellValue}"));
		outputPanel.getChildren().add(dragSupport);

		//DnD Support
		HtmlDndParam dndParam = new HtmlDndParam();
		dndParam.setName("label");
		dndParam.setValue(this.createValueExpression("#{cellValue.cell.ability.name}"));
		dragSupport.getChildren().add(dndParam);

		//Label of ability
		HtmlOutputText abilityTitle = new HtmlOutputText();
		abilityTitle.setStyleClass("field");
		abilityTitle.setValueBinding("value", this.createValueExpression("#{cellValue.cell.ability.name}"));
		outputPanel.getChildren().add(abilityTitle);

		//Adding to datatable
		this.dataTable.getChildren().add(gradeColumn);
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
		knowledgeTitleHeader.setValueBinding("value", this.createValueExpression("#{msgs['matrices.form.abilities.title']}"));
		knowledgeTitleColumn.setHeader(knowledgeTitleHeader);

		HtmlOutputText titleOutputText = new HtmlOutputText();
		titleOutputText.setEscape(false);
		titleOutputText.setValueBinding("value", this.createValueExpression("#{element.name}"));
		knowledgeTitleColumn.getChildren().add(titleOutputText);
		this.dataTable.getChildren().add(knowledgeTitleColumn);
	}

	/**
	 * Creates an EL Value Binding.
	 * @param valueExpression String
	 * @return ValueBinding
	 */
	private ValueBinding createValueExpression(final String valueExpression) {
        return FacesContext.getCurrentInstance().getApplication().createValueBinding(valueExpression);
	}

	/**
	 * Creates an EL Method Binding.
	 * @param methodExpression String
	 * @return MethodBinding
	 */
	private MethodBinding createMethodExpression(final String methodExpression, final Class[] classes) {
        return FacesContext.getCurrentInstance().getApplication().createMethodBinding(methodExpression, classes);
	}

	@SuppressWarnings("unchecked")
	private <X> X createComponent(final Class<X> componentClass, final String componentType) {
		return (X)FacesContext.getCurrentInstance().getApplication().createComponent(componentType);
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
}
