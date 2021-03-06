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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

@Entity (name="auction")
@Table
@XmlRootElement
@NamedQueries({
    @NamedQuery (name= "auction.findByAll", query= "SELECT a FROM auction a"),
    @NamedQuery (name = "auction.findActive", query = "SELECT a FROM auction a WHERE a.expdate > :expdate "),
    @NamedQuery (name= "auction.findByAuctionid", query= "SELECT a FROM auction a WHERE a.auctionid  = :auctionid"),
    @NamedQuery (name = "auction.findByCreator", query = "SELECT a FROM auction a WHERE a.creator = :creator"),
    @NamedQuery (name = "auction.findByCreator", query = "SELECT a FROM auction a WHERE a.creator = :creator"),

})
public class Auction implements Serializable {

    public static final String findByAll = "auction.findByAll";
    public static final String findByAuctionid = "auction.findByAuctionid";
    public static final String findByCreator = "auction.findByCreator";
    public static final String findActive= "auction.findActive";
    public static final long serialVersionUID = 1L;
    
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name= "auctionid")
    @Id
    private Integer auctionid;
    
    @Column(name = "expdate")
    @Temporal(TemporalType.DATE)
    private Date expdate;
    
  //  @ManyToOne
   // @JoinColumn(name="creator", referencedColumnName="username")
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,25}$", message = "invalid username")
    @NotNull(message = "May not be empty")
    private String creator;
    
    @Column(name="title")
    private String title;
    
    @Column(name= "itemdescription")
    @NotNull (message = "May not be empty")
    private String itemdescription;
    
    @Column(name = "iteminfo")
    @NotNull (message = "May not be empty")
    private String iteminfo;
    
    @Column (name = "finita")
    private boolean finita;
    
    private String currentwinner;
    private String definitivewinner;
    
    
     public Auction() {
    }

    public Auction(Integer auctionid) {
        this.auctionid = auctionid;
    }

    public Auction(Integer auctionid, Date expdate, String creator, String title, String itemdescription, String iteminfo, boolean finita) {
        this.auctionid = auctionid;
        this.expdate = expdate;
        this.creator = creator;
        this.title = title;
        this.itemdescription = itemdescription;
        this.iteminfo = iteminfo;
        this.finita= finita;
    }
    
    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    } 
    
     public Date getExpdate() {
        return expdate;
    }

    public void setExpdate(Date expdate) {
        this.expdate = expdate;
    }
    
     public String getCreator() {
        return creator;
    }
     
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setCurrentwinner(String currentwinner) {
        this.currentwinner = currentwinner;
    }
     public String getCurrentwinner() {
        return currentwinner;
    }

    public void setDefinitivewinner(String definitivewinner) {
        this.definitivewinner = definitivewinner;
    }
    
    public String getDefinitivewinner() {
        return definitivewinner;
    }
    
     public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
     public String getItemdescription() {
        return itemdescription;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }
    
     public String getIteminfo() {
        return iteminfo;
    }

    public void setIteminfo(String iteminfo) {
        this.iteminfo = iteminfo;
    }
    
    
    public boolean getFinita() {
        return finita;
    }
    
    public void setFinita (boolean finita) {
        this.finita = finita;
    }
}