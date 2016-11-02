package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import CapaNegocio.dao.Actividad;

public class ActividadesDatos {

	public static List<Actividad> obtenerActividadesFuturas(){
		List<Actividad> actividades = new ArrayList<>();
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		Actividad actividad = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ACTIVIDAD");
			rs = ps.executeQuery();
			while(rs.next()){
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
			System.err.println(e.getSQLState()+" "+e.getMessage());
			e.printStackTrace();
		} finally{
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState()+" "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		return actividades;
	}
	
	public static void apuntarseActividad(Long userId, Long actividadId){
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("INSERT INTO APUNTADO_ACTIVIDAD SET USUARIO_ID = ?, ACTIVIDAD_ID = ?");
			ps.setLong(1, userId);
			ps.setLong(2, actividadId);
		} catch (SQLException e) {
			System.err.println(e.getSQLState()+" "+e.getMessage());
			e.printStackTrace();
		}
	}
}
