package CapaNegocio.dao;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import CapaDatos.InstalacionDatos;
import CapaDatos.ReservaDatos;
import CapaNegocio.EstadoReserva;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerFechas;

/**
 * Created by Carla on 06/10/2016.
 */
public class ReservaDao {
	public static final int DIAS_ANTELACION_RESERVA_MAXIMO = 15;
	public static final int MINUTOS_ANTELACION_RESERVA_MAXIMO_ADMIN = 0;
	public static final int MINUTOS_ANTELACION_RESERVA_MAXIMO_SOCIO = 60;
	public static final int MINUTOS_CANCELACION_MAXIMO_SOCIO = 60;
	public static final int MINUTOS_DURACION_MAXIMO_SOCIO = 120;

	private Long idRes;
	private String tipoRes;
	private DateTime inicio;
	private DateTime fin;
	private Long idInst;
	private Long idUsu;
	// private Usuario usuario;
	private double duracionEnMinutos;
	private String estado;
	private Long idAct;
	private Long idCurso;
	private Long idPago;
	private DateTime horaEntrada;
	private DateTime horaSalida;

	public ReservaDao(Long idRes, TipoReserva socio, DateTime inicio,
			DateTime fin, Long idInst, Long idPago, Long idUsu, Long idAct,
			Long idCurso) throws ExcepcionReserva {
		this.idRes = idRes;
		this.tipoRes = socio.name();
		this.inicio = inicio;
		this.fin = fin;
		this.idInst = idInst;
		this.idPago = idPago;
		this.idUsu = idUsu;
		this.idAct = idAct;
		this.idCurso = idCurso;
		this.duracionEnMinutos = ReservaDatos.calcularDuracionEnMinutos(inicio,
				fin);
		this.estado = EstadoReserva.ACTIVA.name();

		if (duracionEnMinutos > MINUTOS_DURACION_MAXIMO_SOCIO
				&& socio != TipoReserva.CENTRO) {
			throw new ExcepcionReserva("Esta reserva dura demasiado");
		}
		// usuario = UsuarioDatos.obtenerUsuarioAPartirDeID(idUsu);
	}

	public DateTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(DateTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public DateTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(DateTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	public ReservaDao() {
	}

	public ReservaDao(TipoReserva socio, DateTime inicio, DateTime fin,
			Long idInst, Long idPago, Long idUsu, Long idAct, Long idCurso)
			throws ExcepcionReserva {
		this.tipoRes = socio.name();
		this.inicio = inicio;
		this.fin = fin;
		this.idInst = idInst;
		this.idPago = idPago;
		this.idUsu = idUsu;
		this.idAct = idAct;
		this.idCurso = idCurso;
		this.duracionEnMinutos = ReservaDatos.calcularDuracionEnMinutos(inicio,
				fin);
		this.estado = EstadoReserva.ACTIVA.name();

		if (duracionEnMinutos > MINUTOS_DURACION_MAXIMO_SOCIO
				&& socio != TipoReserva.CENTRO) {
			throw new ExcepcionReserva("Esta reserva dura demasiado");
		}
		// usuario = UsuarioDatos.obtenerUsuarioAPartirDeID(idUsu);
	}

	public Long getIdPago() {
		return idPago;
	}

	public DateTime getInicio() {
		return inicio;
	}

	public DateTime getFin() {
		return fin;
	}

	public double getDuracionEnMinutos() {
		return Minutes.minutesBetween(inicio, fin).getMinutes();
	}

	public double calcularImporteReserva() {
		return InstalacionDatos.obtenerPrecioInstalacion(idInst)
				* duracionEnMinutos;
	}

	public Long getIdRes() {
		return idRes;
	}

	public String getTipoRes() {
		return tipoRes;
	}

	public Long getIdInst() {
		return idInst;
	}

	public Long getIdUsu() {
		return idUsu;
	}

	// public Usuario getUsuario() {
	// return usuario;
	// }

	public String getEstado() {
		return estado;
	}

	public Long getIdAct() {
		return idAct;
	}

	public Long getIdCurso() {
		return idCurso;
	}

	public void setIdRes(Long idRes) {
		this.idRes = idRes;
	}

	public void setTipoRes(String tipoRes) {
		this.tipoRes = tipoRes;
	}

	public void setInicio(DateTime inicio) {
		this.inicio = inicio;
	}

	public void setFin(DateTime fin) {
		this.fin = fin;
	}

	public void setIdInst(Long idInst) {
		this.idInst = idInst;
	}

	public void setIdUsu(Long idUsu) {
		this.idUsu = idUsu;
	}

	public void setDuracionEnMinutos(double duracionEnMinutos) {
		this.duracionEnMinutos = duracionEnMinutos;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setIdAct(Long idAct) {
		this.idAct = idAct;
	}

	public void setIdCurso(Long idCurso) {
		this.idCurso = idCurso;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	@Override
	public String toString() {
		return "RESERVA\n\n Tipo de reserva = " + tipoRes + "\n Inicio = "
				+ ManagerFechas.formatearFecha(inicio) + "\n Fin = "
				+ ManagerFechas.formatearFecha(fin)
				+ "\n Duración en minutos = " + getDuracionEnMinutos()
				+ "\n Estado = " + estado;
	}

}
