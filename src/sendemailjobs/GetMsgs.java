/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

    public static ArrayList<Msg> getMsgs(String maxID_Discipline) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        ArrayList<Msg> msgs = new ArrayList<>();

        String query = "select DisciplineID,DateofIncident,DescriptionOfIncident,History,reportedID,Sanction1,Sanction2,SanctionDate1,SanctionDate2,Violation,ReportedBy,ReviewedBy,Status,b.StudentID,Demerits,\"Level\",\"Type\",\n" +
"       b.ClassID,b.CourseID,b.\"Name\",b.\"Section\",b.PersonID,b.LastName,b.FirstName,b.Email,b.Email2,studentFirstName,studentLastName,person.FirstName as reportedFirstName,person.LastName as reportedLastName\n" +
" from person right join\n" +
"(select DisciplineID,DateofIncident,DescriptionOfIncident,History,reportedID,Sanction1,Sanction2,SanctionDate1,SanctionDate2,Violation,ReportedBy,ReviewedBy,Status,a.StudentID,Demerits,\"Level\",\"Type\",\n" +
"       a.ClassID,a.CourseID,a.\"Name\",a.\"Section\",a.PersonID,a.LastName,a.FirstName,a.Email,a.Email2,person.FirstName as studentFirstName,person.LastName as studentLastName\n" +
"\n" +
" from (SELECT DisciplineID,DateofIncident,DescriptionOfIncident,History,Sanction1,Sanction2,SanctionDate1,SanctionDate2,Violation,ReportedBy,ReviewedBy,Status,Discipline.StaffID as reportedID,Discipline.StudentID,Demerits,\"Level\",\"Type\",\n" +
"       Classes.ClassID,Classes.CourseID,Classes.\"Name\",Classes.\"Section\",Person.PersonID,Person.LastName,Person.FirstName,Person.Email,Person.Email2 FROM Discipline inner join roster on (Discipline.studentID = roster.StudentID) \n" +
"                          inner join classes on roster.classID = classes.classID inner join person on classes.StaffID = person.PersonID\n" +
"                          where roster.Enrolled = 1 and (Discipline.DisciplineID > "+maxID_Discipline+") and classes.YearID in (select distinct DefaultYearID from Roster inner join classes on classes.ClassID = Roster.ClassID \n" +
"                                   inner join Courses  on Courses.CourseID = Classes.CourseID \n" +
"                                   inner join ConfigSchool  on ConfigSchool.SchoolCode = Courses.SchoolCode\n" +
"                                   where Roster.StudentID = roster.StudentID) and \n" +
"    ( \n" +
"        (Term1=1 and '1' in (Select DefaultTermID from ConfigSchool where ConfigSchool.SchoolCode in (select distinct schoolcode \n" +
"                from classes inner join roster on classes.ClassID = roster.classid \n" +
"                             inner join courses on courses.CourseID = classes.CourseID \n" +
"                            where StudentID = Roster.StudentID and Classes.YearID in (select DefaultYearID from ConfigSchool where courses.SchoolCode = ConfigSchool.SchoolCode))))\n" +
"        or\n" +
"        (Term2=1 and '2' in (Select DefaultTermID from ConfigSchool where ConfigSchool.SchoolCode in (select distinct schoolcode \n" +
"                from classes inner join roster on classes.ClassID = roster.classid \n" +
"                             inner join courses on courses.CourseID = classes.CourseID \n" +
"                            where StudentID = Roster.StudentID and Classes.YearID in (select DefaultYearID from ConfigSchool where courses.SchoolCode = ConfigSchool.SchoolCode))))\n" +
"        or\n" +
"        (Term3=1 and '3' in (Select DefaultTermID from ConfigSchool where ConfigSchool.SchoolCode in (select distinct schoolcode \n" +
"                from classes inner join roster on classes.ClassID = roster.classid \n" +
"                             inner join courses on courses.CourseID = classes.CourseID \n" +
"                            where StudentID = Roster.StudentID and Classes.YearID in (select DefaultYearID from ConfigSchool where courses.SchoolCode = ConfigSchool.SchoolCode))))\n" +
"        or\n" +
"        (Term4=1 and '4' in (Select DefaultTermID from ConfigSchool where ConfigSchool.SchoolCode in (select distinct schoolcode \n" +
"                from classes inner join roster on classes.ClassID = roster.classid \n" +
"                             inner join courses on courses.CourseID = classes.CourseID \n" +
"                            where StudentID = Roster.StudentID and Classes.YearID in (select DefaultYearID from ConfigSchool where courses.SchoolCode = ConfigSchool.SchoolCode))))\n" +
"        or\n" +
"        (Term5=1 and '5' in (Select DefaultTermID from ConfigSchool where ConfigSchool.SchoolCode in (select distinct schoolcode \n" +
"                from classes inner join roster on classes.ClassID = roster.classid \n" +
"                             inner join courses on courses.CourseID = classes.CourseID \n" +
"                            where StudentID = Roster.StudentID and Classes.YearID in (select DefaultYearID from ConfigSchool where courses.SchoolCode = ConfigSchool.SchoolCode))))\n" +
"        or\n" +
"        (Term6=1 and '6' in (Select DefaultTermID from ConfigSchool where ConfigSchool.SchoolCode in (select distinct schoolcode \n" +
"                from classes inner join roster on classes.ClassID = roster.classid \n" +
"                             inner join courses on courses.CourseID = classes.CourseID \n" +
"                            where StudentID = Roster.StudentID and Classes.YearID in (select DefaultYearID from ConfigSchool where courses.SchoolCode = ConfigSchool.SchoolCode))))\n" +
"    )\n" +
"   )a inner join person on a.StudentID = person.PersonID) b on  b.reportedID = person.PersonID;";
               
        
     
        String connectionUrl = "jdbc:sqlserver://cb-mar.odbc.renweb.com:1433;databaseName=cb_mar;user=cb_mar_cust;password=WhiskerHurry+550";
        int maxAuxID = Integer.parseInt(maxID_Discipline);
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            ResultSet rs = stmt.executeQuery(query);
            // public Msg(String title, String body, String sender, String recip, String type, String rw_event_id, String job_id) constructora
            while (rs.next()) {
                if(maxAuxID < rs.getInt("DisciplineID"))
                    maxAuxID = rs.getInt("DisciplineID");
                
                String bodyHtml="<html><body>";
                bodyHtml += "<Strong>Student: </Strong>"+rs.getString("studentLastName")+", "+rs.getString("studentFirstName")+"<br>";
                bodyHtml += "<Strong>Class: </Strong>"+rs.getString("Name")+"-"+rs.getString("Section")+"<br>";
                bodyHtml += "<Strong>Report by: </Strong>"+rs.getString("reportedLastName")+", "+rs.getString("reportedFirstName")+"<br>";
                bodyHtml += "<Strong>Date of incident: </Strong>"+rs.getString("DateofIncident")+"<br>";
                bodyHtml += "<Strong>Description of incident: </Strong>"+rs.getString("DescriptionOfIncident")+"<br>";
                
                if(rs.getString("Sanction1") != null && !rs.getString("Sanction1").equals(""))
                    bodyHtml += "<Strong>Sanction 1: </Strong>"+rs.getString("Sanction1")+"<br>";
                
                if(rs.getString("Sanction2") != null && !rs.getString("Sanction2").equals(""))
                    bodyHtml += "<Strong>Sanction 2: </Strong>"+rs.getString("Sanction2")+"<br>";
                
                if(rs.getString("SanctionDate1") != null && !rs.getString("SanctionDate1").equals(""))
                    bodyHtml += "<Strong>SanctionDate 1: </Strong>"+rs.getString("SanctionDate1")+"<br>";
                
                if(rs.getString("SanctionDate2") != null && !rs.getString("SanctionDate2").equals(""))
                    bodyHtml += "<Strong>SanctionDate 2: </Strong>"+rs.getString("SanctionDate2")+"<br>";
                      
                bodyHtml += "<Strong>Violation: </Strong>"+rs.getString("Violation")+"<br>";               
                bodyHtml += "<Strong>Teacher email: </Strong>"+rs.getString("Email")+"<br>"; //solo testing
                
                String auxType = rs.getString("Type");
                if(auxType.equals("0")){ // demerits
                    bodyHtml += "<Strong>Demerits: </Strong>"+rs.getString("Demerits")+"<br>";
                }
                else{
                    bodyHtml += "<Strong>Merits: </Strong>"+rs.getString("Demerits")+"<br>";
                }
                bodyHtml += "<Strong>Level: </Strong>"+rs.getInt("Level")+"<br>";
                bodyHtml += "<img src=\"http://www.cas.ac.ma/custom/images/logo.png\" alt=\"Casablanca American School\">";
                bodyHtml += "<body><html>";
                msgs.add(new Msg("Student behavior alert in renweb",bodyHtml,"eduWebGroup","dconde@eduwebgroup.com","Behavior","",""));
                
                System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
            }
            
            PrintWriter out = new PrintWriter(new FileWriter("last_disciplineID.txt"));
            out.println(Integer.toString(maxAuxID));
          //  out.println("4556");
            out.close();
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        /*
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         
        Connection cn = DriverManager.getConnection("jdbc:sqlserver://cb-mar.odbc.renweb.com\\cb_mar?user=cb_mar_cust&password=WhiskerHurry+550");
        //Connection cn = DriverManager.getConnection("jdbc:postgresql://192.168.1.3:5432/Maintenance_jobs?user=eduweb&password=Madrid2016");
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(query);//when equal nul does not return it need to make ISNULL()
        while(rs.next())
        {
            /*if(Objects.equals(rs.getString("type"),"3"))
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

            }*/
       
        return msgs;
    }
}
