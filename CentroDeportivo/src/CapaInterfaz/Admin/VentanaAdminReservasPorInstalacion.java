package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import CapaDatos.PagoDatos;
import CapaInterfaz.ModeloNoEditable;
import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.managers.ManagerAdmin;

public class VentanaAdminReservasPorInstalacion extends JFrame {
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerInicio, spinnerFin;
	private JTable table;
	private ModeloNoEditable modeloTabla;
	private JButton btnBuscar;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VentanaAdminReservasPorInstalacion() {
		instalaciones = ManagerAdmin.verInstalaciones();
		setResizable(false);
		setBounds(100, 100, 786, 525);
		JLabel lblReservasInstalaciones = new JLabel("Reservas Instalaciones");
		lblReservasInstalaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservasInstalaciones.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblReservasInstalaciones.setFont(new Font("Arial Black", Font.BOLD, 25));
		getContentPane().add(lblReservasInstalaciones, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panelCentro = new JPanel();
		panel.add(panelCentro, BorderLayout.CENTER);
		panelCentro.setLayout(new BorderLayout(0, 0));

		JScrollPane spTabla = new JScrollPane();
		panelCentro.add(spTabla, BorderLayout.CENTER);
		modeloTabla = new ModeloNoEditable(
				new String[] { "Día", "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado", "Reservante" }, 0);
		table = new JTable();
		table.setDefaultRenderer(Object.class, new TableCellRendererColorAdmin());
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		spTabla.setViewportView(table);

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);

		JLabel lblInicio = new JLabel("Inicio");
		panelCabecera.add(lblInicio);

		spinnerInicio = new JSpinner();
		spinnerInicio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Calendar date = Calendar.getInstance();
				date.setTime((Date) spinnerInicio.getValue());
				date.add(Calendar.DATE, 7);
				spinnerFin.setValue(date.getTime());
				btnBuscar.setEnabled(true);
			}
		});
		spinnerInicio.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.WEEK_OF_YEAR));
		panelCabecera.add(spinnerInicio);

		JLabel lblFin = new JLabel("Fin");
		panelCabecera.add(lblFin);

		spinnerFin = new JSpinner();
		spinnerFin.setEnabled(false);
		spinnerFin.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		panelCabecera.add(spinnerFin);

		JLabel lblInstalacion = new JLabel("Instalaci\u00F3n");
		panelCabecera.add(lblInstalacion);

		instalaciones = ManagerAdmin.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}
		comboBoxInstalaciones = new JComboBox(instalacionesStrings);
		panelCabecera.add(comboBoxInstalaciones);

		btnBuscar = new JButton("Ver reservas");
		btnBuscar.setEnabled(false);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerReservasSemanal();
			}
		});
		panelCabecera.add(btnBuscar);

		JPanel panelPie = new JPanel();
		panel.add(panelPie, BorderLayout.SOUTH);
		panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblCentro = new JLabel("CENTRO");
		lblCentro.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblCentro.setOpaque(true);
		lblCentro.setBackground(new Color(255, 185, 185));
		panelPie.add(lblCentro);

		JLabel lblSocio = new JLabel("SOCIO");
		lblSocio.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblSocio.setOpaque(true);
		lblSocio.setBackground(new Color(185, 255, 185));
		panelPie.add(lblSocio);

		JButton button = new JButton("Detalles");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					new VentanaDetallesReserva((Long) modeloTabla.getValueAt(table.getSelectedRow(), 1)).show();
				}
			}
		});
		panelPie.add(button);
	}

	public void obtenerReservasSemanal() {
		Date inicio = (Date) spinnerInicio.getValue();
		Date fin = (Date) spinnerFin.getValue();

		List<ReservaDao> reservas = ManagerAdmin.verReservasPorFechaEInstalacion(inicio, fin, obtenerIDInstalacion());
		Object[] line = new Object[7];
		int tam = modeloTabla.getRowCount();
		for (int i = 0; i < tam; i++) {
			modeloTabla.removeRow(0);
		}

		for (int i = 0; i < reservas.size(); i++) {
			ReservaDao reserva = reservas.get(i);
			line[0] = DiasSemana.values()[reserva.getInicio().getDayOfWeek()];
			line[1] = reserva.getIdRes();
			line[2] = reserva.getInicio();
			line[3] = reserva.getFin();
			Pago pago = PagoDatos.obtenerPago(reserva.getIdPago());
			line[4] = pago.getEstado();
			line[5] = reserva.getEstado();
			//line[6] = reserva.getTipoRes();
			line[6] = reserva.getTipoRes().equals(TipoReserva.SOCIO.name()) ? reserva.getIdUsu() : TipoReserva.CENTRO;

			modeloTabla.addRow(line);
		}
	}

	private Long obtenerIDInstalacion() {
		return instalaciones.get(comboBoxInstalaciones.getSelectedIndex()).getIdInst();
	}

}
