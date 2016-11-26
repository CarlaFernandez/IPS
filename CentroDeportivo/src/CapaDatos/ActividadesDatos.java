package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Days;
import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ActividadHoras;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerFechas;

public class ActividadesDatos {

	public static final int USUARIO_YA_APUNTADO = -2;
	public static final int PLAZO_INSCIPCION_NO_ABIERO = -3;
	public static final int NO_HAY_PLAZAS_DISPONIBLES = -4;
	public static final int CLASE_CANCELADA = -5;
	public static final int EXITO = 1;
	public static final int OTRO = 0;

	public static List<Map<String, Object>> obtenerActividadesFuturas() {
		List<Map<String, Object>> actividades = new ArrayList<>();
		List<Map<String, Object>> actividadesFuturas = new ArrayList<>();
		List<Map<String, Object>> instancias = null;
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Map<String, Object> actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ACTIVIDAD");
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new HashMap<String, Object>();
				actividad.put("id", rs.getLong("ID"));
				actividad.put("descripcion", rs.getString("DESCRIPCION"));
				actividad.put("nombre", rs.getString("NOMBRE"));
				actividades.add(actividad);
			}
			for (Map<String, Object> a : actividades) {
				instancias = obtenerInstanciasDeActividadesFuturas(
						(Long) a.get("id"));
				if (!instancias.isEmpty())
					actividadesFuturas.add(a);
			}

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return actividadesFuturas;
	}

	public static List<Map<String, Object>> obtenerActividadesFuturasConInscripcionAbierta() {
		List<Map<String, Object>> actividades = new ArrayList<>();
		List<Map<String, Object>> actividadesFuturas = new ArrayList<>();
		List<Map<String, Object>> instancias = null;
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Map<String, Object> actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ACTIVIDAD");
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new HashMap<String, Object>();
				actividad.put("id", rs.getLong("ID"));
				actividad.put("descripcion", rs.getString("DESCRIPCION"));
				actividad.put("nombre", rs.getString("NOMBRE"));
				actividades.add(actividad);
			}
			for (Map<String, Object> a : actividades) {
				instancias = obtenerInstanciasDeActividadesConInscripcionAbierta(
						(Long) a.get("id"));
				if (!instancias.isEmpty())
					actividadesFuturas.add(a);
			}

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return actividadesFuturas;
	}

	public static List<Map<String, Object>> obtenerActividadesFuturasConInscripcionCerrada() {
		List<Map<String, Object>> actividades = new ArrayList<>();
		List<Map<String, Object>> actividadesFuturas = new ArrayList<>();
		List<Map<String, Object>> instancias = null;
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Map<String, Object> actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ACTIVIDAD");
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new HashMap<String, Object>();
				actividad.put("id", rs.getLong("ID"));
				actividad.put("descripcion", rs.getString("DESCRIPCION"));
				actividad.put("nombre", rs.getString("NOMBRE"));
				actividades.add(actividad);
			}
			for (Map<String, Object> a : actividades) {
				instancias = obtenerInstanciasDeActividadesConInscripcionCerrada(
						(Long) a.get("id"));
				if (!instancias.isEmpty())
					actividadesFuturas.add(a);
			}

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return actividadesFuturas;
	}

	public static int apuntarseActividad(Long userId,
			Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		if (comprobarUsuarioApuntadoActividad(idInstanciaActividad, userId))
			return USUARIO_YA_APUNTADO;
		if (!comprobarPlazoAbierto(idInstanciaActividad))
			return PLAZO_INSCIPCION_NO_ABIERO;
		if (!hayPlazasDisponibles(idInstanciaActividad))
			return NO_HAY_PLAZAS_DISPONIBLES;
		if (esClaseCancelada(idInstanciaActividad))
			return CLASE_CANCELADA;
		try {
			ps = con.prepareStatement(
					"INSERT INTO APUNTADO_ACTIVIDAD (USUARIO_ID, horas_actividad_ID, ASISTIDO, cancelado) VALUES (?,?,?,?)");
			ps.setLong(1, userId);
			ps.setLong(2, idInstanciaActividad);
			ps.setBoolean(3, false);
			ps.setBoolean(4, false);
			ps.executeUpdate();
			aumentarPlazaActividad(idInstanciaActividad);
			return EXITO;
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {

				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return OTRO;
	}

	private static boolean esClaseCancelada(Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT r.estado "
					+ " FROM horas_actividad as h, reserva as r "
					+ "where h.id = ? and r.id = h.reserva_id");
			ps.setLong(1, idInstanciaActividad);
			rs = ps.executeQuery();
			rs.next();
			String cancelado = rs.getString("estado");
			if (cancelado.equals("CANCELADA"))
				return true;
			else
				return false;

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	private static boolean comprobarPlazoAbierto(Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Timestamp horaAntes = new Timestamp(
				DateTime.now().plusHours(1).getMillis());
		Timestamp diaAntes = new Timestamp(
				DateTime.now().plusDays(1).getMillis());
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT count(*) as plazo_abierto"
					+ " FROM horas_actividad as h where h.id = ? and "
					+ "h.fecha_actividad_inicio >= ? and h.fecha_actividad_inicio <= ?");
			ps.setLong(1, idInstanciaActividad);
			ps.setTimestamp(2, horaAntes);
			ps.setTimestamp(3, diaAntes);
			rs = ps.executeQuery();
			rs.next();
			int numero = rs.getInt("plazo_abierto");
			if (numero == 0)
				return false;
			else
				return true;

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	private static void aumentarPlazaActividad(Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(
					"update horas_actividad set plazas_ocupadas = plazas_ocupadas + 1 where id = ?");
			ps.setLong(1, idInstanciaActividad);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private static boolean comprobarUsuarioApuntadoActividad(
			Long idInstanciaActividad, Long idUsuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"select count(*) as is_apuntado from horas_actividad as h, apuntado_actividad as a "
							+ "where h.id = a.horas_actividad_id "
							+ "and h.id=? and a.usuario_id = ?");
			ps.setLong(1, idInstanciaActividad);
			ps.setLong(2, idUsuario);
			rs = ps.executeQuery();
			rs.next();
			return (rs.getInt("is_apuntado") == 0) ? false : true;
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	private static boolean hayPlazasDisponibles(Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"select h.plazas_ocupadas, h.plazas_totales from horas_actividad as h "
							+ "where h.id = ? ");
			ps.setLong(1, idInstanciaActividad);
			rs = ps.executeQuery();
			rs.next();
			return (rs.getInt("plazas_ocupadas") == rs.getInt("plazas_totales"))
					? false : true;
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return false;
	}

	public static Long obtenerUltimoIDActividad() {
		// TODO eliminar duplicacion de codigo al obtener nuevos ids
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		long result = 0;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select id from actividad ");
			sb.append("order by id desc");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getLong("id");
			con.close();
			return result;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static Actividad obtenerActividad(Long idActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from actividad where id= ?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idActividad);
			ResultSet rs = ps.executeQuery();
			Actividad actividad = new Actividad();
			while (rs.next()) {
				actividad.setCodigo(rs.getLong("ID"));
				actividad.setNombre(rs.getString("NOMBRE"));
				actividad.setDescripcion(rs.getString("DESCRIPCION"));
			}
			con.close();

			return actividad;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static List<ActividadHoras> verMisActividadesPorFecha(Date inicio,
			Date fin, Long user) {
		DateTime fecha1 = new DateTime(inicio);
		DateTime fecha2 = new DateTime(fin);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from horas_actividad , apuntado_actividad ");
			sb.append("where id=horas_actividad_id ");
			sb.append("and fecha_actividad_inicio >= ? ");
			sb.append("and fecha_actividad_fin <= ? ");
			sb.append("and usuario_id = ?");
			sb.append("order by fecha_actividad_inicio");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, new Timestamp(fecha1.getMillis()));
			ps.setTimestamp(2, new Timestamp(fecha2.getMillis()));
			ps.setLong(3, user);
			ResultSet rs = ps.executeQuery();
			List<ActividadHoras> clases = new ArrayList<>();
			while (rs.next()) {
				ActividadHoras clase = new ActividadHoras();
				clase.setId(rs.getLong("ID"));
				clase.setIdActividad(rs.getLong("actividad_id"));
				clase.setIdMonitor(rs.getLong("monitor_id"));
				clase.setIdReserva(rs.getLong("reserva_id"));
				clase.setFechaInicio(new DateTime(
						rs.getTimestamp("fecha_actividad_inicio")));
				clase.setFechaFin(
						new DateTime(rs.getTimestamp("fecha_actividad_fin")));
				clase.setPlazasTotales(rs.getInt("plazas_totales"));
				clase.setPlazasOcupadas(rs.getInt("plazas_ocupadas"));
				clases.add(clase);
			}
			con.close();
			return clases;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static void cancelarClase(ActividadHoras clase, Long user) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			PreparedStatement ps = con.prepareStatement(
					"update apuntado_actividad set cancelado=true where horas_actividad_id = ? and usuario_id = ?");
			ps.setLong(1, clase.getId());
			ps.setLong(2, user);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isMiApuntadoActividadCancelada(
			ActividadHoras actividadHoras, Long user) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from apuntado_actividad ");
			sb.append("where horas_actividad_id = ?");
			sb.append("and usuario_id = ?");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, actividadHoras.getId());
			ps.setLong(2, user);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getBoolean("cancelado"))
					return true;
			}
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public static void crearActividad(String nombreAct, String descripcion) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("insert into actividad ");
			sb.append("(nombre, descripcion) ");
			sb.append("values (?, ?)");
			ps = con.prepareStatement(sb.toString());
			ps.setString(1, nombreAct);
			ps.setString(2, descripcion);
			ps.execute();
			ps.close();
			con.close();

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public static void crearReservaActividad(DateTime dateTimeInicio,
			DateTime dateTimeFin, Long idInst, Long idMonitor, int plazasMax) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			// quedamos que el id de actividad quedaba null igualmente
			ReservaDao reserva = new ReservaDao(TipoReserva.CENTRO,
					dateTimeInicio, dateTimeFin, idInst, null, null, null,
					null);
			ReservaDatos.insertarReservaAdmin(reserva);
			Long idReserva = ReservaDatos.obtenerUltimoIDReserva();
			Long idActividad = obtenerUltimoIDActividad();

			StringBuilder sb = new StringBuilder();
			sb.append("insert into horas_actividad ");
			sb.append("(actividad_id, monitor_id, reserva_id, ");
			sb.append("fecha_actividad_inicio, fecha_actividad_fin, ");
			sb.append("plazas_totales, plazas_ocupadas) ");
			sb.append("values (?, ?, ?, ?, ?, ?, ?)");
			ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idActividad);
			ps.setLong(2, idMonitor);
			ps.setLong(3, idReserva);
			ps.setTimestamp(4,
					ManagerFechas.convertirATimestampSql(dateTimeInicio));
			ps.setTimestamp(5,
					ManagerFechas.convertirATimestampSql(dateTimeFin));
			ps.setInt(6, plazasMax);
			ps.setInt(7, 0);
			ps.execute();

			con.close();

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} catch (ExcepcionReserva e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public static List<Map<String, Object>> obtenerInstanciasDeActividadesFuturas(
			Long idActividad) {
		List<Map<String, Object>> actividades = new ArrayList<>();
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Timestamp ahora = new Timestamp(DateTime.now().getMillis());
		Map<String, Object> actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"SELECT h.id, h.fecha_actividad_inicio, h.fecha_actividad_fin, "
							+ " h.plazas_ocupadas, h.plazas_totales, m.nombre, m.apellidos, r.estado, i.codigo "
							+ " FROM horas_actividad as h, monitor as m, "
							+ "reserva as r, instalacion as i where "
							+ "h.actividad_id = ? and r.instalacion_id = i.id and h.reserva_id = r.id and r.estado = 'ACTIVA' and "
							+ " m.id = h.monitor_id and h.fecha_actividad_inicio >= ?");
			ps.setLong(1, idActividad);
			ps.setTimestamp(2, ahora);
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new HashMap<String, Object>();
				actividad.put("id", rs.getLong("ID"));
				actividad.put("fecha_actividad_inicio",
						rs.getTimestamp("fecha_actividad_inicio"));
				actividad.put("fecha_actividad_fin",
						rs.getTimestamp("fecha_actividad_fin"));
				actividad.put("plazas_ocupadas", rs.getInt("plazas_ocupadas"));
				actividad.put("plazas_totales", rs.getInt("plazas_totales"));
				actividad.put("nombre", rs.getString("nombre"));
				actividad.put("apellidos", rs.getString("apellidos"));
				actividad.put("estado", rs.getString("estado"));
				actividad.put("codigo", rs.getString("codigo"));
				actividades.add(actividad);
			}
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return actividades;
	}

	public static List<Map<String, Object>> obtenerInstanciasDeActividadesConInscripcionAbierta(
			Long idActividad) {
		List<Map<String, Object>> actividades = new ArrayList<>();
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Timestamp horaAntes = new Timestamp(
				DateTime.now().plusHours(1).getMillis());
		Timestamp diaAntes = new Timestamp(
				DateTime.now().plusDays(1).getMillis());
		Map<String, Object> actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"SELECT h.id, h.fecha_actividad_inicio, h.fecha_actividad_fin, "
							+ " h.plazas_ocupadas, h.plazas_totales, m.nombre, m.apellidos, r.estado, i.codigo"
							+ " FROM horas_actividad as h, monitor as m, "
							+ "reserva as r, instalacion as i where "
							+ "h.actividad_id = ? and r.instalacion_id = i.id and h.reserva_id = r.id "
							+ "and r.estado='ACTIVA' and m.id = h.monitor_id and "
							+ "h.fecha_actividad_inicio >= ? and h.fecha_actividad_inicio <= ?");
			ps.setLong(1, idActividad);
			ps.setTimestamp(2, horaAntes);
			ps.setTimestamp(3, diaAntes);
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new HashMap<String, Object>();
				actividad.put("id", rs.getLong("ID"));
				actividad.put("fecha_actividad_inicio",
						rs.getTimestamp("fecha_actividad_inicio"));
				actividad.put("fecha_actividad_fin",
						rs.getTimestamp("fecha_actividad_fin"));
				actividad.put("plazas_ocupadas", rs.getInt("plazas_ocupadas"));
				actividad.put("plazas_totales", rs.getInt("plazas_totales"));
				actividad.put("nombre", rs.getString("nombre"));
				actividad.put("apellidos", rs.getString("apellidos"));
				actividad.put("estado", rs.getString("estado"));
				actividad.put("codigo", rs.getString("codigo"));
				actividades.add(actividad);
			}
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return actividades;
	}

	public static List<Map<String, Object>> obtenerInstanciasDeActividadesConInscripcionCerrada(
			Long idActividad) {
		List<Map<String, Object>> actividades = new ArrayList<>();
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Timestamp diaAntes = new Timestamp(
				DateTime.now().plusDays(1).getMillis());
		Map<String, Object> actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(
					"SELECT h.id, h.fecha_actividad_inicio, h.fecha_actividad_fin, "
							+ " h.plazas_ocupadas, h.plazas_totales, m.nombre, m.apellidos, r.estado, i.codigo"
							+ " FROM horas_actividad as h, monitor as m, "
							+ "reserva as r, instalacion as i where "
							+ "h.actividad_id = ? and r.instalacion_id = i.id and h.reserva_id = r.id and m.id = h.monitor_id and r.estado='ACTIVA' and "
							+ "h.fecha_actividad_inicio >= ?");
			ps.setLong(1, idActividad);
			ps.setTimestamp(2, diaAntes);
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new HashMap<String, Object>();
				actividad.put("id", rs.getLong("ID"));
				actividad.put("fecha_actividad_inicio",
						rs.getTimestamp("fecha_actividad_inicio"));
				actividad.put("fecha_actividad_fin",
						rs.getTimestamp("fecha_actividad_fin"));
				actividad.put("plazas_ocupadas", rs.getInt("plazas_ocupadas"));
				actividad.put("plazas_totales", rs.getInt("plazas_totales"));
				actividad.put("nombre", rs.getString("nombre"));
				actividad.put("apellidos", rs.getString("apellidos"));
				actividad.put("estado", rs.getString("estado"));
				actividad.put("codigo", rs.getString("codigo"));
				actividades.add(actividad);
			}
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}

		return actividades;
	}

	public static void cancelarInstanciaActividad(Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long idReserva = null;

		try {
			ps = con.prepareStatement(
					"select horas_actividad.reserva_id from horas_actividad where horas_actividad.id = ?");
			ps.setLong(1, idInstanciaActividad);
			rs = ps.executeQuery();
			rs.next();
			idReserva = rs.getLong("reserva_id");
			rs.close();
			ps.close();
			ps = con.prepareStatement(
					"update reserva set estado = 'ANULADA' where reserva.id = ?");
			ps.setLong(1, idReserva);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void crearReservaActividadSemanal(DiasSemana dia,
			DateTime inicio, DateTime fin, Long idInst, Long idMonitor,
			int plazasMax, boolean todoElDia) throws ExcepcionReserva {

		if (inicio.isAfter(fin.getMillis())) {
			throw new ExcepcionReserva(
					"La fecha de fin no puede ser antes que la de inicio.");
		}
		DateTime inicioPuntual = inicio;

		while (inicioPuntual.dayOfWeek().get() != dia.ordinal() + 1) {
			inicioPuntual = inicioPuntual.plusDays(1);
		}

		int diasEntre = Days.daysBetween(inicioPuntual, fin).getDays();
		DateTime finPuntual = fin.minusDays(diasEntre);

		if (todoElDia) {
			inicioPuntual = inicioPuntual.withHourOfDay(0);
			inicioPuntual = inicioPuntual.withMinuteOfHour(0);
			inicioPuntual = inicioPuntual.withSecondOfMinute(0);
			inicioPuntual = inicioPuntual.withMillisOfSecond(0);

			finPuntual = finPuntual.plusDays(1);
			finPuntual = finPuntual.withHourOfDay(0);
			finPuntual = finPuntual.withMinuteOfHour(0);
			finPuntual = finPuntual.withSecondOfMinute(0);
			finPuntual = finPuntual.withMillisOfSecond(0);
		}

		while (diasEntre >= 0) {
			crearReservaActividad(inicioPuntual, finPuntual, idInst, idMonitor,
					plazasMax);
			inicioPuntual = inicioPuntual.plusDays(7);
			finPuntual = finPuntual.plusDays(7);
			diasEntre = Days.daysBetween(inicioPuntual, fin).getDays();
		}

	}

	public static void cancelarActividadEntera(Long idInstanciaActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long idReserva = null;

		try {
			ps = con.prepareStatement(
					"select horas_actividad.reserva_id from horas_actividad where horas_actividad.actividad_id = ?");
			ps.setLong(1, idInstanciaActividad);
			rs = ps.executeQuery();
			ps.close();
			while (rs.next()) {
				idReserva = rs.getLong("reserva_id");
				ps = con.prepareStatement(
						"update reserva set estado = 'ANULADA' where reserva.id = ?");
				ps.setLong(1, idReserva);
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public static List<Long> obtenerSociosApuntadosInstancia(Long idInstanciaActividad){
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Long> users = new ArrayList<>();

		try {
			ps = con.prepareStatement(
					"select usuario_id from apuntado_actividad where apuntado_actividad.horas_actividad_id = ?");
			ps.setLong(1, idInstanciaActividad);
			rs = ps.executeQuery();
			while(rs.next()){
				users.add((long) rs.getInt("usuario_id"));
			}
			return users;
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
		return users;
	}

}
