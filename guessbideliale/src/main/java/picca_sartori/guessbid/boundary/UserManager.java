/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picca_sartori.guessbid.boundary;

import picca_sartori.guessbid.entity.Groups;
import picca_sartori.guessbid.entity.Users;
import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import picca_sartori.guessbid.gui.UserBean;

@Stateless
public class UserManager {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    Principal principal;
    
    private Users utente;
    private Users a;

    public void save(Users user) {
        user.setGroupname(Groups.USERS);
        user.setBalance(100);
        em.persist(user);
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public Users getLoggedUser() {
        return em.find(Users.class, principal.getName());
    }
    
     public void changeData(Users usertoupdate) {
        //se gira bene, se no faccio partire la transazione e il commit manualmente
            if (!"".equals(usertoupdate.getUsername())) {
         //    a=  UserBean.getCurrentuser();
                      a.setUsername(usertoupdate.getUsername());       
            }
            if (!"".equals(usertoupdate.getFirstname())) {
                utente.setFirstname(usertoupdate.getFirstname());
            }
            if (!"".equals(usertoupdate.getLastname())) {
                utente.setLastname(usertoupdate.getLastname());
            }
            if (!"".equals(usertoupdate.getPassword())) {
                utente.setPassword(usertoupdate.getPassword());
            }
            if (!"".equals(usertoupdate.getAge())) {
                utente.setAge(usertoupdate.getAge());
            }
    }
    
}
