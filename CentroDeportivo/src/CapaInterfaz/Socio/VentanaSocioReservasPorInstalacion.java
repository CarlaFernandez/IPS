package CapaInterfaz.Socio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.joda.time.DateTime;

import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerUsuario;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("rawtypes")
public class VentanaSocioReservasPorInstalacion extends JFrame {
	private Long user;

	@SuppressWarnings("unchecked")
	public VentanaSocioReservasPorInstalacion(Long user) {
		this.user = user;
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblTituloSocio = new JLabel("Reservas Por Instalacion");
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
		t.setDefaultRenderer(Object.class, new TableCellRendererColorInstalacion());
		spTabla.setViewportView(t);

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);

		JLabel lblSemanaDelDia = new JLabel("Semana del dia:");
		panelCabecera.add(lblSemanaDelDia);

		spinnerInicio = new JSpinner();
		spinnerInicio.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		panelCabecera.add(spinnerInicio);
		Calendar date = Calendar.getInstance();
		date.setTime((Date) spinnerInicio.getValue());
		date.add(Calendar.DATE, 7);

		JLabel lblNewLabel = new JLabel("Instalacion");
		panelCabecera.add(lblNewLabel);

		instalaciones = ManagerUsuario.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}
		comboBoxInstalaaciones = new JComboBox(instalacionesStrings);
		panelCabecera.add(comboBoxInstalaaciones);

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

		JLabel lblOtras = new JLabel("Otras");
		lblOtras.setOpaque(true);
		lblOtras.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblOtras.setBackground(new Color(255, 185, 185));
		panelPie.add(lblOtras);

		JLabel lblNewLabel_4 = new JLabel("Libre");
		lblNewLabel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(Color.WHITE);
		panelPie.add(lblNewLabel_4);

		JLabel lblMias = new JLabel("Mias");
		lblMias.setOpaque(true);
		lblMias.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblMias.setBackground(new Color(185, 255, 185));
		panelPie.add(lblMias);

		JButton btnVerDetalles = new JButton("Ver detalles");
		btnVerDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verDetalles(t);
			}
		});
		panelPie.add(btnVerDetalles);
		obtenerReservasPorInstalacion();
	}

	@SuppressWarnings("deprecation")
	private void verDetalles(JTable t) {
		ReservaDao reserva = tablaReservas[t.getSelectedColumn()][t.getSelectedRow()];
		boolean mia = tm.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).equals("Mi reserva");
		if (t.getSelectedRow() != -1 && reserva != null && mia) {
			new VentanaDetallesReserva(reserva.getIdRes()).show();
		}
	}

	public void obtenerReservasPorInstalacion() {

		Long instalacion = instalaciones.get(comboBoxInstalaaciones.getSelectedIndex()).getIdInst();
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

		System.out.println(dateInicio.getTime());
		System.out.println(dateF.getTime());

		// "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado"
		// List<ReservaDao> reservas =
		// ManagerUsuario.verReservasInstalacion(instalacion);
		List<ReservaDao> reservas = ManagerAdmin.verReservasPorFechaEInstalacion(dateInicio.getTime(), fin,
				instalacion);

		for (int i = 0; i < tm.getRowCount(); i++) {
			for (int j = 0; j < tm.getColumnCount() - 1; j++) {
				tm.setValueAt("", i, j + 1);
			}
		}

		tablaReservas = new ReservaDao[8][24];
		for (int i = 0; i < reservas.size(); i++) {
			int dia = reservas.get(i).getInicio().getDayOfWeek();
			int hora = reservas.get(i).getInicio().getHourOfDay();
			if (reservas.get(i).getIdUsu().equals(user)) {
				tm.setValueAt("Mi reserva", hora, dia);
				tablaReservas[dia][hora] = reservas.get(i);
			}
			if (!reservas.get(i).getIdUsu().equals(user)) {
				tm.setValueAt("Reserva ajena", hora, dia);
				tablaReservas[dia][hora] = reservas.get(i);
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

	private JComboBox comboBoxInstalaaciones;

}
