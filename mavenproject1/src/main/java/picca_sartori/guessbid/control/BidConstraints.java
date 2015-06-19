package picca_sartori.guessbid.control;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.entity.Users;

@Stateless
public class BidConstraints {
    
    @PersistenceContext
    private EntityManager em;
    
    // se chi scommette è l'utente che ha creato l'asta torna vero
     public boolean equalsBidderCreator(String bidder, String creator) {
        return bidder.equals(creator);
    }
     
     // se ha più di 2 crediti torna vero (e quindi può scommettere)
      public boolean checkAmount (String username, int importo) {
        Users result = em.createNamedQuery(Users.findByUsername, Users.class)
                  .setParameter("username", username).getSingleResult();
        if (result.getBalance() > (2+ importo)) {
            result.setBalance(result.getBalance() - importo -2);
        return  true;
        }
        return false;
    }
}
