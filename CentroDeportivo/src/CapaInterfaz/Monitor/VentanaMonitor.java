package CapaInterfaz.Monitor;

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

import CapaDatos.MonitorDatos;
import CapaNegocio.dao.Monitor;

import java.awt.FlowLayout;

public class VentanaMonitor extends JFrame{
	public VentanaMonitor(Long idMonitor) {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnFaltaSocio = new JButton("");
		btnFaltaSocio.setEnabled(false);
		btnFaltaSocio.setFont(new Font("Arial", Font.PLAIN, 14));
		btnFaltaSocio.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
//				VentanaSocioInstalacionReservasFecha vsir = new VentanaSocioInstalacionReservasFecha(user);
//				vsir.show();
			}
		});
		
		JButton btnGestionarActividades = new JButton("Gestionar actividades");
		btnGestionarActividades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaMonitorActividades vs = new VentanaMonitorActividades(idMonitor);
				vs.show();
			}
		});
		btnGestionarActividades.setFont(new Font("Arial", Font.PLAIN, 14));
		btnGestionarActividades.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnGestionarActividades);
		btnFaltaSocio.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnFaltaSocio);
		
		JButton btnNuevoSocioPlazaAusente = new JButton("");
		btnNuevoSocioPlazaAusente.setEnabled(false);
		btnNuevoSocioPlazaAusente.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNuevoSocioPlazaAusente.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
//				VentanaSocioReservasPorInstalacion vsri = new VentanaSocioReservasPorInstalacion(user);
//				vsri.show();
			}
		});
		btnNuevoSocioPlazaAusente.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNuevoSocioPlazaAusente);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				VentanaCancelarReservaSocio vcrs = new VentanaCancelarReservaSocio(user);
//				vcrs.show();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 14));
		btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(btnNewButton);
		
		JPanel pnlNorte = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlNorte.getLayout();
		flowLayout.setHgap(100);
		getContentPane().add(pnlNorte, BorderLayout.NORTH);
		
		JLabel lblTituloSocio = new JLabel("Ventana Monitor");
		pnlNorte.add(lblTituloSocio);
		lblTituloSocio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloSocio.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
		
		Monitor monitor = MonitorDatos.getMonitor(idMonitor);
		JLabel lblMonitor = new JLabel("Nombre: "+monitor.getNombre()+" "+monitor.getApellidos());
		lblMonitor.setFont(new Font("Tahoma", Font.PLAIN, 16));
		pnlNorte.add(lblMonitor);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
