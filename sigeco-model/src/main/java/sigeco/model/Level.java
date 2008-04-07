package sigeco.model;

/**
 * Enumeration for Cell Value levels.
 *
 * @author julien
 */
public enum Level {
	Level0("levels.level0"),
	Level1("levels.level1"),
	Level2("levels.level2"),
	Level3("levels.level3"),
	Level4("levels.level4"),
	Level5("levels.level5");

	private String bundleKey;

	/**
	 * C'tor
	 * @param bundleKey The bundle key for viewing the enum value
	 */
	private Level(final String bundleKey) {
		this.bundleKey = bundleKey;
	}

	/**
	 * Gets the bundle key for this enum
	 * @return String
	 */
	public String getBundleKey() {
		return this.bundleKey;
	}
}
