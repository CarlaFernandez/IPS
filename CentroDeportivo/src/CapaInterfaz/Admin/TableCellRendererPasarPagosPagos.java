package CapaInterfaz.Admin;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.DateTime;

import CapaNegocio.dao.TipoReserva;

public class TableCellRendererPasarPagosPagos extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Component componente;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object arg1,
			boolean arg2, boolean arg3, int row, int col) {
		componente = super.getTableCellRendererComponent(table, arg1, arg2,
				arg3, row, col);
		componente.setBackground(Color.white);
		// if (table.getModel().getValueAt(row, col) == null)
		// return componente;
		if (table.getModel().getValueAt(row, 5).equals("PENDIENTE")) {
			componente.setBackground(new Color(255, 67, 67));
		}
		if (table.getModel().getValueAt(row, 5).equals("COBRADO")) {
			componente.setBackground(new Color(73, 255, 67));
		}
		return componente;
	}

}
