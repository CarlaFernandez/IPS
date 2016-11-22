package CapaNegocio.dao;

import org.joda.time.DateTime;

public class ActividadHoras {
	Long id, idActividad, idMonitor, idReserva;
	DateTime fechaInicio, fechaFin;
	int plazasTotales, plazasOcupadas;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(Long idActividad) {
		this.idActividad = idActividad;
	}
	public Long getIdMonitor() {
		return idMonitor;
	}
	public void setIdMonitor(Long idMonitor) {
		this.idMonitor = idMonitor;
	}
	public Long getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}
	public DateTime getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(DateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public DateTime getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(DateTime fechaFin) {
		this.fechaFin = fechaFin;
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
	
	
}
