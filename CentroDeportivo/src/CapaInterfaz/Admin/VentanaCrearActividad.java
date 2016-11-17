package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import CapaDatos.InstalacionDatos;
import CapaDatos.MonitorDatos;
import CapaInterfaz.ModeloConColumnaEditable;
import CapaInterfaz.Monitor.ModeloNoEditable;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Monitor;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;

import com.toedter.calendar.JDateChooser;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class VentanaCrearActividad extends JFrame {
	// private static final long serialVersionUID = 1L;
	// private JTable table;
	// private JDateChooser dateInicio;
	// private JDateChooser dateFin;
	//
	// public VentanaCrearActividad() {
	// setResizable(false);
	// setBounds(100, 100, 786, 525);
	// setTitle("Admin -> Crear actividades");
	// getContentPane().setLayout(new BorderLayout(0, 0));
	//
	// table = new JTable();
	// getContentPane().add(table, BorderLayout.CENTER);
	//
	// JPanel pnFecha = new JPanel();
	// getContentPane().add(pnFecha, BorderLayout.NORTH);
	//
	// JLabel lblDiaInicio = new JLabel("D\u00EDa inicio:");
	// pnFecha.add(lblDiaInicio);
	//
	// dateInicio = new JDateChooser(new Date(System.currentTimeMillis()));
	// dateInicio.setDateFormatString("dd/MM/yyyy");
	// pnFecha.add(dateInicio);
	//
	// JLabel lblDiaFin = new JLabel("D\u00EDa fin:");
	// pnFecha.add(lblDiaFin);
	//
	// dateFin = new JDateChooser(new Date(System.currentTimeMillis()));
	// dateFin.setDateFormatString("dd/MM/yyyy");
	// pnFecha.add(dateFin);
	// }

	private static final long serialVersionUID = 1L;
	private JRadioButton rdbtnPuntual;
	private JRadioButton rdbtnPeriodica;
	private JDateChooser dateInicioPeriodica;
	private JDateChooser dateFinPeriodica;
	private JPanel panelMostrarEleccion;
	private JPanel panelPuntual;
	private List<Instalacion> instalaciones;
	private JTable table;
	private ModeloConColumnaEditable modeloTabla;
	private List<Monitor> monitores;
	private int selectedRow = -1;
	private JSpinner spinnerPlazasPuntual;
	private JComboBox comboBoxInstalacionPuntual;
	private JComboBox comboBoxMonitorPuntual;
	private JCheckBox chckbxTodoElDiaPuntual;
	private JSpinner spinnerDuracionPuntual;
	private JSpinner spinnerHoraPuntual;
	private JDateChooser dateInicioPuntual;
	private JTextField txtNombreActividadPuntual;
	private JTextPane txtpnDescripcionPuntual;

	public VentanaCrearActividad() {
		setTitle("Admin -> Crear actividades");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(false);
		setBounds(100, 100, 786, 525);

		JLabel lblReservaDeActividad = new JLabel("Crear actividades");
		lblReservaDeActividad.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservaDeActividad.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblReservaDeActividad, BorderLayout.NORTH);

		JPanel panelBotones = new JPanel();
		getContentPane().add(panelBotones, BorderLayout.SOUTH);

		JButton btnCrearActividad = new JButton("Crear actividad(es)");
		btnCrearActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnPuntual.isSelected()) {
					insertarActividadPuntual();
				} else {
					insertarActividadPeriodica();
				}
			}
		});
		btnCrearActividad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelBotones.add(btnCrearActividad);

		JPanel panelReserva = new JPanel();
		getContentPane().add(panelReserva, BorderLayout.CENTER);
		panelReserva.setLayout(new BorderLayout(0, 0));

		JPanel panelTipo = new JPanel();
		panelReserva.add(panelTipo, BorderLayout.NORTH);

		JLabel lblTipoReserva = new JLabel("Tipo:");
		lblTipoReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTipo.add(lblTipoReserva);

		rdbtnPuntual = new JRadioButton("Puntual");
		rdbtnPuntual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) panelMostrarEleccion.getLayout()).show(panelMostrarEleccion, "panelSemanal");
			}
		});
		rdbtnPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnPuntual.setSelected(true);
		panelTipo.add(rdbtnPuntual);

		rdbtnPeriodica = new JRadioButton("Peri\u00F3dica");
		rdbtnPeriodica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panelMostrarEleccion.getLayout()).show(panelMostrarEleccion, "panelMensual");
			}
		});
		rdbtnPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTipo.add(rdbtnPeriodica);

		ButtonGroup grupoRadioButton = new ButtonGroup();
		grupoRadioButton.add(rdbtnPeriodica);
		grupoRadioButton.add(rdbtnPuntual);

		JPanel panelOpciones = new JPanel();
		panelReserva.add(panelOpciones, BorderLayout.CENTER);
		panelOpciones.setLayout(new BorderLayout(0, 0));

		instalaciones = ManagerAdmin.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}

		panelMostrarEleccion = new JPanel();
		panelOpciones.add(panelMostrarEleccion, BorderLayout.CENTER);
		panelMostrarEleccion.setLayout(new CardLayout(0, 0));

		panelPuntual = new JPanel();
		panelMostrarEleccion.add(panelPuntual, "panelSemanal");
		GridBagLayout gbl_panelPuntual = new GridBagLayout();
		gbl_panelPuntual.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panelPuntual.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelPuntual.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelPuntual.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelPuntual.setLayout(gbl_panelPuntual);

		JLabel lblFechaPuntual = new JLabel("Fecha:");
		lblFechaPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFechaPuntual = new GridBagConstraints();
		gbc_lblFechaPuntual.insets = new Insets(20, 50, 5, 5);
		gbc_lblFechaPuntual.gridx = 1;
		gbc_lblFechaPuntual.gridy = 0;
		panelPuntual.add(lblFechaPuntual, gbc_lblFechaPuntual);

		dateInicioPuntual = new JDateChooser();
		dateInicioPuntual.setDateFormatString("dd/MM/yyyy");
		dateInicioPuntual.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateInicioPuntual.setDate(new Date(System.currentTimeMillis()));
		GridBagConstraints gbc_dateInicioPuntual = new GridBagConstraints();
		gbc_dateInicioPuntual.insets = new Insets(20, 0, 5, 5);
		gbc_dateInicioPuntual.gridx = 2;
		gbc_dateInicioPuntual.gridy = 0;
		panelPuntual.add(dateInicioPuntual, gbc_dateInicioPuntual);

		JLabel lblHoraPuntual = new JLabel("Hora:");
		lblHoraPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHoraPuntual = new GridBagConstraints();
		gbc_lblHoraPuntual.insets = new Insets(10, 50, 5, 5);
		gbc_lblHoraPuntual.gridx = 1;
		gbc_lblHoraPuntual.gridy = 1;
		panelPuntual.add(lblHoraPuntual, gbc_lblHoraPuntual);

		spinnerHoraPuntual = new JSpinner();
		spinnerHoraPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerHoraPuntual.setModel(new SpinnerNumberModel(15, 0, 23, 1));
		GridBagConstraints gbc_spinnerHoraPuntual = new GridBagConstraints();
		gbc_spinnerHoraPuntual.insets = new Insets(10, 0, 5, 5);
		gbc_spinnerHoraPuntual.gridx = 2;
		gbc_spinnerHoraPuntual.gridy = 1;
		panelPuntual.add(spinnerHoraPuntual, gbc_spinnerHoraPuntual);

		JLabel lblDuracion = new JLabel("Duraci\u00F3n");
		lblDuracion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDuracion = new GridBagConstraints();
		gbc_lblDuracion.insets = new Insets(10, 50, 5, 5);
		gbc_lblDuracion.gridx = 1;
		gbc_lblDuracion.gridy = 2;
		panelPuntual.add(lblDuracion, gbc_lblDuracion);

		spinnerDuracionPuntual = new JSpinner();
		spinnerDuracionPuntual.setModel(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_spinnerDuracionPuntual = new GridBagConstraints();
		gbc_spinnerDuracionPuntual.insets = new Insets(10, 0, 5, 5);
		gbc_spinnerDuracionPuntual.gridx = 2;
		gbc_spinnerDuracionPuntual.gridy = 2;
		panelPuntual.add(spinnerDuracionPuntual, gbc_spinnerDuracionPuntual);

		chckbxTodoElDiaPuntual = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaPuntual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				spinnerDuracionPuntual.setValue(24);
				spinnerHoraPuntual.setValue(0);
				spinnerDuracionPuntual.setEnabled(false);
				spinnerHoraPuntual.setEnabled(false);
			}
		});
		chckbxTodoElDiaPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxTodoElDiaPuntual = new GridBagConstraints();
		gbc_chckbxTodoElDiaPuntual.insets = new Insets(10, 0, 5, 5);
		gbc_chckbxTodoElDiaPuntual.gridx = 3;
		gbc_chckbxTodoElDiaPuntual.gridy = 2;
		panelPuntual.add(chckbxTodoElDiaPuntual, gbc_chckbxTodoElDiaPuntual);
		
		JLabel lblNombreActividad = new JLabel("Nombre actividad:");
		lblNombreActividad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNombreActividad = new GridBagConstraints();
		gbc_lblNombreActividad.anchor = GridBagConstraints.EAST;
		gbc_lblNombreActividad.insets = new Insets(10, 50, 5, 5);
		gbc_lblNombreActividad.gridx = 1;
		gbc_lblNombreActividad.gridy = 3;
		panelPuntual.add(lblNombreActividad, gbc_lblNombreActividad);
		
		txtNombreActividadPuntual = new JTextField();
		txtNombreActividadPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNombreActividadPuntual.setText("Introduzca el nombre de la actividad");
		GridBagConstraints gbc_txtNombreActividadPuntual = new GridBagConstraints();
		gbc_txtNombreActividadPuntual.insets = new Insets(10, 0, 5, 5);
		gbc_txtNombreActividadPuntual.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreActividadPuntual.gridx = 2;
		gbc_txtNombreActividadPuntual.gridy = 3;
		panelPuntual.add(txtNombreActividadPuntual, gbc_txtNombreActividadPuntual);
		txtNombreActividadPuntual.setColumns(10);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDescripcin = new GridBagConstraints();
		gbc_lblDescripcin.insets = new Insets(10, 50, 5, 5);
		gbc_lblDescripcin.gridx = 1;
		gbc_lblDescripcin.gridy = 4;
		panelPuntual.add(lblDescripcin, gbc_lblDescripcin);
		
		txtpnDescripcionPuntual = new JTextPane();
		txtpnDescripcionPuntual.setBorder(new LineBorder(Color.BLACK));
		txtpnDescripcionPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnDescripcionPuntual.setText("Introduzca descripci\u00F3n de la actividad");
		GridBagConstraints gbc_txtpnDescripcionPuntual = new GridBagConstraints();
		gbc_txtpnDescripcionPuntual.insets = new Insets(10, 0, 5, 5);
		gbc_txtpnDescripcionPuntual.fill = GridBagConstraints.BOTH;
		gbc_txtpnDescripcionPuntual.gridx = 2;
		gbc_txtpnDescripcionPuntual.gridy = 4;
		panelPuntual.add(txtpnDescripcionPuntual, gbc_txtpnDescripcionPuntual);

		JLabel lblMonitor = new JLabel("Monitor:");
		lblMonitor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMonitor = new GridBagConstraints();
		gbc_lblMonitor.insets = new Insets(30, 50, 5, 5);
		gbc_lblMonitor.gridx = 1;
		gbc_lblMonitor.gridy = 5;
		panelPuntual.add(lblMonitor, gbc_lblMonitor);

		monitores = MonitorDatos.obtenerMonitores();
		String[] nombresMonitores = new String[monitores.size()];
		for (int i = 0; i < nombresMonitores.length; i++) {
			nombresMonitores[i] = monitores.get(i).getNombre() + " " + monitores.get(i).getApellidos();
		}
		comboBoxMonitorPuntual = new JComboBox(nombresMonitores);
		comboBoxMonitorPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBoxMonitorPuntual = new GridBagConstraints();
		gbc_comboBoxMonitorPuntual.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxMonitorPuntual.insets = new Insets(30, 0, 5, 5);
		gbc_comboBoxMonitorPuntual.gridx = 2;
		gbc_comboBoxMonitorPuntual.gridy = 5;
		panelPuntual.add(comboBoxMonitorPuntual, gbc_comboBoxMonitorPuntual);

		JLabel lblInstalacionPuntual = new JLabel("Instalaci\u00F3n:");
		lblInstalacionPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblInstalacionPuntual = new GridBagConstraints();
		gbc_lblInstalacionPuntual.insets = new Insets(10, 50, 5, 5);
		gbc_lblInstalacionPuntual.gridx = 1;
		gbc_lblInstalacionPuntual.gridy = 6;
		panelPuntual.add(lblInstalacionPuntual, gbc_lblInstalacionPuntual);

		instalaciones = InstalacionDatos.ObtenerInstalaciones();
		String[] nombresInstalaciones = new String[instalaciones.size()];
		for (int i = 0; i < nombresInstalaciones.length; i++) {
			nombresInstalaciones[i] = instalaciones.get(i).getCodigo();
		}
		comboBoxInstalacionPuntual = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBoxInstalacionPuntual = new GridBagConstraints();
		gbc_comboBoxInstalacionPuntual.insets = new Insets(10, 0, 5, 5);
		gbc_comboBoxInstalacionPuntual.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxInstalacionPuntual.gridx = 2;
		gbc_comboBoxInstalacionPuntual.gridy = 6;
		panelPuntual.add(comboBoxInstalacionPuntual, gbc_comboBoxInstalacionPuntual);

		JLabel lblMxPlazas = new JLabel("M\u00E1x. Plazas:");
		lblMxPlazas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblMxPlazas = new GridBagConstraints();
		gbc_lblMxPlazas.insets = new Insets(0, 50, 0, 5);
		gbc_lblMxPlazas.gridx = 1;
		gbc_lblMxPlazas.gridy = 7;
		panelPuntual.add(lblMxPlazas, gbc_lblMxPlazas);

		spinnerPlazasPuntual = new JSpinner();
		spinnerPlazasPuntual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasPuntual.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		GridBagConstraints gbc_comboBoxPlazasPuntual = new GridBagConstraints();
		gbc_comboBoxPlazasPuntual.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxPlazasPuntual.gridx = 2;
		gbc_comboBoxPlazasPuntual.gridy = 7;
		panelPuntual.add(spinnerPlazasPuntual, gbc_comboBoxPlazasPuntual);

		JPanel panelPeriodica = new JPanel();
		panelMostrarEleccion.add(panelPeriodica, "panelMensual");
		panelPeriodica.setLayout(new BorderLayout(0, 0));

		JPanel panelFechas = new JPanel();
		panelPeriodica.add(panelFechas, BorderLayout.NORTH);

		JLabel lblDesdeEl = new JLabel("Desde el:");
		panelFechas.add(lblDesdeEl);
		lblDesdeEl.setFont(new Font("Tahoma", Font.PLAIN, 14));

		dateInicioPeriodica = new JDateChooser();
		panelFechas.add(dateInicioPeriodica);
		BorderLayout bl_dateInicio = (BorderLayout) dateInicioPeriodica.getLayout();
		dateInicioPeriodica.setDateFormatString("dd/MM/yyyy");
		dateInicioPeriodica.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateInicioPeriodica.setDate(new Date(System.currentTimeMillis()));

		JLabel lblHastaEl = new JLabel("Hasta el:");
		panelFechas.add(lblHastaEl);
		lblHastaEl.setFont(new Font("Tahoma", Font.PLAIN, 14));

		dateFinPeriodica = new JDateChooser();
		panelFechas.add(dateFinPeriodica);
		BorderLayout bl_dateFin = (BorderLayout) dateFinPeriodica.getLayout();
		dateFinPeriodica.setDateFormatString("dd/MM/yyyy");
		dateFinPeriodica.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateFinPeriodica.setDate(new Date(System.currentTimeMillis()));

		String[] columnas = { "", "Día", "Hora inicio", "Duración", "Monitor", "Instalación", "Máx. plazas" };

		modeloTabla = new ModeloConColumnaEditable(columnas, 0);
		// table = new JTable(new DefaultTableModel(
		// new Object[][] {
		// {DiasSemana.LUNES.name(), null, null, null, null, null},
		// {DiasSemana.MARTES.name(), null, null, null, null, null},
		// {DiasSemana.MIERCOLES.name(), null, null, null, null, null},
		// {DiasSemana.JUEVES.name(), null, null, null, null, null},
		// {DiasSemana.VIERNES.name(), null, null, null, null, null},
		// {DiasSemana.SABADO.name(), null, null, null, null, null},
		// {DiasSemana.DOMINGO.name(), null, null, null, null, null},
		// },
		// new String[] {
		// "D\u00EDa", "Hora inicio", "Duraci\u00F3n", "Monitor",
		// "Instalaci\u00F3n", "M\u00E1x. plazas"
		// }
		// ) {
		// boolean[] columnEditables = new boolean[] {
		// false, false, false, true, false, false
		// };
		// public boolean isCellEditable(int row, int column) {
		// return columnEditables[column];
		// }
		// });
		// //
		crearTabla();

		JScrollPane scrollPaneTabla = new JScrollPane(table);
		panelPeriodica.add(scrollPaneTabla, BorderLayout.CENTER);
	}

	private void crearTabla() {
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
				boolean seleccionado = (boolean) modeloTabla.getValueAt(selectedRow, 0);
				if (clickedColumn > 0)
					modeloTabla.setValueAt(!seleccionado, selectedRow, 0);
				table.repaint();
			}
		});

		table.setAutoCreateRowSorter(true);
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setDefaultRenderer(Object.class, new TableCellRendererPasarPagos());
		modeloTabla.addRow(new Object[] { true, DiasSemana.LUNES.name(), new JSpinner(), new JSpinner(),
				new JComboBox(), new JComboBox(), new JSpinner() });

		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void insertarActividadPuntual() {
		Long idInst = instalaciones.get(comboBoxInstalacionPuntual.getSelectedIndex()).getIdInst();
		Long idMonitor = monitores.get(comboBoxMonitorPuntual.getSelectedIndex()).getIdMonitor();
		
		int horaInicio = (int) spinnerHoraPuntual.getValue();
		String horaInicioString = String.valueOf(horaInicio);
		horaInicioString += ":00:00";

		int duracion = (int) spinnerDuracionPuntual.getValue();
		String horaFinString;
		int horaFin;
		if (horaInicio + duracion < 24) {
			horaFin = horaInicio + duracion;
			horaFinString = String.valueOf(horaFin);
			horaFinString += ":00:00";
		} else if (horaInicio + duracion == 24) {
			horaFinString = "00:00:00";
			horaFin = 0;
		} else {
			horaFin = (horaInicio + duracion) % 24;
			horaFinString = String.valueOf(horaFin);
			horaFinString += ":00:00";
		}

		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio = fmt.format(dateInicioPuntual.getDate());
		String fechaFin = "";

		// para una reserva no periodica
		// si es de todo el día o la reserva es por ej. de 23 a 1
		// suma 1 dia a fecha
		if (chckbxTodoElDiaPuntual.isSelected() || horaInicio > horaFin) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime dateTimeInicio = new DateTime(formatter.parseDateTime(fechaInicio).getMillis());
			DateTime dtPlusOne = dateTimeInicio.plusDays(1);
			fechaFin = fmt.format(new Date(dtPlusOne.getMillis()));

		} else {
			fechaFin = fechaInicio;
		}

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dateTimeInicio = formatter.parseDateTime(fechaInicio + " " + horaInicioString);
		DateTime dateTimeFin = formatter.parseDateTime(fechaFin + " " + horaFinString);

		try {
			ManagerAdmin.crearReservaActividad(dateTimeInicio, dateTimeFin, 
					idInst, idMonitor, txtNombreActividadPuntual.getText(),
					txtpnDescripcionPuntual.getText(), (int)spinnerPlazasPuntual.getValue() );
			JOptionPane.showMessageDialog(this, "La reserva se ha insertado con éxito");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		}

	}

	public void insertarActividadPeriodica() {

	}
}
