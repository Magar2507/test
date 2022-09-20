package in.co.sunrays.project4.bean;

import in.co.sunrays.project4.bean.BaseBean;


/**
 * Role JavaBean encapsulates Role attributes.
 * @author GARIMA
 *
 */
public class RoleBean extends BaseBean {
	/**
	 * Predefined Role Constants
	 */
	public static final int ADMIN = 1;
	public static final int STUDENT = 2;
	public static final int COLLEGE = 5;
	public static final int KIOSK = 4;
	public static final int FACULTY = 3;
	/**
	 * Role name 
	 */
	private String name;
	/**
	 * Role description 
	 */
	private String description;
	/**
	 * the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getKey() {
		return id+ "";
	}
	
	public String getValue() {
		return name;
	}
}