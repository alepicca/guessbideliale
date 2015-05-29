package picca_sartori.guessbid.control;

//import picca_sartori.guessbid.entity.Auction;
//creare entità auction
import picca_sartori.guessbid.entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
/**
 *
 * @author ale
 */

@Stateless
public class CheckModifications {
    
    EntityManager em;
    
    
   
    //no doppioni username nel db: ritorna vero
    public boolean checkDoubleUsername(String username){
           List<Users> result = em.createNamedQuery(Users.findByUsername, Users.class)
                  .setParameter("username", username)
                   .getResultList();
            return result.isEmpty()==true;
    }
    //user corripondente al pattern definito: ritorna vero
    public boolean checkCorrectUsername(String username){
             return username.matches("^[a-z0-9A-Z_]{5,25}$");
    }
    //no doppioni email nel db: ritorna vero
    public boolean checkDoubleEmail (String email) {
         List<Users> result = em.createNamedQuery(Users.findByEmail, Users.class)
                  .setParameter("email", email)
                   .getResultList();
            return result.isEmpty()==true;
    }
    //mail corrispondente al pattern definito: ritorna vero
    public boolean checkCorrectEmail(String email){
             return email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$");
    }
    //password e password confermata sono uguali: ritorna vero
    public boolean  equalsPassword(String pwd1, String pwd2) {
        return pwd1.equals(pwd2);
    }
    
     public boolean checkCorrectAge(String age){
             return age.matches("^[0-9]*${2,3})$");
    }
    
  /*  public boolean checkDoubleAuctionName(String auctionname){
        List<Auction> result = em.createNamedQuery(Auction.findByAuctionName, Auction.class)
                  .setParameter("auctionname", auctionname)
                   .getResultList();
            return result.isEmpty()==true;
    }
    SISTEMARE METODO (PASSAGGIO DA EVENT A AUCTION
    */ 
}

