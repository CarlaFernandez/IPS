package CapaInterfaz.Socio.Instalaciones;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import CapaDatos.PagoDatos;
import CapaDatos.ReservaDatos;
import CapaInterfaz.ModeloNoEditable;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerUsuario;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaSocioCancelarReserva extends JFrame {
	private static final long serialVersionUID = 1L;
	private JSpinner spinnerInicio, spinnerFin;
	private JTable table;
	private ModeloNoEditable modeloTabla;
	private JButton btnBuscar;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	private int selectedRow;
	private long user;

	public VentanaSocioCancelarReserva(long user) {
		this.user = user;
		instalaciones = ManagerAdmin.verInstalaciones();
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
		panelCentro.add(spTabla, BorderLayout.CENTER);
		modeloTabla = new ModeloNoEditable(new String[] { "Día", "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado" },
				0);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectedRow = table.getSelectedRow();
			}
		});
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

		JButton button = new JButton("Cancelar Reserva");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = table.getSelectedRow();
				int botonDialogo = JOptionPane.YES_NO_OPTION;
				if (fila != -1) {
					long id = (long) modeloTabla.getValueAt(fila, 1);
					ReservaDao reserva = ReservaDatos.obtenerReservaPorId(id);
					botonDialogo = JOptionPane.showConfirmDialog(null, "Está seguro de que quiere cancelar la reserva?",
							"Confirmar Cancelacion", botonDialogo);
					if (botonDialogo == JOptionPane.YES_OPTION)
						try {
							ReservaDatos.cancelarReservaComoAdmin(reserva);
							obtenerReservasSemanal();
						} catch (ExcepcionReserva e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		panelPie.add(button);
	}

	public void obtenerReservasSemanal() {
		Date inicio = (Date) spinnerInicio.getValue();
		Date fin = (Date) spinnerFin.getValue();

		List<ReservaDao> reservas = ManagerUsuario.verReservasPorFecha(inicio, fin, user);
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
			modeloTabla.addRow(line);
		}
	}

	private Long obtenerIDInstalacion() {
		return instalaciones.get(comboBoxInstalaciones.getSelectedIndex()).getIdInst();
	}

}

/*
 * package CapaInterfaz.Socio;
 * 
 * import java.awt.event.ActionEvent; import java.awt.event.ActionListener;
 * 
 * import javax.swing.JButton; import javax.swing.JDialog; import
 * javax.swing.JLabel; import javax.swing.JOptionPane; import
 * javax.swing.JTextField;
 * 
 * import CapaDatos.ReservaDatos; import CapaNegocio.dao.ReservaDao; import
 * CapaNegocio.excepciones.ExcepcionReserva;
 * 
 * public class VentanaCancelarReservaSocio extends JDialog {
 * 
 * 
 * private static final long serialVersionUID = 1L; private JTextField
 * textField; public VentanaCancelarReservaSocio() { setResizable(false);
 * setBounds(100, 100, 450, 300); getContentPane().setLayout(null);
 * 
 * JLabel lblIdreserva = new JLabel("idReserva:"); lblIdreserva.setBounds(107,
 * 63, 67, 22); getContentPane().add(lblIdreserva);
 * 
 * textField = new JTextField(); lblIdreserva.setLabelFor(textField);
 * textField.setBounds(184, 64, 86, 20); getContentPane().add(textField);
 * textField.setColumns(10);
 * 
 * JButton btnCancelarReserva = new JButton(" Cancelar Reserva");
 * btnCancelarReserva.addActionListener(new ActionListener() { public void
 * actionPerformed(ActionEvent e) { cancelarReserva(); } });
 * btnCancelarReserva.setBounds(147, 135, 123, 23);
 * getContentPane().add(btnCancelarReserva); }
 * 
 * private void cancelarReserva() { ReservaDao reserva =
 * ReservaDatos.getReserva(Long.parseLong(textField.getText())); try {
 * ReservaDatos.cancelarReservaComoSocio(reserva); } catch (ExcepcionReserva e1)
 * { JOptionPane.showMessageDialog(this, "El formato es incorrecto"); } catch
 * (NumberFormatException e2){ JOptionPane.showMessageDialog(this,
 * "Formato del id incorrecto"); } }
 * 
 * }
 */