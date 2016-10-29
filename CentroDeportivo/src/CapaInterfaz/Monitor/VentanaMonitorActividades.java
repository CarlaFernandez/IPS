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

@SuppressWarnings("rawtypes")
public class VentanaMonitorActividades extends JFrame {
	private Long idMonitor;
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividadesMonitor;
	private JTable t;
	private DefaultTableModel tm;
	ReservaDao tablaReservas[][];
	private Usuario usuarioSelec=null;

	
	
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
	
	
	@SuppressWarnings("unchecked")
	public VentanaMonitorActividades(Long idMonitor) {
		this.idMonitor = idMonitor;
		actividadesMonitor = MonitorDatos.obtenerActividades(idMonitor);
		
		setResizable(false);
		setBounds(100, 100, 900, 563);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLblTituloMonitor(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
		inicializarTableModel();
		asignarDescripcion();
		rellenarTabla();
	}
	
	private void inicializarTableModel(){
		tm = new DefaultTableModel(24, 7) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Titulos para la cabecera superior. El primero es vacio,
		// puesto que corresponde
		tm.setColumnIdentifiers(new String[] {"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"});

		// Valores para la primera columna, que es la cabecera lateral.
		//for (int i = 0; i < tm.getRowCount(); i++)
		//	tm.setValueAt(i, i, 0);

		// JTable al que se le pasa el modelo recien creado y se
		// sobreescribe el metodo changeSelection para que no permita
		// seleccionar la primera columna.
		
	}

	
	private JTable getT(){
		if(t==null){
			inicializarTableModel();
			t = new JTable(tm) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
	
				@Override
				public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
					if (columnIndex == 0)
						super.changeSelection(rowIndex, columnIndex + 1, toggle, extend);
					else
						super.changeSelection(rowIndex, columnIndex, toggle, extend);
				}
			};
			t.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					usuarioSelec=null;
				}
			});
			t.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int clicks = e.getClickCount();
					if (clicks == 1) {
						if(tm.getValueAt(t.getSelectedRow(),0)!=null){//si la fila no esta vacia
							//"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
							usuarioSelec = new Usuario();
							usuarioSelec.setIdUsu((Long) tm.getValueAt(t.getSelectedRow(),0));
							usuarioSelec.setDNI((String) tm.getValueAt(t.getSelectedRow(),1));
							usuarioSelec.setNombre((String) tm.getValueAt(t.getSelectedRow(),2));
							usuarioSelec.setApellidos((String) tm.getValueAt(t.getSelectedRow(),3));
							usuarioSelec.setDireccion((String) tm.getValueAt(t.getSelectedRow(),4));
							usuarioSelec.setEmail((String) tm.getValueAt(t.getSelectedRow(),5));
							usuarioSelec.setCiudad((String) tm.getValueAt(t.getSelectedRow(),6));
							usuarioSelec.setSocio((boolean) tm.getValueAt(t.getSelectedRow(),7));
						}
					}
				}
			});
			
			
			// Se pone a la primera columna el render del JTableHeader
			// superior.
			//t.getColumnModel().getColumn(0).setCellRenderer(t.getTableHeader().getDefaultRenderer());
			//t.setDefaultRenderer(Object.class, new TableCellRendererColorInstalacion());
			

			
		}
		return t;
	}

	
	private JPanel getPanel(){
		if(panel==null){
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPanelPie(), BorderLayout.SOUTH);
			panel.add(getPnlBotonesAcciones(), BorderLayout.EAST);
			panel.add(getPanelCentro(), BorderLayout.CENTER);
			panel.add(getPnlBuscarActividad(), BorderLayout.NORTH);
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
			pnlAnadirSocio.setLayout(new GridLayout(2, 1, 30, 50));
			pnlAnadirSocio.add(getBtnEliminarDeActividad());
			pnlAnadirSocio.add(getBtnAadirNuevoSocio());
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
			btnAadirNuevoSocio.setAlignmentY(Component.TOP_ALIGNMENT);
			btnAadirNuevoSocio.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		return btnAadirNuevoSocio;
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

		boolean mia = tm.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).equals("Mi reserva");
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
	
	
	private void rellenarTabla(){
		//"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
		tm.getDataVector().clear();
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
					nuevaFila[0] =usuariosAct.get(i).getDireccion();
					nuevaFila[1] =usuariosAct.get(i).getEmail();			
					nuevaFila[2] =usuariosAct.get(i).getCiudad();
					nuevaFila[3] =usuariosAct.get(i).isSocio();
					tm.addRow(nuevaFila);
				}
			}
		}
		tm.fireTableDataChanged();
	}
}
