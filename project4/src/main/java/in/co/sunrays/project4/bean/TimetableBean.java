package in.co.sunrays.project4.bean;

import java.util.Date;

import in.co.sunrays.project4.bean.BaseBean;


/**
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages.
 * @author GARIMA
 *
 */
public class TimetableBean extends BaseBean{
	/**
	 * 
	 * Course name 
	 */
	private String courseName;
	/**
	 * 
	 * Course Id 
	 */
	private int courseId;
	
	/**
	 * 
	 * Subject name 
	 */
	private String subjectName;

	/**
	 * 
	 * Subject Id 
	 */
	private int subjectId;
	
	/**
	 * 
	 * Exam Date
	 */
    private Date examDate;
	
    /**
	 * 
	 * Exam Time
	 */
    private String examTime;
    
    /**
	 * 
	 * semester
	 */
    private String semester;

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
	 * the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * the subjectId
	 */
	public int getSubjectId() {
		return subjectId;
	}

	/**
	 * subjectId the subjectId to set
	 */
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * the examDate
	 */
	public Date getExamDate() {
		return examDate;
	}

	/**
	 * examDate the examDate to set
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	/**
	 * the examTime
	 */
	public String getExamTime() {
		return examTime;
	}

	/**
	 * examTime the examTime to set
	 */
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	/**
	 * the semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * semester the semester to set
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return courseName;
	}
	
}
