package sigeco.utils.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Interface that defines CRUD Data Access Object methods.
 * 
 * This interface is intended to be independent from the type 
 * of persistence choosen.
 * 
 * @author julien
 */
public interface Dao {

	/**
	 * Saves (or updates) the given objects.
	 * @param objects The objects to be saved
	 */
	void save(final Object... objects);
	
	/**
	 * Saves (or updates) all the objects in the given list.
	 * @param objects The List of objects to be saved
	 */
	void save(final List<Object> objects);
	
	/**
	 * Returns the object of the given class with the given id or null.
	 * 
	 * @param <T> The type of the object
	 * @param clazz The class of the object
	 * @param id The id of the object
	 * @return The object of the desired type with the given id or null
	 */
	<T> T get(final Class<T> clazz, final Serializable id);
	
	/**
	 * Returns the list of objects of the given class returned by the given criteria.
	 * 
	 * @param <T> The type 
	 * @param clazz The Class
	 * @param criteria DetachedCriteria
	 * @return List 
	 */
	<T> List<T> get(final Class<T> clazz, final DetachedCriteria criteria);
	
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
	<T> List<T> list(final Class<T> clazz);
	
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
	<T> List<T> list(final Class<T> clazz, final int start, final int amount);
	
	/**
	 * Removes all the given objects.
	 * 
	 * @param objects The objects to be removed
	 */
	void remove(final Object... objects);
	
	/**
	 * Removes all the objects in the List.
	 * 
	 * @param objects The List of objects to be removed
	 */
	void remove(final List<Object> objects);
	
	/**
	 * Executes the given Named Query assuming it returns objects of type clazz.
	 * 
	 * @param <T> The Type
	 * @param queryName The Named Query name
	 * @param clazz The Class
	 * @param parameters Optional parameters, should be provided in the same order as they're used in the Named Query
	 * @return List
	 */
	<T> List<T> query(String queryName, final Class<T> clazz, Object... parameters);
	
	/**
	 * Executes a session operation
	 * @param operation SessionOperation
	 */
	void execute(final SessionOperation operation);
}
