package CapaInterfaz.Admin;

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

public class VentanaGestionInstalaciones extends JFrame {
	@SuppressWarnings("unused")
	private VentanaGestionInstalaciones ventana;
	
	public VentanaGestionInstalaciones() {
		ventana = this;
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnPagar = new JButton("Pagar reservas en efectivo");
		btnPagar.setFont(new Font("Arial", Font.PLAIN, 14));
		btnPagar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				VentanaReservasPagar vrv = new VentanaReservasPagar();
				vrv.show();
			}
		});

		JButton btnVer = new JButton("Ver reservas");
		btnVer.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				new VentanaAdminVerReservas().show();
			}
		});
		btnVer.setFont(new Font("Arial", Font.PLAIN, 14));
		btnVer.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnVer);
		btnPagar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnPagar);

		JButton btnReservaCentro = new JButton("Realizar reserva de centro");
		btnReservaCentro.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReservaCentro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnReservaCentro.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
//				int respuesta = JOptionPane.showOptionDialog(ventana, "¿Quiere realizar una reserva periódica?",
//						"Reserva periódica", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null,
//						null);
//				if (respuesta == JOptionPane.YES_OPTION){
//					VentanaReservaCentroPeriodica v = new VentanaReservaCentroPeriodica();
//					v.show();
//				}
//				else if (respuesta == JOptionPane.NO_OPTION){
					VentanaReservaCentro vrc = new VentanaReservaCentro();
					vrc.show();
//				}
				
			}
		});
		panel.add(btnReservaCentro);

		JButton btnReservaSocio = new JButton("Realizar reserva de socio");
		btnReservaSocio.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReservaSocio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnReservaSocio.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				VentanaAdminReservaSocio vrs = new VentanaAdminReservaSocio();
				vrs.show();
			}
		});
		panel.add(btnReservaSocio);

		JButton btnCancelarCentro = new JButton("Cancelar reserva");
		btnCancelarCentro.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCancelarCentro.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				VentanaCancelarReservaCentro vcrc = new VentanaCancelarReservaCentro();
				vcrc.show();
			}
		});
		btnCancelarCentro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnCancelarCentro);
		
		JButton btnIndicarUtilizacion = new JButton("Registrar utilizacion de la instalacion");
		btnIndicarUtilizacion.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				VentanaSocioUtilizandoInstalacion vsui = new VentanaSocioUtilizandoInstalacion();
				vsui.show();
			}
		});
		btnIndicarUtilizacion.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(btnIndicarUtilizacion);
		


		JLabel lblTituloAdmin = new JLabel("Gestión de instalaciones");
		lblTituloAdmin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloAdmin.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloAdmin.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblTituloAdmin, BorderLayout.NORTH);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
