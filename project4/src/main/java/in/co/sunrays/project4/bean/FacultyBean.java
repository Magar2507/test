package in.co.sunrays.project4.bean;

import java.util.Date;

import in.co.sunrays.project4.bean.BaseBean;


/**
 * Faculty JavaBean Encapsulates Faculty attributes.
 * @author GARIMA
 *
 */
public class FacultyBean extends BaseBean {

	private long Id;
	private int CollegeId;
	private String FirstName;
	private String LastName;
	private String CollegeName;
	private Date DOJ;
	private String EmailId;
	private String MobileNo;
	private String Subject;
	private String Qualification;
	private String Gender;
	private int SubjectId;
	private int CourseId;
	private String CourseName;

	/**
	 * the id
	 */
	public long getId() {
		return Id;
	}

	/**
	 * id the id to set
	 */
	public void setId(long id) {
		Id = id;
	}

	/**
	 * the collegeId
	 */
	public long getCollegeId() {
		return CollegeId;
	}

	/**
	 * collegeId the collegeId to set
	 */
	public void setCollegeId(int collegeId) {
		CollegeId = collegeId;
	}

	/**
	 * the firstName
	 */
	public String getFirstName() {
		return FirstName;
	}

	/**
	 * firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	/**
	 * the lastName
	 */
	public String getLastName() {
		return LastName;
	}

	/**
	 * lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		LastName = lastName;
	}

	/**
	 * the collegeName
	 */
	public String getCollegeName() {
		return CollegeName;
	}

	/**
	 * collegeName the collegeName to set
	 */
	public void setCollegeName(String collegeName) {
		CollegeName = collegeName;
	}

	/**
	 * the dOJ
	 */
	public Date getDOJ() {
		return DOJ;
	}

	/**
	 * string the dOB to set
	 */
	public void setDOJ(Date doj) {
		DOJ = doj;
	}

	/**
	 * the emailId
	 */
	public String getEmailId() {
		return EmailId;
	}

	/**
	 * emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	/**
	 * the mobileNo
	 */
	public String getMobileNo() {
		return MobileNo;
	}

	/**
	 * mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	/**
	 * the subject
	 */
	public String getSubject() {
		return Subject;
	}

	/**
	 * subject the subject to set
	 */
	public void setSubject(String subject) {
		Subject = subject;
	}

	/**
	 * the qualification
	 */
	public String getQualification() {
		return Qualification;
	}

	/**
	 * qualification the qualification to set
	 */
	public void setQualification(String qualification) {
		Qualification = qualification;
	}

	/**
	 * the gender
	 */
	public String getGender() {
		return Gender;
	}

	/**
	 * gender the gender to set
	 */
	public void setGender(String gender) {
		Gender = gender;
	}

	/**
	 * the subjectId
	 */
	public int getSubjectId() {
		return SubjectId;
	}

	/**
	 * subjectId the subjectId to set
	 */
	public void setSubjectId(int subjectId) {
		SubjectId = subjectId;
	}

	/**
	 * the courseId
	 */
	public int getCourseId() {
		return CourseId;
	}

	/**
	 * courseId the courseId to set
	 */
	public void setCourseId(int courseId) {
		CourseId = courseId;
	}

	/**
	 * the courseName
	 */
	public String getCourseName() {
		return CourseName;
	}

	/**
	 * courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return Id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return FirstName;
	}
}