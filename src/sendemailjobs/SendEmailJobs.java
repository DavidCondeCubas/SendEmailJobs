/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import static sendemailjobs.SendMail.SendMail;

/**
 *
 * @author nmohamed
 */
public class SendEmailJobs {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try{
            FileReader fr
                    = new FileReader("last_disciplineID.txt");

            int i;
            String maxID_Discipline ="";

            while ((i = fr.read()) != -1) {
              //  System.out.print((char) i);
                maxID_Discipline += (char) i;
            }
            maxID_Discipline = maxID_Discipline.trim();
            
            ArrayList<Msg> list = GetMsgs.getMsgs(maxID_Discipline);
           // ArrayList<Msg> finallist = RWEvents.getMsgs(list);
           /* ArrayList<Msg> finallist = new ArrayList<>();
            Msg aux = new Msg();
            aux.setBody("Este mensaje va dirigido a yconde@ucm.es");
            aux.setRecipient("yconde@ucm.es");
            aux.setTitle("Este es el titulo de prueba");
            aux.setSender("ProfesorAlex");
            aux.setType("Behavior");
            aux.setRw_event_id("123456");
            finallist.add(aux);*/
            SendMail(list);
          
        }
        catch(Exception e){
            System.err.println("");
        }
    }
    
}
