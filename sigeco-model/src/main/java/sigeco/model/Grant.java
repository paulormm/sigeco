/**
 * 
 */
package sigeco.model;

import java.util.ArrayList;
import java.util.List;

/**
 *	Additional grants for a user.
 */
public enum Grant {
	FILL_OWN(1, "global.security.configuration.fillOwn"),
	FILL_OTHERS(2, "global.security.configuration.fillOthers");
	
	private int grant;
	private String bundleKey;


	/**
	 * C'tor
	 * @param grant int
	 * @param bundleKey String
	 */
	private Grant(final int grant, final String bundleKey) {
		this.grant = grant;
		this.bundleKey = bundleKey;
	}

	/**
	 * Returns true if the given user has this grant.
	 *
	 * @param targetGrant int
	 * @return boolean
	 */
	public boolean isGrant(final int targetGrant) {
		return (targetGrant & this.grant) == this.grant;
	}

	/**
	 * Combines the given grants.
	 * @param grants Grant[]
	 * @return int
	 */
	public static int combine(final Grant... grants) {
		int sum = 0;
		for (Grant g : grants) {
			sum += g.grant;
		}
		return sum;
	}
	
	/**
	 * Combines the given grants.
	 * @param grants Grant[]
	 * @return int
	 */
	public static int combine(final String[] grants) {
		Grant[] list = new Grant[grants.length];
		for (int i = 0; i < grants.length; i++) {
			list[i] = Grant.valueOf(grants[i]);
		}
		return combine(list);
	}
	
	/**
	 * Gets representation of all current grants.
	 * @param currentGrant currentGrant.
	 * @return String[]
	 */
	public static String[] currentGrants(final int currentGrant) {
		final List<String> grants = new ArrayList<String>();
		for (final Grant grant : Grant.values()) {
			if (grant.isGrant(currentGrant)) {
				grants.add(grant.toString());
			}
		}
		return grants.toArray(new String[grants.size()]);
	}

	/**
	 * @return the permission
	 */
	public int getGrant() {
		return this.grant;
	}

	/**
	 * @return the bundleKey
	 */
	public String getBundleKey() {
		return this.bundleKey;
	}
}
