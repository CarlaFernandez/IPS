package CapaInterfaz.Socio.Instalaciones;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import CapaDatos.InstalacionDatos;
import CapaInterfaz.VentanaDetallesReserva;
import CapaInterfaz.Socio.TableCellRendererColorEstadoReserva2;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerUsuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;

import javax.swing.event.ChangeEvent;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;

public class VentanaSocioVerMisReservas extends JFrame {
	private Long user;

	public VentanaSocioVerMisReservas(Long user) {
		tablaReservas = new ReservaDao[8][24];
		this.user = user;
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblTituloSocio = new JLabel("Mis Reservas");
		lblTituloSocio.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloSocio.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblTituloSocio.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblTituloSocio, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelCentro = new JPanel();
		panel.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));

		JScrollPane spTabla = new JScrollPane();
		panelCentro.add(spTabla, BorderLayout.CENTER);
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
		tm.setColumnIdentifiers(new String[] { "", DiasSemana.values()[0].name(), DiasSemana.values()[1].name(),
				DiasSemana.values()[2].name(), DiasSemana.values()[3].name(), DiasSemana.values()[4].name(),
				DiasSemana.values()[5].name(), DiasSemana.values()[6].name() });

		// Valores para la primera columna, que es la cabecera lateral.
		for (int i = 0; i < tm.getRowCount(); i++)
			tm.setValueAt(i + ":00", i, 0);

		// JTable al que se le pasa el modelo recien creado y se
		// sobreescribe el metodo changeSelection para que no permita
		// seleccionar la primera columna.
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
		t.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int clicks = e.getClickCount();
				if (clicks >= 2) {
					verDetalles(t);
				}
			}
		});

		// Se pone a la primera columna el render del JTableHeader
		// superior.
		t.getColumnModel().getColumn(0).setCellRenderer(t.getTableHeader().getDefaultRenderer());
		t.setDefaultRenderer(Object.class, new TableCellRendererColorEstadoReserva2());
		spTabla.setViewportView(t);

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);

		JPanel panel_1 = new JPanel();
		panelCabecera.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblSemanaDelDia = new JLabel("Semana del dia:");
		panel_1.add(lblSemanaDelDia);

		spinnerInicio = new JSpinner();

		spinnerInicio.setToolTipText("Se mostrara la semana a la que pertenece el dia seleccionado");
		Calendar ahora = Calendar.getInstance();
		ahora.set(Calendar.MILLISECOND, 0);
		ahora.set(Calendar.SECOND, 0);
		ahora.set(Calendar.MINUTE, 0);
		ahora.set(Calendar.HOUR, 0);
		spinnerInicio.setModel(new SpinnerDateModel(ahora.getTime(), null, null, Calendar.DAY_OF_YEAR));
		panel_1.add(spinnerInicio);

		JLabel lblElDiaQue = new JLabel("Dia Seleccionado:   ");
		panel_1.add(lblElDiaQue);

		DiasSemana.values()[new DateTime(ahora.getTime()).getDayOfWeek() - 1].name();
		JLabel lblDiaSemana = new JLabel(DiasSemana.values()[new DateTime(ahora.getTime()).getDayOfWeek() - 1].name());
		panel_1.add(lblDiaSemana);
		Calendar date = Calendar.getInstance();
		date.setTime((Date) spinnerInicio.getValue());
		date.add(Calendar.DATE, 7);

		spinnerInicio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblDiaSemana.setText(DiasSemana
						.values()[new DateTime(((Date) spinnerInicio.getValue()).getTime()).getDayOfWeek() - 1].name());
			}
		});

		instalaciones = ManagerUsuario.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}

		usuarios = ManagerUsuario.verUsuarios();
		String[] usuariosStrings = new String[usuarios.size()];
		aux = 0;
		for (Usuario usuario : usuarios) {
			usuariosStrings[aux] = usuario.getIdUsu().toString();
			aux++;
		}

		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerReservasPorInstalacion();
			}
		});
		panelCabecera.add(btnNewButton);

		JPanel panelPie = new JPanel();
		panel.add(panelPie, BorderLayout.SOUTH);
		panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblPasada = new JLabel("Pasadas");
		lblPasada.setOpaque(true);
		lblPasada.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPasada.setBackground(new Color(185, 185, 255));
		panelPie.add(lblPasada);

		JLabel lblPendiente = new JLabel("Pendiente");
		lblPendiente.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblPendiente.setOpaque(true);
		lblPendiente.setBackground(new Color(185, 255, 185));
		panelPie.add(lblPendiente);

		JLabel lblCancelada = new JLabel("Cancelada");
		lblCancelada.setOpaque(true);
		lblCancelada.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblCancelada.setBackground(new Color(255, 185, 185));
		panelPie.add(lblCancelada);

		JButton btnVerDetalles = new JButton("Ver detalles");
		btnVerDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verDetalles(t);
			}
		});

		JLabel lblAnulada = new JLabel("Anulada");
		lblAnulada.setOpaque(true);
		lblAnulada.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblAnulada.setBackground(new Color(255, 255, 185));
		panelPie.add(lblAnulada);
		panelPie.add(btnVerDetalles);
		obtenerReservasPorInstalacion();
	}

	@SuppressWarnings("deprecation")
	private void verDetalles(JTable t) {
		if (t.getSelectedRow() != -1 && tablaReservas[t.getSelectedColumn()][t.getSelectedRow()] != null) {
			ReservaDao reserva = tablaReservas[t.getSelectedColumn()][t.getSelectedRow()];
			new VentanaDetallesReserva(reserva.getIdRes()).show();
		}
	}

	public void obtenerReservasPorInstalacion() {
		Date valorSpin = (Date) spinnerInicio.getValue();

		Calendar dateInicio = Calendar.getInstance();
		dateInicio.setTime((Date) spinnerInicio.getValue());
		dateInicio.add(Calendar.DATE, -new DateTime(valorSpin.getTime()).getDayOfWeek());
		dateInicio.set(Calendar.MILLISECOND, 0);
		dateInicio.set(Calendar.SECOND, 0);
		dateInicio.set(Calendar.MINUTE, 0);
		dateInicio.set(Calendar.HOUR, 0);

		Calendar dateF = Calendar.getInstance();
		dateF.setTime(dateInicio.getTime());
		dateF.add(Calendar.DATE, 8);
		Date fin = dateF.getTime();

		// System.out.println(dateInicio.getTime());
		// System.out.println(dateF.getTime());

		// "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado"
		// List<ReservaDao> reservas =
		// ManagerUsuario.verReservasInstalacion(instalacion);
		List<ReservaDao> reservas = ManagerAdmin.verMisReservasPorFecha(dateInicio.getTime(), fin, user);

		for (int i = 0; i < tm.getRowCount(); i++) {
			for (int j = 0; j < tm.getColumnCount() - 1; j++) {
				tm.setValueAt("", i, j + 1);
			}
		}

		for (int i = 0; i < reservas.size(); i++) {
			int dia = reservas.get(i).getInicio().getDayOfWeek();
			int hora = reservas.get(i).getInicio().getHourOfDay();
			//int nhoras = reservas.get(i).getFin().getHourOfDay() - reservas.get(i).getInicio().getHourOfDay();
			int nhoras = (int) (reservas.get(i).getDuracionEnMinutos() / 60);
			// System.out.println("nhoras" + nhoras);
			for (int j = 0; j < nhoras; j++) {
				tm.setValueAt(InstalacionDatos.ObtenerInstalacion(reservas.get(i).getIdInst()).getCodigo()+"--"+reservas.get(i).getEstado()+"--"+reservas.get(i).getFin(), hora + j,
						dia);
				tablaReservas[dia][hora + j] = reservas.get(i);
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Instalacion> instalaciones;
	private List<Usuario> usuarios;
	private JTable t;
	private DefaultTableModel tm;
	JSpinner spinnerInicio;
	ReservaDao tablaReservas[][];

}
