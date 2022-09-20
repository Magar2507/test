package in.co.sunrays.project4.bean;

import in.co.sunrays.project4.bean.BaseBean;


/**
 * Course JavaBean Encapsulates Course attributes.
 * @author GARIMA
 *
 */
public class CourseBean extends BaseBean {
	/**
	 * Course name
	 */
	private String name;
	/**
	 * Course duration
	 */
	private String duration;
	/**
	 * Course description
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
	 * the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * duration the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
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

		return id + "";
	}

	public String getValue() {

		return name;
	}

}