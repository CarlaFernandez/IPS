package CapaNegocio.dao;

import java.util.Date;

import CapaNegocio.EstadoPago;
import CapaNegocio.TipoPago;

public class Pago {
	private Long id;
	private String concepto;
	private Date fecha;
	private double importe;
	private String estado;
	private String tipo;

	public Pago(Long id, String concepto, Date fecha, double importe,
			EstadoPago estado, TipoPago tipo) {
		this.id = id;
		this.concepto = concepto;
		this.fecha = fecha;
		this.importe = importe;
		this.estado = estado.name();
		this.tipo = tipo.name();
	}

	public Pago(long id, String concepto, java.sql.Date fecha, double importe,
			String estado, String tipo) {
		this.id = id;
		this.concepto = concepto;
		this.fecha = fecha;
		this.importe = importe;
		this.estado = estado.toString();
		this.tipo = tipo.toString();
	}

	public Pago() {
	}

	public Long getId() {
		return id;
	}

	public String getConcepto() {
		return concepto;
	}

	public Date getFecha() {
		return fecha;
	}

	public double getImporte() {
		return importe;
	}

	public String getEstado() {
		return estado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "PAGO\n\n Concepto = " + concepto + "\n Fecha = " + fecha
				+ "\n Importe = " + importe + "\n Estado = " + estado + "\n Tipo = "
				+ tipo;
	}

}
