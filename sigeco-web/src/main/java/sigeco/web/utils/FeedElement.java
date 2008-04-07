package sigeco.web.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sigeco.model.CellValue;
import sigeco.model.Grade;
import sigeco.model.Knowledge;
import sigeco.model.KnowledgeGroup;

/**
 * Represents a feed element
 *
 * @author yugo
 */
public class FeedElement {
	private static final Long NULL_INDEX = new Long(-1);
	private Knowledge knowledge;
	private KnowledgeGroup knowledgeGroup;

	private List<CellValue> cellValues = new ArrayList<CellValue>();
	private Map<Long, List<CellValue>> cellValueMappings;
	private Map<CellValue, Long> syncMappings;

	/**
	 * C'tor
	 * @param knowledge Knowledge
	 * @param cellValues List
	 */
	public FeedElement(final Knowledge knowledge, final List<CellValue> cellValues) {
		this.knowledge = knowledge;
		this.cellValues = cellValues;
	}

	/**
	 * C'tor
	 * @param knowledgeGroup KnowledgeGroup
	 */
	public FeedElement(final KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
	}

	/**
	 * Returns the represented object's name
	 * @return String
	 */
	public String getName() {
		if (this.knowledge == null) {
			return this.knowledgeGroup.getTitle();
		} else {
			return this.knowledge.getName();
		}
	}

	/**
	 * gets the knowledge
	 * @return Knowledge
	 */
	public Knowledge getKnowledge() {
		return this.knowledge;
	}

	/**
	 * Sets the knowledge
	 * @param knowledge Knowledge
	 */
	public void setKnowledge(final Knowledge knowledge) {
		this.knowledge = knowledge;
	}

	/**
	 * gets the knowledge group
	 * @return KnowledgeGroup
	 */
	public KnowledgeGroup getKnowledgeGroup() {
		return this.knowledgeGroup;
	}

	/**
	 * Sets the knowledge group
	 * @param knowledgeGroup KnowledgeGroup
	 */
	public void setKnowledgeGroup(final KnowledgeGroup knowledgeGroup) {
		this.knowledgeGroup = knowledgeGroup;
	}

	/**
	 * Returns the rendered
	 * @return boolean
	 */
	public boolean isRendered() {
		return (this.knowledgeGroup == null);
	}

	/**
	 * Returns the cells
	 * @return List
	 */
	public List<CellValue> getCells0() {
		return this.cellValueMappings.get(0);
	}

	/**
	 * Sets the grades
	 * @param grades List
	 */
	public void setGrades(final List<Grade> grades) {
		this.cellValueMappings = new HashMap<Long, List<CellValue>>();
		this.syncMappings = new HashMap<CellValue, Long>();
		Collections.sort(grades);
		for (Grade grade : grades) {
			final List<CellValue> gradeBasedResult = new ArrayList<CellValue>();
			for (CellValue cellValue : this.cellValues) {
				if (cellValue.getGrade() != null && cellValue.getGrade().equals(grade)) {
					gradeBasedResult.add(cellValue);
					this.syncMappings.put(cellValue, new Long(grade.getGradeOrder()));
				}
			}
			this.cellValueMappings.put(new Long(grade.getGradeOrder()), gradeBasedResult);
		}

		List<CellValue> nonGraded = new ArrayList<CellValue>();
		for (CellValue cellValue : this.cellValues) {
			if (cellValue.getGrade() == null) {
				nonGraded.add(cellValue);
				this.syncMappings.put(cellValue, NULL_INDEX);
			}
		}

		this.cellValueMappings.put(NULL_INDEX, nonGraded);
	}

	/**
	 * Gets the CellValues
	 * @return Map
	 */
	public Map<Long, List<CellValue>> getCellValues() {
		return this.cellValueMappings;
	}

	/**
	 * syncs a cellvalue
	 * @param cellValue CellValue
	 */
	public void synchronize(final CellValue cellValue) {
		if (this.syncMappings.containsKey(cellValue)) {
			this.cellValueMappings.get(this.syncMappings.get(cellValue)).remove(cellValue);
			if (cellValue.getGrade() == null) {
				this.cellValueMappings.get(NULL_INDEX).add(cellValue);
				this.syncMappings.put(cellValue, NULL_INDEX);
			} else {
				this.cellValueMappings.get(new Long(cellValue.getGrade().getGradeOrder())).add(cellValue);
				this.syncMappings.put(cellValue, new Long(cellValue.getGrade().getGradeOrder()));
			}
		}
	}
}
