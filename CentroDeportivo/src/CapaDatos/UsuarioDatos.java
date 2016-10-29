package CapaDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerFechas;

/**
 * Created by Carla on 08/10/2016.
 */
public class UsuarioDatos extends GeneradorIDRandom {

	public static void insertarUsuario(Usuario usuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("insert into usuario ");
			sb.append("(id, dni, nombre, apellidos, direccion, ");
			sb.append("email, ciudad, cuenta_bancaria, socio) ");
			sb.append("values (?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, usuario.getIdUsu());
			ps.setString(2, usuario.getDNI());
			ps.setString(3, usuario.getNombre());
			ps.setString(4, usuario.getApellidos());
			ps.setString(5, usuario.getDireccion());
			ps.setString(6, usuario.getEmail());
			ps.setString(7, usuario.getCiudad());
			ps.setString(8, usuario.getCuentaBancaria());
			ps.setBoolean(9, usuario.isSocio());
			ps.setDate(10, usuario.getBaja());
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void eliminarUsuario(Usuario usuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("delete from usuario ");
			sb.append("where id = ?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, usuario.getIdUsu());
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public static int obtenerNumeroUsuarios() {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) num_clientes from usuario");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			int result = 0;
			while (rs.next()) {
				result = rs.getInt("num_clientes");
			}
			con.close();
			return result;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return 0;
		}

	}

	public static Long obtenerNuevoIDUsuario() {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		int result = 0;
		Long id = null;
		try {
			do {
				id = GeneradorIDRandom.generarID();
				StringBuilder sb = new StringBuilder();
				sb.append("select count(*) num_clientes from usuario ");
				sb.append("where id = ?");
				PreparedStatement ps = con.prepareStatement(sb.toString());
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				result = rs.getInt("num_clientes");
			} while (result != 0);
			con.close();
			return id;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	public static boolean usuarioTieneReservaEnHoras(Long idUsu, DateTime inicio, DateTime fin) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			// (StartDate1 <= EndDate2) and (StartDate2 <= EndDate1)
			sb.append("select count(*) reservas_simultaneas ");
			sb.append("from reserva ");
			sb.append("where hora_inicio < ?");
			sb.append("and ? < hora_fin ");
			sb.append("and usuario_id = ? ");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, ManagerFechas.convertirATimestampSql(fin));
			ps.setTimestamp(2, ManagerFechas.convertirATimestampSql(inicio));
			ps.setLong(3, idUsu);
			ResultSet rs = ps.executeQuery();
			con.close();
			while (rs.next()) {
				int reservasSimultaneas = rs.getInt("reservas_simultaneas");
				if (reservasSimultaneas > 0)
					return true;
			}
			return false;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static List<Usuario> ObtenerUsuarios() {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from usuario ");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			List<Usuario> usuarios = new ArrayList<>();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setIdUsu(rs.getLong("ID"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setDireccion(rs.getString("DIRECCION"));
				usuario.setEmail(rs.getString("EMAIL"));
				usuario.setCiudad(rs.getString("CIUDAD"));
				usuario.setCuentaBancaria(rs.getString("CUENTA_BANCARIA"));
				usuario.setSocio(rs.getBoolean("SOCIO"));
				usuario.setBaja(rs.getDate("FECHA_BAJA"));

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

	public static Usuario ObtenerUsuario(Long idUsu) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from usuario where id= ?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idUsu);
			ResultSet rs = ps.executeQuery();
			Usuario usuario = new Usuario();
			while (rs.next()) {
				usuario.setIdUsu(rs.getLong("ID"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setDireccion(rs.getString("DIRECCION"));
				usuario.setEmail(rs.getString("EMAIL"));
				usuario.setCiudad(rs.getString("CIUDAD"));
				usuario.setCuentaBancaria(rs.getString("CUENTA_BANCARIA"));
				usuario.setSocio(rs.getBoolean("SOCIO"));
				usuario.setSocio(rs.getBoolean("BAJA"));
			}
			con.close();

			return usuario;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public static void usuarioNoPresentadoActividad(Long idUsu, Long idActividad) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("UPDATE apuntado_actividad "
					+ "set asistido = ? "
					+ "where usuario_id=? and actividad_id=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setBoolean(1, false);
			ps.setLong(2, idUsu);
			ps.setLong(3, idActividad);
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
