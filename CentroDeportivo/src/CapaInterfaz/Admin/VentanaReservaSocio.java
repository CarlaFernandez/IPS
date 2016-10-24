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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.toedter.calendar.JDateChooser;

import CapaNegocio.TipoPago;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Usuario;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerUsuario;

public class VentanaReservaSocio extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	private JSpinner spinnerFin;
	private JSpinner spinnerInicio;
	private JDateChooser dateInicio;
	private JRadioButton rdbtnMensual;
	private JRadioButton rdbtnEfectivo;
	private JLabel lblTipoPago;
	private JComboBox<String> comboBoxUsuarios;
	private List<Usuario> usuarios;
	private JLabel lblUsuario;
	private JLabel lblReservaDeSocio;

	@SuppressWarnings("unchecked")
	public VentanaReservaSocio() {
		setTitle("Admin -> Reserva Socio");
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 786, 525);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		ButtonGroup radioButtons = new ButtonGroup();

		instalaciones = ManagerAdmin.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}
		
		usuarios = ManagerUsuario.verUsuarios();
		String[] usuariosStrings = new String[usuarios.size()];
		int i = 0;
		for (Usuario usuario : usuarios) {
			usuariosStrings[i] = usuario.getIdUsu().toString();
			i++;
		}
										
										lblReservaDeSocio = new JLabel("Realizar reserva de socio");
										lblReservaDeSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
										GridBagConstraints gbc_lblReservaDeSocio = new GridBagConstraints();
										gbc_lblReservaDeSocio.gridwidth = 2;
										gbc_lblReservaDeSocio.insets = new Insets(0, -50, 5, 5);
										gbc_lblReservaDeSocio.gridx = 1;
										gbc_lblReservaDeSocio.gridy = 6;
										getContentPane().add(lblReservaDeSocio, gbc_lblReservaDeSocio);
								
										JLabel lblInicio = new JLabel("Fecha inicio:");
										lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
										GridBagConstraints gbc_lblInicio = new GridBagConstraints();
										gbc_lblInicio.insets = new Insets(25, 100, 5, 5);
										gbc_lblInicio.gridx = 0;
										gbc_lblInicio.gridy = 7;
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
										gbc_dateInicio.gridy = 7;
										dateInicio.setMinSelectableDate(new Date(System.currentTimeMillis()));
										dateInicio.setDate(new Date(System.currentTimeMillis()));
										getContentPane().add(dateInicio, gbc_dateInicio);
						
								JLabel lblHoraInicio = new JLabel("Hora inicio: ");
								lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
								GridBagConstraints gbc_lblHoraInicio = new GridBagConstraints();
								gbc_lblHoraInicio.insets = new Insets(25, 0, 5, 5);
								gbc_lblHoraInicio.gridx = 0;
								gbc_lblHoraInicio.gridy = 8;
								getContentPane().add(lblHoraInicio, gbc_lblHoraInicio);
				
						spinnerInicio = new JSpinner();
						spinnerInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
						spinnerInicio.setModel(new SpinnerNumberModel(19, 0, 23, 1));
						GridBagConstraints gbc_spinnerInicio = new GridBagConstraints();
						gbc_spinnerInicio.insets = new Insets(25, 0, 5, 5);
						gbc_spinnerInicio.gridx = 1;
						gbc_spinnerInicio.gridy = 8;
						getContentPane().add(spinnerInicio, gbc_spinnerInicio);
				
						JLabel lblHoraFin = new JLabel("Hora fin:");
						lblHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
						GridBagConstraints gbc_lblHoraFin = new GridBagConstraints();
						gbc_lblHoraFin.insets = new Insets(25, 0, 5, 5);
						gbc_lblHoraFin.gridx = 0;
						gbc_lblHoraFin.gridy = 9;
						getContentPane().add(lblHoraFin, gbc_lblHoraFin);
		
				spinnerFin = new JSpinner();
				spinnerFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
				spinnerFin.setModel(new SpinnerNumberModel(20, 0, 23, 1));
				GridBagConstraints gbc_spinnerFin = new GridBagConstraints();
				gbc_spinnerFin.insets = new Insets(25, 0, 5, 5);
				gbc_spinnerFin.gridx = 1;
				gbc_spinnerFin.gridy = 9;
				getContentPane().add(spinnerFin, gbc_spinnerFin);
		
		lblUsuario = new JLabel("Usuario: ");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(25, 0, 5, 5);
		gbc_lblUsuario.gridx = 0;
		gbc_lblUsuario.gridy = 10;
		getContentPane().add(lblUsuario, gbc_lblUsuario);
		comboBoxUsuarios = new JComboBox(usuariosStrings);
		comboBoxUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBoxUsuarios = new GridBagConstraints();
		gbc_comboBoxUsuarios.insets = new Insets(25, 0, 5, 5);
		gbc_comboBoxUsuarios.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxUsuarios.gridx = 1;
		gbc_comboBoxUsuarios.gridy = 10;
		getContentPane().add(comboBoxUsuarios, gbc_comboBoxUsuarios);
		
				JLabel lblInstalacion = new JLabel("Instalaci\u00F3n:");
				lblInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
				GridBagConstraints gbc_lblInstalacion = new GridBagConstraints();
				gbc_lblInstalacion.insets = new Insets(25, 0, 5, 5);
				gbc_lblInstalacion.gridx = 0;
				gbc_lblInstalacion.gridy = 11;
				getContentPane().add(lblInstalacion, gbc_lblInstalacion);
		comboBoxInstalaciones = new JComboBox(instalacionesStrings);
		comboBoxInstalaciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_comboBoxInstalacion = new GridBagConstraints();
		gbc_comboBoxInstalacion.insets = new Insets(25, 0, 5, 5);
		gbc_comboBoxInstalacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxInstalacion.gridx = 1;
		gbc_comboBoxInstalacion.gridy = 11;
		getContentPane().add(comboBoxInstalaciones, gbc_comboBoxInstalacion);

		lblTipoPago = new JLabel("Tipo pago:");
		lblTipoPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblTipoPago = new GridBagConstraints();
		gbc_lblTipoPago.anchor = GridBagConstraints.EAST;
		gbc_lblTipoPago.insets = new Insets(25, 0, 5, 5);
		gbc_lblTipoPago.gridx = 0;
		gbc_lblTipoPago.gridy = 13;
		getContentPane().add(lblTipoPago, gbc_lblTipoPago);

		rdbtnEfectivo = new JRadioButton("Efectivo");
		rdbtnEfectivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnEfectivo = new GridBagConstraints();
		gbc_rdbtnEfectivo.insets = new Insets(25, 0, 5, 5);
		gbc_rdbtnEfectivo.gridx = 1;
		gbc_rdbtnEfectivo.gridy = 13;
		getContentPane().add(rdbtnEfectivo, gbc_rdbtnEfectivo);
		radioButtons.add(rdbtnEfectivo);

		rdbtnMensual = new JRadioButton("Cuota mensual");
		rdbtnMensual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnMensual = new GridBagConstraints();
		gbc_rdbtnMensual.insets = new Insets(25, 0, 5, 5);
		gbc_rdbtnMensual.gridx = 2;
		gbc_rdbtnMensual.gridy = 13;
		getContentPane().add(rdbtnMensual, gbc_rdbtnMensual);
		radioButtons.add(rdbtnMensual);
				
						JButton btnCrearReserva = new JButton("Crear reserva");
						btnCrearReserva.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								insertarDatosReserva();
							}
						});
						btnCrearReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
						GridBagConstraints gbc_btnCrearReserva = new GridBagConstraints();
						gbc_btnCrearReserva.insets = new Insets(20, 0, 5, 5);
						gbc_btnCrearReserva.gridx = 1;
						gbc_btnCrearReserva.gridy = 15;
						getContentPane().add(btnCrearReserva, gbc_btnCrearReserva);
	}

	public void insertarDatosReserva() {
		String horaInicio = String.valueOf(spinnerInicio.getValue());
		horaInicio += ":00:00";

		String horaFin = String.valueOf(spinnerFin.getValue());
		horaFin += ":00:00";

		Long idInst = instalaciones.get(comboBoxInstalaciones.getSelectedIndex()).getIdInst();
		Long idUsu = usuarios.get(comboBoxUsuarios.getSelectedIndex()).getIdUsu();
		TipoPago tipoPago = rdbtnEfectivo.isSelected() ? TipoPago.EFECTIVO : TipoPago.CUOTA;

		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		String fechaInicio = fmt.format(dateInicio.getDate());
		String fechaFin = "";
		
		// si la reserva es por ej. de 23 a 1...
		if ((int) spinnerFin.getValue() < (int) spinnerInicio.getValue()) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime dateTimeInicio = new DateTime(formatter.parseDateTime(fechaInicio).getMillis());
			DateTime dtPlusOne = dateTimeInicio.plusDays(1);
			fechaFin = fmt.format(new Date(dtPlusOne.getMillis()));

		} else {
			fechaFin = fechaInicio;
		}

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dateTimeInicio = formatter.parseDateTime(fechaInicio + " " + horaInicio);
		DateTime dateTimeFin = formatter.parseDateTime(fechaFin + " " + horaFin);

		try {
			ManagerAdmin.crearReservaSocio(dateTimeInicio, dateTimeFin, idInst,
					idUsu, tipoPago);
			JOptionPane.showMessageDialog(this, "La reserva se ha insertado con éxito");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		} catch (ExcepcionReserva e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}
}
