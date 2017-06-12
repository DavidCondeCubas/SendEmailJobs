/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendemailjobs;

/**
 *
 * @author nmohamed
 */
public class Event {
    private String id;
    private String studentid;
    private String type;// merit or demerit 1 = merit ,0 = Demerit, -1 not entered
    private String eventdscrp;
    private String eventdate;
    private String reportedby;//equivalent to column reportedby
    private String createdby;//equivalent to column staffid(incase the sender will be the creator)
    private String studentname;
    private String recipient;
    private String rw_event_id;

    public String getRw_event_id() {
        return rw_event_id;
    }

    public void setRw_event_id(String rw_event_id) {
        this.rw_event_id = rw_event_id;
    }
    
    
    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventdscrp() {
        return eventdscrp;
    }

    public void setEventdscrp(String eventdscrp) {
        this.eventdscrp = eventdscrp;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getReportedby() {
        return reportedby;
    }

    public void setReportedby(String reportedby) {
        this.reportedby = reportedby;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    
    
}
