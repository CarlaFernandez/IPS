package CapaNegocio.managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import CapaDatos.CreadorConexionBBDD;
import CapaDatos.MonitorDatos;
import CapaDatos.ReservaDatos;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.Curso;
import CapaNegocio.dao.Monitor;
import CapaNegocio.dao.ReservaDao;

public class ManagerMonitores {

	public static List<ReservaDao> verReservasPorFecha(Date inicio, Date fin, Long idUser) {
		return ReservaDatos.obtenerReservasPorFechaYUsuario(inicio,fin, idUser);
	}

	public static List<ReservaDao> verReservasInstalacion(long instalacion) {
		return ReservaDatos.obtenerReservasPorInstalacion(instalacion);
	}
	
	public static List<Actividad> verActividades(Long monitorId){
		return MonitorDatos.obtenerActividades(monitorId);
	}
	
	public static List<Curso> verCursos(Long monitorId){
		return MonitorDatos.obtenerCursos(monitorId);
	}
	
	public static List<Actividad> usuarioEstaEnMisActividades(Long idMonitor, Long idUsuario){
		return MonitorDatos.usuarioEstaEnMisActividades(idMonitor, idUsuario);
	}
	
	public static List<Curso> usuarioEstaEnMisCursos(Long idMonitor, Long idUsuario){
		return MonitorDatos.usuarioEstaEnMisCursos(idMonitor, idUsuario);
	}
	
	public static List<Monitor> verrMonitores(){
		return MonitorDatos.obtenerMonitores();
	}
	
	public static Monitor obtenertMonitor(Long idMonitor){
		return MonitorDatos.getMonitor(idMonitor);
	}

}
