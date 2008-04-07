package sigeco.web.jsf.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import sigeco.business.MatrixManager;
import sigeco.business.search.SearchManager;
import sigeco.business.security.UserLocator;
import sigeco.model.Ability;
import sigeco.model.Grade;
import sigeco.model.Knowledge;
import sigeco.model.KnowledgeGroup;
import sigeco.model.Matrix;
import sigeco.model.search.Operator;
import sigeco.model.search.SearchElement;
import sigeco.model.search.SearchExpression;
import sigeco.model.search.SearchTerm;
import sigeco.utils.BundledEntity;
import sigeco.utils.NamedEntity;

/**
 * Bean for creating a new Search.
 *
 * @author julien
 */
public class NewSearchBean {

	private SearchExpression expression;
	private SearchTerm term;
	private SearchElement element;
	private boolean editingTerm = false;
	private boolean someMatrixSelected = false;
	private MatrixManager matrixManager;
	private SearchManager searchManager;
	private SearchElement elementToRemove;
	private SearchTerm termToRemove;

	/**
	 * Starts a new search.
	 *
	 * Cleans up the bean's state.
	 *
	 * @return String
	 */
	public String newSearch() {
		this.expression = new SearchExpression();
		this.term = new SearchTerm();
		this.element = new SearchElement();
		this.editingTerm = false;
		this.someMatrixSelected = false;
		return "new search";
	}

	/**
	 * Cancels the current search.
	 * @return String
	 */
	public String cancel() {
		return "cancel search";
	}

	/**
	 * Saves the current search.
	 * @return String
	 */
	public String save() {

		if (this.expression != null) {
			if (this.expression.getSearchTerms().size() > 0) {
				try {
					this.expression.setUser(UserLocator.getUser());
					this.searchManager.saveExpression(this.expression);
				} catch (Exception ex) {
					//meu deus que medo desse catch exception... mas tou com sono
					FacesMessage fm = new FacesMessage();
					String msg = this.resolve("search.new.name.in.use");
					fm.setDetail(msg);
					fm.setSummary(msg);
					fm.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage("name", fm);
					return "search not saved";
				}
			} else {
				return "search not saved";
			}
		}

		return "search saved";
	}

	/**
	 * @return the expression
	 */
	public SearchExpression getExpression() {
		return this.expression;
	}

	/**
	 * Starts the filling of a new SearchTerm
	 * @return String
	 */
	public String newTerm() {
		this.term = new SearchTerm();
		this.element = new SearchElement();
		this.editingTerm = true;
		this.someMatrixSelected = false;
		return "new term";
	}

	/**
	 * Cancel the creation of a term
	 * @return String
	 */
	public String cancelTerm() {
		this.term = new SearchTerm();
		this.element = new SearchElement();
		this.editingTerm = false;
		this.someMatrixSelected = false;
		return "term canceled";
	}

	/**
	 * Saves a SearchElement.
	 * @return String
	 */
	public String saveElement() {
		this.term.addElement(this.element);
		this.element = new SearchElement(this.element);
		return "element saved";
	}

	/**
	 * Saves a SearchTerm.
	 * @return String
	 */
	public String saveTerm() {
		this.expression.addTerm(this.term);

		this.editingTerm = false;
		this.someMatrixSelected = false;

		this.term = new SearchTerm();
		return "term saved";
	}

	/**
	 * Fired when a Matrix in the Element Form is selected.
	 * @param event ValueChangeEvent
	 */
	public void matrixSelected(final ValueChangeEvent event) {
		this.someMatrixSelected = true;
		this.element = new SearchElement();
	    this.element.setMatrix((Matrix) event.getNewValue());

//	    FacesContext.getCurrentInstance().renderResponse();
	}

	/**
	 * Returns the list of all matrices wrapped as SelectItem.
	 * @return the matrices
	 */
	public List<SelectItem> getMatrices() {
		List<Matrix> all = this.matrixManager.getAll();
		return this.wrap(all);
	}

	/**
	 * Wraps a Matrix list in a SelectItem list.
	 * @param originalList List
	 * @return List
	 */
    private List<SelectItem> wrap(final List< ? extends NamedEntity> originalList) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (Iterator< ? extends NamedEntity> iter = originalList.iterator(); iter.hasNext();) {
            NamedEntity next = iter.next();
            SelectItem item = new SelectItem(next, next.getName());
            items.add(item);
        }
        return items;
    }

    /**
     * Returns the Knowledges of a selected Matrix
     * @return List
     */
    public List<SelectItem> getElementKnowledges() {
    	List<Knowledge> knowledges = new ArrayList<Knowledge>();
    	if (this.element.getMatrix() != null) {
    		knowledges = this.getKnowledges(this.element.getMatrix().getKnowledgeGroups());
    	}
    	return this.wrap(knowledges);
    }

    /**
     * Returns the Operators list
     * @return List
     */
    public List<SelectItem> getElementOperators() {
    	return this.wrapBundled(Arrays.asList(Operator.values()));
    }

    /**
     * Wraps a list of BundledEntities into a List of SelectItem with labels values 
     * already retrieved from the bundled messages. 
     * 
     * @param list List
     * @return List
     */
    private List<SelectItem> wrapBundled(final List< ? extends BundledEntity> list) {
        List<SelectItem> items = new ArrayList<SelectItem>();
        for (Iterator< ? extends BundledEntity> iter = list.iterator(); iter.hasNext();) {
        	BundledEntity next = iter.next();
            SelectItem item = new SelectItem(next, this.resolve(next.getBundleKey()));
            items.add(item);
        }
        return items;
	}

    /**
     * Return the List of the selected Matrix' Grades wrapped into a SelectItem list.
     * @return List
     */
    public List<SelectItem> getElementGrades() {
    	List<Grade> grades = new ArrayList<Grade>();
    	if (this.element.getMatrix() != null) {
    		grades = this.element.getMatrix().getGrades();
    	}
    	return this.wrap(grades);
    }


    /**
     * Resolves the given bundle key.
     * @param bundleKey Stirng
     * @return String
     */
	private String resolve(final String bundleKey) {
		return ResourceBundle.getBundle("messages").getString(bundleKey);
	}

	/**
	 * Get the Abilities of the selected Matrix wrapped in a SelectItem list
	 * @return List
	 */
	public List<SelectItem> getElementAbilities() {
    	List<Ability> abilities = new ArrayList<Ability>();
    	if (this.element.getMatrix() != null) {
    		abilities = this.element.getMatrix().getAbilities();
    	}
    	return this.wrap(abilities);
    }

	/**
	 * All Knowledges from the given Groups.
	 * @param knowledgeGroups List
	 * @return List
	 */
	private List<Knowledge> getKnowledges(final List<KnowledgeGroup> knowledgeGroups) {
		List<Knowledge> knowledges = new ArrayList<Knowledge>();
		for (KnowledgeGroup group : knowledgeGroups) {
			knowledges.addAll(group.getKnowledges());
			knowledges.addAll(this.getKnowledges(group.getChildren()));
		}
		return knowledges;
	}


	/**
	 * Action called when an Element is removed.
	 * @return String
	 */
	public String removeElement() {

		if (this.elementToRemove != null) {
			this.term.removeElement(this.elementToRemove);
			this.elementToRemove = null;
		}

		return "element removed";
	}


	/**
	 * Action called when a Term is removed.
	 * @return String
	 */
	public String removeTerm() {

		if (this.termToRemove != null) {
			this.expression.removeElement(this.termToRemove);
			this.termToRemove = null;
		}

		return "term removed";
	}

	/**
	 * @return the term
	 */
	public SearchTerm getTerm() {
		return this.term;
	}

	/**
	 * @return the editingTerm
	 */
	public boolean isEditingTerm() {
		return this.editingTerm;
	}

	/**
	 * @return the element
	 */
	public SearchElement getElement() {
		return this.element;
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
	 * @return the someMatrixSelected
	 */
	public boolean isSomeMatrixSelected() {
		return this.someMatrixSelected;
	}

	/**
	 * @return the elementToRemove
	 */
	public SearchElement getElementToRemove() {
		return this.elementToRemove;
	}

	/**
	 * @param elementToRemove the elementToRemove to set
	 */
	public void setElementToRemove(final SearchElement elementToRemove) {
		this.elementToRemove = elementToRemove;
	}

	/**
	 * @return the termToRemove
	 */
	public SearchTerm getTermToRemove() {
		return this.termToRemove;
	}

	/**
	 * @param termToRemove the termToRemove to set
	 */
	public void setTermToRemove(final SearchTerm termToRemove) {
		this.termToRemove = termToRemove;
	}

	/**
	 * @return the searchManager
	 */
	public SearchManager getSearchManager() {
		return this.searchManager;
	}

	/**
	 * @param searchManager the searchManager to set
	 */
	public void setSearchManager(final SearchManager searchManager) {
		this.searchManager = searchManager;
	}

}



