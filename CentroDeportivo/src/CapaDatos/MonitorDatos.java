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

import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.Curso;
import CapaNegocio.dao.Monitor;
import CapaNegocio.dao.Usuario;

public class MonitorDatos extends GeneradorIDRandom {

	public static void insertarMonitor(Monitor monitor) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("insert into monitor ");
			sb.append("(id, nombre, apellidos) ");
			sb.append("values (?,?,?)");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, monitor.getIdMonitor());
			ps.setString(2, monitor.getNombre());
			ps.setString(3, monitor.getApellidos());
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void eliminarMonitor(Monitor monitor) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("delete from monitor ");
			sb.append("where id = ?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, monitor.getIdMonitor());
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static List<Actividad> obtenerActividades(Long monitorId) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from actividad where MONITOR_ID=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, monitorId);
			ResultSet rs = ps.executeQuery();
			List<Actividad> actividades = new ArrayList<>();
			while (rs.next()) {
				Actividad actividad = new Actividad();
				actividad.setCodigo(rs.getLong("ID"));
				actividad.setNombre(rs.getString("NOMBRE"));
				actividad.setDescripcion(rs.getString("DESCRIPCION"));
				actividad.setPlazasTotales(rs.getInt("PLAZAS_TOTALES"));
				actividad.setPlazasOcupadas(rs.getInt("PLAZAS_OCUPADAS"));
				actividad.setNumeroHoras(rs.getDouble("NUMERO_HORAS"));
				actividad.setMonitorID(rs.getLong("MONITOR_ID"));
				actividad.setCancelada(rs.getBoolean("CANCELADA"));

				actividades.add(actividad);
			}
			con.close();
			return actividades;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static List<Actividad> obtenerActividadesEntreFechas(Long monitorId,
			Date inicio, Date fin) {
		DateTime fecha1 = new DateTime(inicio);
		DateTime fecha2 = new DateTime(fin);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from actividad a "
					+ "INNER JOIN HORAS_ACTIVIDAD ha on ha.ACTIVIDAD_ID = a.ID "
					+ "INNER JOIN RESERVA r on r.ID = ha.RESERVA_ID "
					+ "where ha.MONITOR_ID = ? "
					+ "and ha.fecha_actividad_inicio >= ? "
					+ "and ha.fecha_actividad_inicio <= ? "
					+ "order by ha.fecha_actividad_inicio");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, monitorId);
			ps.setTimestamp(2, new Timestamp(fecha1.getMillis()));
			ps.setTimestamp(3, new Timestamp(fecha2.getMillis()));
			ResultSet rs = ps.executeQuery();
			List<Actividad> actividades = new ArrayList<>();
			while (rs.next()) {
				Actividad actividad = new Actividad();
				actividad.setCodigo(rs.getLong("ID"));
				actividad.setNombre(rs.getString("NOMBRE"));
				actividad.setDescripcion(rs.getString("DESCRIPCION"));
				actividad.setPlazasTotales(rs.getInt("PLAZAS_TOTALES"));
				actividad.setPlazasOcupadas(rs.getInt("PLAZAS_OCUPADAS"));
				if(rs.getString("ESTADO").equals("ACTIVA"))
					actividad.setCancelada(false);
				else 
					actividad.setCancelada(true);
				actividad.setFecha_entrada(new DateTime(rs.getTimestamp("FECHA_ACTIVIDAD_INICIO")));
				actividades.add(actividad);
			}
			con.close();
			return actividades;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static List<Curso> obtenerCursos(Long monitorId) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from curso where MONITOR_ID=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, monitorId);
			ResultSet rs = ps.executeQuery();
			List<Curso> cursos = new ArrayList<>();
			while (rs.next()) {
				Curso curso = new Curso();
				curso.setID(rs.getLong("ID"));
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setDescripcion(rs.getString("DESCRIPCION"));
				curso.setPlazasTotales(rs.getInt("PLAZAS_TOTALES"));
				curso.setPlazasOcupadas(rs.getInt("PLAZAS_OCUPADAS"));
				curso.setNumeroHoras(rs.getDouble("NUMERO_HORAS"));
				curso.setPeriodica(rs.getBoolean("PERIODICA"));
				curso.setMonitorID(rs.getLong("MONITOR_ID"));
				curso.setCancelada(rs.getBoolean("CANCELADA"));

				cursos.add(curso);
			}
			con.close();
			return cursos;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// public static int obtenerNumeroUsuariosActividad(Long IdActividad) {
	//
	// }

	/**
	 * 
	 * @param idMonitor
	 * @param idUsuario
	 * @return Lista con las actividades del monitor en las que esta el usuario
	 *         null en caso de que no este en ninguna
	 */
	// public static List<Actividad> usuarioEstaEnMisActividades(Long idMonitor,
	// Long idUsuario) {
	// CreadorConexionBBDD creador = new CreadorConexionBBDD();
	// Connection con = creador.crearConexion();
	// try {
	// StringBuilder sb = new StringBuilder();
	// sb.append("select * "
	// + "from actividad a "
	// + "INNER JOIN APUNTADOS_ACTIVIDAD ap ON a.codigo=ap.ACTIVIDAD_ID "
	// + "where a.MONITOR_ID=? and ap.USUARIO_ID=?");
	// PreparedStatement ps = con.prepareStatement(sb.toString());
	// ps.setLong(1, idMonitor);
	// ps.setLong(1, idUsuario);
	// ResultSet rs = ps.executeQuery();
	// List<Actividad> actividades = new ArrayList<>();
	// while (rs.next()) {
	// Actividad actividad = new Actividad();
	// actividad.setCodigo(rs.getLong("CODIGO"));
	// actividad.setNombre(rs.getString("NOMBRE"));
	// actividad.setDescripcion(rs.getString("DESCRIPCION"));
	// actividad.setPlazasTotales(rs.getInt("PLAZAS_TOTALES"));
	// actividad.setPlazasOcupadas(rs.getInt("PLAZAS_OCUPADAS"));
	// actividad.setNumeroHoras(rs.getDouble("NUMERO_HORAS"));
	// actividad.setMonitorID(rs.getLong("MONITOR_ID"));
	// actividad.setCancelada(rs.getBoolean("CANCELADA"));
	//
	// actividades.add(actividad);
	// }
	// con.close();
	// return actividades;
	//
	// } catch (SQLException e) {
	// System.err.println(e.getMessage());
	// e.printStackTrace();
	// return null;
	// }
	// }
	//
	//
	/**
	 * 
	 * @param idMonitor
	 * @param idUsuario
	 * @return Lista con las cursos del monitor en las que esta el usuario null
	 *         en caso de que no este en ninguna
	 */
	public static List<Curso> usuarioEstaEnMisCursos(Long idMonitor,
			Long idUsuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * " + "from Curso c "
					+ "INNER JOIN APUNTADOS_CURSO ac ON c.ID=ac.CURSO_ID "
					+ "where c.MONITOR_ID=? and ac.USUARIO_ID=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idMonitor);
			ps.setLong(1, idUsuario);
			ResultSet rs = ps.executeQuery();
			List<Curso> cursos = new ArrayList<>();
			while (rs.next()) {
				Curso curso = new Curso();
				curso.setID(rs.getLong("ID"));
				curso.setNombre(rs.getString("NOMBRE"));
				curso.setDescripcion(rs.getString("DESCRIPCION"));
				curso.setPlazasTotales(rs.getInt("PLAZAS_TOTALES"));
				curso.setPlazasOcupadas(rs.getInt("PLAZAS_OCUPADAS"));
				curso.setNumeroHoras(rs.getDouble("NUMERO_HORAS"));
				curso.setPeriodica(rs.getBoolean("PERIODICA"));
				curso.setMonitorID(rs.getLong("MONITOR_ID"));
				curso.setCancelada(rs.getBoolean("CANCELADA"));

				cursos.add(curso);
			}
			con.close();
			return cursos;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static List<Monitor> obtenerMonitores() {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from monitor ");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			List<Monitor> monitores = new ArrayList<>();
			while (rs.next()) {
				Monitor monitor = new Monitor();
				monitor.setIdMonitor(rs.getLong("ID"));
				monitor.setNombre(rs.getString("NOMBRE"));
				monitor.setApellidos(rs.getString("APELLIDOS"));

				monitores.add(monitor);
			}
			con.close();

			return monitores;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static List<Usuario> usuariosActividad(Long idMonitor, Long idActividad, DateTime fecha_inicio) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * "
					+ "from usuario "
					+ "inner join APUNTADO_ACTIVIDAD aa  "
					+ "ON usuario.ID = aa.USUARIO_ID "
					+ "inner join HORAS_ACTIVIDAD ha "
					+ "on aa.HORAS_ACTIVIDAD_ID = ha.ID "
					+ "where ha.MONITOR_ID=? and ha.ACTIVIDAD_ID=? and ha.FECHA_ACTIVIDAD_INICIO=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idMonitor);
			ps.setLong(2, idActividad);
			ps.setTimestamp(3, new Timestamp(fecha_inicio.getMillis()));
			ResultSet rs = ps.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setIdUsu(rs.getLong("ID"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setDNI(rs.getString("DNI"));
//				usuario.setDireccion(rs.getString("DIRECCION"));
//				usuario.setEmail(rs.getString("EMAIL"));
//				usuario.setCiudad(rs.getString("CIUDAD"));
//				usuario.setCuentaBancaria(rs.getString("CUENTA_BANCARIA"));
				usuario.setSocio(rs.getBoolean("SOCIO"));
				if (rs.getDate("FECHA_BAJA") != null) {
					usuario.setBaja(rs.getDate("FECHA_BAJA"));
				} else {
					usuario.setBaja(null);
				}

				usuarios.add(usuario);
			}
			con.close();

			return usuarios;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static Monitor getMonitor(Long idMonitor) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from monitor where id= ?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idMonitor);
			ResultSet rs = ps.executeQuery();
			Monitor monitor = new Monitor();
			while (rs.next()) {
				monitor.setIdMonitor(rs.getLong("ID"));
				monitor.setNombre(rs.getString("NOMBRE"));
				monitor.setApellidos(rs.getString("APELLIDOS"));
			}
			con.close();

			return monitor;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static int maxPlazasActividad(Long idMonitor, Long idActividad, DateTime fecha_inicio) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select PLAZAS_TOTALES from "
					+ "HORAS_ACTIVIDAD where actividad_id= ? and monitor_id=? and FECHA_ACTIVIDAD_INICIO=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idActividad);
			ps.setLong(2, idMonitor);
			ps.setTimestamp(3, new Timestamp(fecha_inicio.getMillis()));
			ResultSet rs = ps.executeQuery();
			int max = -1;
			while (rs.next()) {
				max = rs.getInt("PLAZAS_TOTALES");
			}
			con.close();

			return max;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
}
