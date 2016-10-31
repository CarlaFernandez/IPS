package CapaDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import CapaNegocio.DiasSemana;
import CapaNegocio.EstadoPago;
import CapaNegocio.EstadoReserva;
import CapaNegocio.dao.ReservaDao;
import CapaNegocio.dao.TipoReserva;
import CapaNegocio.excepciones.ExcepcionReserva;
import CapaNegocio.managers.ManagerFechas;

/**
 * Created by Carla on 08/10/2016.
 */
public class ReservaDatos {
	public static void insertarReservaUsuario(ReservaDao reserva) throws ExcepcionReserva {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			comprobarReservaValidaUsuario(reserva);
			StringBuilder sb = new StringBuilder();
			sb.append("insert into reserva ");
			sb.append("(id, hora_inicio, hora_fin, instalacion_id, pago_id, ");
			sb.append("estado, tipo, usuario_id, actividad_id, curso_id) ");
			sb.append("values (?,?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, reserva.getIdRes());
			ps.setTimestamp(2, ManagerFechas.convertirATimestampSql(reserva.getInicio()));
			ps.setTimestamp(3, ManagerFechas.convertirATimestampSql(reserva.getFin()));
			ps.setLong(4, reserva.getIdInst());
			ps.setLong(5, reserva.getIdPago());
			ps.setString(6, reserva.getEstado());
			ps.setString(7, reserva.getTipoRes());
			ps.setLong(8, reserva.getIdUsu());
			ps.setNull(9, java.sql.Types.BIGINT);
			ps.setNull(10, java.sql.Types.BIGINT);
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void insertarReservaAdmin(ReservaDao reserva) throws ExcepcionReserva {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			//comprobarReservaValidaAdmin(reserva);
			StringBuilder sb = new StringBuilder();
			sb.append("insert into reserva ");
			sb.append("(hora_inicio, hora_fin, instalacion_id, pago_id, ");
			sb.append("estado, tipo, usuario_id, actividad_id, curso_id) ");
			sb.append("values (?,?,?,?,?,?,?,?,?)");
			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, ManagerFechas.convertirATimestampSql(reserva.getInicio()));
			ps.setTimestamp(2, ManagerFechas.convertirATimestampSql(reserva.getFin()));
			ps.setLong(3, reserva.getIdInst());
			if (reserva.getIdPago() != null)
				ps.setLong(4, reserva.getIdPago());
			else
				ps.setNull(4, java.sql.Types.BIGINT);
			ps.setString(5, reserva.getEstado());
			ps.setString(6, reserva.getTipoRes());
			if (reserva.getIdUsu() != null)
				ps.setLong(7, reserva.getIdAct());
			else
				ps.setNull(7, java.sql.Types.BIGINT);
			if (reserva.getIdAct() != null)
				ps.setLong(8, reserva.getIdAct());
			else
				ps.setNull(8, java.sql.Types.BIGINT);
			if (reserva.getIdCurso() != null)
				ps.setLong(9, reserva.getIdCurso());
			else
				ps.setNull(9, java.sql.Types.BIGINT);
			ps.execute();
			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void comprobarReservaValidaUsuario(ReservaDao reserva) throws ExcepcionReserva {

		// usuario de baja
		if (comprobarUsuarioBaja(reserva.getIdUsu(), reserva.getInicio())) {
			// reservas simultaneas
			if (!UsuarioDatos.usuarioTieneReservaEnHoras(reserva.getIdUsu(), reserva.getInicio(), reserva.getFin())) {
				// instalacion esta libre
				if (InstalacionDatos.estaLibreEnHoras(reserva.getIdInst(), reserva.getInicio(), reserva.getFin())) {

					long diff = reserva.getInicio().getMillis() - new Date(System.currentTimeMillis()).getTime();
					long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					long minutesDiff = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

					if (daysDiff > ReservaDao.DIAS_ANTELACION_RESERVA_MAXIMO) {
						throw new ExcepcionReserva("Es demasiado pronto para realizar esta reserva");
					} else if (minutesDiff < ReservaDao.MINUTOS_ANTELACION_RESERVA_MAXIMO_SOCIO) {
						throw new ExcepcionReserva("Es demasiado tarde para realizar esta reserva");
					}

				} else {
					throw new ExcepcionReserva("Esta instalacion ya esta reservada para esas fechas");
				}
			} else {
				throw new ExcepcionReserva("El usuario tiene otra actividad simultanea");
			}
		} else {
			throw new ExcepcionReserva("El usuario no puede reservar porque está dado de baja");
		}
	}

	private static boolean comprobarUsuarioBaja(Long idUsu, DateTime inicio) {
		Date baja = UsuarioDatos.ObtenerUsuario(idUsu).getBaja();
		return baja == null || new DateTime(baja).isAfter(inicio);
	}

	private static void comprobarReservaValidaAdmin(ReservaDao reserva) throws ExcepcionReserva {
		// TODO cambiar esta condicion si comprobamos que este disponible
		// tambien en el caso de reserva de centro

		if (InstalacionDatos.estaLibreEnHoras(reserva.getIdInst(), reserva.getInicio(), reserva.getFin())) {
			if (reservaEnHoraEnPunto(reserva.getInicio())) {
				long diff = reserva.getInicio().getMillis() - new Date(System.currentTimeMillis()).getTime();
				long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				long minutesDiff = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

				if (daysDiff > ReservaDao.DIAS_ANTELACION_RESERVA_MAXIMO) {
					throw new ExcepcionReserva("Es demasiado pronto para realizar esta reserva");
				} else if (minutesDiff < ReservaDao.MINUTOS_ANTELACION_RESERVA_MAXIMO_ADMIN) {
					throw new ExcepcionReserva("Es demasiado tarde para realizar esta reserva");
				}
			} else {
				throw new ExcepcionReserva("La reserva ha de comenzar en la hora en punto");
			}

		} else {
			throw new ExcepcionReserva("Esta instalacion ya esta reservada para esas fechas");
		}
	}

	private static boolean reservaEnHoraEnPunto(DateTime inicio) {
		return inicio.getMinuteOfHour() == 0;
	}

	public static Long obtenerNuevoIDReserva() {
		// TODO eliminar duplicacion de codigo al obtener nuevos ids
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		int result = 0;
		Long id = null;
		try {
			do {
				id = GeneradorIDRandom.generarID();
				StringBuilder sb = new StringBuilder();
				sb.append("select count(*) num_reservas from reserva ");
				sb.append("where id = ?");
				PreparedStatement ps = con.prepareStatement(sb.toString());
				ps.setLong(1, id);
				ResultSet rs = ps.executeQuery();
				while (rs.next())
					result = rs.getInt("num_reservas");
			} while (result != 0);
			con.close();
			return id;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public static ReservaDao getReserva(long idReserva) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		ResultSet rs = null;
		ReservaDao reserva = null;
		try {
			PreparedStatement ps = con.prepareStatement("select * from reserva where id = ?");
			ps.setLong(1, idReserva);
			rs = ps.executeQuery();
			reserva = new ReservaDao();
			reserva.setIdRes(rs.getLong("ID"));
			reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
			reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
			Long instalacion_id = rs.getLong("instalacion_id");
			reserva.setIdInst(instalacion_id);
			reserva.setIdPago(rs.getLong("PAGO_ID"));
			reserva.setEstado(rs.getString("ESTADO"));
			reserva.setTipoRes(rs.getString("TIPO"));
			reserva.setIdUsu(rs.getLong("USUARIO_ID"));
			reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
			reserva.setIdCurso(rs.getLong("CURSO_ID"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return reserva;
	}

	public static void cancelarReservaComoSocio(ReservaDao reserva) throws ExcepcionReserva {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		long tiempo = reserva.getInicio().getMillis() - new Date(System.currentTimeMillis()).getTime();
		long tiempoPermitido = TimeUnit.MINUTES.toMillis(ReservaDao.MINUTOS_CANCELACION_MAXIMO_SOCIO);
		if (tiempo < tiempoPermitido)
			throw new ExcepcionReserva("Demasiado tarde para cancelar la reserva");
		else {
			try {
				PreparedStatement ps = con.prepareStatement("update reserva set ESTADO='CANCELADA' where id = ?");
				ps.setLong(1, reserva.getIdRes());
				ps.executeUpdate();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void cancelarReservaComoAdmin(ReservaDao reserva) throws ExcepcionReserva {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			PreparedStatement ps = con.prepareStatement("update reserva set estado='ANULADA' where id = ?");
			ps.setLong(1, reserva.getIdRes());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static double calcularDuracionEnMinutos(DateTime inicio, DateTime fin) {
		return Minutes.minutesBetween(inicio, fin).getMinutes();
	}

	public static double calcularImporteReserva(Long idInst, double duracionReserva) {
		return InstalacionDatos.obtenerPrecioInstalacion(idInst) * duracionReserva;
	}

	public static List<ReservaDao> obtenerReservasPorFechaYUsuario(Date inicio, Date fin, Long idUser) {
		DateTime fecha1 = new DateTime(inicio);
		DateTime fecha2 = new DateTime(fin);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from reserva ");
			sb.append("where hora_inicio >= ? ");
			sb.append("and hora_fin <= ? ");
			sb.append("and usuario_id = ?");
			sb.append("order by hora_inicio");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, new Timestamp(fecha1.getMillis()));
			ps.setTimestamp(2, new Timestamp(fecha2.getMillis()));
			ps.setLong(3, idUser);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));
				Timestamp horaEntrada = rs.getTimestamp("HORA_ENTRADA");
				Timestamp horaSalida = rs.getTimestamp("HORA_SALIDA");
				if (horaEntrada != null)
					reserva.setHoraEntrada(new DateTime(horaEntrada.getTime()));
				if (horaSalida != null)
					reserva.setHoraSalida(new DateTime(horaSalida.getTime()));
				reservas.add(reserva);
			}
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static List<ReservaDao> obtenerReservasPorFechaEInstalacion(Date inicio, Date fin, Long idInst) {
		DateTime fecha1 = new DateTime(inicio);
		DateTime fecha2 = new DateTime(fin);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from reserva ");
			sb.append("where hora_inicio >= ? ");
			sb.append("and hora_fin <= ? ");
			sb.append("and instalacion_id = ? ");
			sb.append("order by hora_inicio");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, new Timestamp(fecha1.getMillis()));
			ps.setTimestamp(2, new Timestamp(fecha2.getMillis()));
			ps.setLong(3, idInst);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));

				reservas.add(reserva);
			}
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static List<ReservaDao> obtenerReservasPorInstalacion(long instalacion) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from reserva ");
			sb.append("where instalacion_id = ? ");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, instalacion);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));

				reservas.add(reserva);
			}
			// System.out.println("Solicitud de reservas para instalacionID:" +
			// instalacion + " y usuarioID:" + usuario
			// + ">>" + reservas);
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static List<ReservaDao> obtenerReservasPorUsuarioEInstalacionSinPagar(long usuario) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM reserva,pago where pago.id=reserva.pago_id and pago.estado=?");
			sb.append("and usuario_id = ? and reserva.estado='ACTIVA'");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setString(1, EstadoPago.PENDIENTE.name());
			ps.setLong(2, usuario);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));

				reservas.add(reserva);
			}
			// System.out.println("Solicitud de reservas sin pagar para
			// usuarioID:" + usuario + ">>" + reservas);
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static ReservaDao obtenerReservaPorId(Long idReserva) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from reserva ");
			sb.append("where id = ?");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idReserva);
			ResultSet rs = ps.executeQuery();
			ReservaDao reserva = null;
			while (rs.next()) {
				reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));
			}

			return reserva;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void pagarReserva(Long idReserva, Long idPago) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("update  reserva SET PAGO_ID = ? ");
			sb.append("where id = ?");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, idPago);
			ps.setLong(2, idReserva);
			ps.executeQuery();

			con.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static List<ReservaDao> obtenerReservaPorInstalacion(Long instalacion) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM reserva ");
			sb.append("where instalacion_id = ? ");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setLong(1, instalacion);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));

				reservas.add(reserva);
			}
			// System.out.println("Solicitud de reservas de la instalacion:" +
			// instalacion + ">>" + reservas);
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public void registrarEntradaSocio(Long idReserva, Timestamp hora) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE RESERVA SET HORA_ENTRADA = ? WHERE ID = ?");
			ps.setTimestamp(1, hora);
			ps.setLong(2, idReserva);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getSQLState());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void registrarSalidaSocio(Long idReserva, Timestamp hora) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		ResultSet rs = null;
		try {
			PreparedStatement ps = con.prepareStatement("UPDATE RESERVA SET HORA_SALIDA = ? WHERE ID = ?");
			ps.setTimestamp(1, hora);
			ps.setLong(2, idReserva);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getSQLState());
		}
	}

	public static void anularReserva(Long idResConflict) {
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			PreparedStatement ps = con
					.prepareStatement("update reserva set ESTADO='" + EstadoReserva.ANULADA + "' where id = ?");
			ps.setLong(1, idResConflict);
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void actualizarHoraEntrada(ReservaDao reserva) throws ExcepcionReserva {
		Timestamp horaEntrada = null;
		if (reserva.getHoraEntrada() != null)
			horaEntrada = new Timestamp(reserva.getHoraEntrada().getMillis());
		else
			return;
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update reserva set hora_entrada = ? where id = ? ");
			ps.setTimestamp(1, horaEntrada);
			ps.setLong(2, reserva.getIdRes());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getSQLState() + " " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static void actualizarHoraSalida(ReservaDao reserva) throws ExcepcionReserva {
		Timestamp horaSalida = null;
		if (reserva.getHoraSalida() != null)
			horaSalida = new Timestamp(reserva.getHoraSalida().getMillis());
		else
			return;
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update reserva set hora_salida = ? where id = ? ");
			ps.setTimestamp(1, horaSalida);
			ps.setLong(2, reserva.getIdRes());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println(e.getSQLState());
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				con.close();
			} catch (SQLException e) {
				System.err.println(e.getSQLState() + " " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static List<ReservaDao> obtenerReservasActivasPorFechaEInstalacion(Date inicio, Date fin, Long idInst) {
		DateTime fecha1 = new DateTime(inicio);
		DateTime fecha2 = new DateTime(fin);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from reserva ");
			sb.append("where hora_inicio >= ? ");
			sb.append("and hora_fin <= ? ");
			sb.append("and instalacion_id = ? ");
			sb.append("and estado = '" + EstadoReserva.ACTIVA.name() + "'");
			sb.append("order by hora_inicio");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, new Timestamp(fecha1.getMillis()));
			ps.setTimestamp(2, new Timestamp(fecha2.getMillis()));
			ps.setLong(3, idInst);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));

				reservas.add(reserva);
			}
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static List<ReservaDao> obtenerMisReservasPorFecha(Date inicio, Date fin, Long user) {
		DateTime fecha1 = new DateTime(inicio);
		DateTime fecha2 = new DateTime(fin);
		CreadorConexionBBDD creador = new CreadorConexionBBDD();
		Connection con = creador.crearConexion();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("select * from reserva ");
			sb.append("where hora_inicio >= ? ");
			sb.append("and hora_fin <= ? ");
			sb.append("and usuario_id = ?");
			sb.append("order by hora_inicio");

			PreparedStatement ps = con.prepareStatement(sb.toString());
			ps.setTimestamp(1, new Timestamp(fecha1.getMillis()));
			ps.setTimestamp(2, new Timestamp(fecha2.getMillis()));
			ps.setLong(3, user);
			ResultSet rs = ps.executeQuery();
			List<ReservaDao> reservas = new ArrayList<>();
			while (rs.next()) {
				ReservaDao reserva = new ReservaDao();
				reserva.setIdRes(rs.getLong("ID"));
				reserva.setInicio(new DateTime(rs.getTimestamp("hora_inicio")));
				reserva.setFin(new DateTime(rs.getTimestamp("hora_fin")));
				Long instalacion_id = rs.getLong("instalacion_id");
				reserva.setIdInst(instalacion_id);
				reserva.setIdPago(rs.getLong("PAGO_ID"));
				reserva.setEstado(rs.getString("ESTADO"));
				reserva.setTipoRes(rs.getString("TIPO"));
				reserva.setIdUsu(rs.getLong("USUARIO_ID"));
				reserva.setIdAct(rs.getLong("ACTIVIDAD_ID"));
				reserva.setIdCurso(rs.getLong("CURSO_ID"));

				reservas.add(reserva);
			}
			con.close();
			return reservas;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	public static void insertarReservaCentroSemanal(List<DiasSemana> dias, DateTime date, DateTime date2, int duracion, Long idInst)
			throws ExcepcionReserva {
		if (dias.isEmpty())
			return;

		DateTime inicio = new DateTime(date);
		DateTime fin = new DateTime(date2);
		DateTime current = inicio;

		while (!current.plusHours(1).equals(fin)) {
			for (int i = 0; i < dias.size(); i++) {
				if (current.getDayOfWeek() == dias.get(i).ordinal() + 1) {
					ReservaDao reserva = new ReservaDao(TipoReserva.CENTRO, current, current.plusHours(duracion), idInst,
							null, null, null, null);
					insertarReservaAdmin(reserva);
				}
			}
			current = current.plusDays(1);

		}

	}
}
