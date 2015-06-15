package picca_sartori.guessbid.boundary;

import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.LegendaAsta;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Bid;
import picca_sartori.guessbid.entity.Users;

@Stateless
public class BidManager {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    Principal principal;

    public Users getLoggedUser() {
        return em.find(Users.class, principal.getName());
    }
    
     public int create(Users user, Auction auction, int importo) {
         Bid bid = new Bid();
         if (user.getBalance() < 2) {
            return LegendaAsta.CREDITOINSUFF;
        }
         bid.setAmount(importo);
        bid.setBidder(user);
        bid.setAuctionid(auction);
         em.persist(bid);
         user.setBalance(-2);
         try {
            em.flush();
            //fare notifiche
            return LegendaAsta.SCOMMESSAFATTA;
        } catch (Exception e) {
            throw e;
        }  
    }
    
    public Bid findBidById(int id) {
        Bid b = em.createNamedQuery(Bid.findByBidid, Bid.class).setParameter("bidid", id).getSingleResult();
        return b;
    }
}
