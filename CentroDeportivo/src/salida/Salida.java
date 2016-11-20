package salida;

import java.util.Date;

import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.ActividadHoras;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;

public class Salida {
	Log logMensajes;
	Log logPagos;
	Consola consola;

	public Salida() {
		this.logMensajes = new Log("LogMensajes.txt");
		this.logPagos = new Log("LogPagos.txt");
		this.consola = new Consola();
	}

	public void reciboReserva(ReservaDao reserva, Usuario usuario) {
		consola.imprimirRecibo(reserva, usuario);
		String cadena = "PAGO-Usuario=" + reserva.getIdUsu() + "-Reserva=" + reserva.getIdRes() + "-Fecha="
				+ new Date();
		logPagos.agregar(cadena);
	}

	public void mensajeUsuario(Usuario usuario, String mensaje) {
		consola.imprimirMensajeUsuario(usuario, mensaje);
		String cadena = "MensajeAUsuario-Usuario=" + usuario.getIdUsu() + "-Fecha=" + new Date() + "-Mensaje="
				+ mensaje;
		logMensajes.agregar(cadena);
	}

	public void mensajeUsuario(Long user, String mensaje) {
		Usuario usuario = UsuarioDatos.ObtenerUsuario(user);
		mensajeUsuario(usuario, mensaje);
	}
}
