package CapaNegocio.excepciones;

/**
 * Created by Carla on 08/10/2016.
 */
public class ExcepcionReserva extends Throwable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcepcionReserva() {
        super();
    }

    public ExcepcionReserva(String message) {
        super(message);
    }

}
