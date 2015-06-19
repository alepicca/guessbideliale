package picca_sartori.guessbid.boundary;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
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
    
    @Resource
    private TimerService timerService;
    
    List <Auction> astefinite = new ArrayList() ;
    
    @PostConstruct
    private void init() {
        ScheduleExpression amezzanotte = new ScheduleExpression().second("0").minute("0").hour("0");
        timerService.createCalendarTimer(amezzanotte, new TimerConfig("", false));
    }    
        
    @Timeout
      private void creatuttenotiffineasta() { 
       List <Auction> tutteleaste =  em.createNamedQuery("Auction.findAll").getResultList();
       
       for (Auction auction : tutteleaste){
          if (finita(auction) && !astefinite.contains(auction)){
          Bid winningBid = trovavincente(auction);
          astefinite.add(auction);
            if (winningBid == null) {
            creanotifnessunvincitore(auction);  
            }
            else {
         
            Users bidder = um.findByUsername(winningBid.getBidder());
            String vincitore = winningBid.getBidder();
            auction.setDefinitivewinner(vincitore);
            Users creatore = um.findByUsername(auction.getCreator());
            creatore.setBalance(creatore.getBalance() + winningBid.getAmount());
            nonhannovinto(auction,vincitore );
          creanotifesito(auction.getAuctionid(), bidder, "Congratulations! You won this auction: " + auction.getTitle());
        }       
          }
       }
    }
    
    
     private void nonhannovinto(Auction asta, String vincitore) {
        List<String> perdenti = em.createNamedQuery("Bid.findPerdenti").setParameter("auctionid", asta.getAuctionid()).setParameter("bidder", vincitore).getResultList();
        List <Bid> perdbid = em.createNamedQuery(Bid.findDefWinner).setParameter("auctionid", asta.getAuctionid()).setParameter("winning", false).getResultList();
        if (!perdenti.isEmpty()) {
            // dire ai perdenti che non hanno vinto (e chi ha vinto)
            for (String bidder : perdenti) {
                creanotifesito(asta.getAuctionid(), (Users)em.createNamedQuery(Users.findByUsername).setParameter("username", bidder).getSingleResult(), "Auction" + asta.getTitle() + " ended: the winner is "+ asta.getDefinitivewinner() + "thanks for your participation");
          
                //far tornare soldi a chi perde?
            }
            for  (Bid singola : perdbid) {
                Users puntatore = (Users)em.createNamedQuery(Users.findByUsername).setParameter("username", singola.getBidder()).getSingleResult();
                puntatore.setBalance(puntatore.getBalance() + singola.getAmount());
            }
        }
    }
     
     private void creanotifesito(Integer asta, Users user, String message) {
        Notification notif = new Notification();
        notif.setMessage(message);
        notif.setAuctionid(asta);
        notif.setReceiver(user.getUsername());
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
        notif.setAuctionid(asta.getAuctionid());
        notif.setReceiver(asta.getCreator()); 
        notif.setNotiftype(Notification.Notiftype.ENDAUCTION);
        em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }
    }
     
   
     
   private Bid trovavincente(Auction auction) {
        Bid winningBid = null;
        
        try { 
           Bid winningbid = (Bid) em.createNamedQuery(Bid.findDefWinner).setParameter("winning", true).setParameter("auctionid", auction.getAuctionid()).getSingleResult(); //capire se torna giusto
        } catch (Exception e) {
        }
        return winningBid;
    } 
     
 /*    public Bid getActualWinning (Auction auction) {
         Bid actualwinning = null;
     //   contamin =  em.createNamedQuery("Bid.findNumMinBid").setParameter("auctionid", auction.getAuctionid());
       // conto quante puntate ci sono con il min amount, se è una è la vincente attuale, se no guardo quelle con amount> ecc..
         
         try {
           //  if (contamin == 1){
           //  auction.setCurrentwinner(/*astatrovata.getBidder()  );
          //   } 
         } catch (Exception e) {
                            
                             }
         return actualwinning;
     }
     */
     
     // per tutti gli users che hanno scommesso nell'asta: se cambia notifica il primo classificato è getActualWinning.bidder

   public void creanotifparziale (String username, int auctionid,boolean vincente) {
       Notification notif = new Notification();
       notif.setAuctionid(auctionid);
       notif.setReceiver(username);
       notif.setNotiftype(Notification.Notiftype.CHANGEPOSITION);
       Auction titolo= (Auction) em.createNamedQuery(Auction.findByAuctionid).setParameter("auctionid", auctionid).getSingleResult();
       if(vincente){
       notif.setMessage("You are the current winner for the auction: "+ titolo.getTitle());
       }else{
       notif.setMessage("You aren't the current winner for the auction: "+ titolo.getTitle());
       }
       em.persist(notif);
        try {
            em.flush();
        } catch (Exception e) {
            throw e;
        }
   }
   
   public boolean finita (Auction asta) {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0); 
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date today = c.getTime();
        if (asta.getExpdate().after(today)) {
            return false;
        }
        return true;
    }
   
}
