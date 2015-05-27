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

@Stateless
public class UserManager {

    @PersistenceContext
    EntityManager em;
    
    @Inject
    Principal principal;

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
    
}
