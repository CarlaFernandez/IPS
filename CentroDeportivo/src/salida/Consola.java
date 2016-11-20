package salida;

import java.util.Date;

import CapaDatos.InstalacionDatos;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.Usuario;
import CapaNegocio.managers.ManagerFechas;

public class Consola {

	public void imprimirRecibo(ReservaDao reserva, Usuario usuario) {
		System.out.println("\t\tClub deportivo IPS");
		System.out.println("\tRecibo de pago de reserva");
		System.out.println("El usuario: " + usuario.getNombre() + ", " + usuario.getApellidos());
		System.out.println("Ha pagado la cantidad de: " + reserva.calcularImporteReserva());
		System.out.println("A fecha: " + new Date());
		String codInst = InstalacionDatos.ObtenerInstalacion(reserva.getIdInst()).getCodigo();
		System.out.println("Concepto: Pago de la reserva de la instalacion " + codInst + " desde: "
				+ ManagerFechas.formatearFecha(reserva.getInicio()) + " hasta: "
				+ ManagerFechas.formatearFecha(reserva.getFin()));
	}

	public void imprimirMensajeUsuario(Usuario usuario, String mensaje) {
		System.out.println("\t\tMensaje enviado");
		System.out.println("Destinatario: ");
		System.out.println("\tUsuario(DNI): "+usuario.getDNI());
		System.out.println("\tUsuario(Email): "+usuario.getEmail());
		System.out.println("Contenido:\n\t"+mensaje);
	}

}
