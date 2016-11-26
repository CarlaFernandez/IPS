package CapaInterfaz.Monitor;
	 
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
 
public class ChkCellEditor extends DefaultCellEditor implements TableCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	private JComponent component = new JCheckBox();
	private boolean value = false;
	

	public ChkCellEditor() {
		super(new JCheckBox());
	}
	
	/**
	 * retorna valor de checkBox
	 */
	@Override
	public Object getCellEditorValue() {
		return ((JCheckBox)component).isSelected();
	}

	/**
	 * cuando termina la manipulacion de celda
	 */
	@Override
	public boolean stopCellEditing(){
		value = ((Boolean)getCellEditorValue()).booleanValue();
//		if(value==true)
//			((JCheckBox) component).setBackground(Color.GREEN);
//		else
//			((JCheckBox) component).setBackground(Color.RED);
		((JCheckBox)component).setSelected(value);
		return super.stopCellEditing();
	}

	/**
	 * retorna componente
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(value==null)
			return null;
		return ((JCheckBox) component);
	}
	
	/**
	 * segun el valor de la celda seleccion/deselecion el JCheckBox
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		
		//obtiene el boolean y pone el valor en el CheckBox
		boolean b = ((Boolean) value).booleanValue();

		((JCheckBox) component).setSelected(b);
	
		return (JCheckBox)component;
	}	
	
}
