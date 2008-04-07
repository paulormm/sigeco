package sigeco.utils.junit;

import java.util.List;

import sigeco.utils.IdentifiedEntity;

/**
 * Abstract Test Case for CRUD tests.
 *
 * @param <E> The IdentifiedEntity that is the object of this test.
 *
 * @author julien
 */
public abstract class AbstractCRUDTestCase <E extends IdentifiedEntity> extends SpringTestCase {


	/**
	 * C'tor
	 * @param beanFactoryFile that should be classpath-searched.
	 */
	public AbstractCRUDTestCase(final String beanFactoryFile) {
		super(beanFactoryFile);
	}

	/**
	 * Returns a list with entities that should be saved correctly.
	 * @return List
	 */
	protected abstract List<E> getValidEntities();

	/**
	 * Returns a list with entities that should not be saved correctly.
	 * @return List
	 */
	protected abstract List<E> getInvalidEntities();

	/**
	 * Returns an entity that should be saved prior to update.
	 * @return Object
	 */
	protected abstract E getEntityToBeUpdated();

	/**
	 * Updates this IdentifiedEntity
	 * @param object Object
	 */
	protected abstract void updateEntity(final E object);

	/**
	 * Tests that all valid entities are correctly saved.
	 */
	public final void testCreate() {
		List<E> entities = this.getValidEntities();
		this.saveEntities(entities);
	}

	/**
	 * Tests that all invalid entities are not saved to the database.
	 * 
	 * All valid entities will be saved first in order to create a scenario
	 * for invalid entities disrespecting unique fields.
	 */
	public final void testCreateInvalid() {
		this.saveEntities(this.getValidEntities());
		
		List<E> invalidEntities = this.getInvalidEntities();
		for (E e : invalidEntities) {
			try {
				this.getSession().save(e);
				this.flushSession();
				fail("An invalid entity was allowed into the database!");
			} catch (Exception ex) {
				System.out.println("Expected: " + ex);
			}
		}
		
	}
	
	/**
	 * Tests that all valid saved entities are correctly fetched from database and are equals
	 * to their related entity.
	 */
	@SuppressWarnings("unchecked")
	public final void testRead() {
		List<E> entities = this.getValidEntities();
		this.saveEntities(entities);

		this.getSession().clear();

		for (E e : entities) {
			this.matchEntity(e);
		}
	}

	/**
	 * Tests than an entity is correctly updated.
	 */
	@SuppressWarnings("unchecked")
	public final void testUpdate() {

		E e = this.getEntityToBeUpdated();
		try {
			this.getSession().save(e);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception while saving the entity that would be updated!");
		}

		this.flushSession();

		this.updateEntity(e);

		try {
			this.getSession().saveOrUpdate(e);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception while updating the entity that would be updated!");
		}

		this.flushSession();
		this.getSession().clear();

		this.matchEntity(e);
	}

	/**
	 * Tests that saved entities can correctly be deleted.
	 */
	public final void testDelete() {
		List<E> entities = this.getValidEntities();
		this.saveEntities(entities);
		this.getSession().clear();
		
		this.deleteEntities(entities);
		this.getSession().clear();
		
		for (E e : entities) {
			Object o = this.getSession().get(e.getClass(), e.getId());
			assertNull("Found an object that should have been deleted!", o);
		}
	}
	
	/**
	 * Matched this entity with it's related entity on the data base.
	 * @param e E
	 */
	@SuppressWarnings("unchecked")
	private void matchEntity(final E e) {
		E bd = (E) this.getSession().get(e.getClass(), e.getId());
		assertEquals("Fetched entity doesn't match!", e, bd);
	}

	/**
	 * Flushes the session failing if an exception occurs.
	 */
	public void flushSession() {
		try {
			this.getSession().flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception while flushing the session!");
		}
	}

	/**
	 * Saves all the entities on the list failing if exceptions are thrown.
	 * @param entities List
	 */
	private void saveEntities(final List<E> entities) {
		for (E e : entities) {
			try {
				this.getSession().save(e);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Exception while saving an entity!");
			}
		}

		this.flushSession();
	}
	
	/**
	 * Deletes all the entities on the list failing if exceptions are thrown.
	 * @param entities List
	 */
	private void deleteEntities(final List<E> entities) {
		for (E e : entities) {
			try {
				this.getSession().delete(e);
			} catch (Exception ex) {
				ex.printStackTrace();
				fail("Exception while deleting an entity!");
			}
		}
		this.flushSession();
	}
}
