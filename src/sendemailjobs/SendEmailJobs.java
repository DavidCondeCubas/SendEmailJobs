/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;

import java.sql.SQLException;
import java.util.ArrayList;
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
        ArrayList<Msg> list = GetMsgs.getMsgs();
        ArrayList<Msg> finallist = RWEvents.getMsgs(list);
        SendMail(finallist);
    }
    
}
