package CapaNegocio.dao;

public class Actividad {
	private Long codigo;
	private String nombre;
	private String descripcion;
	private int plazasTotales;
	private int plazasOcupadas;
	private double numeroHoras;
	private Long monitorID;
	private boolean cancelada;
	

	public Actividad(Long codigo, String nombre, String descripcion, int plazasTotales, int plazasOcupadas,
			double numeroHoras, Long monitorID, boolean cancelada) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.plazasTotales = plazasTotales;
		this.plazasOcupadas = plazasOcupadas;
		this.numeroHoras = numeroHoras;
		this.monitorID = monitorID;
		this.cancelada = cancelada;
	}

	public Actividad(){}
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getPlazasTotales() {
		return plazasTotales;
	}
	public void setPlazasTotales(int plazasTotales) {
		this.plazasTotales = plazasTotales;
	}
	public int getPlazasOcupadas() {
		return plazasOcupadas;
	}
	public void setPlazasOcupadas(int plazasOcupadas) {
		this.plazasOcupadas = plazasOcupadas;
	}
	public double getNumeroHoras() {
		return numeroHoras;
	}
	public void setNumeroHoras(double numeroHoras) {
		this.numeroHoras = numeroHoras;
	}
	public Long getMonitorID() {
		return monitorID;
	}
	public void setMonitorID(Long monitorID) {
		this.monitorID = monitorID;
	}
	public boolean isCancelada() {
		return cancelada;
	}
	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}

	
}
