package CapaInterfaz.Socio;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.joda.time.DateTime;

import CapaNegocio.EstadoReserva;

public class TableCellRendererColorEstadoReserva2 extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Component componente;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object arg1, boolean arg2, boolean arg3, int row,
			int col) {
		componente = super.getTableCellRendererComponent(table, arg1, arg2, arg3, row, col);

		String cadena = table.getModel().getValueAt(row, col).toString();
		if (cadena.equals("")) {
			componente.setBackground(Color.WHITE);
			return componente;
		}
		// // verde pendiente
		if (cadena.split("--")[1].equals(EstadoReserva.ACTIVA.name())) {
			componente.setBackground(new Color(185, 255, 185));
		}
		// // azul pasada
		String fecha = cadena.split("--")[2];
		DateTime dt = DateTime.parse(fecha);
		// System.out.println("dt" + dt);
		// System.out.println("str" + fecha);
		if (dt.isBeforeNow())
			componente.setBackground(new Color(185, 185, 255));
		// // rojo cancelada
		if (cadena.split("--")[1].equals(EstadoReserva.CANCELADA.name())) {
			componente.setBackground(new Color(255, 185, 185));
		}
		// // amarillo anulada
		if (cadena.split("--")[1].equals(EstadoReserva.ANULADA.name())) {
			componente.setBackground(new Color(255, 255, 185));
		}

		return componente;
	}

}
