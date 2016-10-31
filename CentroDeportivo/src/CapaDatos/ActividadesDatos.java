package CapaDatos;

import java.sql.*;
import java.util.*;
import java.util.Date;

import CapaNegocio.dao.Actividad;

public class ActividadesDatos {

	public static List<Actividad> obtenerActividadesFuturas(){
		List<Actividad> actividades = new ArrayList<>();
		Date fecha = new Date();
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		
		PreparedStatement ps = null;
		try {
			con.prepareStatement("SELECT * FROM ACTIVIDAD WHERE FECHA_ACTIVIDAD > ?");
		} catch (SQLException e) {
			System.err.println(e.getSQLState()+" "+e.getMessage());
			e.printStackTrace();
		}
		
		return actividades;
	}
}
