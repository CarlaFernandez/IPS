package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Contable {
	private static void cobrarPagosCuota(long idPago) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		String sql = "update pago set estado = 'COBRADO' where pago.id = ?";

		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setLong(1, idPago);
			ps.executeUpdate();
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

	}

	public static void incrementarPagos(List<Long> idSocios) {
		List<Map<String, Object>> pagos = null;
		List<Long> idPagos = new ArrayList<>();
		for (long id : idSocios) {
			pagos = PagoDatos.obtenerPagosCuentaDeSocio(id);
			for (Map<String, Object> pago : pagos) {
				idPagos.add((Long) pago.get("id"));
			}
		}
		for (long id : idPagos) {
			if (comprobarPagoPendiente(id))
				cobrarPagosCuota(id);
		}
	}

	private static boolean comprobarPagoPendiente(Long idPago) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		String sql = "select estado from pago where id = ?";
		String estado = null;
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(sql);
			ps.setLong(1, idPago);
			rs = ps.executeQuery();
			rs.next();
			estado = rs.getString("estado");
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
		return estado.equals("PENDIENTE") ? true : false;
	}
}
