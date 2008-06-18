package sigeco.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import sigeco.model.RowType;

/**
 * Auxiliary class for displaying feeds
 * @author julien
 */
public class Row {

	private RowType type;
	private String item;
	private Map<Long, String> levels;
	private Map<Long, String> bgcolor;

	/**
	 * C'tor
	 */
	public Row() {
		super();
	}

	/**
	 * C'tor
	 * @param item String
	 * @param levels Map
	 * @param type RowType
	 */
	public Row(final String item, final Map<Long, String> levels, final RowType type) {
		super();
		this.item = item;
		this.levels = levels;
		this.type = type;

		this.bgcolor = new HashMap<Long, String>();
		for (Map.Entry<Long, String> entry : levels.entrySet()) {
			if (entry.getValue().equals("")) {
				this.bgcolor.put(entry.getKey(), "whitesmoke");
			} else {
				this.bgcolor.put(entry.getKey(), this.type.color);
			}
		}
	}

	/**
	 * Returns the background color map
	 * @return Map
	 */
	public Map<Long, String> getBgcolor() {
		return this.bgcolor;
	}

	/**
	 * @return the levels
	 */
	public Map<Long, String> getLevels() {
		return this.levels;
	}
	/**
	 * @param levels the levels to set
	 */
	public void setLevels(final Map<Long, String> levels) {
		this.levels = levels;
	}
	/**
	 * @return the item
	 */
	public String getItem() {
		return this.item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(final String item) {
		this.item = item;
	}

	/**
	 * @return the type
	 */
	public RowType getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final RowType type) {
		this.type = type;
	}



}