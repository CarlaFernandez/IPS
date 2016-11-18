package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
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

import CapaDatos.Contable;
import CapaDatos.PagoDatos;
import CapaDatos.UsuarioDatos;
import CapaInterfaz.ModeloConColumnaEditable;
import CapaInterfaz.ModeloNoEditable;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerAdmin;
import java.awt.Component;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;

public class VentanaContablePasarPagos extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private ModeloConColumnaEditable modeloTabla;
	private ModeloNoEditable modeloTablaPagos;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	private int selectedRow = -1;
	private JTable tablaPagos;

	public VentanaContablePasarPagos() {
		instalaciones = ManagerAdmin.verInstalaciones();
		setResizable(false);
		setBounds(100, 100, 950, 525);
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
		panelCentro.setLayout(new GridLayout(0, 2, 0, 0));
		modeloTabla = new ModeloConColumnaEditable(new String[] { "",
				"ID Socio", "DNI", "Nombre", "Apellidos", "Cuenta bancaria" },
				0);

		modeloTablaPagos = new ModeloNoEditable(new String[] { "ID Socio",
				"ID Pago", "Concepto", "Fecha", "Importe", "Estado" }, 0);

		JPanel panelSocios = new JPanel();
		panelSocios.setBorder(new TitledBorder(null, "Socios",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCentro.add(panelSocios);
		panelSocios.setLayout(new BorderLayout(0, 0));

		JScrollPane spTabla = new JScrollPane();
		panelSocios.add(spTabla);

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
				long idSocio = 0;
				if (selectedRow == -1) {
					idSocio = -1;
				} else
					idSocio = (Long) modeloTabla.getValueAt(selectedRow, 1);

				/*
				 * boolean seleccionado = (boolean) modeloTabla
				 * .getValueAt(selectedRow, 0); if (clickedColumn > 0)
				 * modeloTabla.setValueAt(!seleccionado, selectedRow, 0);
				 */
				getPagosSocio(idSocio);
				table.repaint();
			}
		});

		table.setAutoCreateRowSorter(true);
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setDefaultRenderer(Object.class,
				new TableCellRendererPasarPagosSocios());
		spTabla.setViewportView(table);

		JPanel panelPagos = new JPanel();
		panelPagos.setBorder(new TitledBorder(null, "Pagos a cuenta",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelCentro.add(panelPagos);
		panelPagos.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPagos = new JScrollPane();
		panelPagos.add(scrollPagos);
		tablaPagos = new JTable();
		tablaPagos.setBackground(Color.WHITE);
		tablaPagos.setAutoCreateRowSorter(true);
		tablaPagos.setModel(modeloTablaPagos);
		tablaPagos.getColumnModel().getColumn(5)
				.setCellRenderer(new TableCellRendererPasarPagosPagos());

		scrollPagos.setViewportView(tablaPagos);

		obtenerTodosSocios();

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panelTodos = new JPanel();
		panelCabecera.add(panelTodos);
		panelTodos.setLayout(new GridLayout(0, 2, 0, 0));

		JCheckBox checkTodos = new JCheckBox("Seleccionar todos");
		panelTodos.add(checkTodos);

		JCheckBox chckbxSoloSociosConPagos = new JCheckBox(
				"Solo socios con pagos pendientes");
		chckbxSoloSociosConPagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean seleccionado = ((AbstractButton) e.getSource())
						.isSelected();
				if (seleccionado) {
					obtenerSociosConPagosPendientes();
				} else
					obtenerTodosSocios();

			}
		});
		panelTodos.add(chckbxSoloSociosConPagos);
		checkTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean seleccionado = checkTodos.isSelected();
				for (int i = 0; i < modeloTabla.getRowCount(); i++)
					modeloTabla.setValueAt(seleccionado, i, 0);
				table.repaint();
			}
		});

		JPanel panelFiltro = new JPanel();
		panelCabecera.add(panelFiltro);
		panelFiltro.setLayout(new BorderLayout(0, 0));

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
								+ " socios a sus cuotas mensuales?",
						"Enviar pagos a la cuota mensual", botonDialogo);
				if (botonDialogo == JOptionPane.YES_OPTION) {
					List<Long> idSocios = getSociosSeleccionados();
					Long idPagoCobrado = Contable.incrementarPagos(idSocios);
					if (idPagoCobrado == null)
						JOptionPane.showMessageDialog(null,
								"Los pagos se han enviado correctamente",
								"Exito", JOptionPane.INFORMATION_MESSAGE);
					else {
						Long idSocio = PagoDatos
								.obtenerIDSocioDePago(idPagoCobrado);
						JOptionPane.showMessageDialog(null,
								"El pago con el id: " + idPagoCobrado
										+ " perteneciente al socio con id: "
										+ idSocio
										+ " ya está cobrado.\nSeleccione los pagos sin cobrar y vuelva a intentarlo",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
					getPagosSocio((Long) table.getValueAt(selectedRow, 1));
				}
				return;
			}

		});
		panelPie.add(btnPasarPagosA);
	}

	private int obtenerTodosSocios() {
		List<Usuario> socios = UsuarioDatos.ObtenerUsuarios();
		Object[] line = new Object[6];
		/*
		 * for (int i = 0; i < modeloTabla.getRowCount(); i++)
		 * modeloTabla.removeRow(0);
		 */
		modeloTabla.getDataVector().clear();
		for (int i = 0; i < socios.size(); i++) {
			line[0] = false;
			line[1] = socios.get(i).getIdUsu();
			line[2] = socios.get(i).getDNI();
			line[3] = socios.get(i).getNombre();
			line[4] = socios.get(i).getApellidos();
			line[5] = socios.get(i).getCuentaBancaria();
			modeloTabla.addRow(line);
		}
		table.repaint();
		return socios.size();
	}

	private int obtenerSociosConPagosPendientes() {
		List<Usuario> socios = UsuarioDatos.ObtenerUsuariosConPagosPendientes();
		Object[] line = new Object[6];
		/*
		 * for (int i = 0; i < modeloTabla.getDataVector().size(); i++)
		 * modeloTabla.removeRow(0);
		 */
		modeloTabla.getDataVector().clear();
		for (int i = 0; i < socios.size(); i++) {
			line[0] = false;
			line[1] = socios.get(i).getIdUsu();
			line[2] = socios.get(i).getDNI();
			line[3] = socios.get(i).getNombre();
			line[4] = socios.get(i).getApellidos();
			line[5] = socios.get(i).getCuentaBancaria();
			modeloTabla.addRow(line);
		}
		table.repaint();
		return socios.size();
	}

	private int getNumeroSeleccionados() {
		int condator = 0;
		for (int i = 0; i < table.getRowCount(); i++)
			if (modeloTabla.getValueAt(i, 0).equals(true))
				condator++;
		return condator;
	}

	private void getPagosSocio(Long idSocio) {
		if (idSocio == -1) {
			modeloTablaPagos.getDataVector().clear();
			tablaPagos.repaint();
		}

		List<Map<String, Object>> pagos = PagoDatos
				.obtenerPagosCuentaDeSocio(idSocio);
		Object[] row = new Object[6];
		modeloTablaPagos.getDataVector().clear();
		tablaPagos.repaint();
		for (int i = 0; i < modeloTablaPagos.getRowCount(); i++)
			modeloTablaPagos.removeRow(0);
		for (int i = 0; i < pagos.size(); i++) {
			row[0] = pagos.get(i).get("idSocio");
			row[1] = pagos.get(i).get("id");
			row[2] = pagos.get(i).get("concepto");
			row[3] = pagos.get(i).get("fecha");
			row[4] = pagos.get(i).get("importe");
			row[5] = pagos.get(i).get("estado");
			modeloTablaPagos.addRow(row);
		}
	}

	private List<Long> getSociosSeleccionados() {
		List<Long> idSocios = new ArrayList<>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) table.getValueAt(i, 0))
				idSocios.add((Long) table.getValueAt(i, 1));
		}
		return idSocios;
	}
}