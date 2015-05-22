package picca_sartori.guessbid.control;

import picca_sartori.guessbid.entity.Users;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ale
 */
public class PasswordEncrypter {
    
     public static String encryptPassword(String password) {
        String encPass = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            BigInteger bigInt = new BigInteger(1, hash);
            encPass = bigInt.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encPass;
}
}