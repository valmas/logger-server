package com.ntua.ote.logger.web.xls;

/**
 * The Class XLSColumn.
 */
public class XLSColumn {

	/** The label. */
	private String label;
	
	/** The field. */
	private String field;
	
	/**
	 * Instantiates a new XLS column.
	 *
	 * @param label the label
	 * @param field the field
	 */
	public XLSColumn(String label, String field) {
		this.label = label;
		this.field = field;
	}

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field.
	 *
	 * @param field the new field
	 */
	public void setField(String field) {
		this.field = field;
	}
	
	
}
