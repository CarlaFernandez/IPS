package test.capaDatos;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import CapaDatos.ReservaDatos;
import CapaNegocio.dao.Instalacion;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.excepciones.ExcepcionReserva;

public class ReservaDatosTest {

	private ReservaDao reserva;

	@Before
	public void setUp() {
		try {
			reserva = new ReservaDao(new Long(2), TipoReserva.SOCIO, new DateTime(2016, 10, 16, 11, 0),
					new DateTime(2016, 10, 16, 12, 0), new Long(1),new Long(1), new Long(1), null, null);
		} catch (ExcepcionReserva e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void anadirReservaBBDD() {
		try {
			ReservaDatos.insertarReservaUsuario(reserva);
			// assertEquals(reserva.toString(),
			// ReservaDatos.getReservaPorID("res1"));
		} catch (ExcepcionReserva excepcionReserva) {
			excepcionReserva.printStackTrace();
		}
	}
	
	@Test
	public void cancelarReserva(){
		ReservaDao reserva = ReservaDatos.obtenerReservaPorId((long)1);
		try {
			ReservaDatos.cancelarReservaComoSocio(reserva);
		} catch (ExcepcionReserva e) {
			e.printStackTrace();
		}
	}

	//@Test
	@SuppressWarnings("deprecation")
	public void calcularImporteReserva() {
		Instalacion inst = new Instalacion(2, "padel1", new Long(111), "blablabla", true);
		assertEquals(2.0, reserva.calcularImporteReserva());
	}

}
