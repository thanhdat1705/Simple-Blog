package datnt.mailValidCode;

import java.io.Serializable;

/**
 *
 * @author SE130204
 */
public class MailError implements Serializable{
    private String invalidVerifyCode;

    public MailError() {
    }

    public String getInvalidVerifyCode() {
        return invalidVerifyCode;
    }

    public void setInvalidVerifyCode(String invalidVerifyCode) {
        this.invalidVerifyCode = invalidVerifyCode;
    }
    
    
}
