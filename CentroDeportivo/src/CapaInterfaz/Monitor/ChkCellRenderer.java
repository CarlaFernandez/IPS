package CapaInterfaz.Monitor;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.joda.time.DateTime;

import CapaNegocio.EstadoReserva;

public class ChkCellRenderer extends JCheckBox implements TableCellRenderer {

	private JComponent component = new JCheckBox();
	

	public ChkCellRenderer() {
		setOpaque(true);
	}
	
	

	/**
	 * segun el valor de la celda seleccion/deselecion el JCheckBox
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		//obtiene el boolean y pone el valor en el CeckBox
		boolean b = ((Boolean) value).booleanValue();
		if(b==true)
			((JCheckBox) component).setBackground(Color.GREEN);
		else
			((JCheckBox) component).setBackground(Color.RED);
		((JCheckBox) component).setSelected(b);
		
		return (JCheckBox)component;
	}	
	
}
