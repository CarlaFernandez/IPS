package CapaInterfaz.Socio;

import java.awt.HeadlessException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerUsuario;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaLogin extends JFrame {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public VentanaLogin() throws HeadlessException {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Long user = usuarios.get(comboBox.getSelectedIndex()).getIdUsu();
				new VentanaSocio(user).show();
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

		JLabel lblNewLabel_1 = new JLabel("Usuario");
		lblNewLabel_1.setBounds(298, 141, 89, 20);
		getContentPane().add(lblNewLabel_1);

		usuarios = ManagerUsuario.verUsuarios();
		String[] usuariosStrings = new String[usuarios.size()];
		int aux = 0;
		for (Usuario usuario : usuarios) {
			usuariosStrings[aux] = usuario.getIdUsu().toString();
			aux++;
		}
		comboBox = new JComboBox(usuariosStrings);
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
	private List<Usuario> usuarios;
}
