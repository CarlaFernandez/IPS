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

public class VentanaAdmin extends JFrame {
	public VentanaAdmin() {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnReservas = new JButton("Gestión de instalaciones");
		btnReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReservas.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		btnReservas.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				VentanaGestionInstalaciones vr = new VentanaGestionInstalaciones();
				vr.show();
			}
		});
		panel.add(btnReservas);

		// JButton btnGestionActividades = new JButton("Gestión de
		// actividades");
		// btnGestionActividades.setFont(new Font("Arial", Font.PLAIN, 14));
		// btnGestionActividades.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		// VentanaGestionActividades v = new VentanaGestionActividades();
		// v.show();
		// }
		// });
		// btnGestionActividades.setBorder(new BevelBorder(BevelBorder.RAISED,
		// null, null, null, null));
		// panel.add(btnGestionActividades);

		JButton btnPasarPagosACuenta = new JButton(
				"Enviar pagos a la cuota mensual");
		btnPasarPagosACuenta.setFont(new Font("Arial", Font.PLAIN, 14));
		btnPasarPagosACuenta.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				VentanaContablePasarPagos vpp = new VentanaContablePasarPagos();
				vpp.show();
			}
		});
		btnPasarPagosACuenta.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnPasarPagosACuenta);

		//
		//
		// JButton btnNewButton = new JButton(" ");
		// btnNewButton.setFont(new Font("Wide Latin", Font.PLAIN, 12));
		// btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null,
		// null, null, null));
		// panel.add(btnNewButton);
		//
		// JButton btnNewButton_2 = new JButton(" ");
		// btnNewButton_2.setFont(new Font("Wide Latin", Font.PLAIN, 12));
		// btnNewButton_2.setBorder(new BevelBorder(BevelBorder.RAISED, null,
		// null, null, null));
		// panel.add(btnNewButton_2);

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
