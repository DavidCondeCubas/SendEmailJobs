/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;

import com.sun.mail.smtp.SMTPMessage;
import java.net.PasswordAuthentication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author nmohamed
 */
public class SendMail {
    public static void SendMail(ArrayList<Msg> finallist) {
        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.user", "nmohamed@eduwebgroup.com");
//        props.put("mail.password", "kokowawa1");
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", "discipline_alert@cas.ac.ma");
        props.put("mail.smtp.password", "USdiscipline18");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
//     
         Session session = Session.getDefaultInstance(props);
        try {
 for(Msg m:finallist){
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(m.getSender()));
            // put here if reciepient is not empty, incase the parent doe snot have an email on renweb
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("dconde.test@gmail.com"));//m.getRecipient()));
            message.setSubject(m.getTitle());
            message.setContent(m.getBody(), "text/html; charset=utf-8");
        //    message.setText(m.getBody());
           
 
//            Transport.send(message);
            session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com",587, "discipline_alert@cas.ac.ma", "USdiscipline18");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Sent message successfully....");
            Class.forName("org.postgresql.Driver");
           /* Connection cn = DriverManager.getConnection("jdbc:postgresql://192.168.1.3:5432/Maintenance_jobs?user=eduweb&password=Madrid2016");
        ActivityLog.log(m.getJob_id(),m.getRw_event_id(),m.getRecipient(),m.getBody(), cn);
        Statement st = cn.createStatement();
        st.executeUpdate("update jobs set lastrun = now() where id ="+m.getJob_id());*/
 }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }/* catch (SQLException ex) {
            Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
