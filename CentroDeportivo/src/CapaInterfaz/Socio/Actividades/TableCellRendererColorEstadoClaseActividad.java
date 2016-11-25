package CapaInterfaz.Socio.Actividades;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableCellRendererColorEstadoClaseActividad extends DefaultTableCellRenderer {

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
		String[] splits = cadena.split("--");
		if (splits.length < 2) {
			// // verde activa
			componente.setBackground(new Color(185, 255, 185));
			return componente;
		}
		// // naranja clase cancelada
		if (splits[1].equals(" Inscripcion cancelada")) {
			componente.setBackground(new Color(255, 185, 0));
		}
		// // rojo clase cancelada
		if (splits[1].equals(" Clase/Hora cancelada")) {
			componente.setBackground(new Color(255, 185, 185));
		}

		return componente;
	}

}
