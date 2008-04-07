package sigeco.model.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import sigeco.model.User;
import sigeco.utils.IdentifiedEntity;

/**
 * Represents a Search Expression.
 *
 * A Search Expression consists of a system-wide unique name, an owning User, a flag indicating wether this
 * expression should be publicated and a list of Search Terms.
 *
 * The list of Terms should only be manipulated thru the accessor methods:
 * addTerm
 * addTerms
 * removeTerm
 * removeTerms
 *
 * @author julien
 */
@Entity
@Table(name = "search_expressions", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries(
		{
			@NamedQuery(
				name = "expressionsFromUser", 
				query = "from SearchExpression e where e.user = ?"),
			@NamedQuery(
				name = "publicExpressions",
				query = "from SearchExpression e where e.publish = true")
		})
public class SearchExpression implements IdentifiedEntity, Iterable<SearchTerm> {

	private long id;
	private User user;
	private String name;
	private boolean publish;
	private List<SearchTerm> searchTerms = new ArrayList<SearchTerm>();

	/**
	 * Adds a Term to this Expression.
	 *
	 * @param term SearchTerm
	 */
	public void addTerm(final SearchTerm term) {
		term.setSearchExpression(this);
		this.searchTerms.add(term);
	}

	/**
	 * Adds the Terms to this Expression.
	 *
	 * @param terms List
	 */
	public void addTerms(final List<SearchTerm> terms) {
		for (SearchTerm searchTerm : terms) {
			this.addTerm(searchTerm);
		}
	}

	/**
	 * Removes this term from the expression.
	 *
	 * @param term SearchTerm
	 */
	public void removeElement(final SearchTerm term) {
		this.searchTerms.remove(term);
	}

	/**
	 * Removes all terms from this expression.
	 */
	public void removeTerms() {
		this.searchTerms.clear();
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
	 * @return the user
	 */
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}
	/**
	 * @return the nome
	 */
	@Column(nullable = false)
	public String getName() {
		return this.name;
	}
	/**
	 * @return the publish
	 */
	@Column(nullable = false)
	public boolean isPublish() {
		return this.publish;
	}
	/**
	 * @return the searchTerms
	 */
	@OneToMany(mappedBy = "searchExpression", cascade = CascadeType.ALL)
	public List<SearchTerm> getSearchTerms() {
		return this.searchTerms;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(final long id) {
		this.id = id;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(final User user) {
		this.user = user;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @param publish the publish to set
	 */
	public void setPublish(final boolean publish) {
		this.publish = publish;
	}
	/**
	 * @param searchTerms the searchTerms to set
	 */
	public void setSearchTerms(final List<SearchTerm> searchTerms) {
		for (SearchTerm searchTerm : searchTerms) {
			searchTerm.setSearchExpression(this);
		}
		this.searchTerms = searchTerms;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 * @return int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/** (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @param obj Object
	 * @return boolean
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final SearchExpression other = (SearchExpression) obj;
		if (this.name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns an iterator over the terms
	 * 
	 * @return Iterator
	 */
	public Iterator<SearchTerm> iterator() {
		return this.searchTerms.iterator();
	}



}
