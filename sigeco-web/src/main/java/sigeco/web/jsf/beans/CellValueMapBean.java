package sigeco.web.jsf.beans;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import sigeco.business.CellValueManager;
import sigeco.model.Cell;
import sigeco.model.CellValue;
import sigeco.model.Matrix;
import sigeco.model.User;

public class CellValueMapBean implements Map<Long, CellValue> {
	private CellValueManager cellValueManager;
	private HashMap<Long, CellValue> map = new HashMap<Long, CellValue>();
	
	public CellValueMapBean() {
	}
	
	public static CellValueMapBean getInstance(FacesContext context) {
		return (CellValueMapBean)context.getApplication().createValueBinding("#{" + "cellValueMapBean" + "}").getValue(context);
	}
	
	public void initialize(User user, Matrix matrix) {
		for (CellValue cellValue : cellValueManager.getCellValues(user, matrix)) {
			put(cellValue.getCell().getId(), cellValue);
		}
		
		for (Cell cell : matrix.getCells()) {
			if (!containsKey(cell.getId())) {
				CellValue cellValue = new CellValue();
				cellValue.setCell(cell);
				cellValue.setUser(user);
				put(cell.getId(), cellValue);
			}
		}
	}

	public void clear() {
		map.clear();
		
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	public Set<java.util.Map.Entry<Long, CellValue>> entrySet() {
		return map.entrySet();
	}

	public CellValue get(Object key) {
		return map.get(key);
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<Long> keySet() {
		return map.keySet();
	}

	public CellValue put(Long key, CellValue value) {
		return map.put(key, value);
	}

	public void putAll(Map<? extends Long, ? extends CellValue> m) {
		map.putAll(m);
		
	}

	public CellValue remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection<CellValue> values() {
		return map.values();
	}

	public CellValueManager getCellValueManager() {
		return cellValueManager;
	}

	public void setCellValueManager(CellValueManager cellValueManager) {
		this.cellValueManager = cellValueManager;
	}

	
}
