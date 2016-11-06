package CapaInterfaz;

import javax.swing.table.DefaultTableModel;

public class ModeloConColumnaEditable extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public ModeloConColumnaEditable(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		if (column == 0)
			return true;
		else
			return false;
	}
	
	
}
