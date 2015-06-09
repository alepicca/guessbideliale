package picca_sartori.guessbid.gui;

import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.entity.Users;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
/**
 *
 * @author Ale
 */

@RequestScoped
@Named("RegistrationBean")
public class RegistrationBean {
    
     @EJB
    private UserManager um;

    private Users user;

    public RegistrationBean() {
    }

    public Users getUser() {
        if (user == null) {
            user = new Users();
        }
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String register() {
        um.save(user);
        return "login?faces-redirect=true";
    }
}