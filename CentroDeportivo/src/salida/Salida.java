package salida;

import java.util.Date;
import java.util.List;
import java.util.Map;

import CapaDatos.UsuarioDatos;
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
		String cadena = "PAGO-Usuario=" + reserva.getIdUsu() + "-Reserva="
				+ reserva.getIdRes() + "-Fecha=" + new Date();
		logPagos.agregar(cadena);
	}

	public void mensajeUsuario(Usuario usuario, String mensaje) {
		consola.imprimirMensajeUsuario(usuario, mensaje);
		String cadena = "MensajeAUsuario-Usuario=" + usuario.getIdUsu()
				+ "-Fecha=" + new Date() + "-Mensaje=" + mensaje;
		logMensajes.agregar(cadena);
	}

	public void mensajeUsuario(Long user, String mensaje) {
		Usuario usuario = UsuarioDatos.ObtenerUsuario(user);
		mensajeUsuario(usuario, mensaje);
	}

	public void socioInscritoCorrectamente(Map<String, Object> actividad,
			Long idSocio) {
		consola.imprimirConfirmacionInscripcion(actividad, idSocio);
		logMensajes.agregar("MensajeAUsuario-Usuario=" + idSocio
				+ " Se ha inscrito correctamente en la actividad: "
				+ actividad.get("nombre") + " " + actividad.get("fecha") + " "
				+ actividad.get("instalacion"));
	}

	public void instanciaActividadCancelada(Map<String, Object> instancia,
			List<Long> users) {
		consola.imprimirCancelacionInstanciaActividad(instancia, users);
		for (Long id : users) {
			logMensajes.agregar("MensajeAUsuario-Usuario=" + id
					+ " Se ha cancelado la clase de actividad: "
					+ instancia.get("nombre") + " " + instancia.get("fecha")
					+ " " + instancia.get("instalacion"));
		}
	}
}
