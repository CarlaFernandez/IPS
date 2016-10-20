package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import CapaNegocio.dao.Instalacion;
import CapaNegocio.managers.ManagerFechas;

/**
 * Created by Carla on 08/10/2016.
 */
public class InstalacionDatos {

	/*
	 * public static void obtenerOcupacionSemanal(Long idInst) { DateTime now =
	 * new DateTime(System.currentTimeMillis()); DateTime[] lunesYDomingo =
	 * ManagerFechas.obtenerLunesYDomingoDeSemana(now); CreadorConexionBBDD
	 * creador = new CreadorConexionBBDD(); Connection con =
	 * creador.crearConexion(); try { StringBuilder sb = new StringBuilder();
	 * sb.append("select * from reserva "); sb.append("where hora_inicio >= ? "
	 * ); sb.append("and hora_fin <= ?"); sb.append("and instalacion_id = ?");
	 * 
	 * PreparedStatement ps = con.prepareStatement(sb.toString());
	 * ps.setTimestamp(1, new Timestamp(lunesYDomingo[0].getMillis()));
	 * ps.setTimestamp(2, new Timestamp(lunesYDomingo[1].getMillis()));
	 * ps.setLong(3, idInst); ResultSet rs = ps.executeQuery(); List<String>
	 * reservas = new ArrayList<>(); while (rs.next()) { StringBuilder sb2 = new
	 * StringBuilder(); sb2.append(rs.getLong("id")).append("|");
	 * sb2.append(rs.getTimestamp("hora_inicio")).append("|");
	 * sb2.append(rs.getTimestamp("hora_fin")).append("|");
	 * sb2.append(rs.getLong("instalacion_id")).append("|");
	 * sb2.append(rs.getLong("usuario_id")).append("|");
	 * sb2.append(rs.getLong("actividad_id")).append("|");
	 * sb2.append(rs.getLong("curso_id")).append("|");
	 * reservas.add(sb2.toString()); } for (int i = 0; i < reservas.size(); i++)
	 * { System.out.println(reservas.get(i)); } con.close(); } catch
	 * (SQLException e) { System.err.println(e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * }
	 */

	public static boolean estaLibreEnHoras(Long idInst, DateTime inicio, DateTime fin) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {

			StringBuilder sb = new StringBuilder();
			sb.append("select count(*) reservas_simultaneas ");
			sb.append("from reserva ");
			sb.append("where hora_inicio < ?");
			sb.append("and ? < hora_fin ");
			sb.append("and instalacion_id = ? ");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, ManagerFechas.convertirATimestampSql(fin));
			ps.setTimestamp(2, ManagerFechas.convertirATimestampSql(inicio));
			ps.setLong(3, idInst);
			ResultSet rs = ps.executeQuery();
			con.close();
			while (rs.next()) {
				int reservasSimultaneas = rs.getInt("reservas_simultaneas");
				if (reservasSimultaneas > 0)
					return false;
			}

			return true;

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public static double obtenerPrecioInstalacion(Long idInst) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select precio_instalacion from instalacion ");
			sb.append("where id = ? ");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idInst);
			ResultSet rs = ps.executeQuery();
			double result = 0;
			while (rs.next())
				result = rs.getDouble("precio_instalacion");
			con.close();
			return result;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}

	public static List<Instalacion> ObtenerInstalaciones() {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from instalacion ");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();
			List<Instalacion> instalaciones = new ArrayList<>();
			while (rs.next()) {
				Instalacion instalacion = new Instalacion();
				instalacion.setIdInst(rs.getLong("ID"));
				instalacion.setCodigo(rs.getString("CODIGO"));
				instalacion.setDescripcion(rs.getString("DESCRIPCION"));
				instalacion.setPrecioHora(rs.getLong("PRECIO_INSTALACION"));
				instalacion.setDisponible(rs.getBoolean("DISPONIBLE"));

				instalaciones.add(instalacion);
			}
			con.close();

			return instalaciones;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static Instalacion ObtenerInstalacion(Long idInst) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from instalacion where id=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idInst);
			ResultSet rs = ps.executeQuery();
			Instalacion instalacion = new Instalacion();
			while (rs.next()) {
				instalacion.setIdInst(rs.getLong("ID"));
				instalacion.setCodigo(rs.getString("CODIGO"));
				instalacion.setDescripcion(rs.getString("DESCRIPCION"));
				instalacion.setPrecioHora(rs.getLong("PRECIO_INSTALACION"));
				instalacion.setDisponible(rs.getBoolean("DISPONIBLE"));
			}
			con.close();

			return instalacion;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}
