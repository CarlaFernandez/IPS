package CapaNegocio.dao;

public class Monitor {
	
	private Long idMonitor;
	private String nombre;
	private String apellidos;
	
	
	public Monitor(Long idMonitor, String nombre, String apellidos) {
		this.idMonitor = idMonitor;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}

	public Monitor() {	}

	public Long getIdMonitor() {
		return idMonitor;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setIdMonitor(Long idMonitor) {
		this.idMonitor = idMonitor;
	}

	

	@Override
	public String toString() {
		return "USUARIO\n\n ID Monitor=" + idMonitor + "\n Nombre=" + nombre + "\n Apellidos=" + apellidos + "]";
	}
}
