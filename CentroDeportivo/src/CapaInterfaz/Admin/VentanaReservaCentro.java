package CapaInterfaz.Admin;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;

public class VentanaReservaCentro extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txHoraInicio;
	private JTextField txHoraFin;
	private JTextField txIdInstalacion;
	private JTextField txIdActividad;
	private JTextField txIdCurso;

	public VentanaReservaCentro() {
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
		gbc_lblHoraInicio.insets = new Insets(10, 100, 5, 5);
		gbc_lblHoraInicio.gridx = 0;
		gbc_lblHoraInicio.gridy = 0;
		getContentPane().add(lblHoraInicio, gbc_lblHoraInicio);

		txHoraInicio = new JTextField();
		GridBagConstraints gbc_txHoraInicio = new GridBagConstraints();
		gbc_txHoraInicio.insets = new Insets(10, 0, 5, 0);
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
		gbc_txHoraFin.insets = new Insets(10, 0, 5, 0);
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
		gbc_txIdInstalacion.insets = new Insets(10, 0, 5, 0);
		gbc_txIdInstalacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txIdInstalacion.gridx = 1;
		gbc_txIdInstalacion.gridy = 2;
		getContentPane().add(txIdInstalacion, gbc_txIdInstalacion);
		txIdInstalacion.setColumns(10);

		JLabel lblIdActividad = new JLabel("id actividad:");
		lblIdActividad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblIdActividad = new GridBagConstraints();
		gbc_lblIdActividad.anchor = GridBagConstraints.EAST;
		gbc_lblIdActividad.insets = new Insets(10, 100, 5, 5);
		gbc_lblIdActividad.gridx = 0;
		gbc_lblIdActividad.gridy = 3;
		getContentPane().add(lblIdActividad, gbc_lblIdActividad);

		txIdActividad = new JTextField();
		GridBagConstraints gbc_txIdActividad = new GridBagConstraints();
		gbc_txIdActividad.insets = new Insets(10, 0, 5, 0);
		gbc_txIdActividad.fill = GridBagConstraints.HORIZONTAL;
		gbc_txIdActividad.gridx = 1;
		gbc_txIdActividad.gridy = 3;
		getContentPane().add(txIdActividad, gbc_txIdActividad);
		txIdActividad.setColumns(10);

		JLabel lblIdCurso = new JLabel("id curso:");
		lblIdCurso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblIdCurso = new GridBagConstraints();
		gbc_lblIdCurso.anchor = GridBagConstraints.EAST;
		gbc_lblIdCurso.insets = new Insets(10, 100, 5, 5);
		gbc_lblIdCurso.gridx = 0;
		gbc_lblIdCurso.gridy = 4;
		getContentPane().add(lblIdCurso, gbc_lblIdCurso);

		txIdCurso = new JTextField();
		GridBagConstraints gbc_txIdCurso = new GridBagConstraints();
		gbc_txIdCurso.insets = new Insets(10, 0, 5, 0);
		gbc_txIdCurso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txIdCurso.gridx = 1;
		gbc_txIdCurso.gridy = 4;
		getContentPane().add(txIdCurso, gbc_txIdCurso);
		txIdCurso.setColumns(10);

		JButton btnCrearReserva = new JButton("Crear reserva");
		btnCrearReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertarDatosReserva();
			}
		});
		btnCrearReserva.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_btnCrearReserva = new GridBagConstraints();
		gbc_btnCrearReserva.insets = new Insets(20, 0, 0, 0);
		gbc_btnCrearReserva.gridx = 1;
		gbc_btnCrearReserva.gridy = 5;
		getContentPane().add(btnCrearReserva, gbc_btnCrearReserva);
	}

	public void insertarDatosReserva() {
		String horaInicio = txHoraInicio.getText();
		String horaFin = txHoraFin.getText();
		String idInst = txIdInstalacion.getText();
		String idAct = txIdActividad.getText();
		String idCurso = txIdCurso.getText();

		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
		DateTime dateTimeInicio = formatter.parseDateTime(horaInicio);
		DateTime dateTimeFin = formatter.parseDateTime(horaFin);
		Long longAct = idAct.isEmpty() ? null : Long.parseLong(idAct);
		Long longCurso = idCurso.isEmpty() ? null : Long.parseLong(idCurso);

		try {
			if (longCurso == null && longAct != null) {
				ManagerAdmin.crearReservaCentro(dateTimeInicio, dateTimeFin, Long.parseLong(idInst),
						Long.parseLong(idAct), null);
				JOptionPane.showMessageDialog(this, "La reserva se ha insertado con éxito");
			} else if (longCurso != null && longAct == null) {
				ManagerAdmin.crearReservaCentro(dateTimeInicio, dateTimeFin, Long.parseLong(idInst), null,
						Long.parseLong(idCurso));
				JOptionPane.showMessageDialog(this, "La reserva se ha insertado con éxito");

			} else {
				JOptionPane.showMessageDialog(this, "Has de introducir id de actividad o curso");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		} catch (ExcepcionReserva e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

	}

}
