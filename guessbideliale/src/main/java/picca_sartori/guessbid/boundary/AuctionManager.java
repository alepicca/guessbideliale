package picca_sartori.guessbid.boundary;

import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Users;
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
    
    public void create(Auction auction, String username) {
     //   Users creator = em.find(Users.class, username);
        auction.setCreator(username);
      //  Users creatore = em.find(Users.class, username);
        em.persist(auction);
        //rivedere sto metodo in modo che setti il nome del creatore 
    }
}
