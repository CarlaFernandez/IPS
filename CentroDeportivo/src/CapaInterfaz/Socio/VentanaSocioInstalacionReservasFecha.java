package CapaInterfaz.Socio;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import CapaDatos.InstalacionDatos;
import CapaDatos.PagoDatos;
import CapaInterfaz.ModeloNoEditable;
import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerUsuario;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import javax.swing.event.ChangeListener;

import javax.swing.event.ChangeEvent;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;

public class VentanaSocioInstalacionReservasFecha extends JFrame {
	private Long user;

	public VentanaSocioInstalacionReservasFecha(Long user) {
		this.user = user;
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblTituloSocio = new JLabel("Mis Reservas Por Fecha");
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
		modeloTabla = new ModeloNoEditable(
				new String[] { "Dia", "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado", "Instalacion" }, 0);
		table = new JTable();
		table.setDefaultRenderer(Object.class, new TableCellRendererColorSocio());
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		spTabla.setViewportView(table);

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel("Inicio");
		panelCabecera.add(lblNewLabel);

		spinnerInicio = new JSpinner();
		spinnerInicio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Calendar date = Calendar.getInstance();
				date.setTime((Date) spinnerInicio.getValue());
				date.add(Calendar.DATE, 7);
				spinnerFin.setValue(date.getTime());
			}
		});
		spinnerInicio.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.WEEK_OF_YEAR));
		panelCabecera.add(spinnerInicio);

		usuarios = ManagerUsuario.verUsuarios();
		String[] usuariosStrings = new String[usuarios.size()];
		int aux = 0;
		for (Usuario usuario : usuarios) {
			usuariosStrings[aux] = usuario.getIdUsu().toString();
			aux++;
		}

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerReservasSemanalPorUsuario();
			}
		});

		JLabel lblFin = new JLabel("Fin");
		panelCabecera.add(lblFin);

		spinnerFin = new JSpinner();
		spinnerFin.setEnabled(false);
		Calendar date = Calendar.getInstance();
		date.setTime((Date) spinnerInicio.getValue());
		date.add(Calendar.DATE, 7);
		spinnerFin.setModel(new SpinnerDateModel(date.getTime(), null, null, Calendar.DAY_OF_YEAR));
		panelCabecera.add(spinnerFin);
		panelCabecera.add(btnBuscar);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Fecha Anterior");
		lblNewLabel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(185, 185, 255));
		panel_1.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Cancelada");
		lblNewLabel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(255, 185, 185));
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("Anulada");
		lblNewLabel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(255, 255, 185));
		panel_1.add(lblNewLabel_4);

		JLabel lblNewLabel_3 = new JLabel("Fecha Futura");
		lblNewLabel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(185, 255, 185));
		panel_1.add(lblNewLabel_3);

		JButton button = new JButton("Detalles");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					new VentanaDetallesReserva((Long) modeloTabla.getValueAt(table.getSelectedRow(), 1)).show();
				}
			}
		});
		panel_1.add(button);
	}

	public void obtenerReservasSemanalPorUsuario() {
		Date inicio = (Date) spinnerInicio.getValue();
		Date fin = (Date) spinnerFin.getValue();

		List<ReservaDao> reservas = ManagerUsuario.verReservasPorFecha(inicio, fin, user);
		Object[] line = new Object[7];
		int tam = modeloTabla.getRowCount();
		for (int i = 0; i < tam; i++) {
			modeloTabla.removeRow(0);
		}

		for (int i = 0; i < reservas.size(); i++) {
			line[0] = DiasSemana.values()[reservas.get(i).getInicio().getDayOfWeek()];
			line[1] = reservas.get(i).getIdRes();
			line[2] = reservas.get(i).getInicio();
			line[3] = reservas.get(i).getFin();
			Pago pago = PagoDatos.obtenerPago(reservas.get(i).getIdPago());
			line[4] = pago.getEstado();
			line[5] = reservas.get(i).getEstado();
			Instalacion instalacion = InstalacionDatos.ObtenerInstalacion(reservas.get(i).getIdInst());
			line[6] = instalacion.getCodigo();

			modeloTabla.addRow(line);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerInicio, spinnerFin;
	private List<Usuario> usuarios;
	private JTable table;
	private ModeloNoEditable modeloTabla;
	private JButton btnBuscar;

}
