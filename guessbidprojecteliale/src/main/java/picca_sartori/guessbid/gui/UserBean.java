package picca_sartori.guessbid.gui;

import picca_sartori.guessbid.boundary.UserManager;
import picca_sartori.guessbid.control.CheckModifications;
import picca_sartori.guessbid.control.PasswordEncrypter;
import picca_sartori.guessbid.entity.Users;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Ale
 */   

@Named ("UserBean")
@RequestScoped
public class UserBean {
    
    @EJB
    UserManager um;
    
    @EJB
    private CheckModifications cm;
    
    private Users currentuser;
    private String warning;
    private String message;
    private String pwdconfirmed;
    
    public UserBean() {
    }
    
    public Users getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(Users currentuser) {
        this.currentuser = currentuser;
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

        if (!cm.checkDoubleUsername(currentuser.getUsername())) {
            warning = "Select another username, this one already exists";} 
        else if (!cm.checkDoubleEmail(currentuser.getEmail())) {
            warning = "Insert another email address, the one you entered is already in use";}
        else if (!cm.checkCorrectUsername(currentuser.getUsername())) {
            warning = "Please insert a valid username";}
        else if (!cm.checkCorrectEmail(currentuser.getEmail())) {
            warning = "Please insert a valid email address";}
        else if (!cm.equalsPassword(currentuser.getPassword(), PasswordEncrypter.encryptPassword(pwdconfirmed))) {
            warning = "Two passwords are not the same: check possible errors";}
        else if ((pwdconfirmed.length() < 5 )) {
            warning = "Your password must contain at least five characters";}
        else {
            currentuser.setUsername(um.getLoggedUser().getUsername());
            um.changeData(currentuser);
            this.message = "Succesful update!";
        }

    }
    public String getUsername() {
        return um.getLoggedUser().getUsername();
    }
}

