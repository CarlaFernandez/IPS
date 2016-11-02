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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.toedter.calendar.JDateChooser;

import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;

public class VentanaReservaCentroPeriodica extends JDialog {
	private static final long serialVersionUID = 1L;
	private JRadioButton rdbtnSemanal;
	private JRadioButton rdbtnMensual;
	private JPanel panelMensualSemana1;
	private JPanel panelMensualSemana2;
	private JPanel panelMensualSemana3;
	private JPanel panelMensualSemana4;
	private JDateChooser dateInicio;
	private JDateChooser dateFin;
	private JPanel panelMostrarEleccion;
	private JPanel panelSemanal;
	private List<Instalacion> instalaciones;
	private JSpinner spinnerDuracion;
	private JComboBox comboBoxInstalacion;
	private JSpinner spinnerHora;

	public VentanaReservaCentroPeriodica() {
		setTitle("Admin -> Reserva Centro Periódica");
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

		JButton btnCrearReserva = new JButton("Crear reserva");
		btnCrearReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertarReserva();
			}
		});
		btnCrearReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelBotones.add(btnCrearReserva);

		JPanel panelReserva = new JPanel();
		getContentPane().add(panelReserva, BorderLayout.CENTER);
		panelReserva.setLayout(new BorderLayout(0, 0));

		JPanel panelTipo = new JPanel();
		panelReserva.add(panelTipo, BorderLayout.NORTH);

		JLabel lblTipoReserva = new JLabel("Tipo:");
		lblTipoReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelTipo.add(lblTipoReserva);

		rdbtnSemanal = new JRadioButton("Semanal");
		rdbtnSemanal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				((CardLayout) panelMostrarEleccion.getLayout()).show(panelMostrarEleccion, "panelSemanal");
			}
		});
		rdbtnSemanal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rdbtnSemanal.setSelected(true);
		panelTipo.add(rdbtnSemanal);

		rdbtnMensual = new JRadioButton("Mensual");
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

		JPanel panelFechas = new JPanel();
		panelOpciones.add(panelFechas, BorderLayout.NORTH);

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
		
		JLabel lblHora = new JLabel("Hora:");
		lblHora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelFechas.add(lblHora);
		
		spinnerHora = new JSpinner();
		spinnerHora.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerHora.setModel(new SpinnerNumberModel(12, 0, 23, 1));
		panelFechas.add(spinnerHora);

		JLabel lblDuracion = new JLabel("Duraci\u00F3n (horas):");
		lblDuracion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelFechas.add(lblDuracion);

		spinnerDuracion = new JSpinner();
		spinnerDuracion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelFechas.add(spinnerDuracion);
		spinnerDuracion.setModel(new SpinnerNumberModel(1, 1, 24, 1));

		JLabel lblInstalacion = new JLabel("Instalaci\u00F3n:");
		lblInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelFechas.add(lblInstalacion);

		
		instalaciones = ManagerAdmin.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}
		comboBoxInstalacion = new JComboBox(instalacionesStrings);
		comboBoxInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelFechas.add(comboBoxInstalacion);

		panelMostrarEleccion = new JPanel();
		panelOpciones.add(panelMostrarEleccion, BorderLayout.CENTER);
		panelMostrarEleccion.setLayout(new CardLayout(0, 0));

		panelSemanal = new JPanel();
		panelMostrarEleccion.add(panelSemanal, "panelSemanal");
		panelSemanal.setLayout(new GridLayout(0, 7, 0, 0));

		JCheckBox chckbxLunes = new JCheckBox("Lunes");
		panelSemanal.add(chckbxLunes);
		chckbxLunes.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxMartes = new JCheckBox("Martes");
		panelSemanal.add(chckbxMartes);
		chckbxMartes.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxMiercoles = new JCheckBox("Mi\u00E9rcoles");
		panelSemanal.add(chckbxMiercoles);
		chckbxMiercoles.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxJueves = new JCheckBox("Jueves");
		panelSemanal.add(chckbxJueves);
		chckbxJueves.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxViernes = new JCheckBox("Viernes");
		panelSemanal.add(chckbxViernes);
		chckbxViernes.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxSabado = new JCheckBox("S\u00E1bado");
		panelSemanal.add(chckbxSabado);
		chckbxSabado.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JCheckBox chckbxDomingo = new JCheckBox("Domingo");
		panelSemanal.add(chckbxDomingo);
		chckbxDomingo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		JPanel panelMensual = new JPanel();
		panelMostrarEleccion.add(panelMensual, "panelMensual");
		panelMensual.setLayout(new GridLayout(4, 7, 0, 0));

		panelMensualSemana1 = new JPanel();
		panelMensual.add(panelMensualSemana1);
		panelMensualSemana1.setLayout(new GridLayout(1, 7, 0, 0));

		JLabel lblSemana1 = new JLabel("Semana 1:");
		lblSemana1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelMensualSemana1.add(lblSemana1);

		JCheckBox ck1Lunes = new JCheckBox("Lunes");
		panelMensualSemana1.add(ck1Lunes);

		JCheckBox ck1Martes = new JCheckBox("Martes");
		panelMensualSemana1.add(ck1Martes);

		JCheckBox ck1Miercoles = new JCheckBox("Mi\u00E9rcoles");
		panelMensualSemana1.add(ck1Miercoles);

		JCheckBox ck1Jueves = new JCheckBox("Jueves");
		panelMensualSemana1.add(ck1Jueves);

		JCheckBox ck1Viernes = new JCheckBox("Viernes");
		panelMensualSemana1.add(ck1Viernes);

		JCheckBox ck1Sabado = new JCheckBox("S\u00E1bado");
		panelMensualSemana1.add(ck1Sabado);

		JCheckBox ck1Domingo = new JCheckBox("Domingo");
		panelMensualSemana1.add(ck1Domingo);

		panelMensualSemana2 = new JPanel();
		panelMensual.add(panelMensualSemana2);
		panelMensualSemana2.setLayout(new GridLayout(1, 7, 0, 0));

		JLabel lblSemana2 = new JLabel("Semana 2:");
		lblSemana2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelMensualSemana2.add(lblSemana2);

		JCheckBox ck2Lunes = new JCheckBox("Lunes");
		panelMensualSemana2.add(ck2Lunes);

		JCheckBox ck2Martes = new JCheckBox("Martes");
		panelMensualSemana2.add(ck2Martes);

		JCheckBox ck2Miercoles = new JCheckBox("Mi\u00E9rcoles");
		panelMensualSemana2.add(ck2Miercoles);

		JCheckBox ck2Jueves = new JCheckBox("Jueves");
		panelMensualSemana2.add(ck2Jueves);

		JCheckBox ck2Viernes = new JCheckBox("Viernes");
		panelMensualSemana2.add(ck2Viernes);

		JCheckBox ck2Sabado = new JCheckBox("S\u00E1bado");
		panelMensualSemana2.add(ck2Sabado);

		JCheckBox ck2Domingo = new JCheckBox("Domingo");
		panelMensualSemana2.add(ck2Domingo);

		panelMensualSemana3 = new JPanel();
		panelMensual.add(panelMensualSemana3);
		panelMensualSemana3.setLayout(new GridLayout(1, 7, 0, 0));

		JLabel lblSemana3 = new JLabel("Semana 3:");
		lblSemana3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelMensualSemana3.add(lblSemana3);

		JCheckBox ck3Lunes = new JCheckBox("Lunes");
		panelMensualSemana3.add(ck3Lunes);

		JCheckBox ck3Martes = new JCheckBox("Martes");
		panelMensualSemana3.add(ck3Martes);

		JCheckBox ck3Miercoles = new JCheckBox("Mi\u00E9rcoles");
		panelMensualSemana3.add(ck3Miercoles);

		JCheckBox ck3Jueves = new JCheckBox("Jueves");
		panelMensualSemana3.add(ck3Jueves);

		JCheckBox ck3Viernes = new JCheckBox("Viernes");
		panelMensualSemana3.add(ck3Viernes);

		JCheckBox ck3Sabado = new JCheckBox("S\u00E1bado");
		panelMensualSemana3.add(ck3Sabado);

		JCheckBox ck3Domingo = new JCheckBox("Domingo");
		panelMensualSemana3.add(ck3Domingo);

		panelMensualSemana4 = new JPanel();
		panelMensual.add(panelMensualSemana4);
		panelMensualSemana4.setLayout(new GridLayout(1, 7, 0, 0));

		JLabel lblSemana4 = new JLabel("Semana 4:");
		lblSemana4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelMensualSemana4.add(lblSemana4);

		JCheckBox ck4Lunes = new JCheckBox("Lunes");
		panelMensualSemana4.add(ck4Lunes);

		JCheckBox ck4Martes = new JCheckBox("Martes");
		panelMensualSemana4.add(ck4Martes);

		JCheckBox ck4Miercoles = new JCheckBox("Mi\u00E9rcoles");
		panelMensualSemana4.add(ck4Miercoles);

		JCheckBox ck4Jueves = new JCheckBox("Jueves");
		panelMensualSemana4.add(ck4Jueves);

		JCheckBox ck4Viernes = new JCheckBox("Viernes");
		panelMensualSemana4.add(ck4Viernes);

		JCheckBox ck4Sabado = new JCheckBox("S\u00E1bado");
		panelMensualSemana4.add(ck4Sabado);

		JCheckBox ck4Domingo = new JCheckBox("Domingo");
		panelMensualSemana4.add(ck4Domingo);

	}

	public void insertarReserva() {
		if (rdbtnSemanal.isSelected()) {
			List<DiasSemana> dias = new ArrayList<>();
			JCheckBox checkLunes = (JCheckBox) panelSemanal.getComponents()[0];
			JCheckBox checkMartes = (JCheckBox) panelSemanal.getComponents()[1];
			JCheckBox checkMiercoles = (JCheckBox) panelSemanal.getComponents()[2];
			JCheckBox checkJueves = (JCheckBox) panelSemanal.getComponents()[3];
			JCheckBox checkViernes = (JCheckBox) panelSemanal.getComponents()[4];
			JCheckBox checkSabado = (JCheckBox) panelSemanal.getComponents()[5];
			JCheckBox checkDomingo = (JCheckBox) panelSemanal.getComponents()[6];

			if (checkLunes.isSelected())
				dias.add(DiasSemana.LUNES);
			if (checkMartes.isSelected())
				dias.add(DiasSemana.MARTES);
			if (checkMiercoles.isSelected())
				dias.add(DiasSemana.MIERCOLES);
			if (checkJueves.isSelected())
				dias.add(DiasSemana.JUEVES);
			if (checkViernes.isSelected())
				dias.add(DiasSemana.VIERNES);
			if (checkSabado.isSelected())
				dias.add(DiasSemana.SABADO);
			if (checkDomingo.isSelected())
				dias.add(DiasSemana.DOMINGO);

			Long idInst = instalaciones.get(comboBoxInstalacion.getSelectedIndex()).getIdInst();
			int hora = (int) spinnerHora.getValue();
			int duracion = (int)spinnerDuracion.getValue();
			
			String horaInicio = String.valueOf(hora);
			horaInicio += ":00:00";
			String horaFin = String.valueOf(hora + duracion);
			horaFin += ":00:00";

			DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			String fechaInicio = fmt.format(dateInicio.getDate());
			String fechaFin = fmt.format(dateFin.getDate());

			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
			DateTime dateTimeInicio = formatter.parseDateTime(fechaInicio + " " + horaInicio);				
			DateTime dateTimeFin = formatter.parseDateTime(fechaFin + " " + horaFin);

		}

		if (rdbtnMensual.isSelected()) {

		}
	}

}
