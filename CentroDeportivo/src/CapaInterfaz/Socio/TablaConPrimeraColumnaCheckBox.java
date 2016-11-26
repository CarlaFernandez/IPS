package CapaInterfaz.Socio;

import javax.swing.JTable;

public class TablaConPrimeraColumnaCheckBox extends JTable {
	private static final long serialVersionUID = 1L;

	/*
	 * @Override public Class getColumnClass(int column) { return getValueAt(0,
	 * column).getClass(); }
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int column) {
		if (column == 0)
			return Boolean.class;
		else
			return Object.class;
	}
}
