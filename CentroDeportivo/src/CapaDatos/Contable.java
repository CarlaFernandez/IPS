package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.joda.time.DateTime;

import CapaNegocio.managers.ManagerFechas;

public class Contable {
	public static int incrementarCuotaMensual(long idUsuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		DateTime fechaActual = new DateTime();
		DateTime mesAnterior = fechaActual.minusMonths(1).withDayOfMonth(20);
		DateTime mesActual = fechaActual.withDayOfMonth(19);
		StringBuilder sql = new StringBuilder();
		sql.append("select sum(importe) from pago, reserva, usuario ");
		sql.append("where pago.id = reserva.pago_id and usuario.id = reserva.usuario_id and usuario.id = ");
		sql.append(idUsuario);
		sql.append(" and pago.estado = 1 and pago.tipo_de_pago = 0 ");
		sql.append(" and reserva.HORA_INICIO >= TO_TIMESTAMP('");
		sql.append(ManagerFechas.formatearFecha(mesAnterior));
		sql.append("', 'DD-MM-YYYY HH:MI:SS')");
		sql.append(" and reseva.hora_inicio <= TO_TIMESTAMP('");
		sql.append(ManagerFechas.formatearFecha(mesActual));
		sql.append("', 'DD-MM-YYYY HH:MI:SS')");
		ResultSet rs = null;
		int cuotaAniadida = 0;
		try {
			PreparedStatement ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			cuotaAniadida = rs.getInt(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace(System.err);
		}
		return cuotaAniadida;
	}
}
