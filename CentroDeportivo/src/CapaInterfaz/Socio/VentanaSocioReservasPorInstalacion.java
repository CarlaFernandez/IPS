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

import CapaDatos.PagoDatos;
import CapaInterfaz.ModeloNoEditable;
import CapaInterfaz.VentanaDetallesReserva;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerUsuario;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;

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
		modeloTabla = new ModeloNoEditable(new String[] { "Dia", "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado" },
				0);
		table = new JTable();
		table.setDefaultRenderer(Object.class, new TableCellRendererColorSocio());
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		spTabla.setViewportView(table);

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);

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

		JButton btnNewButton_1 = new JButton("Detalles");
		btnNewButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedRow() != -1) {
					new VentanaDetallesReserva((Long) modeloTabla.getValueAt(table.getSelectedRow(), 1)).show();
				}
			}
		});

		JLabel label = new JLabel("Fecha Anterior");
		label.setOpaque(true);
		label.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		label.setBackground(new Color(185, 185, 255));
		panelPie.add(label);

		JLabel label_1 = new JLabel("Cancelada");
		label_1.setOpaque(true);
		label_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		label_1.setBackground(new Color(255, 185, 185));
		panelPie.add(label_1);

		JLabel lblNewLabel_4 = new JLabel("Anulada");
		lblNewLabel_4.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBackground(new Color(255, 255, 185));
		panelPie.add(lblNewLabel_4);

		JLabel label_2 = new JLabel("Fecha Futura");
		label_2.setOpaque(true);
		label_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		label_2.setBackground(new Color(185, 255, 185));
		panelPie.add(label_2);
		panelPie.add(btnNewButton_1);
	}

	public void obtenerReservasPorInstalacion() {

		Long instalacion = instalaciones.get(comboBoxInstalaaciones.getSelectedIndex()).getIdInst();

		// "ID", "Hora Inicio", "Hora Fin", "Pago", "Estado"
		List<ReservaDao> reservas = ManagerUsuario.verReservasInstalacion(instalacion, this.user);
		Object[] line = new Object[6];
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

			modeloTabla.addRow(line);
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Instalacion> instalaciones;
	private List<Usuario> usuarios;
	private JTable table;
	private ModeloNoEditable modeloTabla;

	private JComboBox comboBoxInstalaaciones;

}
