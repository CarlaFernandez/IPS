package CapaInterfaz;

import javax.swing.table.DefaultTableModel;

public class ModeloConColumnasEditables extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public ModeloConColumnasEditables(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
   }
	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 6 || column == 7)
			return true;
		else
			return false;
    }
}
