package picca_sartori.guessbid.boundary;

import java.security.Principal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Users;
import picca_sartori.guessbid.gui.LoginBean;
import picca_sartori.guessbid.gui.UserBean;

@Stateless
public class AuctionManager {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    Principal principal;
    
    public Users getLoggedUser() {
        return em.find(Users.class, principal.getName());
    }
    
    public void create(Auction auction) {
        em.persist(auction);
       
    }
    
}