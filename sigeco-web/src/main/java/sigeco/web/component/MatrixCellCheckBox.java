package sigeco.web.component;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import sigeco.model.Ability;
import sigeco.model.Knowledge;

/**
 * Checkbox that knows to which Knowledge and Ability it is related.
 * 
 * @author julien
 */
public class MatrixCellCheckBox extends HtmlSelectBooleanCheckbox {

	private Knowledge knowledge;
	private Ability ability;


	/**
	 * C'tor
	 * @param knowledge Knowledge
	 * @param ability Ability
	 */
	public MatrixCellCheckBox(final Knowledge knowledge, final Ability ability) {
		super();
		this.knowledge = knowledge;
		this.ability = ability;
	}
	
	/**
	 * C'tor
	 */
	public MatrixCellCheckBox() {
		super();
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

}
