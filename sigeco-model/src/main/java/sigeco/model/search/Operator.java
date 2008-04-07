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

}
