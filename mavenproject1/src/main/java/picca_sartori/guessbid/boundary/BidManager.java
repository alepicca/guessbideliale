package picca_sartori.guessbid.boundary;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
    
     public int createbid(Bid bid) { //Users user, Auction auction
       // if (user.getBalance() < 2) {
         //  return LegendaAsta.CREDITOINSUFF;
      //  }
       //  else{
      // 
         em.persist(bid);
       // user.setBalance(user.getBalance() -2); 
       //  }
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
    
    public List<Bid> findByAuction(int id) {
		List<Bid> bids = em.createQuery("SELECT * FROM bid WHERE b.auctionid=:auctionid")
				.setParameter("auctionid", id)
			    .getResultList();
		List<Bid> scomm= new ArrayList<Bid> ();
		for (Bid bid : bids) {
		 scomm.add(bid);
		}
		return scomm;
    }
    
    public List<Bid> findByUser(String username) {
		List<Bid> bids = em.createQuery("SELECT * FROM bid WHERE b.username=:username")
				.setParameter("username", username)
			    .getResultList();
		List<Bid> scomm= new ArrayList<Bid> ();
		for (Bid bid : bids) {
			scomm.add(bid);
		}
		return scomm;
	}
}
