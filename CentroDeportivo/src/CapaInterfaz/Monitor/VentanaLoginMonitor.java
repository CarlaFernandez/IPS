package CapaInterfaz.Monitor;

import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import CapaDatos.MonitorDatos;
import CapaNegocio.dao.Monitor;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLoginMonitor extends JFrame {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaLoginMonitor() throws HeadlessException {
		setTitle("Login Monitor");
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Long monitorSelec = monitores.get(comboBox.getSelectedIndex()).getIdMonitor();
				new VentanaMonitor(monitorSelec).show();
				dispose();
			}
		});
		
		btnNewButton.setBounds(298, 254, 89, 23);
		getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(298, 223, 86, 20);
		textField.setEditable(false);
		textField.setText("*********");
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("PassWord:");
		lblNewLabel.setBounds(298, 192, 89, 20);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("ID Monitor");
		lblNewLabel_1.setBounds(298, 141, 89, 20);
		getContentPane().add(lblNewLabel_1);

		monitores = MonitorDatos.obtenerMonitores();
		String[] monitoresStrings = new String[monitores.size()];
		int aux = 0;
		for (Monitor monit : monitores) {
			monitoresStrings[aux] = monit.getIdMonitor().toString();
			aux++;
		}
		comboBox = new JComboBox(monitoresStrings);
		comboBox.setBounds(298, 161, 86, 23);
		getContentPane().add(comboBox);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private List<Monitor> monitores;
}
