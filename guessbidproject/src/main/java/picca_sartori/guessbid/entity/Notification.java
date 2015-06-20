package picca_sartori.guessbid.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@Entity (name="notification")
@Table
@XmlRootElement
@NamedQueries({
    @NamedQuery (name= "notification.findByAll", query= "SELECT n FROM notification n"),
    @NamedQuery (name = "notification.findByNotiftype", query = "SELECT n FROM notification n WHERE n.notiftype = :notiftype "),
    @NamedQuery (name= "notification.findByAuctionid", query= "SELECT n FROM notification n WHERE n.auctionid  = :auctionid"),
    @NamedQuery (name = "notification.findByReceiver", query = "SELECT n FROM notification n WHERE n.receiver = :receiver"),
    @NamedQuery (name = "notification.findByCategory", query = "SELECT n FROM notification n WHERE n.receiver = :receiver and n.notiftype = :notiftype"),
    @NamedQuery (name= "notification.findByNotifid", query= "SELECT n FROM notification n WHERE n.notifid  = :notifid"),
    @NamedQuery (name= "notification.deleteParziali",query= " DELETE FROM notification n WHERE n.auctionid = :auctionid and n.receiver = :receiver and n.notiftype = :notiftype" ),

})

public class Notification implements Serializable {
  
    public static final String findByAll = "notification.findByAll";
    public static final String findByAuctionid = "notification.findByAuctionid";
    public static final String findByReceiver = "notification.findByReceiver";
    public static final String findByNotifid= "notification.findByNotifid";
    public static final String findByCategory= "notification.findByCategory";
    public static final String findByNotiftype= "notification.findByNotiftype";
    public static final String deleteParziali= "notification.deleteParziali";
    
    public static final long serialVersionUID = 1L;

    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name= "notifid")
    @Id
    private Integer notifid;
            
    @Column(name="message")
    private String message;
    
    @Column(name="notiftype")
    private Notiftype notiftype;
    
    // @ManyToOne
    // @JoinColumn(name="auctionid", referencedColumnName="auctionid")
    @NotNull
    private Integer auctionid;
            
    // @ManyToOne
    // @JoinColumn(name="receiver", referencedColumnName="username")
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,25}$", message = "invalid username")
    @NotNull(message = "May not be empty")
    private String receiver;
 
     public Notification() {
    }

    public Notification(Integer notifid) {
        this.notifid = notifid;
    }

    public Notification(Integer auctionid, Integer notifid, String receiver, String message, Notiftype notiftype) {
        this.auctionid = auctionid;
        this.notifid = notifid;
        this.receiver = receiver;
        this.message = message;
        this.notiftype = notiftype;
    }
    
    public Integer getNotifid() {
        return notifid;
    }

    public void setNotifid(Integer notifid) {
        this.notifid = notifid;
    } 
    
    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    } 
    
     public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message= message;
    }
    
    public Notiftype getNotiftype() {
        return notiftype;
    }
    
    public void setNotiftype (Notiftype notiftype) {
        this.notiftype= notiftype;
    }
     
    public enum Notiftype {
        ENDAUCTION,
        CHANGEPOSITION
    }
    
}
