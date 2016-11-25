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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import org.joda.time.DateTime;

import CapaDatos.MonitorDatos;
import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import java.awt.FlowLayout;

import java.awt.Dimension;
import javax.swing.DefaultCellEditor;

import java.awt.Component;
import javax.swing.JTextArea;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.JTextField;


public class VentanaMonitorActividades extends JFrame {
	private Long idMonitor;
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividadesMonitor;
	private JTable tablaSocios;
	private ModeloNoEditable modeloTabla;
	ReservaDao tablaReservas[][];
	
	
	private JLabel lblTituloMonitor;
	private JButton btnAnadirNuevoSocio;
	private JLabel lblActividad;
	private JPanel pnlBotonesAcciones;
	private JPanel pnlBuscarActividad;
	private JPanel panelPie;
	private JComboBox<String> cbActividad;
	private JPanel panelCentro;
	private JScrollPane spTabla;
	private JPanel pnlCentral;
	private JScrollPane sclDescripcion;
	private JTextArea txtAreaDescripcion;
	private JLabel lblNumUsarios;
	private JLabel lblDesde;
	private JLabel lblFin;
	private JSpinner spinnerFin;
	private JSpinner spinnerInicio;
	private JButton btnBuscar;
	private JPanel pnlEliminarSocio;
	private JPanel pnlBotones;
	private JTextField txtAnadir;
	private JLabel lblNewLabel;
	private JButton btnConfirmarCambios;
	private JLabel lblPersonasSinPlaza;
	private int asistidos=0;
	private int sinPlaza=0;
	private JPanel pnlTitulo;
	private JSpinner spinnerReloj;
	private JLabel lblReloj;
	private Actividad actividadActual;


	public VentanaMonitorActividades(Long idMonitor) {
		this.idMonitor = idMonitor;
		
		setResizable(false);
		setBounds(100, 100, 858, 563);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getPnlCentral(), BorderLayout.CENTER);
		getContentPane().add(getPnlTitulo(), BorderLayout.NORTH);
	}

	
	private JTable getT(){
		if(tablaSocios==null){
			String[] nombresColumnas = {"ID","NOMBRE","APELLIDOS","EN CLASE"};
			
			modeloTabla = new ModeloNoEditable(nombresColumnas,0);//creado a mano
	        
			tablaSocios = new JTable(modeloTabla);
			
			
			tablaSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			//aplico el cellEditor y cellRender a la columna de CheckBox
			tablaSocios.getColumnModel().getColumn(3).setCellEditor(new ChkCellEditor());
			tablaSocios.getColumnModel().getColumn(3).setCellRenderer(new ChkCellRenderer());
			
			//sin estas 4 lineas no pinta bien
			JCheckBox chk = new JCheckBox();
			TableColumn tc = tablaSocios.getColumnModel().getColumn(3);
			TableCellEditor tce = new DefaultCellEditor(chk);
			tc.setCellEditor(tce);
	 
					
			tablaSocios.getModel().addTableModelListener(new TableModelListener(){
				@Override
				public void tableChanged(TableModelEvent arg0) {
					// TODO Auto-generated method stub
					revisarNumeroSocios();
				}
			});			
		}
		return tablaSocios;
	}

	
	private JPanel getPnlCentral(){
		if(pnlCentral==null){
			pnlCentral = new JPanel();
			pnlCentral.setLayout(new BorderLayout(0, 0));
			pnlCentral.add(getPanelPie(), BorderLayout.SOUTH);
			pnlCentral.add(getPnlBotonesAcciones(), BorderLayout.EAST);
			pnlCentral.add(getPnlBuscarActividad(), BorderLayout.NORTH);
			pnlCentral.add(getPanelCentro(), BorderLayout.CENTER);
		}
		return pnlCentral;
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
			cbActividad = new JComboBox<String>();
			getCbActividad().setEnabled(false);
			cbActividad.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
//					getBtnConfirmarCambios();
					actividadActual = actividadesMonitor.get(0);
					try{
						actividadActual = actividadesMonitor.get(cbActividad.getSelectedIndex());
					}catch(Exception e){
						actividadActual=null;
						System.out.println("ERROR EN CB ACTIVIDAD");
					}
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
			pnlBuscarActividad.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
			pnlBuscarActividad.add(getLblDesde());
			pnlBuscarActividad.add(getSpinnerInicio());
			pnlBuscarActividad.add(getLblFin());
			pnlBuscarActividad.add(getSpinnerFin());
			pnlBuscarActividad.add(getBtnBuscar());
			pnlBuscarActividad.add(getLblActividad());
			pnlBuscarActividad.add(getCbActividad());
		}
		return pnlBuscarActividad;
	}
	
	private JSpinner getSpinnerReloj(){
		if(spinnerReloj==null){
			spinnerReloj = new JSpinner();
			spinnerReloj.addChangeListener(new ChangeListener() {
				@SuppressWarnings("deprecation")
				public void stateChanged(ChangeEvent arg0) {
					Calendar calendarReloj = Calendar.getInstance();
					calendarReloj.setTime((Date) spinnerReloj.getValue());
					calendarReloj.set(Calendar.MILLISECOND, 0);
					calendarReloj.set(Calendar.SECOND, 0);
					DateTime dateTimeReloj = new DateTime(calendarReloj.getTime());
					System.out.println("DateTime reloj: "+dateTimeReloj.toString());

					DateTime fecha_entrada_actividad = actividadActual.getFecha_entrada();
					
					Calendar calendarFechaAct = Calendar.getInstance();
					calendarFechaAct.setTime((Date) fecha_entrada_actividad.toDate());
					calendarFechaAct.set(Calendar.MILLISECOND, 0);
					calendarFechaAct.set(Calendar.SECOND, 0);
					DateTime dateTimeInicio = new DateTime(calendarFechaAct.getTime());
					
					System.out.println("DateTime reloj: "+dateTimeReloj.toString()+"     dateTimeInicio:"+dateTimeInicio);
					
					
					if(dateTimeReloj.getYear()==dateTimeInicio.getYear() && 
							dateTimeReloj.getDayOfMonth()==dateTimeInicio.getDayOfMonth() &&  
							dateTimeReloj.getMonthOfYear()==dateTimeInicio.getMonthOfYear()){
						int minutosReloj = dateTimeReloj.getMinuteOfHour();
						int horaReloj = dateTimeReloj.getHourOfDay();
						int horaActividad = dateTimeInicio.getHourOfDay();
						int minutosActividad = dateTimeInicio.getMinuteOfHour();
						System.out.println("hora reloj: "+horaReloj+":"+minutosReloj+"     hora actividad: "+horaActividad+":"+minutosActividad);
						if( horaActividad-1==horaReloj && (minutosReloj>=55 && minutosReloj<=59)){
							modeloTabla.editable=true;
							getBtnConfirmarCambios().setEnabled(true);
							getBtnAnadirNuevoSocio().setEnabled(true);
//							modeloTabla.fireTableDataChanged();
							System.out.println(modeloTabla.editable);
						}
					}
					else{
						modeloTabla.editable=false;
						getBtnConfirmarCambios().setEnabled(false);
						getBtnAnadirNuevoSocio().setEnabled(false);
//						modeloTabla.fireTableDataChanged();
						System.out.println(modeloTabla.editable);
						System.out.println("FECHA ACTIVIDAD SUPERIOR A RELOJ");
					}
				}
			});
			spinnerReloj.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
			Calendar date = Calendar.getInstance();
			date.setTime((Date) spinnerReloj.getValue());
			date.add(Calendar.DATE, 7);
		}
		return spinnerReloj;
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
			lblFin = new JLabel("Hasta:");
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
			panelPie.add(getLblPersonasSinPlaza());
			panelPie.add(getBtnConfirmarCambios());
		}
		return panelPie;
	}
	
	private JPanel getPnlBotonesAcciones(){
		if(pnlBotonesAcciones==null){
			pnlBotonesAcciones = new JPanel();
			pnlBotonesAcciones.setLayout(new GridLayout(2, 1, 50, 20));
			pnlBotonesAcciones.add(getSclDescripcion());
			pnlBotonesAcciones.add(getPnlBotones());
		}
		return pnlBotonesAcciones;
	}
	
	private JLabel getLblActividad(){
		if(lblActividad==null){
			lblActividad = new JLabel("     Actividad: ");
			lblActividad.setFont(new Font("Tahoma", Font.BOLD, 18));
			
		}
		return lblActividad;
	}
	
	
	
	private JButton getBtnAnadirNuevoSocio(){
		if(btnAnadirNuevoSocio==null){
			btnAnadirNuevoSocio = new JButton("A\u00F1adir socio");
			btnAnadirNuevoSocio.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnAnadirNuevoSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int maximo = MonitorDatos.maxPlazasActividad(idMonitor, valorCbActividad());
					try{
						Long idTxt = Long.parseLong(getTxtAnadir().getText());
						if(getPersonasEnActividad()>=maximo)
							JOptionPane.showMessageDialog(null, "La actividad tiene el máximo de usuario posible");
						else{
							int existe = UsuarioDatos.anadirUsuarioActividad(valorCbActividad(), idTxt);
							
							//SI NO EXISTE == -1
							if(existe == -1){ 
								//contador a -1 significa que el socio no estaba en la actividad, hay que insertarlo con asistido=false
								UsuarioDatos.insertSocioActividad(idTxt, valorCbActividad());
								rellenarTabla();
							}
							else{//SI EXISTE >0
								//esta en la actividad y a true, no hace nada
								
							}
							
							
							if(existe>0)
								JOptionPane.showMessageDialog(null, "Usuario ya esta en la actividad");
							else
								rellenarTabla();
								
							getTxtAnadir().setText("");
						}
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "Usuario incorrecto");
					}
					
				}
			});
			btnAnadirNuevoSocio.setAlignmentY(Component.TOP_ALIGNMENT);
			btnAnadirNuevoSocio.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnAnadirNuevoSocio.setEnabled(false);
		}
		return btnAnadirNuevoSocio;
	}
	
	private JLabel getLblTituloMonitor(){
		if(lblTituloMonitor==null){
			lblTituloMonitor = new JLabel("Gestionar Actividades");
			lblTituloMonitor.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloMonitor.setBorder(new EmptyBorder(20, 0, 20, 0));
			lblTituloMonitor.setFont(new Font("Arial Black", Font.BOLD, 25));
		}
		return lblTituloMonitor;
	}
		
	

//	@SuppressWarnings("deprecation")
//	private void verDetalles(JTable t) {
//
//		boolean mia = modeloTabla.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).equals("Mi reserva");
//		if (t.getSelectedRow() != -1 && tablaReservas[t.getSelectedColumn()][t.getSelectedRow()] != null && mia) {
//			ReservaDao reserva = tablaReservas[t.getSelectedColumn()][t.getSelectedRow()];
//			new VentanaDetallesReserva(reserva.getIdRes()).show();
//		}
//	}

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
		getTxtAreaDescripcion().setText(actividadActual.getDescripcion());
	}
	
	/**
	 * metodo auxiliar que devuelve el id de la actividad del comboBox en formato Long
	 * el comboBox.getSelectedItem() devuelve un String
	 * @return
	 */
	private Long valorCbActividad(){
		if(cbActividad.getSelectedItem()==null)
			return null;
		return actividadActual.getCodigo();
		
	}
	
	
	public void rellenarTabla(){
		//"ID","NOMBRE","APELLIDOS","EN CLASE",""
		modeloTabla.getDataVector().clear();
		Object[] nuevaFila = new Object[4];
		Long idActividad = actividadActual.getCodigo();
		if(idActividad!=null){
			List<Usuario> usuariosAct = MonitorDatos.usuariosActividad(idMonitor, idActividad, actividadActual.getFecha_entrada());
			if(usuariosAct==null)
				JOptionPane.showMessageDialog(null, "La actividad no tiene asignado ningun usuario");
			else{
				for(int i=0;i< usuariosAct.size();i++){
//					if(usuariosAct.get(i).getBaja()==null){
						nuevaFila[0] =usuariosAct.get(i).getIdUsu();
						nuevaFila[1] =usuariosAct.get(i).getNombre();			
						nuevaFila[2] =usuariosAct.get(i).getApellidos();
						//Long idUsu, Long idActividad, Long idMonitor, DateTime fecha_inicio
						Boolean asis = UsuarioDatos.getAsistenciaSocioActividad(usuariosAct.get(i).getIdUsu(), idActividad, idMonitor, actividadActual.getFecha_entrada());
						nuevaFila[3]= asis;
						modeloTabla.addRow(nuevaFila);
//					}
				}
				modeloTabla.fireTableDataChanged();
				revisarNumeroSocios();
			}
			modeloTabla.fireTableDataChanged();
		}
	}
	private JLabel getLblNumUsarios() {
		if (lblNumUsarios == null) {
			lblNumUsarios = new JLabel("Personas en clase:        ");
			lblNumUsarios.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblNumUsarios;
	}
	
	private void revisarNumeroSocios(){
		int maximo=0;
		if(idMonitor!=null && actividadActual!=null)
			maximo = actividadActual.getPlazasTotales();
		String s = "Personas en clase: "+getPersonasEnActividad()+"/"+maximo+"        ";
		String s2 = "Sin plaza: "+sinPlaza+"        ";
		getLblNumUsarios().setText(s);
		getLblPersonasSinPlaza().setText(s2);
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
							DateTime miFecha = actividadesMonitor.get(i).getFecha_entrada();
							String fecha = miFecha.getDayOfMonth()+"-"+miFecha.getMonthOfYear()+"-"+miFecha.getYear()+" "+miFecha.getHourOfDay()+":00";
							getCbActividad().addItem(actividadesMonitor.get(i).getNombre().toString()+" "+fecha);
						}
						actividadActual = actividadesMonitor.get(0);
						habilitarBotones();
					}
				}
			});
			btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return btnBuscar;
	}
	
	private void habilitarBotones(){
//		getBtnAnadirNuevoSocio().setEnabled(true);
		asignarDescripcion();
		rellenarTabla();
		revisarNumeroSocios();
//		getBtnConfirmarCambios().setEnabled(true);
//		getTxtAnadir().setEnabled(true);
	}
	
	private void vaciar(){
		modeloTabla.getDataVector().clear();
		modeloTabla.fireTableDataChanged();
		getTxtAreaDescripcion().setText("");
		getBtnAnadirNuevoSocio().setEnabled(false);
		getCbActividad().setEnabled(false);
		getCbActividad().removeAllItems();		
		getLblNumUsarios().setText("Personas en clase:        ");
		getBtnConfirmarCambios().setEnabled(false);
		getTxtAnadir().setEnabled(false);
	}
	private JPanel getPnlEliminarSocio() {
		if (pnlEliminarSocio == null) {
			pnlEliminarSocio = new JPanel();
			pnlEliminarSocio.setLayout(new GridLayout(0, 1, 0, 10));
		}
		return pnlEliminarSocio;
	}
	private JPanel getPnlBotones() {
		if (pnlBotones == null) {
			pnlBotones = new JPanel();
			pnlBotones.setLayout(new GridLayout(0, 1, 0, 20));
			pnlBotones.add(getLblNewLabel());
			pnlBotones.add(getTxtAnadir());
			pnlBotones.add(getBtnAnadirNuevoSocio());
			pnlBotones.add(getPnlEliminarSocio());
		}
		return pnlBotones;
	}

	private JTextField getTxtAnadir() {
		if (txtAnadir == null) {
			txtAnadir = new JTextField();
			txtAnadir.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAnadir.setColumns(10);
		}
		return txtAnadir;
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("ID a\u00F1adir a actividad:");
		}
		return lblNewLabel;
	}
	
	public int getPersonasEnActividad(){
		asistidos =0;
		sinPlaza=0;
		for(int i=0;i<getT().getRowCount();i++){
			if((Boolean)modeloTabla.getValueAt(i, 3) == true)
				asistidos++;	
			else if((Boolean)modeloTabla.getValueAt(i, 3) == false)
				sinPlaza++;
		}
		return asistidos;
	}
	private JButton getBtnConfirmarCambios() {
		if (btnConfirmarCambios == null) {
			btnConfirmarCambios = new JButton("Confirmar cambios");
			btnConfirmarCambios.setEnabled(false);
			btnConfirmarCambios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int maximo = actividadActual.getPlazasTotales();
					if(asistidos>maximo){
						JOptionPane.showMessageDialog(null, "Numero de asistidos no puede ser mayor \nque el numero de plazas de la actividad");						
					}else{
						Object[][] cambios = new Object[modeloTabla.getRowCount()][2];
						for(int i=0;i<modeloTabla.getRowCount();i++){
							cambios[i][0] = (Long) modeloTabla.getValueAt(i, 0);
							cambios[i][1] = (Boolean) modeloTabla.getValueAt(i, 3);
						}
						UsuarioDatos.guardarCambiosActividad(valorCbActividad(), idMonitor, actividadActual.getFecha_entrada(), cambios, modeloTabla.getRowCount());
						//guardar numero de personas en clase y numero de personas no asistidas
					}
				}
			});
			btnConfirmarCambios.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return btnConfirmarCambios;
	}
	


	private JLabel getLblPersonasSinPlaza() {
		if (lblPersonasSinPlaza == null) {
			lblPersonasSinPlaza = new JLabel("Sin plaza:        ");
			lblPersonasSinPlaza.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblPersonasSinPlaza;
	}
	private JPanel getPnlTitulo() {
		if (pnlTitulo == null) {
			pnlTitulo = new JPanel();
			pnlTitulo.add(getLblTituloMonitor());
			pnlTitulo.add(getLblReloj());
			pnlTitulo.add(getSpinnerReloj());
		}
		return pnlTitulo;
	}
	private JLabel getLblReloj() {
		if (lblReloj == null) {
			lblReloj = new JLabel("                             Reloj:");
			lblReloj.setFont(new Font("Tahoma", Font.BOLD, 15));
		}
		return lblReloj;
	}
}
