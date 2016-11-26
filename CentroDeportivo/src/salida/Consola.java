package salida;

import java.util.Date;
import java.util.List;
import java.util.Map;

import CapaDatos.InstalacionDatos;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerFechas;

public class Consola {

	public void imprimirRecibo(ReservaDao reserva, Usuario usuario) {
		System.out.println("\t\tClub deportivo IPS");
		System.out.println("\tRecibo de pago de reserva");
		System.out.println("El usuario: " + usuario.getNombre() + ", "
				+ usuario.getApellidos());
		System.out.println("Ha pagado la cantidad de: "
				+ reserva.calcularImporteReserva());
		System.out.println("A fecha: " + new Date());
		String codInst = InstalacionDatos
				.ObtenerInstalacion(reserva.getIdInst()).getCodigo();
		System.out.println("Concepto: Pago de la reserva de la instalacion "
				+ codInst + " desde: "
				+ ManagerFechas.formatearFecha(reserva.getInicio()) + " hasta: "
				+ ManagerFechas.formatearFecha(reserva.getFin()));
	}

	public void imprimirMensajeUsuario(Usuario usuario, String mensaje) {
		System.out.println("\t\tMensaje enviado");
		System.out.println("Destinatario: ");
		System.out.println("\tUsuario(DNI): " + usuario.getDNI());
		System.out.println("\tUsuario(Email): " + usuario.getEmail());
		System.out.println("Contenido:\n\t" + mensaje);
	}

	public void imprimirConfirmacionInscripcion(Map<String, Object> actividad,
			Long idSocio) {
		System.out.println("MensajeAUsuario-Usuario=" + idSocio
				+ " Se ha inscrito correctamente en la actividad: "
				+ actividad.get("nombre") + " " + actividad.get("fecha") + " "
				+ actividad.get("instalacion"));
	}

	public void imprimirCancelacionInstanciaActividad(
			Map<String, Object> instancia, List<Long> users) {
		for (Long id : users) {
			System.out.println("MensajeAUsuario-Usuario=" + id
					+ " Se ha cancelado la clase de actividad: "
					+ instancia.get("nombre") + " " + instancia.get("fecha")
					+ " " + instancia.get("instalacion"));
		}
	}

}
