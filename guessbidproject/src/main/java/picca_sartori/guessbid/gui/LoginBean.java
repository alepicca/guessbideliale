package picca_sartori.guessbid.gui;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import picca_sartori.guessbid.entity.Users;

@Named
@ApplicationScoped
public class LoginBean implements Serializable{
    
    @PersistenceContext
    private EntityManager em;
    
    
    @Inject
    private Logger logger;
    
    public  String username;
    private String password;
    private String credenziale;
    private HttpSession sessionlogin;
    
    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.login(this.username, this.password);
            request.setAttribute("username", username);
            credenziale = (String) request.getAttribute("username");
            return "/user/home";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Login Failed","Login Failed"));
            return null;
        }
    }
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().invalidate();
        return "/guessbidproject/login.xhtml";
    }
    
    public String getCredenziale() {
        return credenziale;
    }
    
    public void setCredenziale (String credenziale) {
        this.credenziale = credenziale;
    }
    
    public Users findByUsername (String username) {
        Users utente = em.createNamedQuery(Users.findByUsername, Users.class).setParameter("username", username).getSingleResult();
        return utente;
    }    
    

}