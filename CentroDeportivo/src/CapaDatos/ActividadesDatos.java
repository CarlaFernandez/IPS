package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerFechas;

public class ActividadesDatos {

	public static List<Actividad> obtenerActividadesFuturas() {
		List<Actividad> actividades = new ArrayList<>();
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Timestamp horaAntesActividad = new Timestamp(DateTime.now().plusHours(1).plusMinutes(1).getMillis());
		Actividad actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ACTIVIDAD WHERE FECHA_ACTIVIDAD >= ?");
			ps.setTimestamp(1, horaAntesActividad);
			rs = ps.executeQuery();
			while (rs.next()) {
				actividad = new Actividad();
				actividad.setCodigo(rs.getLong("ID"));
				actividad.setDescripcion(rs.getString("DESCRIPCION"));
				actividad.setPlazasTotales(rs.getInt("PLAZAS_TOTALES"));
				actividad.setPlazasOcupadas(rs.getInt("PLAZAS_OCUPADAS"));
				actividad.setNumeroHoras(rs.getInt("NUMERO_HORAS"));
				actividad.setMonitorID(rs.getLong("MONITOR_ID"));
				actividad.setCancelada(rs.getBoolean("CANCELADA"));
				actividad.setNombre(rs.getString("NOMBRE"));
				actividad.setFecha_entrada(new DateTime(rs.getTimestamp("FECHA_ACTIVIDAD").getTime()));
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

	public static void apuntarseActividad(Long userId, Long actividadId) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		if (comprobarUsuarioApuntadoActividad(actividadId, userId))
			return;
		try {
			ps = con.prepareStatement(
					"INSERT INTO APUNTADO_ACTIVIDAD (USUARIO_ID, ACTIVIDAD_ID, ASISTIDO) VALUES (?,?,?)");
			ps.setLong(1, userId);
			ps.setLong(2, actividadId);
			ps.setBoolean(3, false);
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

	public static void aumentarPlazaActividad(Long actividadId) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update actividad set plazas_ocupadas = plazas_ocupadas + 1 where id = ?");
			ps.setLong(1, actividadId);
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

	public static boolean comprobarUsuarioApuntadoActividad(Long idActividad, Long idUsuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("select count(*) as is_apuntado from actividad, apuntado_actividad "
					+ "where actividad.id = apuntado_actividad.actividad_id "
					+ "and actividad.id=? and usuario_id = ?");
			ps.setLong(1, idActividad);
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

	public static void crearActividad(DateTime dateTimeInicio, DateTime dateTimeFin, Long idInst, Long idMonitor,
			String nombreAct, String descripcion, int plazasMax) {
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
			ReservaDao reserva = new ReservaDao(TipoReserva.CENTRO, dateTimeInicio, dateTimeFin, idInst, null, null,
					null, null);
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
			ps.setTimestamp(4, ManagerFechas.convertirATimestampSql(dateTimeInicio));
			ps.setTimestamp(5, ManagerFechas.convertirATimestampSql(dateTimeFin));
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
}
