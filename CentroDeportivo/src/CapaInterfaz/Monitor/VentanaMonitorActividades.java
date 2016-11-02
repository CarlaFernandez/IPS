package CapaInterfaz.Monitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import org.joda.time.DateTime;

import CapaDatos.MonitorDatos;
import CapaDatos.UsuarioDatos;
import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

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
	private JLabel lblNewLabel_4;
	private JPanel pnlAnadirSocio;
	private JPanel pnlBotonesAcciones;
	private JPanel pnlBuscarActividad;
	private JPanel panelPie;
	private JComboBox<String> cbActividad;
	private JPanel panelCentro;
	private JScrollPane spTabla;
	private JPanel panel;
	private JScrollPane sclDescripcion;
	private JTextArea txtAreaDescripcion;
	private JLabel lblNumUsarios;
	private JButton btnRegistro;
	private JLabel lblDesde;
	private JLabel lblFin;
	private JSpinner spinnerFin;
	private JSpinner spinnerInicio;
	private JButton btnBuscar;
	private JLabel lblHora;
	
	
	@SuppressWarnings("unchecked")
	public VentanaMonitorActividades(Long idMonitor) {
		this.idMonitor = idMonitor;
		
		setResizable(false);
		setBounds(100, 100, 900, 563);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLblTituloMonitor(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
//		asignarDescripcion();
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
			
			//rellenarTabla();		
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
	
	private JComboBox<String> getCbActividad(){
		if(cbActividad==null){
//			String[] actividadesMonitorStrings = new String[actividadesMonitor.size()];
//			int aux = 0;
//			for (Actividad actividad : actividadesMonitor) {
//				actividadesMonitorStrings[aux] = actividad.getCodigo().toString();
//				aux++;
//			}
			cbActividad = new JComboBox<String>();
			getCbActividad().setEnabled(false);
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
			pnlBuscarActividad.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
			pnlBuscarActividad.add(getLblDesde());
			pnlBuscarActividad.add(getSpinnerInicio());
			pnlBuscarActividad.add(getLblFin());
			pnlBuscarActividad.add(getSpinnerFin());
			pnlBuscarActividad.add(getBtnBuscar());
			pnlBuscarActividad.add(getLblActividad());
			pnlBuscarActividad.add(getCbActividad());
			pnlBuscarActividad.add(getLblHora());
		}
		return pnlBuscarActividad;
	}

	private JSpinner getSpinnerInicio(){
		if(spinnerInicio==null){
			spinnerInicio = new JSpinner();
			spinnerInicio.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					Calendar date = Calendar.getInstance();
					date.setTime((Date) spinnerInicio.getValue());
					date.add(Calendar.DAY_OF_MONTH, 1);
					spinnerFin.setValue(date.getTime());
				}
			});
			
			spinnerInicio.setModel(new SpinnerDateModel(new Date(1477845618209L), null, null, Calendar.DAY_OF_WEEK));
		    JComponent editor = new JSpinner.DateEditor(spinnerInicio, "dd.MM.yyyy");
		    spinnerInicio.setEditor(editor);

		}
		return spinnerInicio;
	}	
	
	private JSpinner getSpinnerFin(){
		if(spinnerFin==null){
			spinnerFin = new JSpinner();
			spinnerFin.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					Calendar dateInicio = Calendar.getInstance();
					Calendar dateFin = Calendar.getInstance();
					dateInicio.setTime((Date) getSpinnerInicio().getValue());
					dateFin.setTime((Date) spinnerFin.getValue());
					if(dateFin.compareTo(dateInicio)<0){
						spinnerFin.setValue(dateInicio.getTime());
					}
				}
			});
			spinnerFin.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
			JComponent editor = new JSpinner.DateEditor(spinnerFin, "dd.MM.yyyy");
			spinnerFin.setEditor(editor);
		}
		return spinnerFin;
	}
	
	private JLabel getLblFin(){
		if(lblFin==null){
			lblFin = new JLabel("  Hasta:");
			lblFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblFin;
	}
	private JLabel getLblDesde(){
		if(lblDesde==null){
			lblDesde = new JLabel("Desde:");
			lblDesde.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDesde;
	}
	
	private JPanel getPanelPie(){
		if(panelPie==null){
			panelPie = new JPanel();
			panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelPie.add(getLblNumUsarios());
			panelPie.add(getLblNewLabel_4());
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
			lblNewLabel_4 = new JLabel("Usuarios en la actividad");
			lblNewLabel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			lblNewLabel_4.setOpaque(true);
			lblNewLabel_4.setBackground(Color.WHITE);
		}
		return lblNewLabel_4;
	}
	
	private JButton getBtnVerDetalles(){
		if(btnVerDetalles==null){
			btnVerDetalles = new JButton("Ver detalles");
			btnVerDetalles.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String s ="Para seleccionar la actividad: \n"
							+ "     abra el comboBox y elija, se le mostrará un pequeña\n"
							+ "     descripción de la misma\n"
							+ "Para eliminar un usuario porque no se presentó:\n"
							+ "     pulse 'Eliminar de actividad' habiendo seleccionado\n"
							+ "     la fila correspondiente al usuario en la tabla. \n"
							+ "     (NOTA: si se elimina de la actividad, no podrá \n"
							+ "     deshacerse el cambio\n"
							+ "Para añadir un usuario a la actividad: \n"
							+ "     pulse 'Añadir nuevo socio' y busque al cliente \n"
							+ "     que desee')";
					
					JOptionPane.showMessageDialog(null, s);
				}
			});
		}
		return btnVerDetalles;
	}
	
	private JLabel getLblActividad(){
		if(lblActividad==null){
			lblActividad = new JLabel("       Actividad: ");
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
			btnEliminarDeActividad.setEnabled(false);
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
			btnAadirNuevoSocio.setEnabled(false);
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
		if(idActividad!=null){
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
	}
	
	private JLabel getLblNumUsarios() {
		if (lblNumUsarios == null) {
			lblNumUsarios = new JLabel("Personas en clase:        ");
			lblNumUsarios.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblNumUsarios;
	}
	
	private void revisarNumeroUsuarios(){
		int maximo = MonitorDatos.maxPlazasActividad(idMonitor, valorCbActividad());
		String s ="Personas en clase: "+modeloTabla.getRowCount()+"/"+maximo+"        ";
		getLblNumUsarios().setText(s);
	}
	private JButton getBtnRegistro() {
		if (btnRegistro == null) {
			btnRegistro = new JButton("Ver registro altas y bajas");
			btnRegistro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					List<Usuario> bajas = UsuarioDatos.bajasSociosEnActividad(valorCbActividad());
					VentanaAltasBajasActividad vaba = new VentanaAltasBajasActividad(bajas,altas);
					vaba.show();
				}
			});
			btnRegistro.setEnabled(false);
		}
		return btnRegistro;
	}
	private JButton getBtnBuscar() {
		if (btnBuscar == null) {
			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Calendar dateInicio = Calendar.getInstance();
					dateInicio.setTime((Date) spinnerInicio.getValue());
					dateInicio.set(Calendar.MILLISECOND, 0);
					dateInicio.set(Calendar.SECOND, 0);
					dateInicio.set(Calendar.MINUTE, 0);
					dateInicio.set(Calendar.HOUR, 0);

					Calendar dateFin = Calendar.getInstance();
					dateFin.setTime((Date) getSpinnerFin().getValue());
					dateFin.set(Calendar.MILLISECOND, 0);
					dateFin.set(Calendar.SECOND, 0);
					dateFin.set(Calendar.MINUTE, 0);
					dateFin.set(Calendar.HOUR, 0);
					
					
					
					
					actividadesMonitor = MonitorDatos.obtenerActividadesEntreFechas(idMonitor, dateInicio.getTime(), dateFin.getTime());
					if(actividadesMonitor.size()==0){
						vaciar();
						JOptionPane.showMessageDialog(null, "No hay ninguna actividad entre las fechas seleccionadas");
					}else if(actividadesMonitor==null){
						JOptionPane.showMessageDialog(null, "Error SQL");
					}else{
						getCbActividad().removeAllItems();
						getCbActividad().setEnabled(true);
						for(int i=0;i<actividadesMonitor.size();i++){
							System.out.println(actividadesMonitor.get(i).getCodigo().toString());
							getCbActividad().addItem(actividadesMonitor.get(i).getCodigo().toString());
						}
						habilitarBotones();
						getLblHora().setText("    Hora:");
					}
				}
			});
			btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return btnBuscar;
	}
	
	private void habilitarBotones(){
		getBtnAadirNuevoSocio().setEnabled(true);
		getBtnRegistro().setEnabled(true);
		getBtnEliminarDeActividad().setEnabled(true);
		asignarDescripcion();
		rellenarTabla();
		revisarNumeroUsuarios();
	}
	
	private void vaciar(){
		modeloTabla.getDataVector().clear();
		modeloTabla.fireTableDataChanged();
		getTxtAreaDescripcion().setText("");
		getBtnAadirNuevoSocio().setEnabled(false);
		getBtnRegistro().setEnabled(false);
		getBtnEliminarDeActividad().setEnabled(false);
		getCbActividad().setEnabled(false);
		getCbActividad().removeAllItems();		
		getLblNumUsarios().setText("Personas en clase:        ");
	}
	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("    Hora:");
			lblHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblHora;
	}
}
