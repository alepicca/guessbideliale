package picca_sartori.guessbid.boundary;

import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    
     public void create(Bid bid) {
        em.persist(bid);
    }
    
    public Bid findBidById(int id) {
        Bid b = em.createNamedQuery(Bid.findByBidid, Bid.class).setParameter("bidid", id).getSingleResult();
        return b;
    }
}
