package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.toedter.calendar.JDateChooser;

import CapaDatos.MonitorDatos;
import CapaDatos.UsuarioDatos;
import CapaInterfaz.Monitor.ChkCellEditor;
import CapaInterfaz.Monitor.ChkCellRenderer;
import CapaInterfaz.Monitor.ModeloNoEditable;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerAdmin;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

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
	private JRadioButton rdbtnSemanal;
	private JRadioButton rdbtnMensual;
	private JDateChooser dateInicio;
	private JDateChooser dateFin;
	private JPanel panelMostrarEleccion;
	private JPanel panelPuntual;
	private List<Instalacion> instalaciones;
	private JTable table;
	private ModeloNoEditable modeloTabla;

	public VentanaCrearActividad() {
		setTitle("Admin -> Crear actividades");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(false);
		setBounds(100, 100, 786, 525);

		JLabel lblReservaDeCentro = new JLabel("Reserva de centro peri\u00F3dica");
		lblReservaDeCentro.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservaDeCentro.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblReservaDeCentro, BorderLayout.NORTH);

		JPanel panelBotones = new JPanel();
		getContentPane().add(panelBotones, BorderLayout.SOUTH);

		JButton btnCrearActividad = new JButton("Crear actividad(es)");
		btnCrearActividad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertarActividad();
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

		rdbtnSemanal = new JRadioButton("Puntual");
		rdbtnSemanal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) panelMostrarEleccion.getLayout()).show(panelMostrarEleccion, "panelSemanal");
			}
		});
		rdbtnSemanal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnSemanal.setSelected(true);
		panelTipo.add(rdbtnSemanal);

		rdbtnMensual = new JRadioButton("Peri\u00F3dica");
		rdbtnMensual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((CardLayout) panelMostrarEleccion.getLayout()).show(panelMostrarEleccion, "panelMensual");
			}
		});
		rdbtnMensual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTipo.add(rdbtnMensual);

		ButtonGroup grupoRadioButton = new ButtonGroup();
		grupoRadioButton.add(rdbtnMensual);
		grupoRadioButton.add(rdbtnSemanal);

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
		gbl_panelPuntual.columnWidths = new int[] { 0 };
		gbl_panelPuntual.rowHeights = new int[] { 0 };
		gbl_panelPuntual.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelPuntual.rowWeights = new double[] { Double.MIN_VALUE };
		panelPuntual.setLayout(gbl_panelPuntual);

		JPanel panelPeriodica = new JPanel();
		panelMostrarEleccion.add(panelPeriodica, "panelMensual");
		panelPeriodica.setLayout(new BorderLayout(0, 0));

		JPanel panelFechas = new JPanel();
		panelPeriodica.add(panelFechas, BorderLayout.NORTH);

		JLabel lblDesdeEl = new JLabel("Desde el:");
		panelFechas.add(lblDesdeEl);
		lblDesdeEl.setFont(new Font("Tahoma", Font.PLAIN, 14));

		dateInicio = new JDateChooser();
		panelFechas.add(dateInicio);
		BorderLayout bl_dateInicio = (BorderLayout) dateInicio.getLayout();
		dateInicio.setDateFormatString("dd/MM/yyyy");
		dateInicio.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateInicio.setDate(new Date(System.currentTimeMillis()));

		JLabel lblHastaEl = new JLabel("Hasta el:");
		panelFechas.add(lblHastaEl);
		lblHastaEl.setFont(new Font("Tahoma", Font.PLAIN, 14));

		dateFin = new JDateChooser();
		panelFechas.add(dateFin);
		BorderLayout bl_dateFin = (BorderLayout) dateFin.getLayout();
		dateFin.setDateFormatString("dd/MM/yyyy");
		dateFin.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateFin.setDate(new Date(System.currentTimeMillis()));

		String[] columnas = {"Día", "Hora inicio", "Duración", 
				"Monitor", "Instalación", "Máx. plazas"};
		
		modeloTabla = new ModeloNoEditable(columnas, 0);
		table = new JTable(new DefaultTableModel(
			new Object[][] {
				{DiasSemana.LUNES.name(), null, null, null, null, null},
				{DiasSemana.MARTES.name(), null, null, null, null, null},
				{DiasSemana.MIERCOLES.name(), null, null, null, null, null},
				{DiasSemana.JUEVES.name(), null, null, null, null, null},
				{DiasSemana.VIERNES.name(), null, null, null, null, null},
				{DiasSemana.SABADO.name(), null, null, null, null, null},
				{DiasSemana.DOMINGO.name(), null, null, null, null, null},
			},
			new String[] {
				"D\u00EDa", "Hora inicio", "Duraci\u00F3n", "Monitor", "Instalaci\u00F3n", "M\u00E1x. plazas"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, true, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
//		
//		table.getColumnModel().getColumn(0).setCellRenderer(new ChkCellRenderer());
//		table.getColumnModel().getColumn(0).setCellEditor(new ChkCellEditor());
//		
//		JCheckBox chk = new JCheckBox();
//		TableColumn tc = table.getColumnModel().getColumn(0);
//		TableCellEditor tce = new DefaultCellEditor(chk);
//		tc.setCellEditor(tce);
		
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPaneTabla = new JScrollPane(table);
		panelPeriodica.add(scrollPaneTabla, BorderLayout.CENTER);
	}

	public void insertarActividad() {
		// if (rdbtnSemanal.isSelected()) {
		// List<DiasSemana> dias = new ArrayList<>();
		// JCheckBox checkLunes = (JCheckBox) panelPuntual.getComponents()[0];
		// JCheckBox checkMartes = (JCheckBox) panelPuntual.getComponents()[1];
		// JCheckBox checkMiercoles = (JCheckBox)
		// panelPuntual.getComponents()[2];
		// JCheckBox checkJueves = (JCheckBox) panelPuntual.getComponents()[3];
		// JCheckBox checkViernes = (JCheckBox) panelPuntual.getComponents()[4];
		// JCheckBox checkSabado = (JCheckBox) panelPuntual.getComponents()[5];
		// JCheckBox checkDomingo = (JCheckBox) panelPuntual.getComponents()[6];
		//
		// if (checkLunes.isSelected())
		// dias.add(DiasSemana.LUNES);
		// if (checkMartes.isSelected())
		// dias.add(DiasSemana.MARTES);
		// if (checkMiercoles.isSelected())
		// dias.add(DiasSemana.MIERCOLES);
		// if (checkJueves.isSelected())
		// dias.add(DiasSemana.JUEVES);
		// if (checkViernes.isSelected())
		// dias.add(DiasSemana.VIERNES);
		// if (checkSabado.isSelected())
		// dias.add(DiasSemana.SABADO);
		// if (checkDomingo.isSelected())
		// dias.add(DiasSemana.DOMINGO);
		//
		// Long idInst =
		// instalaciones.get(comboBoxInstalacion.getSelectedIndex()).getIdInst();
		// int hora = (int) spinnerHora.getValue();
		// int duracion = (int) spinnerDuracion.getValue();
		//
		// String horaInicio = String.valueOf(hora);
		// horaInicio += ":00:00";
		// String horaFin = String.valueOf(hora + duracion);
		// horaFin += ":00:00";
		//
		// DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		// String fechaInicio = fmt.format(dateInicio.getDate());
		// String fechaFin = fmt.format(dateFin.getDate());
		//
		// DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy
		// HH:mm:ss");
		// DateTime dateTimeInicio = formatter.parseDateTime(fechaInicio + " " +
		// horaInicio);
		// DateTime dateTimeFin = formatter.parseDateTime(fechaFin + " " +
		// horaFin);
		//
		// }
		//
		// if (rdbtnMensual.isSelected()) {
		//
		// }
	}
}
