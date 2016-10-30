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

public class VentanaGestionActividades extends JFrame {
	private static final long serialVersionUID = 1L;

	public VentanaGestionActividades() {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnRealizarReserva = new JButton("Realizar reserva");
		btnRealizarReserva.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRealizarReserva.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				VentanaReservasPagar vrv = new VentanaReservasPagar();
				vrv.show();
			}
		});
		panel.add(btnRealizarReserva);
	}

}
