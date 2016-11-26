package CapaInterfaz.Socio;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import CapaInterfaz.Socio.Actividades.VentanaSocioActividades;
import CapaInterfaz.Socio.Instalaciones.VentanaSocioInstalaciones;

public class VentanaSocio extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VentanaSocio(Long user) {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnReservas = new JButton("Gestion Reservas Instalaciones");
		btnReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReservas.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				new VentanaSocioInstalaciones(user).show();
			}
		});
		btnReservas.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnReservas);


		JButton btnActividades = new JButton("Gestion Actividades");
		btnActividades.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				new VentanaSocioActividades(user).show();
			}
		});
		btnActividades.setFont(new Font("Arial", Font.PLAIN, 14));
		btnActividades.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnActividades);

		JButton btnNewButton_2 = new JButton(" ");
		btnNewButton_2.setFont(new Font("Wide Latin", Font.PLAIN, 12));
		btnNewButton_2.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton_2);

		JLabel lblTituloSocio = new JLabel("Ventana Socios");
		lblTituloSocio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloSocio.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblTituloSocio, BorderLayout.NORTH);
	}
}