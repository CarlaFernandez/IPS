package CapaNegocio.dao;

public class Curso {
	private Long ID;
	private String nombre;
	private String descripcion;
	private int plazasTotales;
	private int plazasOcupadas;
	private double numeroHoras;
	private boolean periodica;
	private Long monitorID;
	private boolean cancelada;
	
	
	public Curso(){}
	
	public Curso(Long iD, String nombre, String descripcion, int plazasTotales, int plazasOcupadas, double numeroHoras,
			boolean periodica, Long monitorID, boolean cancelada) {
		ID = iD;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.plazasTotales = plazasTotales;
		this.plazasOcupadas = plazasOcupadas;
		this.numeroHoras = numeroHoras;
		this.periodica = periodica;
		this.monitorID = monitorID;
		this.cancelada = cancelada;
	}
	
	public int getPlazasTotales() {
		return plazasTotales;
	}

	public void setPlazasTotales(int plazasTotales) {
		this.plazasTotales = plazasTotales;
	}

	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
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
	public boolean isPeriodica() {
		return periodica;
	}
	public void setPeriodica(boolean periodica) {
		this.periodica = periodica;
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
