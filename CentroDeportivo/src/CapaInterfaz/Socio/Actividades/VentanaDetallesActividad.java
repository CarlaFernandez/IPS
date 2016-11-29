package CapaInterfaz.Socio.Actividades;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import CapaDatos.ActividadesDatos;
import CapaDatos.InstalacionDatos;
import CapaDatos.ReservaDatos;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ActividadHoras;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.ReservaDao;

public class VentanaDetallesActividad extends JFrame {

	public VentanaDetallesActividad(ActividadHoras clase) {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblTituloSocio = new JLabel("Detalles Actividad");
		lblTituloSocio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloSocio.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblTituloSocio, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 2, 0, 0));

		JTextPane textPaneClase = new JTextPane();
		textPaneClase.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPaneClase.setBorder(
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPaneClase);

		JTextPane textPaneInstalacion = new JTextPane();
		textPaneInstalacion.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPaneInstalacion.setBorder(
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPaneInstalacion);

		JTextPane textPaneActividad = new JTextPane();
		textPaneActividad.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPaneActividad.setBorder(
				new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPaneActividad);

		ReservaDao reserva = ReservaDatos
				.obtenerReservaPorId(clase.getIdReserva());
		Instalacion instalacion = InstalacionDatos
				.ObtenerInstalacion(reserva.getIdInst());
		textPaneInstalacion.setText(instalacion.toString());
		Actividad actividad = ActividadesDatos
				.obtenerActividad(clase.getIdActividad());
		textPaneActividad.setText(actividad.toString());
		textPaneClase.setText(clase.toString());

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
