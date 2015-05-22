package picca_sartori.guessbid.entity;

import picca_sartori.guessbid.control.PasswordEncrypter;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Ale
 */
public class Users implements Serializable {
    
    //sistemareeeeeeeeeee!!!
    private static final long serialVersionUID = 1L;
    public static final String findByCity = "Users.findByCity";
    public static final String advancedSearch = "Users.advancedSearch";
    public static final String findByAll = "Users.findByAll";
    public static final String findByUsername = "Users.findByUsername";
    public static final String findByFirstname = "Users.findByFirstname";
    public static final String findByLastname = "Users.findByLastname";
    public static final String findByEmail= "Users.findByEmail";

    @Id
    @Pattern(regexp = "^[a-z0-9A-Z_]{5,25}$", message = "invalid username")
    @NotNull(message = "May not be empty")
    @Column(name="Username")
    private String username;
    
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$",
            message = "invalid email")
    @NotNull(message = "May not be empty")
    @Size(min=8, max=25)
    @Column(name="Email")
    private String email;
    
    @Pattern(regexp="^[a-z]*$",
            message="You inserted an invalid character")
    @NotNull (message = "May not be empty")
    @Size(min = 1, max = 45)
    @Column(name = "FirstName")
    private String firstname;
    
    @Pattern(regexp="^[a-z]*$",
            message="You inserted an invalid character")
    @NotNull (message = "May not be empty")
    @Size(min = 1, max = 45)
    @Column(name = "LastName")
    private String lastname;
    
    @Pattern(regexp="^[a-z0-9A-Z_-טיעלחאש!@#+*|]*$",
            message="You inserted an invalid character")
    @NotNull(message = "May not be empty")
    @Size(min = 5, max = 45)
    @Column(name = "Password")
    private String password;
    
    @Pattern(regexp="^[a-z0-9A-Z_-טיעלחאש!@#+*|]*$",
            message="You inserted an invalid character")
    @NotNull(message = "May not be empty")
    @Size(min = 1, max = 45)
    @Column(name = "City")
    private String city;
    
    @NotNull(message = "May not be empty")
    @Size(min = 1, max = 45)
    @Column(name="Groupname")
    private String groupname;
    
     public Users() {
    }

    public Users(String username) {
        this.username = username;
    }

    public Users(String username, String email, String firstname, String lastname, String password, String city, String groupname) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.city = city;
        this.groupname = groupname;
    }
  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    
    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public void setGroupName(String groupname) {
        this.groupname = groupname;
    }

    public String getGroupName() {
        return groupname;
    }

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
    
    public String getCity() {
        return city;
    }
    
    public void setCity (String city) {
        this.city = city;
    }
}
