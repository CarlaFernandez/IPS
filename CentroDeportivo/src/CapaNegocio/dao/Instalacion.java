package CapaNegocio.dao;

/**
 * Created by Carla on 08/10/2016.
 */
public class Instalacion {

	private double precioHora;
	private String codigo;
	private Long idInst;
	private String descripcion;
	private boolean disponible;

	public Instalacion(double precioHora, String codigo, Long idInst,
			String descripcion, boolean disponible) {
		this.precioHora = precioHora;
		this.idInst = idInst;
		this.descripcion = descripcion;
	}

	public Instalacion() {
	}

	public double getPrecioHora() {
		return precioHora;
	}

	public void setPrecioHora(double precioHora) {
		this.precioHora = precioHora;
	}

	public String getCodigo() {
		return codigo;
	}

	public Long getIdInst() {
		return idInst;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setIdInst(Long idInst) {
		this.idInst = idInst;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	@Override
	public String toString() {
		return "INSTALACION\n\n Codigo=" + codigo + "\n PrecioHora=" + precioHora
				+ "\n Descripcion=" + descripcion + "\n Disponible="
				+ disponible + "]";
	}

}
