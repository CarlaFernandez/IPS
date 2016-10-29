package CapaInterfaz.Monitor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import CapaDatos.MonitorDatos;
import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.JTextArea;

public class VentanaAltasBajasActividad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;
	private JPanel buttonPane;
	private JPanel pnlBajas;
	
	
	private DefaultTableModel modeloTabla;
	private Usuario usuarioSelec=null;
	private JLabel lblBajas;
	private Long idActividad;
	private VentanaMonitorActividades vma;
	private JPanel pnlAltas;
	private JLabel lblAltas;
	private JScrollPane sclBajas;
	private JScrollPane sclAltas;
	private JTextArea txtAreaBajas;
	private JTextArea txtAreaAltas;

	
	private List<Usuario> bajas;
	private List<Usuario> altas;

	/**
	 * Create the dialog.
	 */
	public VentanaAltasBajasActividad(List<Usuario> bajas, List<Usuario> altas) {
		this.bajas=bajas;
		this.altas=altas;
		setTitle("Registro de altas y bajas ");
		setBounds(100, 100, 676, 411);
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(20);
		getContentPane().setLayout(borderLayout);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(getButtonPane(), BorderLayout.SOUTH);
		contentPanel.setLayout(new GridLayout(1, 2, 10, 0));
		contentPanel.add(getPnlBajas());
		contentPanel.add(getPnlAltas());
		rellenarAreas();
	}
	
	private JPanel getButtonPane(){
		if(buttonPane==null){
			buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
			fl_buttonPane.setVgap(15);
			fl_buttonPane.setHgap(30);
			buttonPane.setLayout(fl_buttonPane);
			buttonPane.add(getCancelButton());
		}
		return buttonPane;
	}
	
	
	private JButton getCancelButton(){
		if(cancelButton == null){
			cancelButton = new JButton("Cerrar");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
			cancelButton.setActionCommand("Cancel");
		}
		return cancelButton;
	}
	

	private JPanel getPnlBajas() {
		if (pnlBajas == null) {
			pnlBajas = new JPanel();
			pnlBajas.setLayout(new BorderLayout(0, 0));
			pnlBajas.add(getLblBajas(), BorderLayout.NORTH);
			pnlBajas.add(getSclBajas(), BorderLayout.CENTER);
		}
		return pnlBajas;
	}
	private JLabel getLblBajas() {
		if (lblBajas == null) {
			lblBajas = new JLabel("Bajas:");
			lblBajas.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lblBajas;
	}
	private JPanel getPnlAltas() {
		if (pnlAltas == null) {
			pnlAltas = new JPanel();
			pnlAltas.setLayout(new BorderLayout(0, 0));
			pnlAltas.add(getLblAltas(), BorderLayout.NORTH);
			pnlAltas.add(getSclAltas(), BorderLayout.CENTER);
		}
		return pnlAltas;
	}
	private JLabel getLblAltas() {
		if (lblAltas == null) {
			lblAltas = new JLabel("Altas:");
			lblAltas.setFont(new Font("Tahoma", Font.BOLD, 18));
		}
		return lblAltas;
	}
	private JScrollPane getSclBajas() {
		if (sclBajas == null) {
			sclBajas = new JScrollPane();
			sclBajas.setViewportView(getTxtAreaBajas());
		}
		return sclBajas;
	}
	private JScrollPane getSclAltas() {
		if (sclAltas == null) {
			sclAltas = new JScrollPane();
			sclAltas.setViewportView(getTxtAreaAltas());
		}
		return sclAltas;
	}
	private JTextArea getTxtAreaBajas() {
		if (txtAreaBajas == null) {
			txtAreaBajas = new JTextArea();
			txtAreaBajas.setEditable(false);
			txtAreaBajas.setLineWrap(true);
			txtAreaBajas.setWrapStyleWord(true);
		}
		return txtAreaBajas;
	}
	private JTextArea getTxtAreaAltas() {
		if (txtAreaAltas == null) {
			txtAreaAltas = new JTextArea();
			txtAreaAltas.setEditable(false);
			txtAreaAltas.setLineWrap(true);
			txtAreaAltas.setWrapStyleWord(true);
		}
		return txtAreaAltas;
	}
	
	
	private void rellenarAreas(){
		getTxtAreaBajas().setText(toStringBajasReducido());
		getTxtAreaAltas().setText(toStringAltasReducido());
	}
	
	private String toStringBajasReducido(){
		String s="";
		if(bajas!=null){
			for(int i=0;i<bajas.size();i++){
				Usuario actual = bajas.get(i);
				s = actual.getIdUsu()+""+actual.getDNI()+""+actual.getNombre()+" "+actual.getApellidos()+""+actual.getCiudad()+"\n";
			}
		}
		return s;
	}
	private String toStringAltasReducido(){
		String s ="";
		if(altas!=null){
			for(int i=0;i<altas.size();i++){
				Usuario actual = altas.get(i);
				s = actual.getIdUsu()+""+actual.getDNI()+""+actual.getNombre()+" "+actual.getApellidos()+""+actual.getCiudad()+"\n";
			}
		}
		return s;
	}
}
