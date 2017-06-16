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
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author nmohamed
 */
public class RWEvents {
     public static ArrayList<Msg> getMsgs (ArrayList<Msg> jobs) throws ClassNotFoundException, SQLException
    {
        
        ArrayList<Msg> msgs = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        try{
         for(Msg m:jobs){
             int type = 2;
            if(Objects.equals(m.getType(),"merit")){
                type = 1;
            }
            if(Objects.equals(m.getType(),"demerit")){
                type = 0;
            }
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection cn = DriverManager.getConnection("jdbc:sqlserver://deb-qat.odbc.renweb.com:1433;databaseName=deb_qat","DEB_QAT_CUST","UnderQuiet+227");
       
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery("select d.DisciplineID,s.FirstName as sfirstname,s.LastName as slastname ,d.StaffID,d.DateofIncident,d.DescriptionOfIncident,d.ReportedBy,p.firstname as pfirstname, p.lastname as plastname,p.Email,ps.parentid from discipline d inner join parent_student ps on d.StudentID = ps.StudentID inner join person p on p.personid = ps.parentid inner join Students s on s.StudentID = d.StudentID where ps.Custody = 1 and DateofIncident between '2017-06-01' and '2017-06-12' and d.type= "+type);
        while(rs.next())
        {
            Event e = new Event();
            e.setId(""+rs.getInt("DisciplineID"));
            e.setCreatedby(""+rs.getInt("StaffID"));
            e.setEventdate(""+rs.getDate("DateofIncident"));
            e.setEventdscrp(rs.getString("DescriptionOfIncident"));
            e.setReportedby(rs.getString("ReportedBy"));
//            e.setStudentid(""+rs.getInt("StudentID"));
            e.setRecipient(rs.getString("Email"));
            e.setRw_event_id(""+rs.getInt("DisciplineID"));
            e.setStudentname(rs.getString("sfirstname")+rs.getString("slastname"));
            events.add(e);
        }
 
        
        for(Event e:events){
          //   get the staff email incase the sender is creator
        if(m.getSender()=="creator"){
        // if not it will be the email inputted later no need to do anything
         ResultSet rs3 = st.executeQuery("select Email,FirstName,LastName from Person where PersonID = "+e.getCreatedby());
        while(rs3.next())
        {
           m.setSender(rs3.getString("Email"));
        }
           }
cn.close();
          Class.forName("org.postgresql.Driver");
            cn = DriverManager.getConnection("jdbc:postgresql://192.168.1.3:5432/Maintenance_jobs?user=eduweb&password=Madrid2016");
        Statement st2 = cn.createStatement();
            ResultSet rs2 = st2.executeQuery("select * from log where rw_event_id = '"+ e.getId()+"'");
            //formatting the emssages that will be sent that were not sent before
       if(!rs2.next())
        {
          Msg x = new Msg();
          x.setTitle(m.getTitle());
          String message =m.getBody();
          message = message.replaceAll("#Stud_fullName#",e.getStudentname());
          message = message.replaceAll("#description#",e.getEventdscrp());
          message = message.replaceAll("#date#", e.getEventdate());
          message = message.replaceAll("#Teacher_fullName#",e.getReportedby());
          x.setBody(message);
          x.setRecipient(e.getRecipient());
          x.setJob_id(m.getJob_id());
          x.setSender(m.getSender());
          x.setRw_event_id(e.getRw_event_id());
            msgs.add(x);
        }
       
        }
 
        }
        }
        catch (SQLException ex) {
     System.out.println("Error"+ex);
        }
        
        return msgs;
    }
}
