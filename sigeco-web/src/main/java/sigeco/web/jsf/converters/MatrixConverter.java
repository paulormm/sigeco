package sigeco.web.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import sigeco.business.MatrixManager;
import sigeco.model.Matrix;

/**
 * Converter for the Matrix entity.
 * 
 * @author julien
 */
public class MatrixConverter implements Converter {

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
		MatrixManager manager = 
			(MatrixManager) context.getApplication().createValueBinding("#{matrixManager}").getValue(context);
		return manager.get(id);
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
			return "" + ((Matrix) arg2).getId();
		}
		return null;
	}

}
