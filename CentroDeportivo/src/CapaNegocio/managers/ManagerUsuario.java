package CapaNegocio.managers;

import java.awt.TrayIcon.MessageType;
import java.util.Date;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import CapaDatos.ActividadesDatos;
import CapaDatos.InstalacionDatos;
import CapaDatos.ReservaDatos;
import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.Actividad;
import CapaNegocio.dao.ActividadHoras;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import salida.Salida;

/**
 * Created by Carla on 08/10/2016.
 */
public class ManagerUsuario {

	public static List<ReservaDao> verReservasPorFecha(Date inicio, Date fin, Long idUser) {
		return ReservaDatos.obtenerReservasPorFechaYUsuario(inicio, fin, idUser);
	}

	public static List<ReservaDao> verReservasInstalacion(long instalacion) {
		return ReservaDatos.obtenerReservasPorInstalacion(instalacion);
	}

	public static List<Instalacion> verInstalaciones() {
		return InstalacionDatos.ObtenerInstalaciones();
	}

	public static List<ReservaDao> verReservasInstalacionSinPagar(long usuario) {
		return ReservaDatos.obtenerReservasPorUsuarioEInstalacionSinPagar(usuario);
	}

	public static List<Usuario> verUsuarios() {
		return UsuarioDatos.ObtenerUsuarios();
	}

	public static List<ReservaDao> verReservasPorInstalacion(Long instalacion) {
		return ReservaDatos.obtenerReservaPorInstalacion(instalacion);
	}

	/*
	 * public static boolean esBajaParaEsteMes(Long idUsuario){ return
	 * UsuarioDatos.esBajaParaEsteMes(idUsuario); }
	 */

	public static boolean esBajaParaElMesQueViene(Long idUsuario) {
		return UsuarioDatos.esBajaParaElMesQueViene(idUsuario);
	}

	public static List<ActividadHoras> verMisActividadesPorFecha(Date time, Date fin, Long user) {
		return ActividadesDatos.verMisActividadesPorFecha(time, fin, user);
	}

	public static void cancelarClaseActividad(ActividadHoras clase, Long user) {
		boolean tiempoMinimo = Hours.hoursBetween(clase.getFechaInicio(), new DateTime()).getHours() > 1
				& clase.getFechaInicio().isBeforeNow();
		boolean claseFutura = clase.getFechaInicio().isAfterNow();
		if (tiempoMinimo && claseFutura) {
			ActividadesDatos.cancelarClase(clase, user);
			Actividad actividad = ActividadesDatos.obtenerActividad(clase.getIdActividad());
			String mensaje = "Su clase de la actividad " + actividad.getNombre() + " para la fecha "
					+ clase.getFechaInicio() + " ha sido cancelada de acuerdo a su petición.";
			new Salida().mensajeUsuario(user, mensaje);
		} else
			JOptionPane.showMessageDialog(null,
					"Cancelaciones solo con una hora de margen hasta inicio de la actividad.");

	}
}
