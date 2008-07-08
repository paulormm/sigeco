package sigeco.model.search;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sigeco.model.Ability;
import sigeco.model.Grade;
import sigeco.model.Knowledge;
import sigeco.model.Matrix;

/**
 * Represents a Search Element.
 *
 * Consists of:
 * A Matrix
 * A Knowledge
 * An Ability
 * An Operator
 * A Grade
 *
 * It translates to:
 * In the given Matrix for the given Knowledge and Ability a User must have "Operator" Grade.
 *
 * Where Operator can be any of < <= = >= >
 *
 * @author julien
 */
@Entity
@Table(name = "search_expressions_terms_elements")
public class SearchElement {

	private long id;
	private SearchTerm searchTerm;

	private Matrix matrix;
	private Knowledge knowledge;
	private Ability ability;
	private Operator operator;
	private Grade grade;

	/**
	 * C'tor
	 * @param element SearchElement
	 */
	public SearchElement(final SearchElement element) {
		this.matrix = element.matrix;
		this.knowledge = element.knowledge;
		this.ability = element.ability;
		this.operator = element.operator;
		this.grade = element.grade;
		this.searchTerm = element.searchTerm;
	}

	
	/**
	 * C'tor
	 */
	public SearchElement() {
		super();
	}

	public String namedElement () {
		String stringExpression; 
		stringExpression = "(Matriz = " + this.getMatrix().getName() + " E " +
		                   " Área = " + this.getKnowledge().getName() + " E " +
		                   " Habilidade " + this.getAbility().getName() +							
		                   " " + this.getOperator().toString() +  
		                   " " + this.getGrade().getName() + ")";
		return stringExpression;
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	/**
	 * @return the searchTerm
	 */
	@ManyToOne(optional = false)
	public SearchTerm getSearchTerm() {
		return this.searchTerm;
	}

	/**
	 * @return the matrix
	 */
	@ManyToOne(optional = false, targetEntity = Matrix.class)
	public Matrix getMatrix() {
		return this.matrix;
	}
	/**
	 * @return the knowledge
	 */
	@ManyToOne(optional = false, targetEntity = Knowledge.class)
	public Knowledge getKnowledge() {
		return this.knowledge;
	}
	/**
	 * @return the ability
	 */
	@ManyToOne(optional = false, targetEntity = Ability.class)
	public Ability getAbility() {
		return this.ability;
	}
	/**
	 * @return the operator
	 */
	@Enumerated
	@Column(nullable = false)
	public Operator getOperator() {
		return this.operator;
	}
	/**
	 * @return the grade
	 */
	@ManyToOne(optional = false, targetEntity = Grade.class)
	public Grade getGrade() {
		return this.grade;
	}
	/**
	 * @param matrix the matrix to set
	 */
	public void setMatrix(final Matrix matrix) {
		this.matrix = matrix;
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
	 * @param operator the operator to set
	 */
	public void setOperator(final Operator operator) {
		this.operator = operator;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(final Grade grade) {
		this.grade = grade;
	}

	/**
	 * @param searchTerm the searchTerm to set
	 */
	public void setSearchTerm(final SearchTerm searchTerm) {
		this.searchTerm = searchTerm;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}



}
