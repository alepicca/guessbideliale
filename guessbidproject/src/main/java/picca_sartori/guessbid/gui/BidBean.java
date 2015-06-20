package picca_sartori.guessbid.gui;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.boundary.BidManager;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Bid;
import picca_sartori.guessbid.entity.Users;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import picca_sartori.guessbid.LegendaAsta;
import picca_sartori.guessbid.control.BidConstraints;

@ManagedBean
@ApplicationScoped
public class BidBean implements Serializable{
    
    @PersistenceContext
    EntityManager em;
    
    @ManagedProperty(value = "#{AuctionBean}")
    private AuctionBean ab;
    
    public void setAb(AuctionBean ab) {
        this.ab = ab;
    }
    
    @EJB
    private BidConstraints bcs;
    
    @EJB
    private BidManager bm;
   
    @EJB
    private UserManager um;
    
    @EJB
    private AuctionManager am;
    
    private Bid bid;
    private int amount,auctionid;
    private String message;
    
    public BidBean() {
    }
   
    public Integer getAuctionid() {
       return auctionid;
   }
    
   public Bid getBid() {
           if (bid== null) {
       bid = new Bid();
   }
       return bid;
   }
   
   public void setBid(Bid bid) {
       this.bid = bid;
   }
   
   public int getAmount() {
       return amount;
   }
   
   public void setMessage(String message) {
       this.message = message;
   }
   
   public String getMessage() {
       return message;
   }
   
   public String scommetti (String user, Integer idasta, Integer importo) {
     //  FacesContext context = FacesContext.getCurrentInstance();
       Auction asta = (Auction) em.createNamedQuery(Auction.findByAuctionid).setParameter("auctionid", idasta).getSingleResult();
       if (!bcs.equalsBidderCreator(user, asta.getCreator()) && bcs.checkAmount(user, importo,asta.getAuctionid())) {
       Bid scommessa = new Bid();
       scommessa.setAuctionid(idasta);
        scommessa.setBidder(user);
        scommessa.setAmount(importo);
        
        try {
            int puntata = bm.createbid(scommessa); 
            message=" Last Bid Succesfully Placed!!!";
            bcs.checkWinner(user,idasta,importo);
         /*  if (puntata == LegendaAsta.SCOMMESSAFATTA) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bid +" bid succesfully placed", "Placed bid"));
            } else if (puntata == LegendaAsta.CREDITOINSUFF) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Insufficient credit", "Insufficient credit"));
            }
*/
        } catch (NumberFormatException e) {
         //   context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The input is not a number", "NaN"));
        }
       }
       else {
           message=" Error in placing the last Bid!!! Remember you can not bid for auctions created by yourself or you don't have enough credits or you have already place a bid with the same import";
           // mex "non puoi scommettere!" FARGLIELO CAGARE FUORI IN QUALCHE MODO
       }
       return "home.xhtml";
    }
   
    public Users findByUsername(String username) {
        Users u = em.createNamedQuery(Users.findByUsername, Users.class).setParameter("username", username).getSingleResult();
        return u;
    }
   }

