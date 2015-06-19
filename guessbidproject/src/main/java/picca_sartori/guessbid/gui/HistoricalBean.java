package picca_sartori.guessbid.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.entity.Auction;
import picca_sartori.guessbid.entity.Bid;

@ApplicationScoped
public class HistoricalBean implements Serializable {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private UserManager um;
    
    @ManagedProperty(value = "#{LoginBean}")
    private LoginBean lb;
    
     private List <Auction> historical;
     private static Map<String, String> mappainit;
     public Auction auction;
     
      public HistoricalBean() {
    }
    
   /* public void init() {
         historical = new ArrayList<>();
        try {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                String s = request.getParameter("e");
                if(s!= null){
                    mappainit.put(this.um.getLoggedUser().getUsername(), s);
                }
                else{
                    s=mappainit.get(this.um.getLoggedUser().getUsername());
                }

                int id = Integer.parseInt(s);
                auction = am.findAuctionById(id);
            } catch (Exception e) {
//                FacesContext context;
//                context = FacesContext.getCurrentInstance();
//              context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred"));
            } 
    }

*/
    

     public List<Auction> getHistorical() {
         Calendar today = Calendar.getInstance();
          today.set(Calendar.HOUR_OF_DAY,0);
          today.set(Calendar.MINUTE, 0);
          today.set(Calendar.SECOND, 0);
          Date date = today.getTime();
          List<Auction> historical = em.createNamedQuery(Bid.findHistorical).setParameter("bidder", "eliseo").setParameter("expdate", date).getResultList(); 
          return historical;
      }
      
      public void setHistorical (List<Auction> historical) {
          this.historical = historical;
      }

      public Auction getAuction() {
        if (auction == null) {
            auction = new Auction();
        }
        return auction;
    }
      
       public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
