package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class VentanaGestionActividades extends JFrame {
	private static final long serialVersionUID = 1L;

	public VentanaGestionActividades() {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JButton btnRealizarReserva = new JButton("Crear nueva actividad");
		btnRealizarReserva.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRealizarReserva.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				VentanaCrearActividad v = new VentanaCrearActividad();
				v.show();
			}
		});
		panel.add(btnRealizarReserva);

		JButton btnCancelarActividad = new JButton("Cancelar actividad");
		btnCancelarActividad.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				VentanaAdminCancelarActividades ventana = new VentanaAdminCancelarActividades();
				ventana.show();
			}
		});
		btnCancelarActividad.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(btnCancelarActividad);
	}

}
