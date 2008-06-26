package sigeco.model.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents a Search Term.
 *
 * It consists of:
 * a pointer to its parent SearchExpression
 * a list of Search Elements
 *
 * A Search Term is satisfied if ALL of his SearchElements are satisfied.
 *
 * The elements list should only be operated thru the accessor methods:
 * addElement
 * addElements
 * removeElement
 * removeElements
 *
 * @author julien
 */
@Entity
@Table(name = "search_expressions_terms")
public class SearchTerm implements Iterable<SearchElement> {

	private long id;
	private SearchExpression searchExpression;
	private List<SearchElement> searchElements = new ArrayList<SearchElement>();
	
	
	public String namedTerm () {
		String stringExpression = "";
		for (SearchElement e : this.searchElements) {
			stringExpression =  e.namedElement();
			Iterator i = this.searchElements.listIterator();
			if (i.hasNext()) {
			stringExpression = stringExpression + " E ";
			}
		}
		return stringExpression;
	}
	
	/**
	 * Adds an Element to this Term.
	 * 
	 * @param element SearchElement
	 */
	public void addElement(final SearchElement element) {
		element.setSearchTerm(this);
		this.searchElements.add(element);
	}
	
	/**
	 * Adds the Elements to this Term.
	 * 
	 * @param elements List
	 */
	public void addElements(final List<SearchElement> elements) {
		for (SearchElement searchElement : elements) {
			this.addElement(searchElement);
		}
	}
	
	/**
	 * Removes this element from the term.
	 * 
	 * @param element SearchElement
	 */
	public void removeElement(final SearchElement element) {
		this.searchElements.remove(element);
	}
	
	/**
	 * Removes all elements from this term.
	 */
	public void removeElements() {
		this.searchElements.clear();
	}
	
	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}
	/**
	 * @return the searchExpression
	 */
	@ManyToOne(optional = false)
	public SearchExpression getSearchExpression() {
		return this.searchExpression;
	}
	/**
	 * @return the searchElements
	 */
	@OneToMany(mappedBy = "searchTerm", cascade = CascadeType.ALL)
	public List<SearchElement> getSearchElements() {
		return this.searchElements;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}
	/**
	 * @param searchExpression the searchExpression to set
	 */
	public void setSearchExpression(final SearchExpression searchExpression) {
		this.searchExpression = searchExpression;
	}
	
	//public SearchExpression getSearchExpression() {
	//	return this.searchExpression;
	//}
	/**
	 * @param searchElements the searchElements to set
	 */
	public void setSearchElements(final List<SearchElement> searchElements) {
		for (SearchElement searchElement : searchElements) {
			searchElement.setSearchTerm(this);
		}
		this.searchElements = searchElements;
	}

	/**
	 * Returns the number of elements
	 * @return int 
	 */
	@Transient
	public int getSize() {
		return this.searchElements.size();
	}
	
	/**
	 * returns an iterator over the elements
	 * 
	 * @return iterator
	 */
	public Iterator<SearchElement> iterator() {
		return this.searchElements.iterator();
	}


}
