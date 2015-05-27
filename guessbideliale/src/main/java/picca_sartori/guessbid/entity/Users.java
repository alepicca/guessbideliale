package picca_sartori.guessbid.entity;

import picca_sartori.guessbid.control.PasswordEncrypter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity(name = "USERS")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,25}$", message = "invalid username")
    @NotNull(message = "May not be empty")
    @Column(name="Username")
    private String username;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "invalid email")
    @NotNull(message = "May not be empty")
    private String email;
    @NotNull(message = "May not be empty")
    private String password;
    @NotNull(message = "May not be empty")
    private String groupname;
    
    @NotNull(message = "May not be empty")
    private String firstname;
    @NotNull(message = "May not be empty")
    private String lastname;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

     public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
     public String getGroupname() {
        return groupname;
    }
    
    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
   
  /*   public String getAge() {
        return age;
    }
     
     public void setAge(String age) {
        this.age = age;
    } */
     
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordEncrypter.encryptPassword(password);
    }

}