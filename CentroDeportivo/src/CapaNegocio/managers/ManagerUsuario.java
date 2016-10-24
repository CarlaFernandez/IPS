package CapaNegocio.managers;

import java.util.Date;
import java.util.List;

import CapaDatos.InstalacionDatos;
import CapaDatos.ReservaDatos;
import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;

/**
 * Created by Carla on 08/10/2016.
 */
public class ManagerUsuario {

	public static List<ReservaDao> verReservasPorFecha(Date inicio, Date fin, Long idUser) {
		return ReservaDatos.obtenerReservasPorFechaYUsuario(inicio,fin, idUser);
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
}
