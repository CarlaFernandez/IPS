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
	// private Usuario usuarioSelec=null;

	private JLabel lblTituloMonitor;
	private JButton btnAnadirNuevoSocio;
	private JButton btnEliminarDeActividad;
	private JLabel lblActividad;
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
	private JLabel lblDesde;
	private JLabel lblFin;
	private JSpinner spinnerFin;
	private JSpinner spinnerInicio;
	private JButton btnBuscar;
	private JLabel lblHora;
	private JPanel pnlEliminarSocio;
	private JPanel pnlBotones;
	private JTextField txtEliminar;
	private JTextField txtAnadir;
	private JLabel lblEliminar;
	private JLabel lblNewLabel;
	@SuppressWarnings("unused")
	private JCheckBox chckbxNewCheckBox;
	private JButton btnConfirmarCambios;
	private JLabel lblPersonasSinPlaza;
	private int asistidos = 0;
	private int sinPlaza = 0;
	private JLabel lblFecha;

	public VentanaMonitorActividades(Long idMonitor) {
		this.idMonitor = idMonitor;

		setResizable(false);
		setBounds(100, 100, 900, 563);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getLblTituloMonitor(), BorderLayout.NORTH);
		getContentPane().add(getPanel(), BorderLayout.CENTER);
	}

	private JTable getT() {
		if (tablaSocios == null) {
			String[] nombresColumnas = { "ID", "NOMBRE", "APELLIDOS",
					"EN CLASE" };

			modeloTabla = new ModeloNoEditable(nombresColumnas, 0);// creado a
																	// mano

			tablaSocios = new JTable(modeloTabla);

			tablaSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			// aplico el cellEditor y cellRender a la columna de CheckBox
			tablaSocios.getColumnModel().getColumn(3)
					.setCellEditor(new ChkCellEditor());
			tablaSocios.getColumnModel().getColumn(3)
					.setCellRenderer(new ChkCellRenderer());

			// sin estas 4 lineas no pinta bien
			JCheckBox chk = new JCheckBox();
			TableColumn tc = tablaSocios.getColumnModel().getColumn(3);
			TableCellEditor tce = new DefaultCellEditor(chk);
			tc.setCellEditor(tce);

			tablaSocios.getModel()
					.addTableModelListener(new TableModelListener() {
						@Override
						public void tableChanged(TableModelEvent arg0) {
							// TODO Auto-generated method stub
							revisarNumeroSocios();
						}
					});

			// tablaSocios.addMouseListener(new MouseAdapter() {
			// @Override
			// public void mouseClicked(MouseEvent e) {
			// int clicks = e.getClickCount();
			// if (clicks == 1) {
			//// if(t.getValueAt(t.getSelectedRow(),0)!=null){//si la fila no
			// esta vacia
			// //"ID","DNI","NOMBRE","APELLIDOS","DIRECCION","EMAIL","CIUDAD","SOCIO"
			// Usuario us = new Usuario();
			// us.setIdUsu((Long)
			// tablaSocios.getValueAt(tablaSocios.getSelectedRow(),0));
			// us.setNombre((String)
			// tablaSocios.getValueAt(tablaSocios.getSelectedRow(),1));
			// us.setApellidos((String)
			// tablaSocios.getValueAt(tablaSocios.getSelectedRow(),2));
			// usuarioSelec = us;
			//// }
			// }
			// }
			// });

		}
		return tablaSocios;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPanelPie(), BorderLayout.SOUTH);
			panel.add(getPnlBotonesAcciones(), BorderLayout.EAST);
			panel.add(getPnlBuscarActividad(), BorderLayout.NORTH);
			panel.add(getPanelCentro(), BorderLayout.CENTER);
		}
		return panel;
	}

	private JScrollPane getSpTabla() {
		if (spTabla == null) {
			spTabla = new JScrollPane();
			spTabla.setViewportView(getT());
		}
		return spTabla;
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setLayout(new BorderLayout(0, 0));
			panelCentro.add(getSpTabla(), BorderLayout.CENTER);
		}
		return panelCentro;
	}

	private JComboBox<String> getCbActividad() {
		if (cbActividad == null) {
			cbActividad = new JComboBox<String>();
			getCbActividad().setEnabled(false);
			cbActividad.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					getBtnConfirmarCambios();
					asignarDescripcion();
					rellenarTabla();
				}
			});

			cbActividad.setMinimumSize(new Dimension(5000, 22));

		}
		return cbActividad;
	}

	private JPanel getPnlBuscarActividad() {
		if (pnlBuscarActividad == null) {
			pnlBuscarActividad = new JPanel();
			pnlBuscarActividad
					.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
			pnlBuscarActividad.add(getLblDesde());
			pnlBuscarActividad.add(getSpinnerInicio());
			pnlBuscarActividad.add(getLblFin());
			pnlBuscarActividad.add(getSpinnerFin());
			pnlBuscarActividad.add(getBtnBuscar());
			pnlBuscarActividad.add(getLblActividad());
			pnlBuscarActividad.add(getCbActividad());
			pnlBuscarActividad.add(getLblFecha());
			pnlBuscarActividad.add(getLblHora());
		}
		return pnlBuscarActividad;
	}

	private JSpinner getSpinnerInicio() {
		if (spinnerInicio == null) {
			spinnerInicio = new JSpinner();
			spinnerInicio.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					Calendar date = Calendar.getInstance();
					date.setTime((Date) spinnerInicio.getValue());
					date.add(Calendar.DAY_OF_MONTH, 1);
					spinnerFin.setValue(date.getTime());
				}
			});

			spinnerInicio
					.setModel(new SpinnerDateModel(new Date(1477845618209L),
							null, null, Calendar.DAY_OF_WEEK));
			JComponent editor = new JSpinner.DateEditor(spinnerInicio,
					"dd.MM.yyyy");
			spinnerInicio.setEditor(editor);

		}
		return spinnerInicio;
	}

	private JSpinner getSpinnerFin() {
		if (spinnerFin == null) {
			spinnerFin = new JSpinner();
			spinnerFin.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					Calendar dateInicio = Calendar.getInstance();
					Calendar dateFin = Calendar.getInstance();
					dateInicio.setTime((Date) getSpinnerInicio().getValue());
					dateFin.setTime((Date) spinnerFin.getValue());
					if (dateFin.compareTo(dateInicio) < 0) {
						spinnerFin.setValue(dateInicio.getTime());
					}
				}
			});
			spinnerFin.setModel(new SpinnerDateModel(new Date(), null, null,
					Calendar.DAY_OF_YEAR));
			JComponent editor = new JSpinner.DateEditor(spinnerFin,
					"dd.MM.yyyy");
			spinnerFin.setEditor(editor);
		}
		return spinnerFin;
	}

	private JLabel getLblFin() {
		if (lblFin == null) {
			lblFin = new JLabel("  Hasta:");
			lblFin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblFin;
	}

	private JLabel getLblDesde() {
		if (lblDesde == null) {
			lblDesde = new JLabel("Desde:");
			lblDesde.setFont(new Font("Tahoma", Font.PLAIN, 15));
		}
		return lblDesde;
	}

	private JPanel getPanelPie() {
		if (panelPie == null) {
			panelPie = new JPanel();
			panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panelPie.add(getLblNumUsarios());
			panelPie.add(getLblPersonasSinPlaza());
			panelPie.add(getBtnConfirmarCambios());
		}
		return panelPie;
	}

	private JPanel getPnlBotonesAcciones() {
		if (pnlBotonesAcciones == null) {
			pnlBotonesAcciones = new JPanel();
			pnlBotonesAcciones.setLayout(new GridLayout(2, 1, 50, 20));
			pnlBotonesAcciones.add(getSclDescripcion());
			pnlBotonesAcciones.add(getPnlBotones());
		}
		return pnlBotonesAcciones;
	}

	private JPanel getPnlAnadirSocio() {
		if (pnlAnadirSocio == null) {
			pnlAnadirSocio = new JPanel();
			pnlAnadirSocio.setLayout(new GridLayout(3, 1, 0, 5));
			pnlAnadirSocio.add(getLblEliminar());
			pnlAnadirSocio.add(getTxtEliminar());
			pnlAnadirSocio.add(getBtnEliminarDeActividad());
		}
		return pnlAnadirSocio;
	}

	private JLabel getLblActividad() {
		if (lblActividad == null) {
			lblActividad = new JLabel("       Actividad: ");
			lblActividad.setFont(new Font("Tahoma", Font.BOLD, 18));

		}
		return lblActividad;
	}

	private JButton getBtnEliminarDeActividad() {
		if (btnEliminarDeActividad == null) {
			btnEliminarDeActividad = new JButton("Quitar de tabla");
			btnEliminarDeActividad.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnEliminarDeActividad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						Long idTxt = Long.parseLong(getTxtEliminar().getText());
						Long idAcSelec = Long.valueOf(
								(String) getCbActividad().getSelectedItem());
						UsuarioDatos.usuarioNoPresentadoActividad(idTxt,
								idAcSelec);
						int fila = existeId(idTxt);
						if (fila == -1)
							JOptionPane.showMessageDialog(null,
									"Usuario incorrecto");
						else {
							UsuarioDatos.updateSocioActividad(idTxt,
									valorCbActividad(), false);
							modeloTabla.removeRow(fila);
							modeloTabla.fireTableDataChanged();
							getTxtEliminar().setText("");
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Usuario incorrecto");
					}
				}
			});
			btnEliminarDeActividad.setEnabled(false);
		}
		return btnEliminarDeActividad;
	}

	/**
	 * deuelve la fila en la que esta el id del socio, sino devuelve -1
	 * 
	 * @return
	 */
	private int existeId(Long idTxt) {
		for (int i = 0; i < modeloTabla.getRowCount(); i++) {
			if (modeloTabla.getValueAt(i, 0).equals(idTxt))
				return i;
		}
		return -1;
	}

	private JButton getBtnAnadirNuevoSocio() {
		if (btnAnadirNuevoSocio == null) {
			btnAnadirNuevoSocio = new JButton("A\u00F1adir socio");
			btnAnadirNuevoSocio.setFont(new Font("Tahoma", Font.PLAIN, 16));
			btnAnadirNuevoSocio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int maximo = MonitorDatos.maxPlazasActividad(idMonitor,
							valorCbActividad());
					try {
						Long idTxt = Long.parseLong(getTxtAnadir().getText());
						if (getPersonasEnActividad() >= maximo)
							JOptionPane.showMessageDialog(null,
									"La actividad tiene el máximo de usuario posible");
						else {
							int existe = UsuarioDatos.anadirUsuarioActividad(
									valorCbActividad(), idTxt);

							// SI NO EXISTE == -1
							if (existe == -1) {
								// contador a -1 significa que el socio no
								// estaba en la actividad, hay que insertarlo
								// con asistido=false
								UsuarioDatos.insertSocioActividad(idTxt,
										valorCbActividad());
								rellenarTabla();
							} else {// SI EXISTE >0
									// esta en la actividad y a true, no hace
									// nada

							}

							if (existe > 0)
								JOptionPane.showMessageDialog(null,
										"Usuario ya esta en la actividad");
							else
								rellenarTabla();

							getTxtAnadir().setText("");
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Usuario incorrecto");
					}

				}
			});
			btnAnadirNuevoSocio.setAlignmentY(Component.TOP_ALIGNMENT);
			btnAnadirNuevoSocio.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnAnadirNuevoSocio.setEnabled(false);
		}
		return btnAnadirNuevoSocio;
	}

	private JLabel getLblTituloMonitor() {
		if (lblTituloMonitor == null) {
			lblTituloMonitor = new JLabel("Mis Actividades");
			lblTituloMonitor.setHorizontalAlignment(SwingConstants.CENTER);
			lblTituloMonitor.setBorder(new EmptyBorder(20, 0, 20, 0));
			lblTituloMonitor.setFont(new Font("Arial Black", Font.BOLD, 25));
		}
		return lblTituloMonitor;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	private void verDetalles(JTable t) {

		boolean mia = modeloTabla
				.getValueAt(t.getSelectedRow(), t.getSelectedColumn())
				.equals("Mi reserva");
		if (t.getSelectedRow() != -1 && tablaReservas[t.getSelectedColumn()][t
				.getSelectedRow()] != null && mia) {
			ReservaDao reserva = tablaReservas[t.getSelectedColumn()][t
					.getSelectedRow()];
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
			txtAreaDescripcion.setBorder(new TitledBorder(null,
					"Descripci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP,
					null, Color.BLACK));
			txtAreaDescripcion.setLineWrap(true);
			txtAreaDescripcion.setWrapStyleWord(true);
			txtAreaDescripcion.setEditable(false);
			txtAreaDescripcion.setText("");
		}
		return txtAreaDescripcion;
	}

	private void asignarDescripcion() {
		String idAcSelec = (String) cbActividad.getSelectedItem();
		for (Actividad actividad : actividadesMonitor) {
			if (String.valueOf(actividad.getCodigo()).equals(idAcSelec)) {
				getTxtAreaDescripcion().setText(actividad.getDescripcion());
				String sHora = actividad.getFecha_entrada().getHourOfDay() + ":"
						+ actividad.getFecha_entrada().getMinuteOfHour();
				String fecha = actividad.getFecha_entrada().getDayOfMonth()
						+ "-" + actividad.getFecha_entrada().getMonthOfYear();
				getLblHora().setText("    Hora: " + sHora);
				getLblFecha().setText("Fecha: " + fecha);
			}
		}
	}

	/**
	 * metodo auxiliar que devuelve el id de la actividad del comboBox en
	 * formato Long el comboBox.getSelectedItem() devuelve un String
	 * 
	 * @return
	 */
	private Long valorCbActividad() {
		String idAcSelec = (String) cbActividad.getSelectedItem();
		Long idAct = null;
		for (Actividad actividad : actividadesMonitor) {
			if (String.valueOf(actividad.getCodigo()).equals(idAcSelec)) {
				idAct = actividad.getCodigo();
			}
		}
		return idAct;

	}

	public void rellenarTabla() {
		// "ID","NOMBRE","APELLIDOS","EN CLASE",""
		modeloTabla.getDataVector().clear();
		Object[] nuevaFila = new Object[4];
		Long idActividad = valorCbActividad();
		if (idActividad != null) {
			List<Usuario> usuariosAct = MonitorDatos
					.usuariosActividad(idMonitor, idActividad);
			if (usuariosAct == null)
				JOptionPane.showMessageDialog(null,
						"La actividad no tiene asignado ningun usuario");
			else {
				for (int i = 0; i < usuariosAct.size(); i++) {
					// if(usuariosAct.get(i).getBaja()==null){
					nuevaFila[0] = usuariosAct.get(i).getIdUsu();
					nuevaFila[1] = usuariosAct.get(i).getNombre();
					nuevaFila[2] = usuariosAct.get(i).getApellidos();
					Boolean asis = UsuarioDatos.getAsistenciaSocioActividad(
							usuariosAct.get(i).getIdUsu(), idActividad);
					nuevaFila[3] = asis;
					modeloTabla.addRow(nuevaFila);
					// }
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

	private void revisarNumeroSocios() {
		int maximo = 0;
		if (idMonitor != null && valorCbActividad() != null)
			maximo = MonitorDatos.maxPlazasActividad(idMonitor,
					valorCbActividad());
		String s = "Personas en clase: " + getPersonasEnActividad() + "/"
				+ maximo + "        ";
		String s2 = "Sin plaza: " + sinPlaza + "        ";
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

					actividadesMonitor = MonitorDatos
							.obtenerActividadesEntreFechas(idMonitor,
									dateInicio.getTime(), dateFin.getTime());
					if (actividadesMonitor.size() == 0) {
						vaciar();
						JOptionPane.showMessageDialog(null,
								"No hay ninguna actividad entre las fechas seleccionadas");
					} else if (actividadesMonitor == null) {
						JOptionPane.showMessageDialog(null, "Error SQL");
					} else {
						getCbActividad().removeAllItems();
						getCbActividad().setEnabled(true);
						for (int i = 0; i < actividadesMonitor.size(); i++) {
							// System.out.println(actividadesMonitor.get(i).getCodigo().toString());
							getCbActividad().addItem(actividadesMonitor.get(i)
									.getCodigo().toString());
						}
						habilitarBotones();
					}
				}
			});
			btnBuscar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return btnBuscar;
	}

	private void habilitarBotones() {
		getBtnAnadirNuevoSocio().setEnabled(true);
		getBtnEliminarDeActividad().setEnabled(true);
		asignarDescripcion();
		rellenarTabla();
		revisarNumeroSocios();
		getBtnConfirmarCambios().setEnabled(true);
		getTxtAnadir().setEnabled(true);
		getTxtEliminar().setEnabled(true);
	}

	private void vaciar() {
		// modeloTabla.getDataVector().clear();
		// modeloTabla.fireTableDataChanged();
		modeloTabla.getDataVector().clear();
		modeloTabla.fireTableDataChanged();
		getTxtAreaDescripcion().setText("");
		getBtnAnadirNuevoSocio().setEnabled(false);
		getBtnEliminarDeActividad().setEnabled(false);
		getCbActividad().setEnabled(false);
		getCbActividad().removeAllItems();
		getLblNumUsarios().setText("Personas en clase:        ");
		getLblHora().setText("    Hora:");
		getBtnConfirmarCambios().setEnabled(false);
		getTxtAnadir().setEnabled(false);
		getTxtEliminar().setEnabled(false);
	}

	private JLabel getLblHora() {
		if (lblHora == null) {
			lblHora = new JLabel("    Hora:");
			lblHora.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblHora;
	}

	private JPanel getPnlEliminarSocio() {
		if (pnlEliminarSocio == null) {
			pnlEliminarSocio = new JPanel();
			pnlEliminarSocio.setLayout(new GridLayout(0, 1, 0, 5));
			pnlEliminarSocio.add(getLblNewLabel());
			pnlEliminarSocio.add(getTxtAnadir());
			pnlEliminarSocio.add(getBtnAnadirNuevoSocio());
		}
		return pnlEliminarSocio;
	}

	private JPanel getPnlBotones() {
		if (pnlBotones == null) {
			pnlBotones = new JPanel();
			pnlBotones.setLayout(new GridLayout(0, 1, 0, 0));
			pnlBotones.add(getPnlEliminarSocio());
			pnlBotones.add(getPnlAnadirSocio());
		}
		return pnlBotones;
	}

	private JTextField getTxtEliminar() {
		if (txtEliminar == null) {
			txtEliminar = new JTextField();
			txtEliminar.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtEliminar.setColumns(10);
		}
		return txtEliminar;
	}

	private JTextField getTxtAnadir() {
		if (txtAnadir == null) {
			txtAnadir = new JTextField();
			txtAnadir.setFont(new Font("Tahoma", Font.PLAIN, 15));
			txtAnadir.setColumns(10);
		}
		return txtAnadir;
	}

	private JLabel getLblEliminar() {
		if (lblEliminar == null) {
			lblEliminar = new JLabel("ID quitar de tabla:");
		}
		return lblEliminar;
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("ID a\u00F1adir a actividad:");
		}
		return lblNewLabel;
	}

	public int getPersonasEnActividad() {
		asistidos = 0;
		sinPlaza = 0;
		for (int i = 0; i < getT().getRowCount(); i++) {
			if ((Boolean) modeloTabla.getValueAt(i, 3) == true)
				asistidos++;
			else if ((Boolean) modeloTabla.getValueAt(i, 3) == false)
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
					int maximo = MonitorDatos.maxPlazasActividad(idMonitor,
							valorCbActividad());
					if (asistidos > maximo) {
						JOptionPane.showMessageDialog(null,
								"Numero de asistidos no puede ser mayor \nque el numero de plazas de la actividad");
					} else {
						Object[][] cambios = new Object[modeloTabla
								.getRowCount()][2];
						for (int i = 0; i < modeloTabla.getRowCount(); i++) {
							cambios[i][0] = (Long) modeloTabla.getValueAt(i, 0);
							cambios[i][1] = (Boolean) modeloTabla.getValueAt(i,
									3);
						}
						UsuarioDatos.guardarCambiosActividad(valorCbActividad(),
								cambios, modeloTabla.getRowCount());
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

	private JLabel getLblFecha() {
		if (lblFecha == null) {
			lblFecha = new JLabel("Fecha:");
			lblFecha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		}
		return lblFecha;
	}
}
