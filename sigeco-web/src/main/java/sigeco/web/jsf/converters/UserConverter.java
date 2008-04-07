package sigeco.web.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import sigeco.model.User;
import sigeco.utils.dao.Dao;

/**
 * JSF Converter for the User entity
 * @author julien
 */
public class UserConverter implements Converter {

	/**
	 * Converts back from request parameter.
	 * 
	 * @param context FacesContext
	 * @param arg1 UIComponent
	 * @param arg2 The value from request
	 * 
	 * @return Object the represented entity
	 */
	public Object getAsObject(
			final FacesContext context,
			final UIComponent arg1,
			final String arg2) {
		long id = Long.parseLong(arg2);
		if (id > 0) {
			Dao dao = (Dao) context.getApplication().createValueBinding("#{tdao}").getValue(context);
			return dao.get(User.class, id);
		}
		return null;
	}

	/**
	 * Converts from the object to a String that identifies it on the request
	 * 
	 * @param arg0 FacesContext
	 * @param arg1 UIComponent
	 * @param arg2 The entity
	 * 
	 * @return a String that identifies the entity
	 */
	public String getAsString(
			final FacesContext arg0,
			final UIComponent arg1,
			final Object arg2) {
		if (arg2 != null) {
			return "" + ((User) arg2).getId();
		}
		return null;
	}

}
