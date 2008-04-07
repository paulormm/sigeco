package sigeco.web.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import sigeco.model.Ability;
import sigeco.utils.dao.Dao;

/**
 * Converter for the Ability entity.
 * 
 * @author julien
 */
public class AbilityConverter implements Converter {

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
		Dao dao = (Dao) context.getApplication().createValueBinding("#{tdao}").getValue(context);
		return dao.get(Ability.class, id);
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
			return "" + ((Ability) arg2).getId();
		}
		return null;
	}

}
