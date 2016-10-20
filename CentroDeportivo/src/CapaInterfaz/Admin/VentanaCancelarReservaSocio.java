package CapaInterfaz.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CapaDatos.ReservaDatos;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.excepciones.ExcepcionReserva;

public class VentanaCancelarReservaSocio extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	public VentanaCancelarReservaSocio() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblIdreserva = new JLabel("idReserva:");
		lblIdreserva.setBounds(107, 63, 67, 22);
		getContentPane().add(lblIdreserva);
		
		textField = new JTextField();
		lblIdreserva.setLabelFor(textField);
		textField.setBounds(184, 64, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnCancelarReserva = new JButton(" Cancelar Reserva");
		btnCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelarReserva();
			}
		});
		btnCancelarReserva.setBounds(147, 135, 123, 23);
		getContentPane().add(btnCancelarReserva);
	}
	
	private void cancelarReserva() {
		ReservaDao reserva = ReservaDatos.getReserva(Long.parseLong(textField.getText()));
		try {
			ReservaDatos.cancelarReservaComoSocio(reserva);
		} catch (ExcepcionReserva e1) {
			JOptionPane.showMessageDialog(this, "El formato es incorrecto");
		} catch (NumberFormatException e2){
			JOptionPane.showMessageDialog(this, "Formato del id incorrecto");
		}
	}
	
}
