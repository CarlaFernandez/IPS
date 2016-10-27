package test.capaDatos;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import CapaDatos.UsuarioDatos;
import CapaNegocio.dao.Usuario;

public class UsuarioDatosTest {
	private Usuario usuario;
	
    @Before
    public void setUp() throws Exception {
        usuario = new Usuario(true, "carla", "fernandez", new Long(12345), 
				"746756B","calle bla", "blabla@bla.com", "oviedo", "1384879342798", null);

    }

	@Test
	public void insertarUsuario() {
		int numClientes = UsuarioDatos.obtenerNumeroUsuarios();
		UsuarioDatos.insertarUsuario(usuario);
		assertEquals(numClientes+1,  UsuarioDatos.obtenerNumeroUsuarios());
	}
	
	@Test
	public void eliminarUsuario() {
		int numClientes = UsuarioDatos.obtenerNumeroUsuarios();
		UsuarioDatos.eliminarUsuario(usuario);
		assertEquals(numClientes-1,  UsuarioDatos.obtenerNumeroUsuarios());
	}

}
