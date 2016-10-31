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

public class VentanaAnadirUsuarioActividad extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;
	private JButton btnAnadir;
	private JPanel buttonPane;
	private JPanel pnlNorte;
	private JButton btnBuscar;
	private JScrollPane sclBuscados;
	private JTable tablaBuscados;
	
	
	private DefaultTableModel modeloTabla;
	private Usuario usuarioSelec=null;
	private JLabel lblBuscarPor;
	private JComboBox comboBox;
	private Long idActividad;
	private VentanaMonitorActividades vma;


	/**
	 * Create the dialog.
	 */
	public VentanaAnadirUsuarioActividad(VentanaMonitorActividades vma, Long idActividad) {
		this.vma=vma;
		this.idActividad=idActividad;
		setTitle("A\u00F1adir usuario a actividad");
		setBounds(100, 100, 676, 411);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(getButtonPane(), BorderLayout.SOUTH);
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getPnlNorte(), BorderLayout.NORTH);
		contentPanel.add(getSclBuscados(), BorderLayout.CENTER);
	}
	
	private JPanel getButtonPane(){
		if(buttonPane==null){
			buttonPane = new JPanel();
			FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
			fl_buttonPane.setVgap(15);
			fl_buttonPane.setHgap(30);
			buttonPane.setLayout(fl_buttonPane);
			
			getRootPane().setDefaultButton(getBtnAnadir());
			buttonPane.add(getBtnAnadir());
			buttonPane.add(getCancelButton());
		}
		return buttonPane;
	}
	
	
	private JButton getCancelButton(){
		if(cancelButton == null){
			cancelButton = new JButton("Cancel");
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
	
	private JButton getBtnAnadir(){
		if(btnAnadir==null){
			btnAnadir = new JButton("A\u00F1adir");
			btnAnadir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(usuarioSelec==null)
						JOptionPane.showMessageDialog(null, "Seleccione un usuario en la tabla");
					else{
						String mensaje="¿Confirmar añadir usuario a actividad?:\n"
								+ usuarioSelec.getNombre()+" "+usuarioSelec.getApellidos()+"\n"
										+ "ID: "+usuarioSelec.getIdUsu()+"  DNI: "+usuarioSelec.getDNI();
						int respuesta = JOptionPane.showConfirmDialog(null, mensaje);
						if(respuesta == 0){//confirma == da OK
							UsuarioDatos.anadirUsuarioActividad(idActividad, usuarioSelec.getIdUsu()); 
							vma.rellenarTabla();
							vma.altas.add(usuarioSelec);
						}
					}				
				}
			});
			btnAnadir.setFont(new Font("Tahoma", Font.PLAIN, 18));
			btnAnadir.setActionCommand("OK");
			
		}
		return btnAnadir;
	}
	

	private JPanel getPnlNorte() {
		if (pnlNorte == null) {
			pnlNorte = new JPanel();
			pnlNorte.add(getLblBuscarPor());
			pnlNorte.add(getComboBox());
			pnlNorte.add(getBtnBuscar());
		}
		return pnlNorte;
	}
	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					rellenarTabla();
				}
			});
		}
		return btnBuscar;
	}
	private JScrollPane getSclBuscados() {
		if (sclBuscados == null) {
			sclBuscados = new JScrollPane();
			sclBuscados.setViewportView(getTablaBuscados());
		}
		return sclBuscados;
	}
	private JTable getTablaBuscados() {
		if (tablaBuscados == null) {
			String[] nombresColumnas = {"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"};
			modeloTabla = new ModeloNoEditable(nombresColumnas,0);//creado a mano
			tablaBuscados = new JTable(modeloTabla);
			tablaBuscados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			tablaBuscados.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int clicks = e.getClickCount();
					if (clicks == 1) {
						//"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
						Usuario us = new Usuario();
						us.setIdUsu((Long) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),0));
						us.setDNI((String) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),1));
						us.setNombre((String) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),2));
						us.setApellidos((String) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),3));
						us.setDireccion((String) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),4));
						us.setEmail((String) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),5));
						us.setCiudad((String) tablaBuscados.getValueAt(tablaBuscados.getSelectedRow(),6));
						usuarioSelec = us;

					}
				}
			});
			
			
		}
		return tablaBuscados;
	}
	
	
	private void rellenarTabla(){
		//"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
		modeloTabla.getDataVector().clear();
		Object[] nuevaFila = new Object[8];
		String campoBusqueda = (String) getComboBox().getSelectedItem();
		List<Usuario> usuariosAct = UsuarioDatos.buscarUsuariosQueNoEstenEnActividad(idActividad, campoBusqueda);
		if(usuariosAct==null)
			JOptionPane.showMessageDialog(null, "No hay ningún usuario con los parámetros de búsqueda");
		else{
			for(int i=0;i< usuariosAct.size();i++){
				if(usuariosAct.get(i).getBaja()==null){
					nuevaFila[0] =usuariosAct.get(i).getIdUsu();
					nuevaFila[1] =usuariosAct.get(i).getDNI();			
					nuevaFila[2] =usuariosAct.get(i).getNombre();
					nuevaFila[3] =usuariosAct.get(i).getApellidos();
					nuevaFila[4] =usuariosAct.get(i).getDireccion();
					nuevaFila[5] =usuariosAct.get(i).getEmail();			
					nuevaFila[6] =usuariosAct.get(i).getCiudad();
					nuevaFila[7] =usuariosAct.get(i).isSocio();
					modeloTabla.addRow(nuevaFila);
				}
			}
			modeloTabla.fireTableDataChanged();
		}
		
	}
	private JLabel getLblBuscarPor() {
		if (lblBuscarPor == null) {
			lblBuscarPor = new JLabel("Buscar por:");
		}
		return lblBuscarPor;
	}
	private JComboBox getComboBox() {
		if (comboBox == null) {
			String[] actividadesMonitorStrings = {"ID","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD"};
			comboBox = new JComboBox(actividadesMonitorStrings);
		}
		return comboBox;
	}
}
