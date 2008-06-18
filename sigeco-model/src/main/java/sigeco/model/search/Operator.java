package sigeco.model.search;

import sigeco.utils.BundledEntity;

/**
 * Enum for the search Operators
 * 
 * @author julien
 */
public enum Operator implements BundledEntity {

	GREATER("operator.greater"),
	GREATER_OR_EQUALS("operator.greaterEquals"),
	EQUALS("operator.equals"),
	LESSER_OR_EQUALS("operator.lesserEquals"),
	LESSER("operator.lesser");
	
	private String bundleKey;
	
	/**
	 * C'tor
	 * @param bundleKey The bundleKey for i18n effects
	 */
	private Operator(final String bundleKey) {
		this.bundleKey = bundleKey;
	}

	/**
	 * @return the bundleKey
	 */
	public String getBundleKey() {
		return bundleKey;
	}
	
	public String toString(){
		if (bundleKey.compareTo("operator.greater")==0)
			return ">";
		else if (bundleKey.compareTo("operator.greaterEquals")==0)
			return ">=";
		else if (bundleKey.compareTo("operator.equals")==0)
			return "=";	
		else if (bundleKey.compareTo("operator.lesserEquals")==0)
			return "<=";
		else if (bundleKey.compareTo("operator.lesser")==0)
			return "<";
		else
		return "#";
	}

}
