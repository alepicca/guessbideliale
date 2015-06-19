package picca_sartori.guessbid.boundary;

import java.security.Principal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Bid;
import picca_sartori.guessbid.entity.Notification;
import picca_sartori.guessbid.entity.Users;

@Stateless
public class NotificationManager {
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    Principal principal;
    
    @EJB
    UserManager um;

    @EJB
    BidManager bm;
    
     private void nonhannovinto(Auction asta, Users vincitore) {
        Query query = em.createNamedQuery("Bid.findPerdenti");
        query.setParameter("auctionid", asta);
        query.setParameter("bidder", vincitore);
         List<Users> results = query.getResultList();
        if (!results.isEmpty()) {
            for (Users bidder : results) {
                creanotifesito(asta, bidder, "Auction" + asta + " ended: the winner is "+ vincitore + "thanks for your participation");
            }
        }
    }
     
     private void creanotifesito(Auction asta, Users user, String message) {
        Notification notif = new Notification();
        notif.setMessage(message);
        notif.setAuctionid(asta);
        notif.setReceiver(user);
        notif.setNotiftype(Notification.Notiftype.ENDAUCTION);
        em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }
    }
    
     private void creanotifnessunvincitore(Auction asta) {
        Notification notif = new Notification();
        notif.setMessage("Sorry, nobody placed a bid for your auction");
        notif.setAuctionid(asta);
      //  notif.setReceiver(asta.getCreator()); passargli l'user e non la stringa
        notif.setNotiftype(Notification.Notiftype.ENDAUCTION);
        em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }
    }
     
     private void creatuttenotiffineasta(Auction auction, Users vincitore) { // capire dove richiamarlo quando finisce l'asta
        Bid winningBid = trovavincente(auction);

        if (winningBid == null) {
            creanotifnessunvincitore(auction);
            nonhannovinto(auction, vincitore);
        } else {
            Users creatoreasta = um.findByUsername(auction.getCreator());
            Users bidder = um.findByUsername(winningBid.getBidder().getUsername());

        //    updateCredit(seller, bidder, winningBid.getAmount());
       //     auction.setWinningBid(winningBid);      AGGIUNGERE AD AUCTION WINNINGBID E WINNER
          //  auction.setWinner (winningBid.getBidder())

          creanotifesito(auction, bidder, "Congratulations! You won this auction!");
         /*     generateOutcomeNotification(auction, seller, "Your auction has finished. The winning user is " + bidder.getEmail());
            generateLoserNotifications(auction, bidder);*/
        }       
    }
     
   private Bid trovavincente(Auction auction) {
        Bid winningBid = null;
        
        try { 
           Bid winbid = (Bid) em.createNamedQuery("Bid.findWinning").setParameter("bidder", auction.getDefinitivewinner()).setParameter("auctionid", auction.getAuctionid()).getSingleResult(); //capire se torna il primo risultato o uno qualsiasi
            winningBid = bm.findBidById(winbid.getBidid());
        } catch (Exception e) {
        }
    /*    if (winningBid != null) { //if winning bid exsists check if bidder has enough credit left
            Users bidder = um.findByUsername(winningBid.getBidder().getUsername());
            if (bidder.getCredit() < winningBid.getAmount()) { //gestione crediti
                generateOutcomeNotification(auction, bidder, "Insufficient credit");
                winningBid = findSuccessionalWinningBid(auction);
            }
        } */
        return winningBid;
    } 
     
     public Bid getActualWinning (Auction auction) {
         Bid actualwinning = null;
     //   contamin =  em.createNamedQuery("Bid.findNumMinBid").setParameter("auctionid", auction.getAuctionid());
       // conto quante puntate ci sono con il min amount, se è una è la vincente attuale, se no guardo quelle con amount> ecc..
         
         try {
           //  if (contamin == 1){
           //  auction.setCurrentwinner(/*astatrovata.getBidder() */ );
          //   } 
         } catch (Exception e) {
                            
                             }
         return actualwinning;
     }
     
     
     // per tutti gli users che hanno scommesso nell'asta: se cambia notifica il primo classificato è getActualWinning.bidder
}
