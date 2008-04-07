package sigeco.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * KnowledgeGroup class.
 *
 * A KnowledgeGroup is an entity that has a title, may have nested (children)
 * KnowledgeGroups and may have Knowledges.
 *
 * Its purpose is to group Knowledges in categories and sub-categories.
 *
 * @author julien
 *
 */
@Entity
@Table(name = "knowledge_groups")
public class KnowledgeGroup implements CellMappeable {

    private Matrix matrix;
    private KnowledgeGroup parent;
    private List<KnowledgeGroup> children = new ArrayList<KnowledgeGroup>();
    private List<Knowledge> knowledges = new ArrayList<Knowledge>();
    private long id;
    private String title;
    private boolean active = false;

    /**
     * C'tor
     */
    public KnowledgeGroup() {
    }

    /**
     * C'tor
     * @param matrix The owning Matrix
     * @param title The group title
     */
    public KnowledgeGroup(final Matrix matrix, final String title) {
    	this.matrix = matrix;
    	this.title = title;
    }

	/**
	 * C'tor
	 * @param parent The owning Group
	 * @param title The group title
	 */
    public KnowledgeGroup(final KnowledgeGroup parent, final String title) {
    	this.parent = parent;
    	this.title = title;
    }

    /**
     * Returns the Matrix
     * @return Matrix
     */
    @ManyToOne(optional = false)
    public Matrix getMatrix() {
            return this.matrix;
    }

    /**
     * Sets the Matrix
     * @param matrix Matrix
     */
    public void setMatrix(final Matrix matrix) {
            this.matrix = matrix;
    }

    /**
     * Returns the parent
     * @return KnowledgeGroup
     */
    @ManyToOne
    public KnowledgeGroup getParent() {
            return this.parent;
    }

    /**
     * Sets the parent
     * @param parent KnowledgeGroup
     */
    public void setParent(final KnowledgeGroup parent) {
            this.parent = parent;
    }

    /**
     * Returns the children
     * @return List
     */
    @OneToMany(targetEntity = KnowledgeGroup.class, mappedBy = "parent", cascade = { CascadeType.ALL })
    public List<KnowledgeGroup> getChildren() {
            return this.children;
    }

    /**
     * Sets the children
     * @param children List
     */
    public void setChildren(final List<KnowledgeGroup> children) {
            this.children = children;
    }

    /**
     * Gets the Knowledges
     * @return List
     */
    @OneToMany(targetEntity = Knowledge.class, mappedBy = "knowledgeGroup", cascade = { CascadeType.ALL })
    public List<Knowledge> getKnowledges() {
            return this.knowledges;
    } 

    /**
     * Sets the Knowledges 
     * @param knowledges List
     */
    public void setKnowledges(final List<Knowledge> knowledges) {
    	for (Knowledge knowledge : knowledges) {
			knowledge.setKnowledgeGroup(this);
		}
        this.knowledges = knowledges;
    }

    /**
     * Gets the id.
     * @return long
     */
    @Id
    @GeneratedValue
    public long getId() {
    	return this.id;
    }

    /**
     * Sets the id
     * @param id long
     */
    public void setId(final long id) {
            this.id = id;
    }

    /**
     * Sets the title
     * @param name String
     */
    public void setTitle(final String name) {
        this.title = name;
    }

    /**
     * Gets the title
     * @return String
     */
    @Column(nullable = false)
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the active flag.
     * @return boolean
     */
    @Transient
	public boolean isActive() {
		return this.active;
	}
    
    /**
     * Returns if this is the first group.
     * @return boolean
     */
    @Transient
    public boolean isSuperGroup() {
    	return this.parent == null;
    }

    /**
     * Sets the active flag.
     * @param active booloean
     */
	public void setActive(final boolean active) {
		this.active = active;
	}
	
	/**
	 * String representation of this object.
	 * @return String
	 */
	@Override
	public String toString() {
		return this.title;
	}

	/**
	 * Gets the mapped cells.
	 * @return Map
	 */
	@Transient
	public Map<String, Cell> getMappedCells() {
		return new HashMap<String, Cell>();
	}
}
