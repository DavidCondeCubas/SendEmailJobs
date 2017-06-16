/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;

/**
 *
 * @author nmohamed
 */
public class ActivityLog {
    static Logger log = Logger.getLogger(ActivityLog.class.getName());
    public static void log (String job_id,String rw_event_id,String recipients,String body,Connection cn){
        try{
        Statement st = cn.createStatement();
       
        st.executeUpdate("insert into log(job_id,timestamp,rw_event_id,recipients,message) values('"+job_id+"',now(),'"+rw_event_id+"','"+recipients+"','"+body+"')");//add student id as well
        
        }
        catch(SQLException ex) {
           StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            log.error(ex+errors.toString());
        }
    }
    
}
