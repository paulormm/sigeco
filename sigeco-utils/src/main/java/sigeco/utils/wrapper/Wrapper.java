package sigeco.utils.wrapper;

import java.util.Collections;
import java.util.List;

/**
 * Wrapper for an element that is associated to a list of other elements.
 *
 * @author ywatari
 *
 * @param <X> Key Element
 * @param <Y> List of relates Elements
 */
public class Wrapper<X, Y extends Comparable<Y>> {
	private X controllerElement;
	private List<Y> dataList;
	private int index = 0;
	private boolean flag;

	/**
	 * C'tor
	 * @param controllerElement X
	 * @param dataList List< Y >
	 */
	public Wrapper(final X controllerElement, final List<Y> dataList) {
		this.controllerElement = controllerElement;
		this.dataList = dataList;
		Collections.sort(dataList);
	}

	/**
	 * Gets the Key Element
	 * @return X
	 */
	public X getControllerElement() {
		this.index = 0;
		return this.controllerElement;
	}

	/**
	 * Sets the Key Element
	 * @param controllerElement X
	 */
	public void setControllerElement(final X controllerElement) {
		this.controllerElement = controllerElement;
		this.index = 0;
	}

	/**
	 * Returns the next related element in the list.
	 * @return The Element or null
	 */
	public Y getIteratedData() {
		if (this.dataList.isEmpty()) {
			return null;
		}
		if (this.index >= this.dataList.size()) {
			this.index = 0;
		}

		Y element = this.dataList.get(this.index);
		this.index++;
		return element;
	}

	/**
	 * Sets the next related element in the list.
	 * @param iteratedData Y
	 */
	public void setIteratedData(final Y iteratedData) {
		if (!(this.dataList.isEmpty())) {
			if (this.index >= this.dataList.size()) {
				this.index = 0;
			}
			this.dataList.set(this.index, iteratedData);
			this.index++;
		}
	}

	/**
	 * Gets the Control flag.
	 * @return boolean
	 */
	public boolean isFlag() {
		return this.flag;
	}

	/**
	 * Sets the Control flag
	 * @param flag boolean
	 */
	public void setFlag(final boolean flag) {
		this.flag = flag;
	}
}
