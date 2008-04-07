package sigeco.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Permission levels for the system.
 *
 *
 *
 * @author julien
 */
public enum Permission {

	USER(1, "global.security.user"),
	MANAGER(2, "global.security.manager"),
	ADMIN(4, "global.security.admin");

	private int permission;
	private String bundleKey;


	/**
	 * C'tor
	 * @param permission int
	 * @param bundleKey String
	 */
	private Permission(final int permission, final String bundleKey) {
		this.permission = permission;
		this.bundleKey = bundleKey;
	}

	/**
	 * Returns true if the given user has this permission.
	 *
	 * @param targetPermission int
	 * @return boolean
	 */
	public boolean isRole(final int targetPermission) {
		return (targetPermission & this.permission) == this.permission;
	}

	/**
	 * Combines the given permissions.
	 * @param permissions Permission[]
	 * @return int
	 */
	public static int combine(final Permission... permissions) {
		int sum = 0;
		for (Permission p : permissions) {
			sum += p.permission;
		}
		return sum;
	}
	
	/**
	 * Combines the given permissions.
	 * @param permissions Permission[]
	 * @return int
	 */
	public static int combine(final String[] permissions) {
		Permission[] perms = new Permission[permissions.length];
		for (int i = 0; i < permissions.length; i++) {
			perms[i] = Permission.valueOf(permissions[i]);
		}
		return combine(perms);
	}
	
	/**
	 * Gets representation of all current permissions.
	 * @param currentPermission currentPermission.
	 * @return String[]
	 */
	public static String[] currentRoles(final int currentPermission) {
		final List<String> roles = new ArrayList<String>();
		for (final Permission permission : Permission.values()) {
			if (permission.isRole(currentPermission)) {
				roles.add(permission.toString());
			}
		}
		return roles.toArray(new String[roles.size()]);
	}

	/**
	 * @return the permission
	 */
	public int getPermission() {
		return this.permission;
	}

	/**
	 * @return the bundleKey
	 */
	public String getBundleKey() {
		return this.bundleKey;
	}
}
