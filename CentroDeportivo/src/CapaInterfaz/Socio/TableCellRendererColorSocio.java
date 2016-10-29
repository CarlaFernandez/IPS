package CapaInterfaz.Socio;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.DateTime;

import CapaNegocio.EstadoReserva;

public class TableCellRendererColorSocio extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Component componente;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object arg1, boolean arg2, boolean arg3, int row,
			int col) {
		componente = super.getTableCellRendererComponent(table, arg1, arg2, arg3, row, col);

		componente.setBackground(new Color(185, 185, 255));// azul
		if (((DateTime) table.getModel().getValueAt(row, 2)).isAfterNow()) {
			componente.setBackground(new Color(185, 255, 185));// verde
		}
		if (table.getModel().getValueAt(row, 5).toString().equals(EstadoReserva.CANCELADA.name())) {
			componente.setBackground(new Color(255, 185, 185));// rojo
		}
		if (table.getModel().getValueAt(row, 5).toString().equals(EstadoReserva.ANULADA.name())) {
			componente.setBackground(new Color(255, 255, 185));// rojo
		}
		return componente;
	}

}
