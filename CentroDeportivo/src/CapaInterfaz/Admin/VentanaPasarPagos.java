package CapaInterfaz.Admin;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CapaDatos.Contable;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPasarPagos extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	public VentanaPasarPagos() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblIdsocio = new JLabel("IdSocio:");
		lblIdsocio.setBounds(112, 77, 53, 21);
		getContentPane().add(lblIdsocio);
		
		textField = new JTextField();
		lblIdsocio.setLabelFor(textField);
		textField.setBounds(182, 77, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnPasarPagosA = new JButton("Pasar pagos a cuenta");
		btnPasarPagosA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pasarPagos();
			}
		});
		btnPasarPagosA.setBounds(150, 167, 137, 23);
		getContentPane().add(btnPasarPagosA);
	}
	
	private void pasarPagos(){
		try{
		int total = Contable.incrementarCuotaMensual(Long.parseLong(textField.getText()));
		JOptionPane.showMessageDialog(this, "El total pasado acuenta es:"+total);
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(this, "Incorrecto formato de identificador");
		}
		
	}
	
}
