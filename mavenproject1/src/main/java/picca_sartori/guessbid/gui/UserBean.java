package picca_sartori.guessbid.gui;

import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.control.PasswordEncrypter;
import picca_sartori.guessbid.entity.Users;
import javax.ejb.EJB;
import javax.ejb.SessionBean;
import javax.ejb.Stateful;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Ale
 */   

@Named
@ApplicationScoped
public class UserBean {
    
    @EJB
    UserManager um;
    
    @EJB
    private CheckModifications cm;
    
    private Users currentuser;
    private Users modifieduser;
    private String warning;
    private String message;
    private String pwdconfirmed;
    
    public UserBean() {
    }
    
    public Users getCurrentuser() {
        return um.getLoggedUser();
    }

    public void setCurrentuser(Users currentuser) {
        this.currentuser = currentuser;
    }
    
     public Users getModifieduser() {
        return modifieduser;
    }

    public void setModifieduser(Users modifieduser) {
        this.modifieduser = modifieduser;
    }
    
     public String getWarning() {
        return this.warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
    
    public String getPwdconfirmed() {
        return this.pwdconfirmed;
    }

    public void setPwdconfirmed(String pwdconfirmed) {
        this.pwdconfirmed = pwdconfirmed;
    }
    
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

   public void submitChangeData() {

        if (!cm.checkDoubleUsername(modifieduser.getUsername())) {
            warning = "Select another username, this one already exists";} 
        else if (!cm.checkDoubleEmail(modifieduser.getEmail())) {
            warning = "Insert another email address, the one you entered is already in use";}
        else if (!cm.checkCorrectUsername(modifieduser.getUsername())) {
            warning = "Please insert a valid username";}
        else if (!cm.checkCorrectEmail(modifieduser.getEmail())) {
            warning = "Please insert a valid email address";}
        else if (!cm.equalsPassword(modifieduser.getPassword(), PasswordEncrypter.encryptPassword(pwdconfirmed))) {
            warning = "Two passwords are not the same: check possible errors";}
        else if ((pwdconfirmed.length() < 5 )) {
            warning = "Your password must contain at least five characters";}
        else if (!cm.checkCorrectAge(modifieduser.getAge())) {
            warning = "Insert your real age, please";}
        else {
            ;
            um.changeData(modifieduser);
            this.message = "Succesful update!"; 
        } 

    }
    
    public String getUsername() {
        return um.getLoggedUser().getUsername();
    }
    
    public String gotobid() {
        return "./user/insertbid.xhtml";
    }
}