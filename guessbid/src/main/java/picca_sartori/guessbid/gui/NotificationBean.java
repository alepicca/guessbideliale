package picca_sartori.guessbid.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import picca_sartori.guessbid.entity.Notification;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.entity.Auction;

@ApplicationScoped
public class NotificationBean {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private AuctionManager am;
   
    @EJB
    private UserManager um;
    
    private static Map<String, String> mappa;
    private Map<String, String> parametri;
    int notifid;
    private Notification notification, selectednotification;
    private List <Notification> tuttelenotifiche, fineasta, cambioposizione; //em.createNamedQuery(Auction.findActive).getResultList();  
    
    public NotificationBean() {
    }
    
    public void init() {
         tuttelenotifiche = new ArrayList<>();
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

               /* int id = Integer.parseInt(s);
                auction = am.findAuctionById(id);*/
            } catch (Exception e) {
//                FacesContext context;
//                context = FacesContext.getCurrentInstance();
//                context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred"));
            } 
    
    }

    
    
      public Notification getNotification() {
        if (notification == null) {
            notification = new Notification();
        }
        return notification;
    }
      
      public Notification getSelectednotification () {
          return selectednotification;
      }
      
      public void setSelectednotification (Notification selectednotification) {
          this.selectednotification = selectednotification;
      }

      public List<Notification> getFineasta() {
          List<Notification> fineasta = em.createNamedQuery(Notification.findByCategory).setParameter("receiver", um.getLoggedUser()).setParameter("notiftype", Notification.Notiftype.ENDAUCTION).getResultList();
          return fineasta;
      }
      
      public List<Notification> getCambiopos() {
          List<Notification> cambioposizione = em.createNamedQuery(Notification.findByCategory).setParameter("receiver", um.getLoggedUser()).setParameter("notiftype", Notification.Notiftype.CHANGEPOSITION).getResultList();
          return cambioposizione;
      }
      
      public void setFineasta( List<Notification> fineasta) {
          this.fineasta = fineasta;
      }
      
      public void setCambiopos( List<Notification> cambioposizione) {
          this.cambioposizione = cambioposizione;
      }
      
      public List<Notification> getTuttelenotifiche() {
          List<Notification> tuttelenotifiche = em.createNamedQuery(Notification.findByReceiver).setParameter("receiver", um.getLoggedUser()).getResultList(); // modifico query in modo che mi torni solo quelle dell'utente
          return tuttelenotifiche;
      }
      
      public void setTuttelenotifiche (List<Notification> tuttelenotifiche) {
          this.tuttelenotifiche = tuttelenotifiche;
      }
      
    public void setNotification(Notification notification) {
        this.notification = notification;
    }
    
  /* fare il create notification e sistemare l'xhtml
    
    
    
    public String createauction(){
      //  auction.setCreator(user.getUsername());
        am.create(auction);
      //  auction.setCreator(lb.getCredenziale());
        return "/user/home";
    } 
    
    public String submit(){
        tutteleastevalide = em.createNamedQuery(Auction.findByAll).getResultList(); //moodifico query in modo che mi dia solo aste attive
       return "./user/currentauction.xhtml";
    }
    
    public String loadAuctionPage (Auction auction) {
        
         return "/user/auctioninfo?faces-redirect=true";
        } */
    
     public Notification findNotificationById(int notifid) {
        Notification n = em.createNamedQuery(Notification.findByAuctionid, Notification.class).setParameter("notifid", notifid).getSingleResult();
        return n;
    }
}
