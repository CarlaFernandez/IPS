package CapaInterfaz.Socio.Instalaciones;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import CapaDatos.ActividadesDatos;
import CapaInterfaz.ModeloNoEditable;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.managers.ManagerAdmin;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaApuntarseActividad extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
	public ModeloNoEditable modeloTabla;
	private List<Instalacion> instalaciones;
	private JComboBox<String> comboBoxInstalaciones;
	JTextArea textArea;
	private int selectedRow = -1;
	private HashMap<Long, String> descriptions;
	private long user;

	public VentanaApuntarseActividad(long user) {
		this.user = user;
		instalaciones = ManagerAdmin.verInstalaciones();
		setResizable(false);
		setBounds(100, 100, 786, 525);
		JLabel lblReservasInstalaciones = new JLabel("Apuntarse a actividades");
		lblReservasInstalaciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblReservasInstalaciones.setBorder(new EmptyBorder(20, 0, 20, 0));
		lblReservasInstalaciones
				.setFont(new Font("Arial Black", Font.BOLD, 25));
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
				new String[] { "Fecha", "ID", "Nombre", "Plazas Totales",
						"Plazas Ocupadas", "Numero de horas" },
				0);
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selectedRow = table.getSelectedRow();
				actualizarDescripcion();
			}
		});
		table.setModel(modeloTabla);
		table.setBackground(Color.WHITE);
		spTabla.setViewportView(table);
		obtenerActividades();

		JPanel panelCabecera = new JPanel();
		panel.add(panelCabecera, BorderLayout.NORTH);

		JPanel panelPie = new JPanel();
		panelPie.setBorder(new TitledBorder(null, "Descripcion",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panelPie, BorderLayout.SOUTH);
		panelPie.setLayout(new GridLayout(2, 1, 0, 0));

		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		panelPie.add(textArea);

		JPanel panel_1 = new JPanel();
		panelPie.add(panel_1);

		JButton btnApuntarme = new JButton("Apuntarme");
		btnApuntarme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null,
							"No ha seleccionado ninguna actividad.\nPor favor seleccione una actividad y vuelva a intentarlo.",
							"No hay actividad seleccionada",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Long iDActividad = (Long) modeloTabla
							.getValueAt(selectedRow, 1);
					String nombreActividad = (String) modeloTabla
							.getValueAt(selectedRow, 2);
					if (ActividadesDatos.comprobarUsuarioApuntadoActividad(
							iDActividad, user)) {
						JOptionPane.showMessageDialog(null,
								"Ya está apuntado a esta actividad.",
								"ERROR: Ya está apuntado",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					int botonDialogo = JOptionPane.YES_NO_OPTION;
					botonDialogo = JOptionPane.showConfirmDialog(null,
							"Está seguro de que quiere apuntarse a la actividad: "
									+ nombreActividad + "?",
							"Confirmar Inscripcion", botonDialogo);
					if (botonDialogo == JOptionPane.YES_OPTION) {
						ActividadesDatos.apuntarseActividad(user, iDActividad);
						JOptionPane.showMessageDialog(null,
								"Se ha apuntado correctamente.", "Correcto",
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}

		});
		panel_1.add(btnApuntarme);
	}

	private void actualizarDescripcion() {
		textArea.setText(
				descriptions.get(modeloTabla.getValueAt(selectedRow, 1)));
	}

	private void obtenerActividades() {
		List<Actividad> actividades = ActividadesDatos
				.obtenerActividadesFuturas();
		descriptions = new HashMap<>();
		Object[] line = new Object[9];
		int tam = modeloTabla.getRowCount();
		for (int i = 0; i < tam; i++) {
			modeloTabla.removeRow(0);
		}

		for (int i = 0; i < actividades.size(); i++) {
			line[0] = actividades.get(i).getFecha_entrada();
			line[1] = actividades.get(i).getCodigo();
			line[2] = actividades.get(i).getNombre();
			line[3] = actividades.get(i).getPlazasTotales();
			line[4] = actividades.get(i).getPlazasOcupadas();
			line[5] = actividades.get(i).getNumeroHoras();
			descriptions.put(actividades.get(i).getCodigo(),
					actividades.get(i).getDescripcion());
			modeloTabla.addRow(line);
		}
	}

}