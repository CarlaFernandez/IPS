package CapaNegocio.dao;

import java.sql.Date;

/**
 * Created by Carla on 08/10/2016.
 */
public class Usuario {
	private boolean socio;
	private String nombre;
	private String apellidos;
	private Long idUsu;
	private String DNI;
	private String direccion;
	private String cuentaBancaria;
	private String email;
	private String ciudad;
	private Date baja;

	public Usuario(boolean socio, String nombre, String apellidos, Long idUsu, String DNI, String direccion,
			String email, String ciudad, String cuentaBancaria, Date baja) {
		this.socio = socio;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.idUsu = idUsu;
		this.DNI = DNI;
		this.direccion = direccion;
		this.email = email;
		this.ciudad = ciudad;
		this.cuentaBancaria = cuentaBancaria;
		this.baja = baja;
	}

	public Usuario() {
	}

	public Long getIdUsu() {
		return idUsu;
	}

	public boolean isSocio() {
		return socio;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getDNI() {
		return DNI;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getCuentaBancaria() {
		return cuentaBancaria;
	}

	public String getEmail() {
		return email;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setSocio(boolean socio) {
		this.socio = socio;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setIdUsu(Long idUsu) {
		this.idUsu = idUsu;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setCuentaBancaria(String cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public Date getBaja() {
		return baja;
	}

	public void setBaja(Date baja) {
		this.baja = baja;
	}

	@Override
	public String toString() {
		return "USUARIO\n\n Socio = " + socio + "\n Nombre = " + nombre + "\n Apellidos = " + apellidos + "\n IDUsuario = " + idUsu
				+ "\n DNI = " + DNI + "\n Direccion = " + direccion + "\n CuentaBancaria = " + cuentaBancaria + "\n Email = "
				+ email + "\n Ciudad = " + ciudad + "\n baja =  " + baja;
	}

}
