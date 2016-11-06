package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import CapaDatos.UsuarioDatos;
import CapaInterfaz.ModeloConColumnaEditable;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerAdmin;

public class VentanaContablePasarPagos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private ModeloConColumnaEditable modeloTabla;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	private int selectedRow = -1;

	public VentanaContablePasarPagos() {
		instalaciones = ManagerAdmin.verInstalaciones();
		setResizable(false);
		setBounds(100, 100, 786, 525);
		JLabel lblReservasInstalaciones = new JLabel(
				"Enviar pagos a la cuota mensual");
		lblReservasInstalaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservasInstalaciones.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblReservasInstalaciones
				.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblReservasInstalaciones, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelCentro = new JPanel();
		panel.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));

		JScrollPane spTabla = new JScrollPane();
		panelCentro.add(spTabla, BorderLayout.CENTER);
		modeloTabla = new ModeloConColumnaEditable(new String[] { "",
				"ID Socio", "DNI", "Nombre", "Apellidos", "Cuenta bancaria" },
				0);

		table = new JTable() {

			private static final long serialVersionUID = 1L;

			/*
			 * @Override public Class getColumnClass(int column) { return
			 * getValueAt(0, column).getClass(); }
			 */
			@Override
			public Class getColumnClass(int column) {
				if (column == 0)
					return Boolean.class;
				else
					return Object.class;
			}
		};
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectedRow = table.getSelectedRow();
				int clickedColumn = table.getSelectedColumn();
				boolean seleccionado = (boolean) modeloTabla
						.getValueAt(selectedRow, 0);
				if (clickedColumn > 0)
					modeloTabla.setValueAt(!seleccionado, selectedRow, 0);
				table.repaint();
			}
		});

		table.setAutoCreateRowSorter(true);
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setDefaultRenderer(Object.class,
				new TableCellRendererPasarPagos());
		spTabla.setViewportView(table);
		obtenerSocios();

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(new BoxLayout(panelCabecera, BoxLayout.X_AXIS));

		JCheckBox checkTodos = new JCheckBox("Seleccionar todos");
		checkTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean seleccionado = checkTodos.isSelected();
				for (int i = 0; i < modeloTabla.getRowCount(); i++)
					modeloTabla.setValueAt(seleccionado, i, 0);
				table.repaint();
			}
		});
		panelCabecera.add(checkTodos);

		JPanel panelPie = new JPanel();
		panel.add(panelPie, BorderLayout.SOUTH);
		panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnPasarPagosA = new JButton("Enviar pagos");
		btnPasarPagosA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int botonDialogo = JOptionPane.YES_NO_OPTION;
				botonDialogo = JOptionPane.showConfirmDialog(null,
						"Está seguro de enviar los pagos de "
								+ getNumeroSeleccionados()
								+ " a sus cuotas mensuales?",
						"Enviar pagos a la cuota mensual", botonDialogo);
				if (botonDialogo == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null,
							"Los pagos se han enviado correctamente", "Exito",
							JOptionPane.INFORMATION_MESSAGE);
				}
				return;
			}

		});
		panelPie.add(btnPasarPagosA);
	}

	private void obtenerSocios() {
		List<Usuario> socios = UsuarioDatos.ObtenerUsuarios();
		Object[] line = new Object[6];
		for (int i = 0; i < modeloTabla.getRowCount(); i++)
			modeloTabla.removeRow(0);
		for (int i = 0; i < socios.size(); i++) {
			line[0] = false;
			line[1] = socios.get(i).getIdUsu();
			line[2] = socios.get(i).getDNI();
			line[3] = socios.get(i).getNombre();
			line[4] = socios.get(i).getApellidos();
			line[5] = socios.get(i).getCuentaBancaria();
			modeloTabla.addRow(line);
		}
	}

	private int getNumeroSeleccionados() {
		int condator = 0;
		for (int i = 0; i < table.getRowCount(); i++)
			if (modeloTabla.getValueAt(i, 0).equals(true))
				condator++;
		return condator;
	}
}