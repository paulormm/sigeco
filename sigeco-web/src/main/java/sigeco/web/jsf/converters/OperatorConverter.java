package sigeco.web.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import sigeco.model.search.Operator;

/**
 * Converter for the Operator enum.
 * 
 * @author julien
 */
public class OperatorConverter implements Converter {

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
		return Operator.valueOf(arg2);
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
			return ((Operator) arg2).name();
		}
		return null;
	}

}
