/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.saljex.sxserver;

import java.sql.Connection;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;

/**
 *
 * @author Ulf
 */
@Stateless
@LocalBean
public class TimerForUnderhall {
	@javax.annotation.Resource(name = "sxadm")
	private javax.sql.DataSource sxadm;
	
	@Schedule(dayOfWeek="1-7", hour="3")
	private void daily() {
		Connection con = null;
		try { 
			ServerUtil.log("TimerForUnderhall.daily() startad");
			con = sxadm.getConnection();
			con.createStatement().executeUpdate("select TimerDaily();");
			ServerUtil.log("TimerForUnderhall.daily() slutförd");
		} catch (SQLException e) { ServerUtil.log("Fel vid TimerForUnderhall.daily: " + e.getMessage());
			
		}finally {
			try { con.close(); } catch (Exception ee) {}
		}
	}
	
	@Schedule(dayOfWeek="Sun", hour="3")
	private void weeklySunday() {
		Connection con = null;
		try {
			ServerUtil.log("TimerForUnderhall.weekly() startad");
			con = sxadm.getConnection();
			con.createStatement().executeUpdate("select TimerWeekly();");			
			ServerUtil.log("TimerForUnderhall.weekly() slutförd");
		} catch (SQLException e) { ServerUtil.log("Fel vid TimerForUnderhall.errkly: " + e.getMessage());
			
		}finally {
			try { con.close(); } catch (Exception ee) {}
		}
		
	}
	
	@Schedule(dayOfMonth="1", hour="3")
	private void monthly() {
		Connection con = null;
		try {
			ServerUtil.log("TimerForUnderhall.monthly1() startad");
			con = sxadm.getConnection();
			con.createStatement().executeUpdate("select TimerMonthly();");			
			ServerUtil.log("TimerForUnderhall.monthly() slutförd");
		} catch (SQLException e) { ServerUtil.log("Fel vid TimerForUnderhall.maonthly: " + e.getMessage());
			
		}finally {
			try { con.close(); } catch (Exception ee) {}
		}
		
	}

	
	
}
