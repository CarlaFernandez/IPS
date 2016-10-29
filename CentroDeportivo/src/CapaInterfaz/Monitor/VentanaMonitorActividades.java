package CapaInterfaz.Monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;

import CapaDatos.MonitorDatos;
import CapaDatos.UsuarioDatos;
import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.TitledBorder;
import javax.swing.ListSelectionModel;

@SuppressWarnings("rawtypes")
public class VentanaMonitorActividades extends JFrame {
	private Long idMonitor;
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividadesMonitor;
	private JTable t;
	private DefaultTableModel modeloTabla;
	ReservaDao tablaReservas[][];
	private Usuario usuarioSelec=null;
	public List<Usuario> altas;
	
	
	private JLabel lblTituloMonitor;
	private JButton btnAadirNuevoSocio;
	private JButton btnEliminarDeActividad;
	private JLabel lblActividad;
	private JButton btnVerDetalles;
	private JLabel lblMias;
	private JLabel lblNewLabel_4;
	private JPanel pnlAnadirSocio;
	private JPanel pnlBotonesAcciones;
	private JPanel pnlBuscarActividad;
	private JLabel lblOtras;
	private JPanel panelPie;
	private JComboBox cbActividad;
	private JPanel panelCentro;
	private JScrollPane spTabla;
	private JPanel panel;
	private JScrollPane sclDescripcion;
	private JTextArea txtAreaDescripcion;
	private JLabel lblNumUsarios;
	private JButton btnRegistro;
	
	
	@SuppressWarnings("unchecked")
	public VentanaMonitorActividades(Long idMonitor) {
		this.idMonitor = idMonitor;
		actividadesMonitor = MonitorDatos.obtenerActividades(idMonitor);
		
		setResizable(false);
		setBounds(100, 100, 900, 563);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLblTituloMonitor(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		asignarDescripcion();
	}

	
	private JTable getT(){
		if(t==null){
			String[] nombresColumnas = {"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"};
			modeloTabla = new ModeloNoEditable(nombresColumnas,0);//creado a mano
			t = new JTable(modeloTabla);
			t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int clicks = e.getClickCount();
					if (clicks == 1) {
//						if(t.getValueAt(t.getSelectedRow(),0)!=null){//si la fila no esta vacia
							//"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
							Usuario us = new Usuario();
							us.setIdUsu((Long) t.getValueAt(t.getSelectedRow(),0));
							us.setDNI((String) t.getValueAt(t.getSelectedRow(),1));
							us.setNombre((String) t.getValueAt(t.getSelectedRow(),2));
							us.setApellidos((String) t.getValueAt(t.getSelectedRow(),3));
							us.setDireccion((String) t.getValueAt(t.getSelectedRow(),4));
							us.setEmail((String) t.getValueAt(t.getSelectedRow(),5));
							us.setCiudad((String) t.getValueAt(t.getSelectedRow(),6));
							usuarioSelec = us;
//						}
					}
				}
			});
			
			rellenarTabla();		
		}
		return t;
	}

	
	private JPanel getPanel(){
		if(panel==null){
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPanelPie(), BorderLayout.SOUTH);
			panel.add(getPnlBotonesAcciones(), BorderLayout.EAST);
			panel.add(getPnlBuscarActividad(), BorderLayout.NORTH);
			panel.add(getPanelCentro(), BorderLayout.CENTER);
		}
		return panel;
	}
	
	
	private JScrollPane getSpTabla(){
		if(spTabla==null){
			spTabla = new JScrollPane();
			spTabla.setViewportView(getT());
		}
		return spTabla;
	}
	
	private JPanel getPanelCentro(){
		if(panelCentro==null){
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getSpTabla(), BorderLayout.CENTER);
		}
		return panelCentro;
	}
	
	private JComboBox getCbActividad(){
		if(cbActividad==null){
			String[] actividadesMonitorStrings = new String[actividadesMonitor.size()];
			int aux = 0;
			for (Actividad actividad : actividadesMonitor) {
				actividadesMonitorStrings[aux] = actividad.getCodigo().toString();
				aux++;
			}
			cbActividad = new JComboBox(actividadesMonitorStrings);
			
			cbActividad.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					asignarDescripcion();
					rellenarTabla();
				}
			});
			
			cbActividad.setMinimumSize(new Dimension(5000, 22));
			
		}
		return cbActividad;
	}
	
	
	
	private JPanel getPnlBuscarActividad(){
		if(pnlBuscarActividad==null){
			pnlBuscarActividad = new JPanel();
			pnlBuscarActividad.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			pnlBuscarActividad.add(getLblActividad());
			pnlBuscarActividad.add(getCbActividad());
			pnlBuscarActividad.add(getLblNumUsarios());
		}
		return pnlBuscarActividad;
	}
	
	private JLabel getLblOtras(){
		if(lblOtras==null){
			lblOtras = new JLabel("Otras");
			lblOtras.setOpaque(true);
			lblOtras.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lblOtras.setBackground(new Color(255, 185, 185));
			
		}
		return lblOtras;
	}
	
	private JPanel getPanelPie(){
		if(panelPie==null){
			panelPie = new JPanel();
			panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelPie.add(getLblOtras());
			panelPie.add(getLblNewLabel_4());
			panelPie.add(getLblMias());
			panelPie.add(getBtnVerDetalles());
		}
		return panelPie;
	}
	
	private JPanel getPnlBotonesAcciones(){
		if(pnlBotonesAcciones==null){
			pnlBotonesAcciones = new JPanel();
			pnlBotonesAcciones.setLayout(new GridLayout(2, 1, 50, 20));
			pnlBotonesAcciones.add(getSclDescripcion());
			pnlBotonesAcciones.add(getPnlAnadirSocio());
		}
		return pnlBotonesAcciones;
	}
	
	private JPanel getPnlAnadirSocio(){
		if(pnlAnadirSocio==null){
			pnlAnadirSocio = new JPanel();
			pnlAnadirSocio.setLayout(new GridLayout(3, 1, 30, 25));
			pnlAnadirSocio.add(getBtnEliminarDeActividad());
			pnlAnadirSocio.add(getBtnAadirNuevoSocio());
			pnlAnadirSocio.add(getBtnRegistro());
		}
		return pnlAnadirSocio;
	}
	
	private JLabel getLblNewLabel_4(){
		if(lblNewLabel_4==null){
			lblNewLabel_4 = new JLabel("Libre");
			lblNewLabel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lblNewLabel_4.setOpaque(true);
			lblNewLabel_4.setBackground(Color.WHITE);
		}
		return lblNewLabel_4;
	}
	
	
	private JLabel getLblMias(){
		if(lblMias==null){
			lblMias = new JLabel("Mias");
			lblMias.setOpaque(true);
			lblMias.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lblMias.setBackground(new Color(185, 255, 185));
		}
		return lblMias;
	}
	
	private JButton getBtnVerDetalles(){
		if(btnVerDetalles==null){
			btnVerDetalles = new JButton("Ver detalles");
			btnVerDetalles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					verDetalles(t);
				}
			});
		}
		return btnVerDetalles;
	}
	
	private JLabel getLblActividad(){
		if(lblActividad==null){
			lblActividad = new JLabel("Actividad: ");
			lblActividad.setFont(new Font("Tahoma", Font.BOLD, 18));
			
		}
		return lblActividad;
	}
	
	private JButton getBtnEliminarDeActividad(){
		if(btnEliminarDeActividad==null){
			btnEliminarDeActividad = new JButton("Eliminar de actividad");
			btnEliminarDeActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(usuarioSelec==null)
						JOptionPane.showMessageDialog(null, "Seleccione un usuario en la tabla");
					else{
						String mensaje="Confirmar eliminar de actividad a usuario:\n"
								+ usuarioSelec.getNombre()+" "+usuarioSelec.getApellidos()+"\n"
										+ "ID: "+usuarioSelec.getIdUsu()+"  DNI: "+usuarioSelec.getDNI();
						int respuesta = JOptionPane.showConfirmDialog(null, mensaje);
						if(respuesta == 0){//confirma == da OK
							Long idAcSelec = Long.valueOf((String) getCbActividad().getSelectedItem());
							UsuarioDatos.usuarioNoPresentadoActividad(usuarioSelec.getIdUsu(), idAcSelec); 
							rellenarTabla();//actualiza la tabla
						}
					}
				}
			});
		}
		return btnEliminarDeActividad;
	}
	
	private JButton getBtnAadirNuevoSocio(){
		if(btnAadirNuevoSocio==null){
			btnAadirNuevoSocio = new JButton("A\u00F1adir nuevo socio");
			btnAadirNuevoSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int maximo = MonitorDatos.maxPlazasActividad(idMonitor, valorCbActividad());
					if(getT().getRowCount()>=maximo)
						JOptionPane.showMessageDialog(null, "La actividad tiene el máximo de usuario posible");
					else{
						mostrarVentanaAnadirUsuarioActividad();
					}
				}
			});
			btnAadirNuevoSocio.setAlignmentY(Component.TOP_ALIGNMENT);
			btnAadirNuevoSocio.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return btnAadirNuevoSocio;
	}
	
	private void mostrarVentanaAnadirUsuarioActividad(){
		VentanaAnadirUsuarioActividad v = new VentanaAnadirUsuarioActividad(this, valorCbActividad());
		v.show();
	}
	
	private JLabel getLblTituloMonitor(){
		if(lblTituloMonitor==null){
			lblTituloMonitor = new JLabel("Mis Actividades");
			lblTituloMonitor.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloMonitor.setBorder(new EmptyBorder(20, 0, 20, 0));
			lblTituloMonitor.setFont(new Font("Arial Black", Font.BOLD, 25));
		}
		return lblTituloMonitor;
	}
		
	

	@SuppressWarnings("deprecation")
	private void verDetalles(JTable t) {

		boolean mia = modeloTabla.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).equals("Mi reserva");
		if (t.getSelectedRow() != -1 && tablaReservas[t.getSelectedColumn()][t.getSelectedRow()] != null && mia) {
			ReservaDao reserva = tablaReservas[t.getSelectedColumn()][t.getSelectedRow()];
			new VentanaDetallesReserva(reserva.getIdRes()).show();
		}
	}

	private JScrollPane getSclDescripcion() {
		if (sclDescripcion == null) {
			sclDescripcion = new JScrollPane();
			sclDescripcion.setViewportView(getTxtAreaDescripcion());
		}
		return sclDescripcion;
	}
	private JTextArea getTxtAreaDescripcion() {
		if (txtAreaDescripcion == null) {
			txtAreaDescripcion = new JTextArea();
			txtAreaDescripcion.setBorder(new TitledBorder(null, "Descripci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
			txtAreaDescripcion.setLineWrap(true);
			txtAreaDescripcion.setWrapStyleWord(true);
			txtAreaDescripcion.setEditable(false);
			txtAreaDescripcion.setText("");
		}
		return txtAreaDescripcion;
	}
	
	
	private void asignarDescripcion(){
		String idAcSelec = (String) cbActividad.getSelectedItem();
		for (Actividad actividad : actividadesMonitor) {
			if(String.valueOf(actividad.getCodigo()).equals(idAcSelec)){
				getTxtAreaDescripcion().setText(actividad.getDescripcion());
			}
		}
	}
	
	/**
	 * metodo auxiliar que devuelve el id de la actividad del comboBox en formato Long
	 * el comboBox.getSelectedItem() devuelve un String
	 * @return
	 */
	private Long valorCbActividad(){
		String idAcSelec = (String) cbActividad.getSelectedItem();
		Long idAct = null;
		for (Actividad actividad : actividadesMonitor) {
			if(String.valueOf(actividad.getCodigo()).equals(idAcSelec)){
				idAct = actividad.getCodigo();
			}
		}
		return idAct;
		
	}
	
	
	public void rellenarTabla(){
		//"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
		modeloTabla.getDataVector().clear();
		Object[] nuevaFila = new Object[8];//4 columnas
		Long idActividad = valorCbActividad();
		List<Usuario> usuariosAct = MonitorDatos.usuariosActividad(idMonitor, idActividad);
		if(usuariosAct==null)
			JOptionPane.showMessageDialog(null, "La actividad no tiene asignado ningun usuario");
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
			revisarNumeroUsuarios();
		}
	}
	
	private JLabel getLblNumUsarios() {
		if (lblNumUsarios == null) {
			lblNumUsarios = new JLabel("                   Personas en clase:");
			lblNumUsarios.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblNumUsarios;
	}
	
	private void revisarNumeroUsuarios(){
		int maximo = MonitorDatos.maxPlazasActividad(idMonitor, valorCbActividad());
		String s ="                   Personas en clase: "+modeloTabla.getRowCount()+"/"+maximo;
		getLblNumUsarios().setText(s);
	}
	private JButton getBtnRegistro() {
		if (btnRegistro == null) {
			btnRegistro = new JButton("Ver registro altas y bajas");
			btnRegistro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Usuario> bajas = UsuarioDatos.bajasEnActividad(valorCbActividad());
					VentanaAltasBajasActividad vaba = new VentanaAltasBajasActividad(bajas,altas);
					vaba.show();
				}
			});
		}
		return btnRegistro;
	}
}
