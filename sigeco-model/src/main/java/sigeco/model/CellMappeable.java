package sigeco.model;

import java.util.Map;

/**
 * Interface for objects that map their cells.
 *  
 * @author julien
 */
public interface CellMappeable {
	/**
	 * Gets the Map of Cells
	 * @return Map
	 */
	Map<String, Cell> getMappedCells();
}
