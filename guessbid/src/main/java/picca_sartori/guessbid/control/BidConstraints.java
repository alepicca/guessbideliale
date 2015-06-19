package picca_sartori.guessbid.control;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.entity.Bid;
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
      
      public void checkWinner(String username,int auctionid,int value) {
          Bid bid = (Bid) em.createNamedQuery(Bid.findtuttoproprio).setParameter("auctionid", auctionid).setParameter("amount", value).setParameter("bidder", username).getSingleResult();
         long conta = (long) (em.createNamedQuery(Bid.contascommesseasta).setParameter("auctionid", auctionid).getSingleResult());
      System.out.println(conta);
         if (conta == 1) { 
              bid.setWinning(true);
          }
         else  {
            Bid tempbid =(Bid) em.createNamedQuery(Bid.findWinningTrue).setParameter("winning", true).setParameter("auctionid", auctionid).getSingleResult();
            long uguali = (long)(em.createNamedQuery(Bid.contascommessestessoimporto).setParameter("amount", value).setParameter("auctionid", auctionid).getSingleResult());
            System.out.println(uguali);
            if(tempbid.getAmount()>bid.getAmount() && uguali == 1 ){
             tempbid.setWinning(false);
             bid.setWinning(true);
            }
            
            if(tempbid.getAmount()==bid.getAmount()){
                         long contatemp = (long) (em.createNamedQuery(Bid.contascommesseasta).setParameter("auctionid", auctionid).getSingleResult());
                         int maxamount = (int) (em.createNamedQuery(Bid.findMaxPerAuction).setParameter("auctionid", auctionid).getSingleResult());  
              //  tempbid.setWinning(false);
                int temp=value;
                do{
                    temp++;
                    if((long) em.createNamedQuery(Bid.contascommessestessoimporto).setParameter("amount", temp).setParameter("auctionid", auctionid).getSingleResult()== 1){
                       Bid nuovavin = (Bid) em.createNamedQuery(Bid.findByAmount).setParameter("auctionid", auctionid).setParameter("amount", temp).getSingleResult();
                       nuovavin.setWinning(true);
                       tempbid.setWinning(false);
                       break;
                    }
            } while(temp<maxamount);
            }
         }
      }
      
      }

