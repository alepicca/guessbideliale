package picca_sartori.guessbid.gui;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.entity.Auction;

@Named
@SessionScoped
public class CreateAuctionBean implements Serializable {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private AuctionManager am;
    
    @EJB
    private CheckModifications cm;
   
    @EJB
    private UserManager um;
    
    private static Map<String, String> mappa;
    private Auction auction, selectedauction;
    int auctionid;
    private List <Auction> tutteleastevalide; //em.createNamedQuery(Auction.findActive).getResultList();  
    
    public CreateAuctionBean() {
    }
    
    public void init() {
         tutteleastevalide = new ArrayList<>();
        try {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                String s = request.getParameter("e");
                if(s!= null){
                    mappa.put(this.um.getLoggedUser().getUsername(), s);
                }
                else{
                    s=mappa.get(this.um.getLoggedUser().getUsername());
                }

                int id = Integer.parseInt(s);
                auction = am.findAuctionById(id);
            } catch (Exception e) {
//                FacesContext context;
//                context = FacesContext.getCurrentInstance();
//                context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred"));
            } 
    /*    Date date = new Date();
        
    for (auctionid=1; auctionid<tutteleastevalide.size(); auctionid ++) {
        if (date.before(auction.getExpdate())) {
            tutteleastevalide.add(auction);
        }
               }
         */
    }

    
    
      public Auction getAuction() {
        if (auction == null) {
            auction = new Auction();
        }
        return auction;
    }
      
      public Auction getSelectedauction () {
          return selectedauction;
      }
      
      public void setSelectedauction (Auction selectedauction) {
          this.selectedauction = selectedauction;
      }

      public List<Auction> getTutteleastevalide() {
          List<Auction> tutteleastevalide = em.createNamedQuery(Auction.findByAll).getResultList(); // modifico query
          return tutteleastevalide;
      }
      
      public void setTutteleastevalide (List<Auction> tutteleastevalide) {
          this.tutteleastevalide = tutteleastevalide;
      }
      
    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    
    public String createauction(){
        am.create(auction);
      //  auction.setCreator(lb.getCredenziale());
        return "/user/home";
    }
    
    public String submit(){
        tutteleastevalide = em.createNamedQuery(Auction.findByAll).getResultList(); //moodifico query in modo che mi dia solo aste attive
       return "./user/currentauction.xhtml";
    }
    
    public void loadAuctionPage (Auction auction) {
         try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("auctioninfo.xhtml?e=" +auction.getAuctionid().toString());
            } catch (IOException ex) {
                Logger.getLogger(CreateAuctionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
       
    }