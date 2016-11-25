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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import CapaDatos.InstalacionDatos;
import CapaDatos.MonitorDatos;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Monitor;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("rawtypes")
public class VentanaCrearActividad extends JFrame {
	private static final long serialVersionUID = 1L;
	private JRadioButton rdbtnPuntual;
	private JRadioButton rdbtnPeriodica;
	private JDateChooser dateInicioPeriodica;
	private JDateChooser dateFinPeriodica;
	private JPanel panelMostrarEleccion;
	private JPanel panelPuntual;
	private List<Instalacion> instalaciones;
	private List<Monitor> monitores;
	private JSpinner spinnerPlazasPuntual;
	private JComboBox comboBoxInstalacionPuntual;
	private JComboBox comboBoxMonitorPuntual;
	private JCheckBox chckbxTodoElDiaPuntual;
	private JSpinner spinnerDuracionPuntual;
	private JSpinner spinnerHoraPuntual;
	private JDateChooser dateInicioPuntual;
	private JTextField txtNombreActividadPuntual;
	private JTextPane txtpnDescripcionPuntual;
	private JTextField txtNombreActividadPeriodica;
	private JCheckBox chckbxLunes;
	private JCheckBox chckbxMartes;
	private JCheckBox chckbxMiercoles;
	private JCheckBox chckbxJueves;
	private JCheckBox chckbxViernes;
	private JCheckBox chckbxSabado;
	private JCheckBox chckbxDomingo;
	private JSpinner spinnerInicioLunes;
	private JSpinner spinnerInicioMartes;
	private JSpinner spinnerInicioMiercoles;
	private JSpinner spinnerInicioJueves;
	private JSpinner spinnerInicioViernes;
	private JSpinner spinnerInicioSabado;
	private JSpinner spinnerInicioDomingo;
	private JSpinner spinnerDuracionLunes;
	private JSpinner spinnerDuracionMartes;
	private JSpinner spinnerDuracionMiercoles;
	private JSpinner spinnerDuracionJueves;
	private JSpinner spinnerDuracionViernes;
	private JSpinner spinnerDuracionSabado;
	private JSpinner spinnerDuracionDomingo;
	private JComboBox comboBoxMonitorLunes;
	private JComboBox comboBoxMonitorMartes;
	private JComboBox comboBoxMonitorMiercoles;
	private JComboBox comboBoxMonitorJueves;
	private JComboBox comboBoxMonitorViernes;
	private JComboBox comboBoxMonitorSabado;
	private JComboBox comboBoxMonitorDomingo;
	private JComboBox comboBoxInstalacionLunes;
	private JComboBox comboBoxInstalacionMartes;
	private JComboBox comboBoxInstalacionMiercoles;
	private JComboBox comboBoxInstalacionJueves;
	private JComboBox comboBoxInstalacionViernes;
	private JComboBox comboBoxInstalacionSabado;
	private JComboBox comboBoxInstalacionDomingo;
	private JSpinner spinnerPlazasLunes;
	private JSpinner spinnerPlazasMartes;
	private JSpinner spinnerPlazasMiercoles;
	private JSpinner spinnerPlazasJueves;
	private JSpinner spinnerPlazasViernes;
	private JSpinner spinnerPlazasSabado;
	private JSpinner spinnerPlazasDomingo;
	private JCheckBox chckbxTodoElDiaLunes;
	private JCheckBox chckbxTodoElDiaMartes;
	private JCheckBox chckbxTodoElDiaMiercoles;
	private JCheckBox chckbxTodoElDiaJueves;
	private JCheckBox chckbxTodoElDiaViernes;
	private JCheckBox chckbxTodoElDiaSabado;
	private JCheckBox chckbxTodoElDiaDomingo;
	private JTextPane txtpnDescripcionPeriodica;

	@SuppressWarnings({ "unchecked" })
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
		dateInicioPeriodica.setDateFormatString("dd/MM/yyyy");
		dateInicioPeriodica.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateInicioPeriodica.setDate(new Date(System.currentTimeMillis()));

		JLabel lblHastaEl = new JLabel("Hasta el:");
		panelFechas.add(lblHastaEl);
		lblHastaEl.setFont(new Font("Tahoma", Font.PLAIN, 14));

		dateFinPeriodica = new JDateChooser();
		panelFechas.add(dateFinPeriodica);
		dateFinPeriodica.setDateFormatString("dd/MM/yyyy");
		dateFinPeriodica.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateFinPeriodica.setDate(new Date(System.currentTimeMillis()));

		JPanel panelDiasPeriodica = new JPanel();
		panelPeriodica.add(panelDiasPeriodica);
		panelDiasPeriodica.setLayout(null);

		JLabel lblDiaSemanaPeriodica = new JLabel("D\u00EDa semana");
		lblDiaSemanaPeriodica.setHorizontalAlignment(SwingConstants.CENTER);
		lblDiaSemanaPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDiaSemanaPeriodica.setBounds(63, 11, 87, 14);
		panelDiasPeriodica.add(lblDiaSemanaPeriodica);

		JLabel lblHoraInicioPeriodica = new JLabel("Hora Inicio");
		lblHoraInicioPeriodica.setHorizontalAlignment(SwingConstants.CENTER);
		lblHoraInicioPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHoraInicioPeriodica.setBounds(188, 11, 75, 14);
		panelDiasPeriodica.add(lblHoraInicioPeriodica);

		JLabel lblDuracionPeriodica = new JLabel("Duraci\u00F3n");
		lblDuracionPeriodica.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuracionPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDuracionPeriodica.setBounds(308, 11, 87, 14);
		panelDiasPeriodica.add(lblDuracionPeriodica);

		JLabel lblMonitorPeriodica = new JLabel("Monitor");
		lblMonitorPeriodica.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonitorPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMonitorPeriodica.setBounds(462, 11, 46, 14);
		panelDiasPeriodica.add(lblMonitorPeriodica);

		JLabel lblInstalacionPeriodica = new JLabel("Instalaci\u00F3n");
		lblInstalacionPeriodica.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstalacionPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInstalacionPeriodica.setBounds(562, 11, 75, 14);
		panelDiasPeriodica.add(lblInstalacionPeriodica);

		JLabel lblMaxPlazasPeriodica = new JLabel("Max plazas");
		lblMaxPlazasPeriodica.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaxPlazasPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaxPlazasPeriodica.setBounds(677, 11, 93, 14);
		panelDiasPeriodica.add(lblMaxPlazasPeriodica);

		chckbxLunes = new JCheckBox("LUNES");
		chckbxLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxLunes.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxLunes.setBounds(73, 32, 97, 23);
		panelDiasPeriodica.add(chckbxLunes);

		chckbxMartes = new JCheckBox("MARTES");
		chckbxMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxMartes.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxMartes.setBounds(73, 58, 97, 23);
		panelDiasPeriodica.add(chckbxMartes);

		chckbxMiercoles = new JCheckBox("MI\u00C9RCOLES");
		chckbxMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxMiercoles.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxMiercoles.setBounds(73, 84, 113, 23);
		panelDiasPeriodica.add(chckbxMiercoles);

		chckbxJueves = new JCheckBox("JUEVES");
		chckbxJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJueves.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxJueves.setBounds(73, 110, 97, 23);
		panelDiasPeriodica.add(chckbxJueves);

		chckbxViernes = new JCheckBox("VIERNES");
		chckbxViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxViernes.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxViernes.setBounds(73, 136, 97, 23);
		panelDiasPeriodica.add(chckbxViernes);

		chckbxSabado = new JCheckBox("S\u00C1BADO");
		chckbxSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxSabado.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxSabado.setBounds(73, 162, 97, 23);
		panelDiasPeriodica.add(chckbxSabado);

		chckbxDomingo = new JCheckBox("DOMINGO");
		chckbxDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxDomingo.setHorizontalAlignment(SwingConstants.LEFT);
		chckbxDomingo.setBounds(73, 188, 97, 23);
		panelDiasPeriodica.add(chckbxDomingo);

		JCheckBox chckbxTodos = new JCheckBox("TODOS");
		chckbxTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxTodos.isSelected()) {
					chckbxLunes.setSelected(true);
					chckbxMartes.setSelected(true);
					chckbxMiercoles.setSelected(true);
					chckbxJueves.setSelected(true);
					chckbxViernes.setSelected(true);
					chckbxSabado.setSelected(true);
					chckbxDomingo.setSelected(true);
					/*
					 * AL SELECCIONAR "TODOS" SOLO SE PODRAN EDITAR LOS DATOS DE
					 * UNO DE LOS DIAS, PERO LOS CAMBIOS SE APLICARAN A LOS
					 * SPINNERS Y COMBOBOXES DE TODOS LOS DIAS.
					 */

				} else {
					chckbxLunes.setSelected(false);
					chckbxMartes.setSelected(false);
					chckbxMiercoles.setSelected(false);
					chckbxJueves.setSelected(false);
					chckbxViernes.setSelected(false);
					chckbxSabado.setSelected(false);
					chckbxDomingo.setSelected(false);
				}
			}
		});
		chckbxTodos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxTodos.setBounds(6, 32, 71, 23);
		panelDiasPeriodica.add(chckbxTodos);

		spinnerInicioLunes = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioLunes.setBounds(188, 36, 75, 20);
		panelDiasPeriodica.add(spinnerInicioLunes);

		spinnerInicioMartes = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioMartes.setBounds(188, 59, 75, 20);
		panelDiasPeriodica.add(spinnerInicioMartes);

		spinnerInicioMiercoles = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioMiercoles.setBounds(188, 85, 75, 20);
		panelDiasPeriodica.add(spinnerInicioMiercoles);

		spinnerInicioJueves = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioJueves.setBounds(188, 111, 75, 20);
		panelDiasPeriodica.add(spinnerInicioJueves);

		spinnerInicioViernes = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioViernes.setBounds(188, 137, 75, 20);
		panelDiasPeriodica.add(spinnerInicioViernes);

		spinnerInicioSabado = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioSabado.setBounds(188, 163, 75, 20);
		panelDiasPeriodica.add(spinnerInicioSabado);

		spinnerInicioDomingo = new JSpinner((new SpinnerNumberModel(15, 0, 23, 1)));
		spinnerInicioDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicioDomingo.setBounds(188, 189, 75, 20);
		panelDiasPeriodica.add(spinnerInicioDomingo);

		spinnerDuracionLunes = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionLunes.setBounds(308, 36, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionLunes);

		spinnerDuracionMartes = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionMartes.setBounds(308, 59, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionMartes);

		spinnerDuracionMiercoles = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionMiercoles.setBounds(308, 85, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionMiercoles);

		spinnerDuracionJueves = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionJueves.setBounds(308, 111, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionJueves);

		spinnerDuracionViernes = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionViernes.setBounds(308, 137, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionViernes);

		spinnerDuracionSabado = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionSabado.setBounds(308, 163, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionSabado);

		spinnerDuracionDomingo = new JSpinner(new SpinnerNumberModel(1, 1, 23, 1));
		spinnerDuracionDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuracionDomingo.setBounds(308, 189, 32, 20);
		panelDiasPeriodica.add(spinnerDuracionDomingo);

		comboBoxMonitorLunes = new JComboBox(nombresMonitores);
		comboBoxMonitorLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorLunes.setBounds(445, 36, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorLunes);

		comboBoxMonitorMartes = new JComboBox(nombresMonitores);
		comboBoxMonitorMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorMartes.setBounds(445, 59, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorMartes);

		comboBoxMonitorMiercoles = new JComboBox(nombresMonitores);
		comboBoxMonitorMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorMiercoles.setBounds(445, 87, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorMiercoles);

		comboBoxMonitorJueves = new JComboBox(nombresMonitores);
		comboBoxMonitorJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorJueves.setBounds(445, 113, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorJueves);

		comboBoxMonitorViernes = new JComboBox(nombresMonitores);
		comboBoxMonitorViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorViernes.setBounds(445, 139, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorViernes);

		comboBoxMonitorSabado = new JComboBox(nombresMonitores);
		comboBoxMonitorSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorSabado.setBounds(445, 165, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorSabado);

		comboBoxMonitorDomingo = new JComboBox(nombresMonitores);
		comboBoxMonitorDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxMonitorDomingo.setBounds(445, 191, 93, 20);
		panelDiasPeriodica.add(comboBoxMonitorDomingo);

		comboBoxInstalacionLunes = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionLunes.setBounds(561, 36, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionLunes);

		comboBoxInstalacionMartes = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionMartes.setBounds(561, 59, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionMartes);

		comboBoxInstalacionMiercoles = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionMiercoles.setBounds(561, 85, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionMiercoles);

		comboBoxInstalacionJueves = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionJueves.setBounds(561, 111, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionJueves);

		comboBoxInstalacionViernes = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionViernes.setBounds(561, 137, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionViernes);

		comboBoxInstalacionSabado = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionSabado.setBounds(561, 163, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionSabado);

		comboBoxInstalacionDomingo = new JComboBox(nombresInstalaciones);
		comboBoxInstalacionDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBoxInstalacionDomingo.setBounds(561, 189, 93, 20);
		panelDiasPeriodica.add(comboBoxInstalacionDomingo);

		spinnerPlazasLunes = new JSpinner();
		spinnerPlazasLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasLunes.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasLunes.setBounds(687, 36, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasLunes);

		spinnerPlazasMartes = new JSpinner(
				new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasMartes.setBounds(687, 59, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasMartes);

		spinnerPlazasMiercoles = new JSpinner(
				new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasMiercoles.setBounds(687, 85, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasMiercoles);

		spinnerPlazasJueves = new JSpinner(
				new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasJueves.setBounds(687, 111, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasJueves);

		spinnerPlazasViernes = new JSpinner(
				new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasViernes.setBounds(687, 137, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasViernes);

		spinnerPlazasSabado = new JSpinner(
				new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasSabado.setBounds(687, 163, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasSabado);

		spinnerPlazasDomingo = new JSpinner(
				new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerPlazasDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerPlazasDomingo.setBounds(687, 189, 83, 20);
		panelDiasPeriodica.add(spinnerPlazasDomingo);

		JLabel lblNombreActividadPeriodica = new JLabel("Nombre actividad:");
		lblNombreActividadPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreActividadPeriodica.setBounds(63, 234, 144, 14);
		panelDiasPeriodica.add(lblNombreActividadPeriodica);

		txtNombreActividadPeriodica = new JTextField();
		txtNombreActividadPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNombreActividadPeriodica.setText("Introduzca aqu\u00ED el nombre");
		txtNombreActividadPeriodica.setBounds(237, 231, 178, 20);
		panelDiasPeriodica.add(txtNombreActividadPeriodica);
		txtNombreActividadPeriodica.setColumns(10);

		JLabel lblDescripcionActividadPeriodica = new JLabel("Descripci\u00F3n actividad:");
		lblDescripcionActividadPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescripcionActividadPeriodica.setBounds(63, 276, 164, 23);
		panelDiasPeriodica.add(lblDescripcionActividadPeriodica);

		txtpnDescripcionPeriodica = new JTextPane();
		txtpnDescripcionPeriodica.setBorder(new LineBorder(Color.BLACK));
		txtpnDescripcionPeriodica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtpnDescripcionPeriodica.setText("Introduzca aqu\u00ED la descripci\u00F3n");
		txtpnDescripcionPeriodica.setBounds(237, 274, 178, 75);
		panelDiasPeriodica.add(txtpnDescripcionPeriodica);

		chckbxTodoElDiaLunes = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaLunes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaLunes.isSelected()) {
					spinnerDuracionLunes.setEnabled(false);
					spinnerInicioLunes.setEnabled(false);
				} else {
					spinnerDuracionLunes.setEnabled(true);
					spinnerInicioLunes.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaLunes.setBounds(346, 34, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaLunes);

		chckbxTodoElDiaMartes = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaMartes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaMartes.isSelected()) {
					spinnerDuracionMartes.setEnabled(false);
					spinnerInicioMartes.setEnabled(false);
				} else {
					spinnerDuracionMartes.setEnabled(true);
					spinnerInicioMartes.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaMartes.setBounds(346, 60, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaMartes);

		chckbxTodoElDiaMiercoles = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaMiercoles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaMiercoles.isSelected()) {
					spinnerDuracionMiercoles.setEnabled(false);
					spinnerInicioMiercoles.setEnabled(false);
				} else {
					spinnerDuracionMiercoles.setEnabled(true);
					spinnerInicioMiercoles.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaMiercoles.setBounds(346, 86, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaMiercoles);

		chckbxTodoElDiaJueves = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaJueves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaJueves.isSelected()) {
					spinnerDuracionJueves.setEnabled(false);
					spinnerInicioJueves.setEnabled(false);
				} else {
					spinnerDuracionJueves.setEnabled(true);
					spinnerInicioJueves.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaJueves.setBounds(346, 112, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaJueves);

		chckbxTodoElDiaViernes = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaViernes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaViernes.isSelected()) {
					spinnerDuracionViernes.setEnabled(false);
					spinnerInicioViernes.setEnabled(false);
				} else {
					spinnerDuracionViernes.setEnabled(true);
					spinnerInicioViernes.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaViernes.setBounds(346, 136, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaViernes);

		chckbxTodoElDiaSabado = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaSabado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaSabado.isSelected()) {
					spinnerDuracionSabado.setEnabled(false);
					spinnerInicioSabado.setEnabled(false);
				} else {
					spinnerDuracionSabado.setEnabled(true);
					spinnerInicioSabado.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaSabado.setBounds(346, 162, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaSabado);

		chckbxTodoElDiaDomingo = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDiaDomingo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxTodoElDiaDomingo.isSelected()) {
					spinnerDuracionDomingo.setEnabled(false);
					spinnerInicioDomingo.setEnabled(false);
				} else {
					spinnerDuracionDomingo.setEnabled(true);
					spinnerInicioDomingo.setEnabled(true);
				}
			}
		});
		chckbxTodoElDiaDomingo.setBounds(346, 188, 97, 23);
		panelDiasPeriodica.add(chckbxTodoElDiaDomingo);
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
			ManagerAdmin.crearActividad(txtNombreActividadPuntual.getText(), txtpnDescripcionPuntual.getText());
			ManagerAdmin.crearReservaActividad(dateTimeInicio, dateTimeFin, idInst, idMonitor,
					(int) spinnerPlazasPuntual.getValue());
			JOptionPane.showMessageDialog(this, "La reserva se ha insertado con éxito");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		}

	}

	public void insertarActividadPeriodica() {
		ManagerAdmin.crearActividad(txtNombreActividadPeriodica.getText(), txtpnDescripcionPeriodica.getText());
		try {
			if (chckbxLunes.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionLunes.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorLunes.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioLunes, spinnerDuracionLunes)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioLunes, spinnerDuracionLunes)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.LUNES, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasLunes.getValue(), chckbxTodoElDiaLunes.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del LUNES se han insertado con éxito");
			}
			
			if (chckbxMartes.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionMartes.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorMartes.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioMartes, spinnerDuracionMartes)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioMartes, spinnerDuracionMartes)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.MARTES, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasMartes.getValue(), chckbxTodoElDiaMartes.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del MARTES se han insertado con éxito");
			}
			
			if (chckbxMiercoles.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionMiercoles.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorMiercoles.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioMiercoles, spinnerDuracionMiercoles)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioMiercoles, spinnerDuracionMiercoles)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.MIERCOLES, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasMiercoles.getValue(), chckbxTodoElDiaMiercoles.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del MIÉRCOLES se han insertado con éxito");
			}
			
			if (chckbxJueves.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionJueves.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorJueves.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioJueves, spinnerDuracionJueves)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioJueves, spinnerDuracionJueves)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.JUEVES, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasJueves.getValue(), chckbxTodoElDiaJueves.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del JUEVES se han insertado con éxito");
			}
			
			if (chckbxViernes.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionViernes.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorViernes.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioViernes, spinnerDuracionViernes)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioViernes, spinnerDuracionViernes)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.VIERNES, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasViernes.getValue(), chckbxTodoElDiaViernes.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del VIERNES se han insertado con éxito");
			}
			
			if (chckbxSabado.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionSabado.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorSabado.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioSabado, spinnerDuracionSabado)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioSabado, spinnerDuracionSabado)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.SABADO, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasSabado.getValue(), chckbxTodoElDiaSabado.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del SÁBADO se han insertado con éxito");
			}
			
			if (chckbxDomingo.isSelected()) {
				Long idInst = instalaciones.get(comboBoxInstalacionDomingo.getSelectedIndex()).getIdInst();
				Long idMonitor = monitores.get(comboBoxMonitorDomingo.getSelectedIndex()).getIdMonitor();
				DateTime dateTimeInicio = crearDateTimePeriodica(spinnerInicioDomingo, spinnerDuracionDomingo)[0];
				DateTime dateTimeFin = crearDateTimePeriodica(spinnerInicioDomingo, spinnerDuracionDomingo)[1];

				ManagerAdmin.crearReservaActividadSemanal(DiasSemana.DOMINGO, dateTimeInicio, dateTimeFin, idInst,
						idMonitor, (int) spinnerPlazasDomingo.getValue(), chckbxTodoElDiaDomingo.isSelected());
				JOptionPane.showMessageDialog(this, "Las reservas del DOMINGO se han insertado con éxito");
			}
		} catch (ExcepcionReserva e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	private DateTime[] crearDateTimePeriodica(JSpinner spInicio, JSpinner spDuracion) {
		int horaInicio = (int) spInicio.getValue();
		String horaInicioString = String.valueOf(horaInicio);
		horaInicioString += ":00:00";
		int duracion = (int) spDuracion.getValue();
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
		String fechaInicio = fmt.format(dateInicioPeriodica.getDate());
		String fechaFin = fmt.format(dateFinPeriodica.getDate());

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dateTimeInicio = formatter.parseDateTime(fechaInicio + " " + horaInicioString);
		DateTime dateTimeFin = formatter.parseDateTime(fechaFin + " " + horaFinString);

		return new DateTime[] { dateTimeInicio, dateTimeFin };
	}
}
