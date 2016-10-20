package CapaInterfaz;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import CapaDatos.InstalacionDatos;
import CapaDatos.PagoDatos;
import CapaDatos.ReservaDatos;
import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;

import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.border.BevelBorder;

public class VentanaDetallesReserva extends JFrame {

	public VentanaDetallesReserva(Long idReserva) {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblTituloSocio = new JLabel("Detalles Reserva");
		lblTituloSocio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloSocio.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblTituloSocio, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 2, 0, 0));

		JTextPane textPaneUser = new JTextPane();
		textPaneUser.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPaneUser.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPaneUser);

		JTextPane textPaneInstalacion = new JTextPane();
		textPaneInstalacion.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPaneInstalacion.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPaneInstalacion);

		JTextPane textPaneReserva = new JTextPane();
		textPaneReserva.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPaneReserva.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPaneReserva);
		
		JTextPane textPanePago = new JTextPane();
		textPanePago.setFont(new Font("Times New Roman", Font.BOLD, 16));
		textPanePago.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(textPanePago);

		ReservaDao reserva = ReservaDatos.obtenerReservaPorId(idReserva);
		textPaneReserva.setText(reserva.toString());
		Usuario usuario = UsuarioDatos.ObtenerUsuario(reserva.getIdUsu());
		textPaneUser.setText(usuario.toString());
		Instalacion instalacion = InstalacionDatos.ObtenerInstalacion(reserva.getIdInst());
		textPaneInstalacion.setText(instalacion.toString());
		Pago pago = PagoDatos.obtenerPago(reserva.getIdPago());
		textPanePago.setText(pago.toString());
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
