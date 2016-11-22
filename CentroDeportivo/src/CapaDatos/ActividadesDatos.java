package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;

import CapaNegocio.DiasSemana;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ActividadHoras;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.dao.Usuario;
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

	public static List<ActividadHoras> verMisActividadesPorFecha(Date inicio, Date fin, Long user) {
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
				clase.setFechaInicio(new DateTime(rs.getTimestamp("fecha_actividad_inicio")));
				clase.setFechaFin(new DateTime(rs.getTimestamp("fecha_actividad_fin")));
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

	public static boolean isMiApuntadoActividadCancelada(ActividadHoras actividadHoras, Long user) {
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
	
	public static void crearReservaActividad(DateTime dateTimeInicio, DateTime dateTimeFin, Long idInst, Long idMonitor,
			int plazasMax) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			// quedamos que el id de actividad quedaba null igualmente
			ReservaDao reserva = new ReservaDao(TipoReserva.CENTRO, dateTimeInicio, dateTimeFin, idInst, null, null,
					null, null);
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

	public static void crearReservaActividadSemanal(DiasSemana dia, DateTime inicio, DateTime fin, Long idInst,
			Long idMonitor, int plazasMax, boolean todoElDia) throws ExcepcionReserva {
		
		if (inicio.isAfter(fin.getMillis())) {
			throw new ExcepcionReserva("La fecha de fin no puede ser antes que la de inicio.");
		}
		DateTime inicioPuntual = inicio;
		
		while (inicioPuntual.dayOfWeek().get() != dia.ordinal() + 1){
			inicioPuntual = inicioPuntual.plusDays(1);
		}
		
		int diasEntre = Days.daysBetween(inicioPuntual, fin).getDays();
		DateTime finPuntual = fin.minusDays(diasEntre);
		
		if (todoElDia){
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

		while(diasEntre >= 0){
			crearReservaActividad(inicioPuntual, finPuntual, idInst, idMonitor, plazasMax);
			inicioPuntual = inicioPuntual.plusDays(7);
			finPuntual = finPuntual.plusDays(7);
			diasEntre = Days.daysBetween(inicioPuntual, fin).getDays();
		}

	}

}
