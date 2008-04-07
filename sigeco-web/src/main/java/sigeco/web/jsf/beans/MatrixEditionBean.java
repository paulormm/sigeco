package sigeco.web.jsf.beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlColumn;
import org.richfaces.component.html.HtmlDataTable;

import sigeco.model.Ability;
import sigeco.model.Cell;
import sigeco.model.Grade;
import sigeco.model.Knowledge;
import sigeco.model.KnowledgeGroup;
import sigeco.model.Matrix;
import sigeco.web.component.MatrixCellCheckBox;

/**
 * Managed bean responsible for matrix edition operations.
 *
 */
public class MatrixEditionBean {
	private Matrix matrixOnFocus;

	private String newKnowledgeGroupName;
	private KnowledgeGroup groupOnFocus;
	private Knowledge knowledgeOnFocus;
	private String abilityName;
	private Ability abilityOnFocus;
	private String gradeName;
	private Grade gradeOnFocus;
	private List<Row> matrixCellsRows;
	private HtmlDataTable matrixCellsDataTable;
	private Map<String, Boolean> emptyMap;
	private Map<Knowledge, Map<String, CellStatus>> cellStatusMap;

	/**
	 * Inserts a group on the new matrix.
	 * @param event event.
	 */
	public void insertGroup(final ActionEvent event) {
		if (this.newKnowledgeGroupName != null && !this.newKnowledgeGroupName.equals("")) {
			if (this.checkKnowledgeName(newKnowledgeGroupName, groupOnFocus)) {
				KnowledgeGroup group = new KnowledgeGroup(this.matrixOnFocus, this.newKnowledgeGroupName);
				this.newKnowledgeGroupName = "";
				if (this.groupOnFocus != null) {
					this.groupOnFocus.getChildren().add(group);
					group.setParent(this.groupOnFocus);
					this.groupOnFocus.setActive(false);
				}
				else {
					this.matrixOnFocus.getKnowledgeGroups().add(group);
				}
			} else {
				String msg = ResourceBundle.getBundle("messages").getString("matrices.form.nameinuse");
				FacesContext.getCurrentInstance().addMessage(
						"messages", 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
			}
			
		}
	}

	/**
	 * Inserts a knowledge on the new matrix.
	 * @param event event.
	 */
	public void insertKnowledge(final ActionEvent event) {
		if (this.newKnowledgeGroupName != null && !this.newKnowledgeGroupName.equals("")) {
			if (this.groupOnFocus != null) {
				if (this.checkKnowledgeName(this.newKnowledgeGroupName, this.groupOnFocus)) {
					Knowledge knowledge = new Knowledge(this.newKnowledgeGroupName, this.groupOnFocus);
					this.groupOnFocus.getKnowledges().add(knowledge);
					this.groupOnFocus.setActive(false);
					this.newKnowledgeGroupName = "";
				} else {
					String msg = ResourceBundle.getBundle("messages").getString("matrices.form.nameinuse");
					FacesContext.getCurrentInstance().addMessage(
							"messages", 
							new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
				}
			}
		}
	}
	
	/**
	 * Removes a group from the matrix.
	 * @param event event.
	 */
	public void removeGroup(final ActionEvent event) {
		if (this.groupOnFocus != null) {
			final KnowledgeGroup parentGroup = this.groupOnFocus.getParent();
			if (parentGroup == null) {
				this.matrixOnFocus.getKnowledgeGroups().remove(groupOnFocus);
			}
			else {
				parentGroup.getChildren().remove(groupOnFocus);
			}
		}
	}
	
	/**
	 * Removes a knowledge from the matrix.
	 * @param event event.
	 */
	public void removeKnowledge(final ActionEvent event) {
		if (this.knowledgeOnFocus != null && this.groupOnFocus != null) {
			groupOnFocus.getKnowledges().remove(knowledgeOnFocus);
		}
	}

//	/**
//	 * Checks wether this name is permitted i.e. has not been used yet in this matrix
//	 * @param name String
//	 * @return boolean
//	 */
//	private boolean checkKnowledgeName(final String name) {
//		List<KnowledgeGroup> groups = this.matrixOnFocus.getKnowledgeGroups();
//		for (KnowledgeGroup kg : groups) {
//			if (!this.checkKnowledgeName(name, kg)) {
//				return false;
//			}
//		}
//		return true;
//	}

	/**
	 * Checks wether this name is permitted i.e. has not been used yet in this group's knowledges
	 * @param name String
	 * @param group KnowledgeGroup
	 * @return boolean
	 */
	private boolean checkKnowledgeName(final String name, final KnowledgeGroup group) {
		if (group.getTitle().equals(name)) {
			return false;
		}
		
		for (Knowledge k : group.getKnowledges()) {
			if (name.equals(k.getName())) {
				return false;
			}
		}

		for (KnowledgeGroup kg : group.getChildren()) {
			if (!this.checkKnowledgeName(name, kg)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Starts a new matrix inclusion.
	 * @param event
	 */
	public void startMatrixInclusion(final ActionEvent event) {
		this.matrixOnFocus = new Matrix();
		
		String root = ResourceBundle.getBundle("messages").getString("matrices.form.knowledgeGroup.root");
		
		KnowledgeGroup defaultGroup = new KnowledgeGroup(this.matrixOnFocus, root);
		this.matrixOnFocus.getKnowledgeGroups().add(defaultGroup);
	}

	/**
	 * Inserts a ability on the new matrix.
	 * @param event event.
	 */
	public void insertAbility(final ActionEvent event) {
		if (this.abilityName != null && !this.abilityName.equals("")) {
			if (this.checkAbilityName(this.abilityName)) {
				Ability ability = new Ability(this.abilityName, this.matrixOnFocus);
				this.matrixOnFocus.getAbilities().add(ability);
				this.abilityName = "";
			}
		}
	}
	
	/**
	 * Removes chosen ability of the matrix.
	 * @param event event.
	 */
	public void removeAbility(final ActionEvent event) {
		if (this.abilityOnFocus != null) {
			this.matrixOnFocus.getAbilities().remove(this.abilityOnFocus);
		}
	}

	/**
	 * Checks that an ability name is good to be used.
	 * @param name String
	 * @return boolean
	 */
	private boolean checkAbilityName(final String name) {
		for (Ability a : this.matrixOnFocus.getAbilities()) {
			if (a.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Inserts a new grade on the matrix.
	 * @param event event.
	 */
	public void insertGrade(final ActionEvent event) {
		if (this.gradeName != null && !this.gradeName.equals("")) {
			if (this.checkGradeName(this.gradeName)) {
				Grade grade = new Grade(this.gradeName, this.matrixOnFocus);
				this.matrixOnFocus.getGrades().add(grade);
				this.gradeName = "";
			}
		}
	}
	
	/**
	 * Removes chosen grade from matrix.
	 * @param event event.
	 */
	public void removeGrade(final ActionEvent event) {
		if (this.gradeOnFocus != null) {
			this.matrixOnFocus.getGrades().remove(this.gradeOnFocus);
		}
	}

	/**
	 * Checks that a grade name is good to be used.
	 * @param name String
	 * @return boolean
	 */
	private boolean checkGradeName(String name) {
		for (Grade g : this.matrixOnFocus.getGrades()) {
			if (g.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public void save(final ActionEvent event) {
		MatrixManagerBean mmb = MatrixManagerBean.getInstance(FacesContext.getCurrentInstance());
		mmb.saveMatrix(this.matrixOnFocus);
		this.processCells();
		mmb.saveMatrix(this.matrixOnFocus);
		this.resetBean();
	}

	/**
	 * Processes the values of the checkboxes for the cells of this Matrix.
	 */
	private void processCells() {
		for (Map.Entry<Knowledge, Map<String, CellStatus>> kentry : this.cellStatusMap.entrySet()) {
			for (Entry<String, CellStatus> aentry : kentry.getValue().entrySet()) {
				CellStatus cs = aentry.getValue();
				if (cs.getKnowledge() != null) {
					Cell c = this.matrixOnFocus.getCellFor(cs.getAbility(), cs.getKnowledge());
					c.setActive(cs.isStatus());
				}
			}
		}
	}

	/**
	 * When the Abilities phase is over we move on to the Cell phase
	 * and this event is fired so we can prepare the matrix that is to be shown.
	 *
	 * @param event ActionEvent
	 */
	public void prepareCellMatrix(final ActionEvent event) {
		this.matrixCellsRows = new ArrayList<Row>();
		this.cellStatusMap = new HashMap<Knowledge, Map<String, CellStatus>>();
		this.createCellStatus(null);

		//first we prepare the rows
		for (KnowledgeGroup group : this.matrixOnFocus.getKnowledgeGroups()) {
			this.matrixCellsRows.addAll(this.getRowsFor(group, 0));
		}

		//now we prepare the grid
		this.createGrid();
	}

	/**
	 * Fires when a cell checkbox has been modified.
	 * @param event ValueChangeEvent
	 */
	public void checkboxModified(final ValueChangeEvent event) {
		System.out.println(event);
	}

	/**
	 * Creates a
	 */
	@SuppressWarnings("unchecked")
	private void createGrid() {
		this.matrixCellsDataTable = new HtmlDataTable();
		this.matrixCellsDataTable.setId("cellStatusGrid");
		this.createHeader();

		for(Ability ability : this.matrixOnFocus.getAbilities()){

			HtmlColumn abilityNameColumn = new HtmlColumn();
			abilityNameColumn.setStyle("text-align: center;");
			HtmlOutputText headerOutputText = new HtmlOutputText();
			headerOutputText.setStyleClass("field");
			headerOutputText.setValue(ability.getName());
			abilityNameColumn.setHeader(headerOutputText);

//			HtmlOutputText abilityOutputText = new HtmlOutputText();
//			abilityOutputText.setValueBinding("value", this.createValueExpression("#{row.levels[" + ability.getId() + "]}"));

			MatrixCellCheckBox cb = new MatrixCellCheckBox();
			cb.setId("cellCheckBox" + ability.hashCode());
			cb.setAbility(ability);
//			cb.setValueBinding("value", this.createValueExpression("#{row.values['" + ability.getName() + "']}"));
			cb.setValueBinding(
					"value",
					this.createValueExpression(
							"#{matrixEditionBean.cellStatusMap[row.knowledge]['" + ability.getName() + "'].status}"));
			cb.setValueBinding("rendered", this.createValueExpression("#{row.knowledge != null}"));
//			cb.setValueBinding("valueChangeListener", this.createValueExpression("#{matrixEditionBean.checkboxModified}"));

			abilityNameColumn.getChildren().add(cb);
			this.matrixCellsDataTable.getChildren().add(abilityNameColumn);

		}

	}


	/**
	 * Returns the Component Map for the given Knowledge
	 * @param k Knowledge
	 * @return Map
	 */
	private Map<String, Boolean> getComponentMap(final Knowledge k) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for (Ability a : this.matrixOnFocus.getAbilities()) {
			map.put(a.getName(), true);
		}
		return map;
	}

	/**
	 * Returns an empty component Map for the current matrix
	 * @return Map
	 */
	private Map<String, Boolean> getEmptyMap() {
		if (this.emptyMap == null) {
			this.emptyMap = new HashMap<String, Boolean>();
			for (Ability a : this.matrixOnFocus.getAbilities()) {
				this.emptyMap.put(a.getName(), false);
			}
		}
		return this.emptyMap;
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
	 * Creates the header of the Data Table.
	 */
	@SuppressWarnings("unchecked")
	private void createHeader() {
		HtmlColumn knowledgeTitleColumn = new HtmlColumn();
		knowledgeTitleColumn.setStyleClass("field");
		HtmlOutputText knowledgeTitleHeader = new HtmlOutputText();
		knowledgeTitleHeader.setStyleClass("field");
		knowledgeTitleHeader.setValueBinding("value", this.createValueExpression("#{msgs['matrices.form.knowledges.title']}"));
		knowledgeTitleColumn.setHeader(knowledgeTitleHeader);

		HtmlOutputText titleOutputText = new HtmlOutputText();
		titleOutputText.setEscape(false);
		titleOutputText.setValueBinding("value", this.createValueExpression("#{row.name}"));
		knowledgeTitleColumn.getChildren().add(titleOutputText);
		this.matrixCellsDataTable.getChildren().add(knowledgeTitleColumn);
	}

	/**
	 * Returns the rows for the given group.
	 * @param group KnowledgeGroup
	 * @param n int
	 * @return List
	 */
	private List<Row> getRowsFor(final KnowledgeGroup group, final int n) {
		List<Row> rows = new ArrayList<Row>();
		rows.add(new Row(this.getSpacer(n), group, this.getEmptyMap()));

		for (Knowledge k : group.getKnowledges()) {
			rows.add(new Row(this.getSpacer(n + 1), k, this.getComponentMap(k)));
			this.createCellStatus(k);
		}

		for (KnowledgeGroup g : group.getChildren()) {
			rows.addAll(this.getRowsFor(g, n + 1));
		}

		return rows;
	}

	private void createCellStatus(final Knowledge k) {
		Map<String, CellStatus> map = new HashMap<String, CellStatus>();
		for (Ability a : this.matrixOnFocus.getAbilities()) {
			map.put(a.getName(), new CellStatus(k, a, true));
		}
		this.cellStatusMap.put(k, map);
	}

	private String getSpacer(final int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < n ; i++) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return sb.toString();
	}

	/**
	 * Inner class for the matrix cells rows.
	 * @author julien
	 */
	public class Row {
		private Knowledge knowledge;
		private KnowledgeGroup knowledgeGroup;
		private String name;
		private Map<String, Boolean> values;


		public Row(final String spacer, final Knowledge knowledge, final Map<String, Boolean> values) {
			this.knowledge = knowledge;
			this.name = spacer + knowledge.getName();
			this.values = values;
		}

		public Row(final String spacer, final KnowledgeGroup knowledgeGroup, final Map<String, Boolean> values) {
			this.knowledgeGroup = knowledgeGroup;
			this.name = spacer + knowledgeGroup.getTitle();
			this.values = values;
		}

		/**
		 * @return the values
		 */
		public Map<String, Boolean> getValues() {
			return this.values;
		}

		/**
		 * @return the knowledge
		 */
		public Knowledge getKnowledge() {
			return this.knowledge;
		}

		/**
		 * @return the knowledgeGroup
		 */
		public KnowledgeGroup getKnowledgeGroup() {
			return this.knowledgeGroup;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}
	}

	/**
	 * data holder for cell status
	 * @author julien
	 */
	public class CellStatus {
		private Knowledge knowledge;
		private Ability ability;
		private boolean status;

		public CellStatus() {
		}

		public CellStatus(final Knowledge knowledge, final Ability ability, final boolean status) {
			super();
			this.knowledge = knowledge;
			this.ability = ability;
			this.status = status;
		}
		/**
		 * @return the knowledge
		 */
		public Knowledge getKnowledge() {
			return this.knowledge;
		}
		/**
		 * @return the ability
		 */
		public Ability getAbility() {
			return this.ability;
		}
		/**
		 * @return the status
		 */
		public boolean isStatus() {
			return this.status;
		}
		/**
		 * @param knowledge the knowledge to set
		 */
		public void setKnowledge(final Knowledge knowledge) {
			this.knowledge = knowledge;
		}
		/**
		 * @param ability the ability to set
		 */
		public void setAbility(final Ability ability) {
			this.ability = ability;
		}
		/**
		 * @param status the status to set
		 */
		public void setStatus(final boolean status) {
			this.status = status;
		}
	}

	public void resetBean() {
		this.matrixOnFocus = null;
		this.groupOnFocus = null;
		this.newKnowledgeGroupName = "";
		this.abilityName = "";
	}

	public Matrix getMatrixOnFocus() {
		return this.matrixOnFocus;
	}

	public void setMatrixOnFocus(final Matrix matrixOnFocus) {
		this.matrixOnFocus = matrixOnFocus;
	}

	public String getNewKnowledgeGroupName() {
		return this.newKnowledgeGroupName;
	}

	public void setNewKnowledgeGroupName(final String newKnowledgeGroupName) {
		this.newKnowledgeGroupName = newKnowledgeGroupName;
	}

	public KnowledgeGroup getGroupOnFocus() {
		return this.groupOnFocus;
	}

	public void setGroupOnFocus(final KnowledgeGroup groupOnFocus) {
		this.groupOnFocus = groupOnFocus;
	}

	public String getAbilityName() {
		return this.abilityName;
	}

	public void setAbilityName(final String abilityName) {
		this.abilityName = abilityName;
	}

	public void removeMatrix(final ActionEvent event) {
		MatrixManagerBean.getInstance(FacesContext.getCurrentInstance()).removeMatrix(this.matrixOnFocus);
	}

	public String getGradeName() {
		return this.gradeName;
	}

	public void setGradeName(final String gradeName) {
		this.gradeName = gradeName;
	}

	/**
	 * @return the matrixCellsRows
	 */
	public List<Row> getMatrixCellsRows() {
		return this.matrixCellsRows;
	}

	/**
	 * @return the matrixCellsDataTable
	 */
	public HtmlDataTable getMatrixCellsDataTable() {
		return this.matrixCellsDataTable;
	}

	/**
	 * @param matrixCellsRows the matrixCellsRows to set
	 */
	public void setMatrixCellsRows(final List<Row> matrixCellsRows) {
		this.matrixCellsRows = matrixCellsRows;
	}

	/**
	 * @param matrixCellsDataTable the matrixCellsDataTable to set
	 */
	public void setMatrixCellsDataTable(final HtmlDataTable matrixCellsDataTable) {
		this.matrixCellsDataTable = matrixCellsDataTable;
	}

	/**
	 * @return the cellStatusMap
	 */
	public Map<Knowledge, Map<String, CellStatus>> getCellStatusMap() {
		return this.cellStatusMap;
	}

	/**
	 * @param cellStatusMap the cellStatusMap to set
	 */
	public void setCellStatusMap(
			final Map<Knowledge, Map<String, CellStatus>> cellStatusMap) {
		this.cellStatusMap = cellStatusMap;
	}

	/**
	 * Getter.
	 * @return knowledgeOnFocus.
	 */
	public Knowledge getKnowledgeOnFocus() {
		return knowledgeOnFocus;
	}

	/**
	 * Setter.
	 * @param knowledgeOnFocus knowledgeOnFocus.
	 */
	public void setKnowledgeOnFocus(final Knowledge knowledgeOnFocus) {
		this.knowledgeOnFocus = knowledgeOnFocus;
	}

	/**
	 * Getter.
	 * @return ability on focus.
	 */
	public Ability getAbilityOnFocus() {
		return abilityOnFocus;
	}

	/**
	 * Setter.
	 * @param abilityOnFocus ability on focus.
	 */
	public void setAbilityOnFocus(final Ability abilityOnFocus) {
		this.abilityOnFocus = abilityOnFocus;
	}

	/**
	 * Getter.
	 * @return grade on focus.
	 */
	public Grade getGradeOnFocus() {
		return gradeOnFocus;
	}

	/**
	 * Setter.
	 * @param gradeOnFocus grade on focus.
	 */
	public void setGradeOnFocus(final Grade gradeOnFocus) {
		this.gradeOnFocus = gradeOnFocus;
	}
}
