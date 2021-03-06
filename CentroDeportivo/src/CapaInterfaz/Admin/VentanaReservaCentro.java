package CapaInterfaz.Admin;

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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.toedter.calendar.JDateChooser;

import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;

@SuppressWarnings(value = { "rawtypes", "deprecation", "unchecked" })
public class VentanaReservaCentro extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	private int horaInicio;
	private int horaFin;
	private JSpinner spinnerFin;
	private JSpinner spinnerInicio;
	private JDateChooser dateInicio;
	private JCheckBox chckbxTodoElDia;
	private JDateChooser dateFin;
	private JCheckBox chckbxPeridioca;
	private JComboBox comboBoxDia;

	@SuppressWarnings({})
	public VentanaReservaCentro() {
		setTitle("Admin -> Reserva Centro");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		setResizable(false);
		setBounds(100, 100, 786, 525);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, 0.0,
				0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblRealizarReservaDe = new JLabel("Realizar reserva de centro");
		lblRealizarReservaDe.setFont(new Font("Arial Black", Font.BOLD, 25));
		GridBagConstraints gbc_lblRealizarReservaDe = new GridBagConstraints();
		gbc_lblRealizarReservaDe.gridwidth = 2;
		gbc_lblRealizarReservaDe.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRealizarReservaDe.insets = new Insets(0, 0, 5, 5);
		gbc_lblRealizarReservaDe.gridx = 1;
		gbc_lblRealizarReservaDe.gridy = 1;
		getContentPane().add(lblRealizarReservaDe, gbc_lblRealizarReservaDe);

		JLabel lblInicio = new JLabel("Fecha inicio:");
		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblInicio = new GridBagConstraints();
		gbc_lblInicio.insets = new Insets(25, 100, 5, 5);
		gbc_lblInicio.gridx = 0;
		gbc_lblInicio.gridy = 2;
		getContentPane().add(lblInicio, gbc_lblInicio);
		gbc_lblInicio.insets = new Insets(50, 0, 5, 5);
		gbc_lblInicio.gridx = 1;
		gbc_lblInicio.gridy = 1;

		dateInicio = new JDateChooser(new Date(System.currentTimeMillis()));
		dateInicio.setDateFormatString("dd/MM/yyyy");
		GridBagConstraints gbc_dateInicio = new GridBagConstraints();
		gbc_dateInicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateInicio.insets = new Insets(25, 0, 5, 5);
		gbc_dateInicio.gridx = 1;
		gbc_dateInicio.gridy = 2;
		dateInicio.setMinSelectableDate(new Date(System.currentTimeMillis()));
		dateInicio.setDate(new Date(System.currentTimeMillis()));
		getContentPane().add(dateInicio, gbc_dateInicio);

		JLabel lblHoraInicio = new JLabel("Hora inicio: ");
		lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHoraInicio = new GridBagConstraints();
		gbc_lblHoraInicio.insets = new Insets(25, 100, 5, 5);
		gbc_lblHoraInicio.gridx = 0;
		gbc_lblHoraInicio.gridy = 3;
		getContentPane().add(lblHoraInicio, gbc_lblHoraInicio);

		JLabel lblHoraFin = new JLabel("Hora fin:");
		lblHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHoraFin = new GridBagConstraints();
		gbc_lblHoraFin.insets = new Insets(15, 100, 5, 5);
		gbc_lblHoraFin.gridx = 0;
		gbc_lblHoraFin.gridy = 4;
		getContentPane().add(lblHoraFin, gbc_lblHoraFin);

		spinnerInicio = new JSpinner();
		spinnerInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerInicio.setModel(new SpinnerNumberModel(19, 0, 23, 1));
		GridBagConstraints gbc_spinnerInicio = new GridBagConstraints();
		gbc_spinnerInicio.insets = new Insets(25, 0, 5, 5);
		gbc_spinnerInicio.gridx = 1;
		gbc_spinnerInicio.gridy = 3;
		getContentPane().add(spinnerInicio, gbc_spinnerInicio);

		chckbxTodoElDia = new JCheckBox("Todo el d\u00EDa");
		chckbxTodoElDia.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (chckbxTodoElDia.isSelected()) {
					horaInicio = 0;
					horaFin = 0;
					spinnerInicio.setValue(horaInicio);
					spinnerFin.setValue(horaFin);
					spinnerInicio.setEnabled(false);
					spinnerFin.setEnabled(false);
					lblHoraInicio.setEnabled(false);
					lblHoraFin.setEnabled(false);
				} else {
					horaInicio = new Date(System.currentTimeMillis())
							.getHours();
					horaFin = new Date(System.currentTimeMillis()).getHours()
							+ 1;
					spinnerInicio.setValue(horaInicio);
					spinnerFin.setValue(horaFin);
					spinnerInicio.setEnabled(true);
					spinnerFin.setEnabled(true);
					lblHoraInicio.setEnabled(true);
					lblHoraFin.setEnabled(true);
				}
			}
		});
		chckbxTodoElDia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxTodoElDa = new GridBagConstraints();
		gbc_chckbxTodoElDa.anchor = GridBagConstraints.WEST;
		gbc_chckbxTodoElDa.insets = new Insets(25, 0, 5, 5);
		gbc_chckbxTodoElDa.gridx = 2;
		gbc_chckbxTodoElDa.gridy = 3;
		getContentPane().add(chckbxTodoElDia, gbc_chckbxTodoElDa);

		spinnerFin = new JSpinner();
		spinnerFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerFin.setModel(new SpinnerNumberModel(20, 0, 23, 1));
		GridBagConstraints gbc_spinnerFin = new GridBagConstraints();
		gbc_spinnerFin.insets = new Insets(15, 0, 5, 5);
		gbc_spinnerFin.gridx = 1;
		gbc_spinnerFin.gridy = 4;
		getContentPane().add(spinnerFin, gbc_spinnerFin);

		instalaciones = ManagerAdmin.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}

		JLabel lblInstalacion = new JLabel("Instalaci\u00F3n:");
		lblInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblInstalacion = new GridBagConstraints();
		gbc_lblInstalacion.anchor = GridBagConstraints.EAST;
		gbc_lblInstalacion.insets = new Insets(25, 100, 5, 5);
		gbc_lblInstalacion.gridx = 0;
		gbc_lblInstalacion.gridy = 5;
		getContentPane().add(lblInstalacion, gbc_lblInstalacion);
		comboBoxInstalaciones = new JComboBox(instalacionesStrings);
		comboBoxInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBoxInstalacion = new GridBagConstraints();
		gbc_comboBoxInstalacion.insets = new Insets(25, 0, 5, 5);
		gbc_comboBoxInstalacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxInstalacion.gridx = 1;
		gbc_comboBoxInstalacion.gridy = 5;
		getContentPane().add(comboBoxInstalaciones, gbc_comboBoxInstalacion);

		JLabel lblDia = new JLabel("D\u00EDa:");
		lblDia.setEnabled(false);
		lblDia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblDia = new GridBagConstraints();
		gbc_lblDia.insets = new Insets(15, 100, 5, 5);
		gbc_lblDia.gridx = 0;
		gbc_lblDia.gridy = 8;
		getContentPane().add(lblDia, gbc_lblDia);

		comboBoxDia = new JComboBox(DiasSemana.values());
		comboBoxDia.setEnabled(false);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(15, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 8;
		getContentPane().add(comboBoxDia, gbc_comboBox);

		JLabel lblFechaFin = new JLabel("Fecha fin:");
		lblFechaFin.setEnabled(false);
		lblFechaFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
		gbc_lblFechaFin.insets = new Insets(15, 100, 5, 5);
		gbc_lblFechaFin.gridx = 0;
		gbc_lblFechaFin.gridy = 9;
		getContentPane().add(lblFechaFin, gbc_lblFechaFin);

		dateFin = new JDateChooser(new Date(System.currentTimeMillis()));
		dateFin.setDateFormatString("dd/MM/yyyy");
		dateFin.setEnabled(false);
		GridBagConstraints gbc_dateFin = new GridBagConstraints();
		gbc_dateFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateFin.insets = new Insets(25, 0, 5, 5);
		gbc_dateFin.gridx = 1;
		gbc_dateFin.gridy = 9;
		getContentPane().add(dateFin, gbc_dateFin);
		dateFin.setMinSelectableDate(dateInicio.getDate());
		dateFin.setDate(dateInicio.getDate());

		JButton btnCrearReserva = new JButton("Crear reserva");
		btnCrearReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertarDatosReserva();
			}
		});

		chckbxPeridioca = new JCheckBox("Peri\u00F3dica");
		chckbxPeridioca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxPeridioca.isSelected()) {
					lblDia.setEnabled(true);
					comboBoxDia.setEnabled(true);
					lblFechaFin.setEnabled(true);
					dateFin.setEnabled(true);
				} else {
					lblDia.setEnabled(false);
					comboBoxDia.setEnabled(false);
					lblFechaFin.setEnabled(false);
					dateFin.setEnabled(false);
				}
			}
		});
		chckbxPeridioca.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxPeridica = new GridBagConstraints();
		gbc_chckbxPeridica.insets = new Insets(15, 100, 5, 5);
		gbc_chckbxPeridica.gridx = 0;
		gbc_chckbxPeridica.gridy = 7;
		getContentPane().add(chckbxPeridioca, gbc_chckbxPeridica);

		btnCrearReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnCrearReserva = new GridBagConstraints();
		gbc_btnCrearReserva.insets = new Insets(20, 0, 5, 5);
		gbc_btnCrearReserva.gridx = 1;
		gbc_btnCrearReserva.gridy = 11;
		getContentPane().add(btnCrearReserva, gbc_btnCrearReserva);
	}

	public void insertarDatosReserva() {
		String horaInicio = String.valueOf(spinnerInicio.getValue());
		horaInicio += ":00:00";
		String horaFin = String.valueOf(spinnerFin.getValue());
		horaFin += ":00:00";
		Long idInst = instalaciones
				.get(comboBoxInstalaciones.getSelectedIndex()).getIdInst();

		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio = fmt.format(dateInicio.getDate());
		String fechaFin = "";

		boolean todoElDia = false;

		// para una reserva no periodica
		// si es de todo el d�a o la reserva es por ej. de 23 a 1...
		if (!chckbxPeridioca.isSelected()) {
			if (chckbxTodoElDia.isSelected() || (int) spinnerFin
					.getValue() < (int) spinnerInicio.getValue()) {
				todoElDia = true;
				DateTimeFormatter formatter = DateTimeFormat
						.forPattern("dd/MM/yyyy");
				DateTime dateTimeInicio = new DateTime(
						formatter.parseDateTime(fechaInicio).getMillis());
				DateTime dtPlusOne = dateTimeInicio.plusDays(1);
				fechaFin = fmt.format(new Date(dtPlusOne.getMillis()));

			} else {
				fechaFin = fechaInicio;
			}
		}
		// para reserva periodica
		else {
			if (chckbxTodoElDia.isSelected() || (int) spinnerFin
					.getValue() < (int) spinnerInicio.getValue()) {
				todoElDia = true;
			}

			fechaFin = fmt.format(dateFin.getDate());

		}

		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dateTimeInicio = formatter
				.parseDateTime(fechaInicio + " " + horaInicio);
		DateTime dateTimeFin = formatter
				.parseDateTime(fechaFin + " " + horaFin);

		try {
			if (chckbxPeridioca.isSelected()) {
				ManagerAdmin.insertarReservaCentroSemanal(
						(DiasSemana) comboBoxDia.getSelectedItem(),
						dateTimeInicio, dateTimeFin, idInst, todoElDia);
			} else {
				ManagerAdmin.crearReservaCentro(dateTimeInicio, dateTimeFin,
						idInst, null, null);
			}
			JOptionPane.showMessageDialog(this,
					"La reserva se ha insertado con �xito");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		} catch (ExcepcionReserva e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}

}
