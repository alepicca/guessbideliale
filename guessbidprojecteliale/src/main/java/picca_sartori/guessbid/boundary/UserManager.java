package picca_sartori.guessbid.boundary;

import picca_sartori.guessbid.control.PasswordEncrypter;
import picca_sartori.guessbid.entity.Group;
import picca_sartori.guessbid.entity.Users;
import java.security.Principal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ale
 */
@Stateless
public class UserManager {
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    Principal principal;
    
    private Users utente;

    public void save(Users user) {
        user.setGroupName(Group.USERS);
        em.persist(user);
    }

    public void unregister() {
        em.remove(getLoggedUser());
    }

    public Users getLoggedUser() {
        return em.find(Users.class, principal.getName());
    }
    
    public List<Users> searchingUsers (String username, String city){
        
          if(username == null){
              username = "";
          }
         
          if(city == null){
              city = "";
          }
           List<Users> result = em.createNamedQuery(Users.advancedSearch, Users.class)
                  .setParameter("name user:", "%"+username+"%")
                  .setParameter("city user:", "%"+city+"%")
                  .getResultList();
          
          return result;
          }
    
    public void changeData(Users usertoupdate) {
        //se gira bene, se no faccio partire la transazione e il commit manualmente
            if (!"".equals(usertoupdate.getUsername())) {
                utente.setUsername(usertoupdate.getUsername());
            }
            if (!"".equals(usertoupdate.getFirstName())) {
                utente.setFirstName(usertoupdate.getFirstName());
            }
            if (!"".equals(usertoupdate.getLastName())) {
                utente.setLastName(usertoupdate.getLastName());
            }
            if (!"".equals(usertoupdate.getPassword())) {
                utente.setPassword(usertoupdate.getPassword());
            }
            if (!"".equals(usertoupdate.getCity())) {
                utente.setCity(usertoupdate.getCity());
            }
    }
}
