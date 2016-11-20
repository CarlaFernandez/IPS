package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import CapaNegocio.dao.Actividad;
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
					+ "where h.id = ? and r.id = h.reseva_id");
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

	public static void crearActividad(DateTime dateTimeInicio,
			DateTime dateTimeFin, Long idInst, Long idMonitor, String nombreAct,
			String descripcion, int plazasMax) {
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

			// quedamos que el id de actividad quedaba null igualmente
			ReservaDao reserva = new ReservaDao(TipoReserva.CENTRO,
					dateTimeInicio, dateTimeFin, idInst, null, null, null,
					null);
			ReservaDatos.insertarReservaAdmin(reserva);
			Long idReserva = ReservaDatos.obtenerUltimoIDReserva();
			Long idActividad = obtenerUltimoIDActividad();

			sb = new StringBuilder();
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
							+ " h.plazas_ocupadas, h.plazas_totales, m.nombre, m.apellidos, r.estado, i.codigo"
							+ " FROM horas_actividad as h, monitor as m, "
							+ "reserva as r, instalacion as i where "
							+ "h.actividad_id = ? and r.instalacion_id = i.id and h.reserva_id = r.id and m.id = h.monitor_id and h.fecha_actividad_inicio >= ?");
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
							+ "h.actividad_id = ? and r.instalacion_id = i.id and h.reserva_id = r.id and m.id = h.monitor_id and "
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
}
