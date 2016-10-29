package CapaInterfaz.Monitor;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRendererColorInstalacion extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Component componente;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object arg1, boolean arg2, boolean arg3, int row,
			int col) {
		componente = super.getTableCellRendererComponent(table, arg1, arg2, arg3, row, col);
		componente.setBackground(Color.white);
		if (table.getModel().getValueAt(row, col) == null)
			return componente;
		if (table.getModel().getValueAt(row, col).equals("Mi reserva")) {
			componente.setBackground(new Color(185, 255, 185));// verde
		}
		if (table.getModel().getValueAt(row, col).equals("Reserva ajena")) {
			componente.setBackground(new Color(255, 185, 185));// rojo
		}
		return componente;
	}

}
