package picca_sartori.guessbid.gui;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.boundary.BidManager;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Bid;

public class BidBean implements Serializable{
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private BidManager bm;
   
    @EJB
    private UserManager um;
    
    @EJB
    private AuctionManager am;
    
    private Bid bid;
    
    public BidBean() {
    }
   
   public String createbid(){
        bm.create(bid);
      bid.setBidder(um.getLoggedUser());
      bid.setAuctionid(em.createNamedQuery(Auction.findByAuctionid, Auction.class).getSingleResult());
      um.getLoggedUser().setBalance(-2);
        return "/user/home";
    } 
   
   public Bid getBid() {
           if (bid== null) {
       bid = new Bid();
   }
       return bid;
   }
   
   public void setBid(Bid bid) {
       this.bid = bid;
   }
}
