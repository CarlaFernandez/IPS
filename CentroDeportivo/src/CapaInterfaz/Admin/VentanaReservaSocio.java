package CapaInterfaz.Admin;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import CapaNegocio.TipoPago;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;

public class VentanaReservaSocio extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txHoraInicio;
	private JTextField txHoraFin;
	private JTextField txIdInstalacion;
	private JTextField txIdSocio;
	private JRadioButton rdbtnMensual;
	private JRadioButton rdbtnEfectivo;

	public VentanaReservaSocio() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		setAlwaysOnTop(true);
		setResizable(false);
		setBounds(100, 100, 450, 300);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblHoraInicio = new JLabel("Hora inicio:");
		lblHoraInicio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHoraInicio = new GridBagConstraints();
		gbc_lblHoraInicio.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblHoraInicio.insets = new Insets(25, 100, 5, 5);
		gbc_lblHoraInicio.gridx = 0;
		gbc_lblHoraInicio.gridy = 0;
		getContentPane().add(lblHoraInicio, gbc_lblHoraInicio);

		txHoraInicio = new JTextField();
		GridBagConstraints gbc_txHoraInicio = new GridBagConstraints();
		gbc_txHoraInicio.insets = new Insets(25, 0, 5, 5);
		gbc_txHoraInicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txHoraInicio.gridx = 1;
		gbc_txHoraInicio.gridy = 0;
		getContentPane().add(txHoraInicio, gbc_txHoraInicio);
		txHoraInicio.setColumns(10);

		JLabel lblHoraFin = new JLabel("Hora fin:");
		lblHoraFin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblHoraFin = new GridBagConstraints();
		gbc_lblHoraFin.anchor = GridBagConstraints.EAST;
		gbc_lblHoraFin.insets = new Insets(10, 100, 5, 5);
		gbc_lblHoraFin.gridx = 0;
		gbc_lblHoraFin.gridy = 1;
		getContentPane().add(lblHoraFin, gbc_lblHoraFin);

		txHoraFin = new JTextField();
		GridBagConstraints gbc_txHoraFin = new GridBagConstraints();
		gbc_txHoraFin.insets = new Insets(10, 0, 5, 5);
		gbc_txHoraFin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txHoraFin.gridx = 1;
		gbc_txHoraFin.gridy = 1;
		getContentPane().add(txHoraFin, gbc_txHoraFin);
		txHoraFin.setColumns(10);

		JLabel lblIdInstalacion = new JLabel("id instalación:");
		lblIdInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblIdInstalacion = new GridBagConstraints();
		gbc_lblIdInstalacion.anchor = GridBagConstraints.EAST;
		gbc_lblIdInstalacion.insets = new Insets(10, 100, 5, 5);
		gbc_lblIdInstalacion.gridx = 0;
		gbc_lblIdInstalacion.gridy = 2;
		getContentPane().add(lblIdInstalacion, gbc_lblIdInstalacion);

		txIdInstalacion = new JTextField();
		GridBagConstraints gbc_txIdInstalacion = new GridBagConstraints();
		gbc_txIdInstalacion.insets = new Insets(10, 0, 5, 5);
		gbc_txIdInstalacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txIdInstalacion.gridx = 1;
		gbc_txIdInstalacion.gridy = 2;
		getContentPane().add(txIdInstalacion, gbc_txIdInstalacion);
		txIdInstalacion.setColumns(10);

		JButton btnCrearReserva = new JButton("Crear reserva");
		btnCrearReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertarDatosReserva();
			}
		});

		JLabel lblIdSocio = new JLabel("id socio:");
		lblIdSocio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblIdSocio = new GridBagConstraints();
		gbc_lblIdSocio.anchor = GridBagConstraints.EAST;
		gbc_lblIdSocio.insets = new Insets(10, 100, 5, 5);
		gbc_lblIdSocio.gridx = 0;
		gbc_lblIdSocio.gridy = 3;
		getContentPane().add(lblIdSocio, gbc_lblIdSocio);

		txIdSocio = new JTextField();
		GridBagConstraints gbc_txIdSocio = new GridBagConstraints();
		gbc_txIdSocio.insets = new Insets(10, 0, 5, 5);
		gbc_txIdSocio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txIdSocio.gridx = 1;
		gbc_txIdSocio.gridy = 3;
		getContentPane().add(txIdSocio, gbc_txIdSocio);
		txIdSocio.setColumns(10);

		JLabel lblPago = new JLabel("pago:");
		lblPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblPago = new GridBagConstraints();
		gbc_lblPago.anchor = GridBagConstraints.EAST;
		gbc_lblPago.insets = new Insets(10, 100, 5, 5);
		gbc_lblPago.gridx = 0;
		gbc_lblPago.gridy = 4;
		getContentPane().add(lblPago, gbc_lblPago);

		rdbtnEfectivo = new JRadioButton("Efectivo");
		rdbtnEfectivo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnEfectivo = new GridBagConstraints();
		gbc_rdbtnEfectivo.insets = new Insets(10, 0, 5, 5);
		gbc_rdbtnEfectivo.gridx = 1;
		gbc_rdbtnEfectivo.gridy = 4;
		getContentPane().add(rdbtnEfectivo, gbc_rdbtnEfectivo);

		rdbtnMensual = new JRadioButton("Cuota mensual");
		rdbtnMensual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_rdbtnMensual = new GridBagConstraints();
		gbc_rdbtnMensual.insets = new Insets(10, 0, 5, 5);
		gbc_rdbtnMensual.gridx = 2;
		gbc_rdbtnMensual.gridy = 4;
		getContentPane().add(rdbtnMensual, gbc_rdbtnMensual);
		btnCrearReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnCrearReserva = new GridBagConstraints();
		gbc_btnCrearReserva.insets = new Insets(20, 0, 0, 5);
		gbc_btnCrearReserva.gridx = 1;
		gbc_btnCrearReserva.gridy = 5;
		getContentPane().add(btnCrearReserva, gbc_btnCrearReserva);
		ButtonGroup radioButtons = new ButtonGroup();
		radioButtons.add(rdbtnMensual);
		radioButtons.add(rdbtnEfectivo);
	}

	public void insertarDatosReserva() {
		String horaInicio = txHoraInicio.getText();
		String horaFin = txHoraFin.getText();
		String idInst = txIdInstalacion.getText();
		String idSocio = txIdSocio.getText();
		TipoPago tipoPago = rdbtnEfectivo.isSelected() ? TipoPago.EFECTIVO : TipoPago.CUOTA;

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dateTimeInicio = formatter.parseDateTime(horaInicio);
		DateTime dateTimeFin = formatter.parseDateTime(horaFin);

		try {
			ManagerAdmin.crearReservaSocio(dateTimeInicio, dateTimeFin, Long.parseLong(idInst),
					Long.parseLong(idSocio), tipoPago);
			JOptionPane.showMessageDialog(this, "La reserva se ha insertado con éxito");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		} catch (ExcepcionReserva e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
}
