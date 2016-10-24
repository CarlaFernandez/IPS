package CapaNegocio.managers;

import java.sql.Timestamp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class ManagerFechas {
		public static Timestamp convertirATimestampSql(DateTime fecha){
			return new Timestamp(fecha.getMillis());
		}
		
		public static DateTime[] obtenerLunesYDomingoDeSemana(DateTime dateTime){
			LocalDate today = dateTime.toLocalDate();

			// Go backward to get Monday
			LocalDate monday = today;
			while (monday.dayOfWeek().get() != DateTimeConstants.MONDAY) {
				monday = monday.minusDays(1);
			}

			// Go forward to get Sunday
			LocalDate sunday = today;
			while (sunday.dayOfWeek().get() != DateTimeConstants.SUNDAY) {
				sunday = sunday.plusDays(1);
			}
			
			DateTime mondayDate = monday.toDateTimeAtStartOfDay();
			@SuppressWarnings("deprecation")
			DateTime sundayDate = sunday.toDateTimeAtMidnight();
			return new DateTime[]{mondayDate, sundayDate};
		}
		
		public static String formatearFecha(DateTime fecha){
			StringBuilder sb = new StringBuilder();
			sb.append(fecha.getYear()).append("-");
			sb.append(fecha.getMonthOfYear()).append("-");
			sb.append(fecha.getDayOfMonth()).append(" ");
			
			sb.append(fecha.getHourOfDay()).append(":");
			sb.append(fecha.getMinuteOfHour()).append(":");
			sb.append(fecha.getSecondOfMinute());
			
			return sb.toString();
		}
}