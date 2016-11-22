package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.joda.time.DateTime;

import CapaDatos.ActividadesDatos;
import CapaInterfaz.ModeloConColumnaEditable;
import CapaInterfaz.ModeloNoEditable;
import CapaInterfaz.Admin.TableCellRendererPasarPagosPagos;
import CapaInterfaz.Admin.TableCellRendererPasarPagosSocios;
import CapaInterfaz.Socio.TablaConPrimeraColumnaCheckBox;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerFechas;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class VentanaAdminCancelarActividades extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable tablaActividades;
	public ModeloNoEditable modeloTablaActividades;
	private ModeloNoEditable modeloTablaInstanciasActividad;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	JTextArea textAreaDescripcion;
	JCheckBox chckbxSoloActiConPlazoAbierto;
	private int selectedRowActividad = -1;
	private int selectedRowInstancia = -1;
	private JTable tablaInstanciasActividades;
	private HashMap<Long, String> descriptions;

	public VentanaAdminCancelarActividades() {
		instalaciones = ManagerAdmin.verInstalaciones();
		setResizable(false);
		setBounds(100, 100, 1050, 525);
		JLabel lblCancelarActividad = new JLabel("Cancelar actividad");
		lblCancelarActividad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCancelarActividad.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblCancelarActividad.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblCancelarActividad, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelCentro = new JPanel();
		panel.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new GridLayout(2, 1, 0, 0));
		modeloTablaActividades = new ModeloNoEditable(
				new String[] { "ID", "Nombre" }, 0);

		modeloTablaInstanciasActividad = new ModeloNoEditable(new String[] {
				"ID", "Fecha Inicio", "Fecha Fin", "Plazas Totales",
				"Plazas Ocupadas", "Monitor", "Instalacion", "Estado" }, 0);

		JPanel panelGridArriba = new JPanel();
		panelCentro.add(panelGridArriba);
		GridBagLayout gbl_panelGridArriba = new GridBagLayout();
		gbl_panelGridArriba.columnWidths = new int[] { 314, 730, 0 };
		gbl_panelGridArriba.rowHeights = new int[] { 182, 0 };
		gbl_panelGridArriba.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panelGridArriba.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelGridArriba.setLayout(gbl_panelGridArriba);

		obtenerTodasActividadesFuturas();

		JPanel panelActividades = new JPanel();
		GridBagConstraints gbc_panelActividades = new GridBagConstraints();
		gbc_panelActividades.fill = GridBagConstraints.BOTH;
		gbc_panelActividades.insets = new Insets(0, 0, 0, 5);
		gbc_panelActividades.gridx = 0;
		gbc_panelActividades.gridy = 0;
		panelGridArriba.add(panelActividades, gbc_panelActividades);
		panelActividades.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"),
						"Actividades", TitledBorder.LEADING, TitledBorder.TOP,
						null, new Color(0, 0, 0)));
		panelActividades
				.setLayout(new BoxLayout(panelActividades, BoxLayout.X_AXIS));

		JScrollPane spTabla = new JScrollPane();
		panelActividades.add(spTabla);

		tablaActividades = new JTable();
		tablaActividades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectedRowActividad = tablaActividades.getSelectedRow();
				long idActividad = (long) tablaActividades
						.getValueAt(selectedRowActividad, 0);
				if (chckbxSoloActiConPlazoAbierto.isSelected()) {
					obtenerSoloInstanciasConPlazoCerrado(idActividad);
				} else {
					obtenerTodasInstanciasFuturas(idActividad);
				}
				actualizarDescipcion(idActividad);
			}
		});

		tablaActividades.setAutoCreateRowSorter(true);
		tablaActividades.setModel(modeloTablaActividades);
		tablaActividades.getColumnModel().getColumn(0).setMaxWidth(20);
		tablaActividades.setBackground(Color.WHITE);

		spTabla.setViewportView(tablaActividades);

		JPanel panelInstanciasActividad = new JPanel();
		GridBagConstraints gbc_panelInstanciasActividad = new GridBagConstraints();
		gbc_panelInstanciasActividad.fill = GridBagConstraints.BOTH;
		gbc_panelInstanciasActividad.gridx = 1;
		gbc_panelInstanciasActividad.gridy = 0;
		panelGridArriba.add(panelInstanciasActividad,
				gbc_panelInstanciasActividad);
		panelInstanciasActividad.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"),
						"Clases de actividades", TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelInstanciasActividad.setLayout(
				new BoxLayout(panelInstanciasActividad, BoxLayout.X_AXIS));

		JScrollPane scrollPagos = new JScrollPane();
		panelInstanciasActividad.add(scrollPagos);
		tablaInstanciasActividades = new JTable();
		tablaInstanciasActividades.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedRowInstancia = tablaInstanciasActividades
						.getSelectedRow();
			}
		});
		tablaInstanciasActividades.setBackground(Color.WHITE);
		tablaInstanciasActividades.setAutoCreateRowSorter(true);
		tablaInstanciasActividades.setModel(modeloTablaInstanciasActividad);
		tablaInstanciasActividades.getColumnModel().getColumn(3)
				.setMinWidth(50);
		tablaInstanciasActividades.getColumnModel().getColumn(4)
				.setMinWidth(50);
		tablaInstanciasActividades.getColumnModel().getColumn(5)
				.setMinWidth(100);
		tablaInstanciasActividades.getColumnModel().getColumn(0)
				.setMaxWidth(20);
		scrollPagos.setViewportView(tablaInstanciasActividades);

		JPanel panelGridAbajo = new JPanel();
		panelCentro.add(panelGridAbajo);
		panelGridAbajo.setLayout(new BorderLayout(0, 0));

		JPanel panelDescripcion = new JPanel();
		panelDescripcion.setBorder(
				new TitledBorder(null, "Descripcion", TitledBorder.LEADING,
						TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelGridAbajo.add(panelDescripcion, BorderLayout.CENTER);
		panelDescripcion.setLayout(new BorderLayout(0, 0));

		textAreaDescripcion = new JTextArea();
		panelDescripcion.add(textAreaDescripcion);

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(new BorderLayout(0, 0));

		JPanel panelFiltro = new JPanel();
		panelCabecera.add(panelFiltro, BorderLayout.WEST);
		panelFiltro.setLayout(new BorderLayout(0, 0));

		chckbxSoloActiConPlazoAbierto = new JCheckBox(
				"Solo actividades con plazo de inscripcion cerrado");
		panelFiltro.add(chckbxSoloActiConPlazoAbierto, BorderLayout.EAST);
		chckbxSoloActiConPlazoAbierto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((AbstractButton) e.getSource()).isSelected()) {
					obtenerSoloActividadesConPlazoCerrado();
				} else {
					obtenerTodasActividadesFuturas();
				}
			}
		});

		JPanel panelPie = new JPanel();
		panel.add(panelPie, BorderLayout.SOUTH);
		panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnCancelarInstancia = new JButton("Cancelar una clase");
		btnCancelarInstancia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedRowInstancia == -1)
					JOptionPane.showMessageDialog(null,
							"No ha seleccionado ninguna instancia para apuntarse.",
							"Error: Ninguna instancia seleccionada",
							JOptionPane.ERROR_MESSAGE);
				else {
					long idInstanciaActividad = (long) tablaInstanciasActividades
							.getValueAt(selectedRowInstancia, 0);
					cancelarInstanciaActividad(idInstanciaActividad);
				}
			}

		});

		JButton btnCancelarActividad = new JButton(
				"Cancelar actividad completa");
		panelPie.add(btnCancelarActividad);
		panelPie.add(btnCancelarInstancia);
	}

	private void actualizarDescipcion(Long idActividad) {
		textAreaDescripcion.setText(descriptions.get(idActividad));
	}

	private void obtenerActividades(List<Map<String, Object>> listActividades) {
		List<Map<String, Object>> actividades = listActividades;
		descriptions = new HashMap<>();
		Object[] line = new Object[2];
		modeloTablaActividades.getDataVector().clear();

		for (Map<String, Object> actividad : actividades) {
			long id = (long) actividad.get("id");
			line[0] = id;
			line[1] = actividad.get("nombre");
			descriptions.put(id, (String) actividad.get("descripcion"));
			modeloTablaActividades.addRow(line);
		}
	}

	private void obtenerSoloActividadesConPlazoCerrado() {
		obtenerActividades(ActividadesDatos
				.obtenerActividadesFuturasConInscripcionCerrada());
	}

	private void obtenerSoloActividadesConPlazoAbierto() {
		obtenerActividades(ActividadesDatos
				.obtenerActividadesFuturasConInscripcionAbierta());
	}

	private void obtenerTodasActividadesFuturas() {
		obtenerActividades(ActividadesDatos.obtenerActividadesFuturas());
	}

	private void obtenerTodasInstanciasFuturas(long idActividad) {
		obtenerInstanciasDeActividades(ActividadesDatos
				.obtenerInstanciasDeActividadesFuturas(idActividad));
	}

	private void obtenerSoloInstanciasConPlazoAbierto(long idActividad) {
		obtenerInstanciasDeActividades(ActividadesDatos
				.obtenerInstanciasDeActividadesConInscripcionAbierta(
						idActividad));
	}

	private void obtenerSoloInstanciasConPlazoCerrado(long idActividad) {
		obtenerInstanciasDeActividades(ActividadesDatos
				.obtenerInstanciasDeActividadesConInscripcionCerrada(
						idActividad));
	}

	private void cancelarInstanciaActividad(Long idInstanciaActividad) {
		ActividadesDatos.cancelarInstanciaActividad(idInstanciaActividad);
		modeloTablaInstanciasActividad.getDataVector().clear();
		tablaInstanciasActividades.repaint();
	}

	private void obtenerInstanciasDeActividades(
			List<Map<String, Object>> listActividades) {
		selectedRowInstancia = -1;
		List<Map<String, Object>> actividades = listActividades;
		Object[] line = new Object[8];
		modeloTablaInstanciasActividad.getDataVector().clear();
		tablaInstanciasActividades.repaint();
		for (Map<String, Object> actividad : actividades) {
			line[0] = actividad.get("id");
			line[1] = actividad.get("fecha_actividad_inicio");
			line[2] = actividad.get("fecha_actividad_fin");
			line[3] = actividad.get("plazas_totales");
			line[4] = actividad.get("plazas_ocupadas");
			line[5] = actividad.get("nombre") + " "
					+ actividad.get("apellidos");
			line[6] = actividad.get("codigo");
			line[7] = actividad.get("estado");
			modeloTablaInstanciasActividad.addRow(line);
		}
	}
}