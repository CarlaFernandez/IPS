package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import CapaNegocio.EstadoPago;
import CapaNegocio.dao.Pago;
import CapaNegocio.managers.ManagerFechas;

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

	public static List<Map<String, Object>> obtenerPagosCuentaDeSocio(
			Long idSocio) {
		List<Map<String, Object>> pagos = new ArrayList<>();
		DateTime fechaActual = new DateTime();
		DateTime mesAnterior = fechaActual.minusMonths(1).withDayOfMonth(20);
		DateTime mesActual = fechaActual.withDayOfMonth(19);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			String sql = "select r.usuario_id, p.id, p.concepto, p.fecha, p.importe, "
					+ "p.estado from pago as p, reserva as r where r.pago_id = p.id "
					+ "and r.usuario_id = ? and r.hora_inicio >= ? and r.hora_inicio <= ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setLong(1, idSocio);
			ps.setTimestamp(2,
					ManagerFechas.convertirATimestampSql(mesAnterior));
			ps.setTimestamp(3, ManagerFechas.convertirATimestampSql(mesActual));
			ResultSet rs = ps.executeQuery();

			Map<String, Object> pago = null;
			while (rs.next()) {
				pago = new HashMap<>();
				pago.put("idSocio", rs.getLong("usuario_id"));
				pago.put("id", rs.getLong("id"));
				pago.put("concepto", rs.getString("concepto"));
				pago.put("fecha", rs.getTimestamp("fecha"));
				pago.put("importe", rs.getDouble("importe"));
				pago.put("estado", rs.getString("estado"));
				pagos.add(pago);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pagos;
	}

	public static Long obtenerIDSocioDePago(Long idPago) {
		Long idSocio = null;
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		String sql = "select u.id from usuario as u, pago as p, reserva as r "
				+ "where p.id = ? and r.pago_id = p.id and u.id = r.usuario_id";
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(sql);
			ps.setLong(1, idPago);
			rs = ps.executeQuery();
			rs.next();
			idSocio = rs.getLong("id");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return idSocio;
	}

}
