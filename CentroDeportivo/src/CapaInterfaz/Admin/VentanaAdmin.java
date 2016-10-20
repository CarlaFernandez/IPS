package CapaInterfaz.Admin;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class VentanaAdmin extends JFrame{
	public VentanaAdmin() {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnReservas = new JButton("Gesti�n Reservas Instalaciones");
		btnReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReservas.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnReservas.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				VentanaReservas vr = new VentanaReservas();
				vr.show();
			}
		});
		panel.add(btnReservas);
		
				JButton btnPasarPagosACuenta = new JButton("Pasar Pagos Cuenta");
				btnPasarPagosACuenta.setFont(new Font("Arial", Font.PLAIN, 14));
				btnPasarPagosACuenta.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {
						VentanaPasarPagos vpp = new VentanaPasarPagos();
						vpp.show();
					}
				});
				btnPasarPagosACuenta.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				panel.add(btnPasarPagosACuenta);
		

//		JButton btnNewButton_1 = new JButton(" ");
//		btnNewButton_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//			}
//		});
//		btnNewButton_1.setFont(new Font("Wide Latin", Font.PLAIN, 12));
//		btnNewButton_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
//		panel.add(btnNewButton_1);
//
//		
//		JButton btnNewButton = new JButton(" ");
//		btnNewButton.setFont(new Font("Wide Latin", Font.PLAIN, 12));
//		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
//		panel.add(btnNewButton);
//		
//		JButton btnNewButton_2 = new JButton(" ");
//		btnNewButton_2.setFont(new Font("Wide Latin", Font.PLAIN, 12));
//		btnNewButton_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
//		panel.add(btnNewButton_2);
		
		JLabel lblTituloAdmin = new JLabel("Ventana Administrador");
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
