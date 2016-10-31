package CapaInterfaz;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import CapaInterfaz.Admin.VentanaAdmin;
import CapaInterfaz.Monitor.VentanaLoginMonitor;
import CapaInterfaz.Socio.VentanaLogin;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInicial extends JFrame{
	public VentanaInicial() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
				
				JPanel panel_recepcion = new JPanel();
				panel_recepcion.setBorder(new EmptyBorder(2, 2, 2, 2));
				panel.add(panel_recepcion);
				panel_recepcion.setLayout(new BorderLayout(0, 0));
				
				JButton btnRecepcion = new JButton("");
				btnRecepcion.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent arg0) {
						VentanaAdmin va = new VentanaAdmin();
						va.show();
					}
				});
				btnRecepcion.setToolTipText("Boton Admin");
				panel_recepcion.add(btnRecepcion, BorderLayout.CENTER);
				btnRecepcion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				btnRecepcion.setIcon(new ImageIcon(VentanaInicial.class.getResource("/Resources/imagenRecepcion.png")));
				
				JPanel panel_1 = new JPanel();
				panel_recepcion.add(panel_1, BorderLayout.NORTH);
				panel_1.setLayout(new GridLayout(0, 1, 0, 0));
				
				JLabel lblRecepcion = new JLabel("RECEPCIÓN");
				panel_1.add(lblRecepcion);
				lblRecepcion.setHorizontalTextPosition(SwingConstants.CENTER);
				lblRecepcion.setHorizontalAlignment(SwingConstants.CENTER);
				lblRecepcion.setFont(new Font("Arial Black", Font.BOLD, 12));
				lblRecepcion.setBorder(new EmptyBorder(10, 0, 0, 0));
				
				JLabel lblNewLabel = new JLabel("(Admin)");
				lblNewLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
				panel_1.add(lblNewLabel);
				
				JPanel panel_portal = new JPanel();
				panel_portal.setBorder(new EmptyBorder(2, 2, 2, 2));
				panel.add(panel_portal);
				panel_portal.setLayout(new BorderLayout(0, 0));
				
				JButton btnPortal = new JButton("");
				btnPortal.addActionListener(new ActionListener() {
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e) {
						new VentanaLogin().show();
					}
				});
				btnPortal.setToolTipText("Boton Usuario");
				btnPortal.setIcon(new ImageIcon(VentanaInicial.class.getResource("/Resources/imagenPortal.png")));
				panel_portal.add(btnPortal);
				btnPortal.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				
				JPanel panel_3 = new JPanel();
				panel_portal.add(panel_3, BorderLayout.NORTH);
				panel_3.setLayout(new GridLayout(2, 1, 0, 0));
				
				JLabel lblPortalWeb = new JLabel("PORTAL WEB");
				panel_3.add(lblPortalWeb);
				lblPortalWeb.setHorizontalTextPosition(SwingConstants.CENTER);
				lblPortalWeb.setHorizontalAlignment(SwingConstants.CENTER);
				lblPortalWeb.setFont(new Font("Arial Black", Font.BOLD, 12));
				lblPortalWeb.setBorder(new EmptyBorder(10, 0, 0, 0));
				
				JLabel lblusuario = new JLabel("(Usuario)");
				lblusuario.setBorder(new EmptyBorder(0, 0, 5, 0));
				lblusuario.setHorizontalAlignment(SwingConstants.CENTER);
				lblusuario.setFont(new Font("Arial", Font.BOLD, 12));
				panel_3.add(lblusuario);
				
				JPanel panel_monitor = new JPanel();
				panel_monitor.setBorder(new EmptyBorder(2, 2, 2, 2));
				panel.add(panel_monitor);
				panel_monitor.setLayout(new BorderLayout(0, 0));
				
				JButton btnNewButton = new JButton("");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new VentanaLoginMonitor().show();
					}
				});
				btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
				btnNewButton.setToolTipText("Boton Monitor");
				btnNewButton.setIcon(new ImageIcon(VentanaInicial.class.getResource("/Resources/imagenMonitor.png")));
				panel_monitor.add(btnNewButton);
				
				JPanel panel_2 = new JPanel();
				panel_monitor.add(panel_2, BorderLayout.NORTH);
				panel_2.setLayout(new GridLayout(2, 1, 0, 0));
				
				JLabel lblMonitor = new JLabel("TABLET");
				lblMonitor.setHorizontalAlignment(SwingConstants.CENTER);
				panel_2.add(lblMonitor);
				lblMonitor.setBorder(new EmptyBorder(10, 0, 0, 0));
				lblMonitor.setFont(new Font("Arial Black", Font.BOLD, 12));
				
				JLabel lblmonitor = new JLabel("(Monitor)");
				lblmonitor.setBorder(new EmptyBorder(0, 0, 5, 0));
				lblmonitor.setHorizontalAlignment(SwingConstants.CENTER);
				lblmonitor.setFont(new Font("Arial", Font.BOLD, 12));
				panel_2.add(lblmonitor);
		
		JLabel lblPrincipalTitulo = new JLabel("CENTRO DEPORTIVO IPS");
		lblPrincipalTitulo.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblPrincipalTitulo.setFont(new Font("Arial Black", Font.BOLD, 25));
		lblPrincipalTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblPrincipalTitulo, BorderLayout.NORTH);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
