package picca_sartori.guessbid.entity;

import java.io.Serializable;
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

@Entity (name="bid")
@Table
@XmlRootElement
@NamedQueries({
    @NamedQuery (name= "bid.findByAll", query= "SELECT b FROM bid b"),
    @NamedQuery (name = "bid.findByAuctionid", query = "SELECT b FROM bid b WHERE b.auctionid = :auctionid "),
    @NamedQuery (name= "bid.findByBidid", query= "SELECT b FROM bid b WHERE b.bidid  = :bidid"),
    @NamedQuery (name = "bid.findByBidder", query = "SELECT b FROM bid b WHERE b.bidder = :bidder"),
    @NamedQuery(name = "bid.findPerdenti", query = "SELECT DISTINCT(b.bidder) FROM bid b WHERE b.auctionid = :auctionid and b.bidder != :bidder "),
    @NamedQuery(name="bid.findHistorical", query = "SELECT DISTINCT a FROM bid b JOIN auction a on b.auctionid = a.auctionid WHERE b.bidder = :bidder"),  //and date = finished
    @NamedQuery (name = "bid.findWinning", query = "SELECT b FROM bid b WHERE b.bidder = :bidder and b.auctionid = :auctionid ORDER BY b.amount"),
    @NamedQuery (name = "bid.findNumMinBid", query = "SELECT b.bidid, min(b.amount) FROM bid b WHERE b.auctionid = :auctionid"),
    @NamedQuery (name = "bid.contascommesseasta", query = "SELECT COUNT (b) FROM bid b WHERE b.auctionid = :auctionid"),
    @NamedQuery (name= "bid.findtuttoproprio",query= "SELECT b FROM bid b where b.auctionid = :auctionid and b.amount = :amount and b.bidder = :bidder"),
    @NamedQuery ( name ="bid.findDefWinner", query= "SELECT b FROM bid b where b.auctionid = :auctionid and b.winning= :winning"),
    @NamedQuery (name= "bid.quantepiubuone", query = "SELECT COUNT (b) FROM bid b WHERE b.auctionid = :auctionid AND b.amount < :amount"),
    @NamedQuery (name ="bid.findWinningTrue", query= "SELECT b FROM bid b where b.winning = :winning and b.auctionid= :auctionid"),
    @NamedQuery (name = "bid.contascommessestessoimporto", query = "SELECT COUNT (b) FROM bid b WHERE b.auctionid = :auctionid and b.amount =:amount"),
    @NamedQuery (name = "bid.findByAmount", query = "SELECT  b FROM bid b WHERE b.auctionid = :auctionid and b.amount =:amount"),
    @NamedQuery (name = "bid.findMaxPerAuction", query ="SELECT max(b.amount) FROM bid b WHERE b.auctionid =:auctionid"),
})
public class Bid implements Serializable{
    
    public static final String findByAll = "bid.findByAll";
    public static final String findByAuctionid = "bid.findByAuctionid";
    public static final String findByBidder = "bid.findByBidder";
    public static final String findByBidid= "bid.findByBidid";
    public static final String findPerdenti= "bid.findPerdenti";
    public static final String findHistorical= "bid.findHistorical";
    public static final String findWinning= "bid.findWinning";
    public static final String findNumMinBid= "bid.findNumMinBid";
    public static final String contascommesseasta="bid.contascommesseasta";
    public static final String findtuttoproprio = "bid.findtuttoproprio";
    public static final String findDefWinner ="bid.findDefWinner";
    public static final String findWinningTrue = "bid.findWinningTrue";
    public static final String contascommessestessoimporto = "bid.contascommessestessoimporto";
    public static final String findByAmount = "bid.findByAmount";
    public static final String findMaxPerAuction = "bid.findMaxPerAuction";
    public static final long serialVersionUID = 1L;
    
    
    @NotNull
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name= "bidid")
    @Id
    private Integer bidid;
    
    @Column(name = "amount")
    private int amount;
    
   // @ManyToOne
   // @JoinColumn(name="bidder", referencedColumnName="username")
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,25}$", message = "invalid username")
    @NotNull(message = "May not be empty")
    private String bidder;
    
   // @ManyToOne
   // @JoinColumn(name="auctionid", referencedColumnName="auctionid")
    @NotNull(message = "May not be empty")
    private Integer auctionid;
    
    @Column (name= "WINNING")
    private boolean winning;
    
    public Bid() {
    }

    public Bid(Integer bidid) {
        this.bidid = bidid;
    }

    public Bid(Integer auctionid, Integer bidid, String bidder, int amount) {
        this.auctionid = auctionid;
        this.bidid = bidid;
        this.bidder = bidder;
        this.amount = amount;
    }
    
   
    
    
    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    } 
    
    public Integer getBidid() {
        return bidid;
    }

    public void setBidid(Integer bidid) {
        this.bidid = bidid;
    } 
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    } 
    
    public String getBidder() {
        return bidder;
    }

    public void setBidder(String bidder) {
        this.bidder = bidder;
    }
    
    public boolean getWinning () {
        return winning;
    }
    
    public void setWinning (boolean winning) {
        this.winning = winning;
    }
}
