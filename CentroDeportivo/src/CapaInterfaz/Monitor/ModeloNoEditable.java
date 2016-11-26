package CapaInterfaz.Monitor;

import javax.swing.table.*;
/**
 * clase envoltorio que solo cambia en que la celda nunca sera editable
 * @author UO239718
 *
 */
public class ModeloNoEditable extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloNoEditable(Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
   }
	@Override
	public boolean isCellEditable(int row, int column) {
		if(column==3)
			return true;
        return false;
    }
	 
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// Si queremos que la tabla sea editable deberemos establecer estos valores
		super.setValueAt(aValue, rowIndex, columnIndex);
		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
	
}