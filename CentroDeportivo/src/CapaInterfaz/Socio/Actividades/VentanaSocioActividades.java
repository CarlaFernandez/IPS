package CapaInterfaz.Socio.Actividades;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class VentanaSocioActividades extends JFrame {
	public VentanaSocioActividades(Long user) {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JButton btnReservas = new JButton("Ver/Cancelar mis Actividades");
		btnReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		btnReservas.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				new VentanaSocioVerMisActividades(user).show();
			}
		});

		JButton btnRealizarReserva = new JButton("Inscribirse a actividades");
		btnRealizarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaSocioInscribirseActividades vaas = new VentanaSocioInscribirseActividades(
						user);
				vaas.show();
				if (vaas.modeloTablaActividades.getRowCount() == 0) {
					JOptionPane.showMessageDialog(null,
							"No hay actividades disponibles para apntarse.",
							"No hay actividades disponibles",
							JOptionPane.INFORMATION_MESSAGE);
					vaas.dispose();
				}
			}
		});
		btnRealizarReserva.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRealizarReserva.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnRealizarReserva);
		btnReservas.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnReservas);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		btnNewButton_1.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton_1);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton.setBorder(
				new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton);

		JLabel lblTituloSocio = new JLabel("Ventana Socios>Actividades");
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
