package picca_sartori.guessbid.gui;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import picca_sartori.guessbid.boundary.AuctionManager;
import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.entity.Auction;

@Named
@ApplicationScoped
public class AuctionBean {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private AuctionManager am;
    @EJB
    private UserManager um;
    
    private Auction auction,ciao;
    private int auctionid;
    private String title, iteminfo, itemdescription;
    private static Map<String, String> map;
    
    /**
     * Retrieve all the information about the event
     */
    @PostConstruct
    public void init(){
        if(map == null){
            map = new ConcurrentHashMap<>();
        }
        if(auction == null){
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
                String s = request.getParameter("e");
                if(s!= null){
                    map.put(this.um.getLoggedUser().getUsername(), s);
                }
                else{
                    s=map.get(this.um.getLoggedUser().getUsername());
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

    public void loadAuction() throws IOException {
        Auction questaasta = am.findAuctionById(auctionid);
        if (questaasta == null) {
            //ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            //ec.redirect(ec.getRequestContextPath() + "/user/404.xhtml");
            
        } else {
            this.auction = questaasta;
        }
    }
    
    public void setAuctionid(int auctionid) {
        this.auctionid = auctionid;
    }
    public Auction findAuctionById(int auctionid) {
        System.out.println(auctionid);
        Auction a = em.createNamedQuery(Auction.findByAuctionid, Auction.class).setParameter("auctionid", auctionid).getSingleResult();
        
        return a;
        
    }
               
    public String getTitle() {
        return title;
    }
    
    public void setTitle (String title) {
        this.title = title;
    }
    
    public String getIteminfo() {
        return iteminfo;
    }
    
    public void setIteminfo (String iteminfo) {
        this.iteminfo = iteminfo;
    }
    
    public String getItemdescription() {
        return itemdescription;
    }
    
    public void setItemdescription (String itemdescription) {
        this.itemdescription = itemdescription;
    }
}
