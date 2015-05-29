package picca_sartori.guessbid.gui;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.entity.Auction;

@ManagedBean
@ViewScoped
public class CreateAuctionBean implements Serializable {
    
    @EJB
    private AuctionManager am;
    
    @EJB
    private CheckModifications cm;
    
    private Auction auction;
    
    public CreateAuctionBean() {
    }
    
      public Auction getAuction() {
        if (auction == null) {
            auction = new Auction();
        }
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    
    public String createauction(String username){
        am.create(auction, username);
        return "/user/currentauction";
    }
    
    
}
