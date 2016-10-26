package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

import org.joda.time.DateTime;

import CapaDatos.PagoDatos;
import CapaDatos.ReservaDatos;
import CapaDatos.UsuarioDatos;
import CapaInterfaz.ModeloConColumnasEditables;
import CapaInterfaz.ModeloNoEditable;
import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.dao.Usuario;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerFechas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;

public class VentanaSocioUtilizandoInstalacion extends JFrame {
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerInicio, spinnerFin;
	private JTable table;
	private ModeloConColumnasEditables modeloTabla;
	private JButton btnBuscar;
	private List<Usuario> usuarios;
	private JComboBox<String> comboBoxInstalaciones;
	private int selectedRow;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VentanaSocioUtilizandoInstalacion() {
		usuarios = UsuarioDatos.ObtenerUsuarios();
		setResizable(false);
		setBounds(100, 100, 786, 525);
		JLabel lblReservasInstalaciones = new JLabel("Cancelar Reservas");
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
		panelCentro.add(spTabla, BorderLayout.NORTH);
		modeloTabla = new ModeloConColumnasEditables(new String[] { "Día", "ID", "Hora Inicio", "Hora Fin", "Pago",
				"Estado", "Hora Entrada", "Hora Salida" }, 0);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectedRow = table.getSelectedRow();
			}
		});
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

		JLabel lblInstalacion = new JLabel("Socio");
		panelCabecera.add(lblInstalacion);

		usuarios = UsuarioDatos.ObtenerUsuarios();
		Long[] usuariosIds = new Long[usuarios.size()];
		int aux = 0;
		for (Usuario usuario : usuarios) {
			usuariosIds[aux] = usuario.getIdUsu();
			aux++;
		}
		comboBoxInstalaciones = new JComboBox(usuariosIds);
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

		JButton btnModificarHoraEntradasalida = new JButton("Modificar hora entrada/salida");
		btnModificarHoraEntradasalida.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				int fila = table.getSelectedRow();
				System.out.println(fila);
				int botonDialogo = JOptionPane.YES_NO_OPTION;
				if (fila != -1) {
					long id = (long) modeloTabla.getValueAt(fila, 1);
					String horaEntrada = "";
					String horaSalida = "";
					Long reservaId = new Long(0);
					List<ReservaDao> reservas = new ArrayList<>();
					for (int i = 0; i < modeloTabla.getDataVector().size(); i++) {
						horaEntrada = (String) modeloTabla.getValueAt(fila, 6);
						horaSalida = (String) modeloTabla.getValueAt(fila, 7);
						reservaId = (Long) modeloTabla.getValueAt(fila, 1);
						ReservaDao reserva = null;
						if (horaEntrada != "" | horaSalida != "") {
							reserva = ReservaDatos.obtenerReservaPorId(reservaId);
							try {
								reserva.setHoraEntrada(ManagerFechas.getDateTimeDeString(horaEntrada));
								reserva.setHoraSalida(ManagerFechas.getDateTimeDeString(horaSalida));
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(null, "Formato de fecha de la reserva "
										+ reserva.getIdRes() + "es incorrecto!\n Vuelve a intentarlo.");
							}
							reservas.add(reserva);
						}
					}

					botonDialogo = JOptionPane.showConfirmDialog(null,
							"Está seguro de que quiere modificar los datos de entrada/salida del socio?",
							"Confirmar Cancelacion", botonDialogo);
					if (botonDialogo == JOptionPane.YES_OPTION)
						try {
							for (ReservaDao reserva : reservas)
								ReservaDatos.actualizarHoraEntradaSalida(reserva);
							obtenerReservasSemanal();
						} catch (ExcepcionReserva e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		panelPie.add(btnModificarHoraEntradasalida);
	}

	public void obtenerReservasSemanal() {
		Date inicio = (Date) spinnerInicio.getValue();
		Date fin = (Date) spinnerFin.getValue();

		List<ReservaDao> reservas = ReservaDatos.obtenerReservasPorFechaYUsuario(inicio, fin, obtenerIDUsuario());
		Object[] line = new Object[9];
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
			// line[6] = reserva.getTipoRes().equals(TipoReserva.SOCIO.name()) ?
			// reserva.getIdUsu() : TipoReserva.CENTRO;
			DateTime entrada = reserva.getHoraEntrada();
			DateTime salida = reserva.getHoraSalida();
			if (entrada != null)
				line[6] = ManagerFechas.getStringDeDateTime(reserva.getHoraEntrada());
			else
				line[6] = "";
			if (salida != null)
				line[7] = ManagerFechas.getStringDeDateTime(reserva.getHoraSalida());
			else
				line[7] = "";
			modeloTabla.addRow(line);
		}
	}

	private Long obtenerIDUsuario() {
		return usuarios.get(comboBoxInstalaciones.getSelectedIndex()).getIdUsu();
	}

}
