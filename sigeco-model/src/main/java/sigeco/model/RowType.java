package sigeco.model;

//public class RowType {
	/**
	 * Enum for defining a row type.
	 * 
	 * @author julien
	 */
	public enum RowType{
		KNOWLEDGE("white"),
		KNOWLEDGE_GROUP("whitesmoke");
	
		String color;
	
		/**
		 * C'tor 
		 * @param color The html color this row type should be colored with
		 */
		private RowType(final String color) {
			this.color = color;
		}
	
		/**
		 * Returns what color the row should be represented with
		 * @return String
		 */
		public String getColor() {
			return this.color;
		}
	}
//}
