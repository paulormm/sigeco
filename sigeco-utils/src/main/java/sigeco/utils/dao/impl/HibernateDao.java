package sigeco.utils.dao.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import sigeco.utils.dao.Dao;
import sigeco.utils.dao.SessionOperation;

/**
 * Implementation of the {@link Dao} interface for specific use within
 * a Hibernate Persistence Context based on the Spring Framework support
 * class {@link HibernateDaoSupport}.
 *
 * @author julien
 */
public class HibernateDao extends HibernateDaoSupport implements Dao {

	/**
	 * Saves (or updates) the given objects.
	 * @param objects The objects to be saved
	 */
	public void save(final Object... objects) {
		this.save(Arrays.asList(objects));
	}

	/**
	 * Saves (or updates) all the objects in the given list.
	 * @param objects The List of objects to be saved
	 */
	public void save(final List<Object> objects) {
		this.getHibernateTemplate().saveOrUpdateAll(objects);
		this.getHibernateTemplate().flush();
	}

	/**
	 * Returns the object of the given class with the given id or null.
	 *
	 * @param <T> The type of the object
	 * @param clazz The class of the object
	 * @param id The id of the object
	 * @return The object of the desired type with the given id or null
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(final Class<T> clazz, final Serializable id) {
		return (T) this.getHibernateTemplate().get(clazz, id);
	}

	/**
	 * Returns a List of all the objects of the given type
	 * found in the database.
	 *
	 * If none were found an empty list is returned.
	 *
	 * @param <T> The type of the objects
	 * @param clazz The class of the objects
	 * @return The list with all the objects retrieved
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(final Class<T> clazz) {
		return this.getHibernateTemplate().loadAll(clazz);
	}

	/**
	 * Returns a List of the objects of the given type
	 * found in the database pagined from start with at
	 * most amount entries.
	 *
	 * If none were found an empty list is returned.
	 *
	 * @param <T> The type of the objects
	 * @param clazz The class of the objects
	 * @param start The index from which to start
	 * @param amount The maximum amount of objects to be retrieved
	 * @return The list with the objects retrieved
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(final Class<T> clazz, final int start, final int amount) {
		DetachedCriteria crit = DetachedCriteria.forClass(clazz);
		return this.getHibernateTemplate().findByCriteria(crit, start, amount);
	}

	/**
	 * Removes all the given objects.
	 *
	 * @param objects The objects to be removed
	 */
	public void remove(final Object... objects) {
		for (Object object : objects) {
			this.getHibernateTemplate().delete(
				this.getHibernateTemplate().merge(object)
			);
		}
	}

	/**
	 * Removes all the objects in the List.
	 *
	 * @param objects The List of objects to be removed
	 */
	public void remove(final List<Object> objects) {
		for (Object object : objects) {
			this.getHibernateTemplate().delete(
				this.getHibernateTemplate().merge(object)
			);
		}
	}

	/**
	 * Returns the list of objects returned by the given criteria.
	 *
	 * @param <T> The Type
	 * @param clazz The class
	 * @param criteria DetachedCriteria
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> get(final Class<T> clazz, final DetachedCriteria criteria) {
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * Executes the given Named Query assuming it returns instances of the given class.
	 *
	 * @param <T> The Type
	 * @param queryName The name of the Named Query
	 * @param clazz The Class
	 * @param parameters Optional parameters as per the Named Query
	 *
	 * @return list
	 *
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> query(final String queryName, final Class<T> clazz, final Object... parameters) {
		return this.getHibernateTemplate().findByNamedQuery(queryName, parameters);
	}

	/**
	 * Executes a Session Operation within the current session
	 * @param operation SessionOperation 
	 */
	public void execute(final SessionOperation operation) {
		operation.execute(this.getSession());
	}

}
