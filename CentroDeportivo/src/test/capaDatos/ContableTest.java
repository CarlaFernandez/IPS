package test.capaDatos;

import static org.junit.Assert.*;

import org.junit.Test;

import CapaDatos.Contable;

public class ContableTest {

	@Test
	public void testPasarPagoACuenta() {
		int valor = Contable.incrementarCuotaMensual((long)12345);
		assertEquals(10, valor);
	}

}
