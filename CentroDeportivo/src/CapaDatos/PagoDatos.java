package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CapaNegocio.EstadoPago;
import CapaNegocio.dao.Pago;

public class PagoDatos {
	public static Long obtenerNuevoIDPago() {
		// TODO eliminar duplicacion de codigo al obtener nuevos ids
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		int result = 0;
		Long id = null;
		try {
			do {
				id = GeneradorIDRandom.generarID();
				StringBuilder sb = new StringBuilder();
				sb.append("select count(*) num_pago from pago ");
				sb.append("where id = ?");
				PreparedStatement ps = con.prepareStatement(sb.toString());
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next())
					result = rs.getInt("num_pago");
			} while (result != 0);
			con.close();
			return id;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static void insertarPago(Pago pago) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("insert into pago ");
			sb.append("(id, concepto, fecha, importe, estado, tipo_de_pago) ");
			sb.append("values (?,?,?,?,?,?)");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, pago.getId());
			ps.setString(2, pago.getConcepto());
			ps.setDate(3, new java.sql.Date(pago.getFecha().getTime()));
			ps.setDouble(4, pago.getImporte());
			ps.setString(5, pago.getEstado());
			ps.setString(6, pago.getTipo());
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}


	public static Pago obtenerPago(Long idPago) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from pago where id= ?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idPago);
			ResultSet rs = ps.executeQuery();
			Pago pago = new Pago();
			while (rs.next()) {
				pago.setId(rs.getLong("ID"));
				pago.setConcepto(rs.getString("concepto"));
				pago.setFecha(rs.getDate("fecha"));
				pago.setImporte(rs.getDouble("importe"));
				pago.setEstado(rs.getString("estado"));
				pago.setTipo(rs.getString("tipo_de_pago"));
			}
			con.close();
			return pago;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static void CobrarPago(Long id) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update pago set estado= ? where id=?");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setString(1, EstadoPago.COBRADO.name());
			ps.setLong(2, id);
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
