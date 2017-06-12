/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 *
 * @author nmohamed
 */
public class GetMsgs {
    
    public static ArrayList<Msg> getMsgs () throws ClassNotFoundException, SQLException
    {
    ArrayList<Msg> msgs = new ArrayList<>();
     
     Class.forName("org.postgresql.Driver");
           Connection cn = DriverManager.getConnection("jdbc:postgresql://192.168.1.3:5432/Maintenance_jobs?user=eduweb&password=Madrid2016");
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery("select * from jobs where runfreq != 'disable'");//when equal nul does not return it need to make ISNULL()
        while(rs.next())
        {
            if(Objects.equals(rs.getString("type"),"3"))
            {
            Msg m = new Msg();
            if(rs.getTimestamp("lastrun")== null){
                m.setBody(rs.getString("message"));
                m.setTitle(rs.getString("msgtitle"));
                 m.setType(rs.getString("setting"));
                m.setSender(rs.getString("sender"));
                m.setJob_id(""+rs.getInt("id"));
                 msgs.add(m);
            }
            else{
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            Calendar cal = Calendar.getInstance();
//            Date dt2 = new DateAndTime().getCurrentDateTime();
//            Date d1 = null;
//            Date d2 = null;
//            d1 = dateFormat.parse(cal.getTime());
//            d2 = dateFormat.parse(rs.getTimestamp("lastrun"));
//            long diff = rs.getTimestamp("lastrun").getTime()- cal.getTime();
//            long diffDays = diff / (24 * 60 * 60 * 1000);
            DateTime startDate = new DateTime(rs.getTimestamp("lastrun"));
            DateTime endDate = new DateTime();
            Days d = Days.daysBetween(startDate, endDate);
            int days = d.getDays();
            if((Objects.equals(rs.getString("runfreq"),"daily") & days == 1) | (Objects.equals(rs.getString("runfreq"),"weekly") & days == 7) )
            {
                m.setBody(rs.getString("message"));
                m.setTitle(rs.getString("msgtitle"));
                m.setType(rs.getString("setting"));
                m.setSender(rs.getString("sender"));
                m.setJob_id(""+rs.getInt("id"));
                msgs.add(m);
            }
            }

            }
        }
    return msgs;
    }
}
