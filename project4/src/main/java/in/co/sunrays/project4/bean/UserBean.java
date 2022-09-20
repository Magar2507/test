package in.co.sunrays.project4.bean;

import java.sql.Timestamp;
import java.util.Date;


/**
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages.
 * @author GARIMA
 *
 */
public class UserBean extends BaseBean{
	
	/**
	 * Lock Active Constant for User.
	 */
	private static final String ACTIVE=null;
	
	/**
	 * Lock Inactive Constant for User
	 */
	private static final String INACTIVE=null;
	
	  /**
     * First Name of User
     */
    private String firstName;
    /**
     * Last Name of User
     */
    private String lastName;
    /**
     * Login of User
     */
    private String login;
    /**
     * Password of User
     */
    private String password;
    /**
     * Confirm Password of User
     */
    private String confirmPassword;
    /**
     * Date of Birth of User
     */
    private Date dob;
    /**
     * MobielNo of User
     */
    private String mobileNo;
    /**
     * Role of User
     */
    private long roleId;
    
    /**
     * Gender of User
     */
    private String gender;
    
    /**
     * accessor
     */

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

   
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return firstName;
	}

}