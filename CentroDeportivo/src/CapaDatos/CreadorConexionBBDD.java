package CapaDatos;

import java.sql.*;

/**
 * Created by Carla on 06/10/2016.
 */
public class CreadorConexionBBDD {

	@SuppressWarnings("finally")
	public Connection crearConexion() {
		Connection conexion = null;
		try {
			DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
			String url = "jdbc:hsqldb:hsql://localhost/";
			String user = "SA";
			String pass = "";
			conexion = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return conexion;
		}
	}
}