package CapaInterfaz.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.joda.time.DateTime;

import CapaDatos.InstalacionDatos;
import CapaDatos.PagoDatos;
import CapaInterfaz.ModeloNoEditable;
import CapaInterfaz.VentanaDetallesReserva;
import CapaInterfaz.Socio.TableCellRendererColorSocio;
import CapaNegocio.DiasSemana;
import CapaNegocio.EstadoPago;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerAdmin;
import CapaNegocio.managers.ManagerUsuario;

import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;

public class VentanaReservasPagar extends JFrame {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VentanaReservasPagar() {
		setResizable(false);
		setBounds(100, 100, 786, 525);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel lblTituloSocio = new JLabel("Reservas sin pagar");
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

		instalaciones = ManagerUsuario.verInstalaciones();
		String[] instalacionesStrings = new String[instalaciones.size()];
		int aux = 0;
		for (Instalacion instalacion : instalaciones) {
			instalacionesStrings[aux] = instalacion.getCodigo();
			aux++;
		}

		JLabel lblNewLabel_3 = new JLabel("UsuarioID");
		panelCabecera.add(lblNewLabel_3);
		usuarios = ManagerUsuario.verUsuarios();
		String[] usuariosStrings = new String[usuarios.size()];
		aux = 0;
		for (Usuario usuario : usuarios) {
			usuariosStrings[aux] = usuario.getIdUsu().toString();
			aux++;
		}
		comboBoxUsuarios = new JComboBox(usuariosStrings);
		panelCabecera.add(comboBoxUsuarios);

		JButton btnNewButton = new JButton("Buscar/Actualizar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obtenerReservasPorInstalacion();
			}
		});
		panelCabecera.add(btnNewButton);

		JPanel panelPie = new JPanel();
		panel.add(panelPie, BorderLayout.SOUTH);
		panelPie.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					if (new DateTime(modeloTabla.getValueAt(table.getSelectedRow(), 2)).isAfterNow()) {
						JOptionPane.showMessageDialog(null, "Aun no ha pasado la fecha/hora (inicio)!");
						return;
					}
					if (modeloTabla.getValueAt(table.getSelectedRow(), 5).toString().equals("true")) {
						JOptionPane.showMessageDialog(null, "No se puede abonar una reserva cancelada!");
						return;
					}
					ManagerAdmin.crearPagoEfectivo((Long) modeloTabla.getValueAt(table.getSelectedRow(), 1));
					modeloTabla.setValueAt("COBRADO", table.getSelectedRow(), 4);
				}

			}
		});

		JLabel label = new JLabel("Fecha Anterior");
		label.setOpaque(true);
		label.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		label.setBackground(new Color(185, 185, 255));
		panelPie.add(label);

		JLabel label_2 = new JLabel("Fecha Futura");
		label_2.setOpaque(true);
		label_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		label_2.setBackground(new Color(185, 255, 185));
		panelPie.add(label_2);

		JButton button = new JButton("Detalles");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() != -1) {
					new VentanaDetallesReserva((Long) modeloTabla.getValueAt(table.getSelectedRow(), 1)).show();
				}
			}
		});
		panelPie.add(button);
		panelPie.add(btnPagar);
	}

	public void obtenerReservasPorInstalacion() {
		Long user = usuarios.get(comboBoxUsuarios.getSelectedIndex()).getIdUsu();

		// "ID", "Hora Inicio", "Hora Fin", "PagoId", "Cancelada"
		List<ReservaDao> reservas = ManagerUsuario.verReservasInstalacionSinPagar(user);
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
	private List<Instalacion> instalaciones;
	private List<Usuario> usuarios;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBoxUsuarios;
	private JTable table;
	private ModeloNoEditable modeloTabla;

}