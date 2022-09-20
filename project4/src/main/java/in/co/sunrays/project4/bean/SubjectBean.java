package in.co.sunrays.project4.bean;

import in.co.sunrays.project4.bean.BaseBean;


/**
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages.
 * @author GARIMA
 *
 */
public class SubjectBean extends BaseBean{
	/**
	 *Subject description 
	 */
	private String description;
	/**
	 *Subject coursename 
	 */
	private String courseName;
	/**
	 *Subject courseId 
	 */
	private int courseId;
	
	/**
	 * subject name 
	 */
	private String name;
	/**
	 * the courseId
	 */
	public int getCourseId() {
		return courseId;
	}
	/**
	 * courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
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
	/**
	 * the courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}
}