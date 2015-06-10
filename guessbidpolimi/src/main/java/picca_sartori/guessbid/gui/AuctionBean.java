package picca_sartori.guessbid.gui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.entity.Auction;

@Named
@RequestScoped
public class AuctionBean {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private AuctionManager am;
    @EJB
    private UserManager um;
    
    private Auction auction;
    private int auctionid;
    
    private static Map<String, String> sam;
    
    /**
     * Retrieve all the information about the event
     */
    @PostConstruct
    public void init(){
        if(sam == null){
            sam = new ConcurrentHashMap<>();
        }
        if(auction == null){
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                String s = request.getParameter("e");
                if(s!= null){
                    sam.put(this.um.getLoggedUser().getUsername(), s);
                }
                else{
                    s=sam.get(this.um.getLoggedUser().getUsername());
                }

                int id = Integer.parseInt(s);
                auction = am.findAuctionById(id);
            } catch (Exception e) {
//                FacesContext context;
//                context = FacesContext.getCurrentInstance();
//                context.addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occoured"));
            }
        }
    }
    
    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
    
    public int getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(int auctionid) {
        this.auctionid = auctionid;
    }
    public Auction findAuctionById() {
        Auction a = em.createNamedQuery(Auction.findByAuctionid, Auction.class).setParameter("auctionid", auctionid).getSingleResult();
        return a;
    }
               
}
