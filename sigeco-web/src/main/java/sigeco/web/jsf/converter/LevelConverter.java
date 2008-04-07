package sigeco.web.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import sigeco.model.Level;

/**
 * Converter for the Level enum.
 *
 * @author julien
 */
public class LevelConverter implements Converter {

	/**
	 * Converts from String to Object
	 * 
	 * @param context {@link FacesContext}
	 * @param component {@link UIComponent}
	 * @param newValue String
	 * 
	 * @return Object (Level instance)
	 */
	public Object getAsObject(
			final FacesContext context, 
			final UIComponent component, 
			final String newValue) {
		return Level.valueOf(newValue);
	}

	/**
	 * Converts from Object to String
	 * 
	 * @param context {@link FacesContext}
	 * @param component {@link UIComponent}
	 * @param value Object (Level instance)
	 * 
	 * @return String
	 */
	public String getAsString(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) {
		if (value == null) {
			return Level.Level0.toString();
		}
		return ((Level) value).toString();
	}
}
