package CapaNegocio.managers;

import javax.swing.JOptionPane;

import org.joda.time.DateTime;
import java.util.Date;
import java.util.List;

import CapaDatos.InstalacionDatos;
import CapaDatos.PagoDatos;
import CapaDatos.ReservaDatos;
import CapaNegocio.EstadoPago;
import CapaNegocio.TipoPago;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.Pago;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.excepciones.ExcepcionReserva;

/**
 * Created by Carla on 08/10/2016.
 */
public class ManagerAdmin {

	public static void crearReservaCentro(DateTime inicio, DateTime fin, Long idInst, Long idAct, Long idCurso)
			throws ExcepcionReserva {
		ReservaDao reserva = new ReservaDao(ReservaDatos.obtenerNuevoIDReserva(), TipoReserva.CENTRO, inicio, fin,
				idInst, null, null, idAct, idCurso);
		ReservaDatos.insertarReservaAdmin(reserva);
	}

	public static void crearReservaSocio(DateTime inicio, DateTime fin, Long idInst, Long idUsu, TipoPago tipoPago)
			throws ExcepcionReserva {
		double duracionReserva = ReservaDatos.calcularDuracionEnMinutos(inicio, fin);
		Long idPago = PagoDatos.obtenerNuevoIDPago();	
		Pago pago = new Pago(idPago, "Reserva instalacion",
				new Date(System.currentTimeMillis()), ReservaDatos.calcularImporteReserva(idInst, duracionReserva), 
				EstadoPago.PENDIENTE, tipoPago);
		PagoDatos.insertarPago(pago);
		
		ReservaDao reserva = new ReservaDao(ReservaDatos.obtenerNuevoIDReserva(), TipoReserva.SOCIO, inicio, fin,
				idInst, idPago, idUsu, null, null);

		ReservaDatos.insertarReservaUsuario(reserva);
	}

	public static List<ReservaDao> verReservasInstalacion(Long idInst) {
		return ReservaDatos.obtenerReservaPorInstalacion(idInst);
	}
	
	public static List<Instalacion> verInstalaciones() {
		return InstalacionDatos.ObtenerInstalaciones();
	}
	
	public static List<ReservaDao> verReservasPorFechaEInstalacion(Date inicio, Date fin, Long idInst) {
		return ReservaDatos.obtenerReservasPorFechaEInstalacion(inicio,fin, idInst);
	}


	public static void crearPagoEfectivo(Long idReserva) {
		ReservaDao reserva = ReservaDatos.obtenerReservaPorId(idReserva);
		Pago pago = PagoDatos.obtenerPago(reserva.getIdPago());
		if (pago.getEstado().equals(EstadoPago.COBRADO.name())) {
			JOptionPane.showMessageDialog(null, "Reserva ya estaba cobrada");
			return;
		}
		PagoDatos.CobrarPago(pago.getId());
		System.out.println("Imprimiendo recibo....");
	}
}