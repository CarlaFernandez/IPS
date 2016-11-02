package CapaInterfaz.Socio;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import CapaInterfaz.Socio.Instalaciones.VentanaApuntarseActividad;
import CapaInterfaz.Socio.Instalaciones.VentanaSocioInstalaciones;

import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaSocio extends JFrame{
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
		btnReservas.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnReservas);
		
		JButton btnNewButton_1 = new JButton("Apuntarse a Actividades");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaApuntarseActividad vaas = new VentanaApuntarseActividad(user);
				vaas.show();
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton(" ");
		btnNewButton.setFont(new Font("Wide Latin", Font.PLAIN, 12));
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton(" ");
		btnNewButton_2.setFont(new Font("Wide Latin", Font.PLAIN, 12));
		btnNewButton_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton_2);
		
		JLabel lblTituloSocio = new JLabel("Ventana Socios");
		lblTituloSocio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloSocio.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblTituloSocio, BorderLayout.NORTH);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
