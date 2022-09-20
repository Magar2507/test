package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.project4.model.CollegeModel;
import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.model.SubjectModel;
import in.co.sunrays.project4.bean.CollegeBean;
import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.bean.FacultyBean;
import in.co.sunrays.project4.bean.SubjectBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;

/**
 * JDBC Implementation of Faculty Model.
 * @author GARIMA
 *
 */
public class FacultyModel {
	/**
	 * Next PK of Faculty
	 *
	 */

	public Integer nextPK() throws ApplicationException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("DataBase Exception ..", e);
			throw new ApplicationException("Exception in Getting the PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Faculty Model nextPK method End");
		return pk + 1;
	}
	/**
	 * Add a Faculty
	 *
	 */

	public long add(FacultyBean fb) throws Exception {
		// log.debug("Model add Started");
		Connection conn = null;

		int pk = 0;

		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findbypk(fb.getCollegeId());
		String collegeName = cb.getName();

		CourseModel crsm = new CourseModel();
		CourseBean crsb = crsm.findByPk(fb.getCourseId());
		String courseName = crsb.getName();

		SubjectModel sm = new SubjectModel();
		SubjectBean sb = sm.findByPK(fb.getSubjectId());
		String subjectname = sb.getName();
		
		FacultyBean fname = findByEmail(fb.getEmailId());
		if(fname != null){
			throw new DuplicateRecordException("Faculty Already Exists!");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, nextPK());
			ps.setString(2, fb.getFirstName());
			ps.setString(3, fb.getLastName());
			ps.setString(4, fb.getGender());
			ps.setDate(5, new java.sql.Date(fb.getDOJ().getTime()));
			ps.setString(6, fb.getEmailId());
			ps.setString(7, fb.getMobileNo());
			ps.setString(8, fb.getQualification());
			ps.setLong(9, fb.getCollegeId());
			ps.setString(10, collegeName);
			ps.setInt(11, fb.getSubjectId());
			ps.setString(12, subjectname);
			ps.setInt(13, fb.getCourseId());
			ps.setString(14, courseName);
			ps.setString(15, fb.getCreatedBy());
			ps.setString(16, fb.getModifiedBy());
			ps.setTimestamp(17, fb.getCreatedDatetime());
			ps.setTimestamp(18, fb.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Faculty");
		} finally {
			System.out.println("INSERTION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}
	/**
	 * Delete a Faculty
	 *
	 */
	public void delete(FacultyBean fb) throws ApplicationException {
		// log.debug("Faculty Model Delete method End");
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
			ps.setLong(1, fb.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			// log.error("DATABASE EXCEPTION ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception in Faculty Model rollback" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Faculty Model Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	/**
	 * Update a Faculty
	 *
	 */
	public void update(FacultyBean fb) throws Exception {
		// log.debug("Model update Started");
		Connection conn = null;

		CollegeModel cmodel = new CollegeModel();
		CollegeBean cbean = cmodel.findbypk(fb.getCourseId());
		String collegeName = cbean.getName();

		SubjectModel smodel = new SubjectModel();
		SubjectBean sbean = smodel.findByPK(fb.getSubjectId());
		String subjectname = sbean.getName();

		FacultyBean beanExist = findByEmail(fb.getFirstName());
		if (beanExist != null && fb.getId() != fb.getId()) {
			throw new DuplicateRecordException("faculty already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_FACULTY SET FIRST_NAME=?, LAST_NAME=?, GENDER=?, DOJ=?,EMAIL_ID=?, MOBILE_NO=?, QUALIFICATION=?, COLLEGE_ID=?, COLLEGE_NAME=?, SUBJECT_ID=?, SUBJECT_NAME=?,COURSE_ID=?,COURSE_NAME=?, CREATED_BY=? , MODIFIED_BY=? , CREATED_DATETIME=? , MODIFIED_DATETIME=?  WHERE ID= ? ");

			ps.setString(1, fb.getFirstName());
			ps.setString(2, fb.getLastName());
			ps.setString(3, fb.getGender());
			ps.setDate(4, new java.sql.Date(fb.getDOJ().getTime()));
			ps.setString(5, fb.getEmailId());
			ps.setString(6, fb.getMobileNo());
			ps.setString(7, fb.getQualification());
			ps.setLong(8, fb.getCollegeId());
			ps.setString(9, collegeName);
			ps.setInt(10, fb.getSubjectId());
			ps.setString(11, subjectname);
			ps.setInt(12, fb.getCourseId());
			ps.setString(13, fb.getCourseName());
			ps.setString(14, fb.getCreatedBy());
			ps.setString(15, fb.getModifiedBy());
			ps.setTimestamp(16, fb.getCreatedDatetime());
			ps.setTimestamp(17, fb.getModifiedDatetime());
			ps.setLong(18, fb.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("DATABASE EXCEPTION ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception in rollback faculty model .." +
				// ex.getMessage());
			}
			// throw new ApplicationException("Exception in faculty model Update Method..");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Faculty Model update method End");
	}
	/**
	 * Find Faculty by Email
	 *
	 */
	public FacultyBean findByEmail(String EmailId) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
		Connection conn = null;
		FacultyBean fb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ps.setString(1, EmailId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setFirstName(rs.getString(2));
				fb.setLastName(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setEmailId(rs.getString(6));
				fb.setMobileNo(rs.getString(7));
				fb.setQualification(rs.getString(8));
				fb.setCollegeId(rs.getInt(9));
				fb.setCollegeName(rs.getString(10));
				fb.setSubjectId(rs.getInt(11));
				fb.setSubject(rs.getString(12));
				fb.setCourseId(rs.getInt(13));
				fb.setCourseName(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));
				
			
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database exception ..." , e);
			throw new ApplicationException("Exception : Exception in faculty model in findbyName method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println(" faculty  find by name 4");
		// log.debug("Faculty Model findbyName method End");
		return fb;
	}
	/**
	 * Find Faculty by PK
	 *
	 */

	public FacultyBean findByPK(int pk) throws ApplicationException {
		// log.debug("Faculty Model findByPK method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
		Connection conn = null;
		FacultyBean fb = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setFirstName(rs.getString(2));
				fb.setLastName(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setEmailId(rs.getString(6));
				fb.setMobileNo(rs.getString(7));
				fb.setQualification(rs.getString(8));
				fb.setCollegeId(rs.getInt(9));
				fb.setCollegeName(rs.getString(10));
				fb.setSubjectId(rs.getInt(11));
				fb.setSubject(rs.getString(12));
				fb.setCourseId(rs.getInt(13));
				fb.setCourseName(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Faculty Model FindByPK method end");
		return fb;
	}
	/**
	 * Search Faculty 
	 *
	 */

	public List search(FacultyBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}
	/**
	 * Search Faculty with pagination
	 *
	 */

	public List search(FacultyBean fb, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE true=true");
		if (fb != null) {
			if (fb.getId() > 0) {
				sql.append(" AND id = " + fb.getId());
			}
			if (fb.getCourseId() > 0) {
				sql.append(" AND college_Id = " + fb.getCourseId());
			}
			if (fb.getCollegeName() != null && fb.getCollegeName().length() > 0) {
				sql.append(" AND college_Name like '" + fb.getCollegeName() + "%'");
			}
			if (fb.getCourseId() > 0) {
				sql.append(" AND course_Id = " + fb.getCourseId());
			}
			if (fb.getCourseName() != null && fb.getCourseName().length() > 0) {
				sql.append(" AND course_Name like '" + fb.getCourseName() + "%'");
			}
			if (fb.getSubjectId() > 0) {
				sql.append(" AND Subject_Id = " + fb.getSubjectId());
			}
			if (fb.getSubject() != null && fb.getSubject().length() > 0) {
				sql.append(" AND subject_Name like '" + fb.getSubject() + "%'");
			}
			if (fb.getFirstName() != null && fb.getFirstName().trim().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + fb.getFirstName() + "%'");
			}
			if (fb.getLastName() != null && fb.getLastName().trim().length() > 0) {
				sql.append(" AND LAST_NAME like '" + fb.getLastName() + "%'");
			}

			if (fb.getEmailId() != null && fb.getEmailId().length() > 0) {
				sql.append(" AND Email_Id like '" + fb.getEmailId() + "%'");
			}

			if (fb.getGender() != null && fb.getGender().length() > 0) {
				sql.append(" AND Gender like '" + fb.getGender() + "%'");
			}

			if (fb.getMobileNo() != null && fb.getMobileNo().length() > 0) {
				sql.append(" AND Mobile_No like '" + fb.getMobileNo() + "%'");
			}

		}

		// if page no is greater then zero then apply pagination
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setFirstName(rs.getString(2));
				fb.setLastName(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setEmailId(rs.getString(6));
				fb.setMobileNo(rs.getString(7));
				fb.setQualification(rs.getString(8));
				fb.setCollegeId(rs.getInt(9));
				fb.setCollegeName(rs.getString(10));
				fb.setSubjectId(rs.getInt(11));
				fb.setSubject(rs.getString(12));
				fb.setCourseId(rs.getInt(13));
				fb.setCourseName(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

				list.add(fb);
				
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception .. " , e);
			e.printStackTrace();
			// throw new ApplicationException("Exception : Exception in Search method of
			// Faculty Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
	/**
	 * List of Faculty 
	 *
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}
	/**
	 * List of Faculty with pagination
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Faculty Model List method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY");
		Connection conn = null;
		ArrayList list = new ArrayList();

		// if page is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				FacultyBean fb = new FacultyBean();
				fb.setId(rs.getLong(1));
				fb.setFirstName(rs.getString(2));
				fb.setLastName(rs.getString(3));
				fb.setGender(rs.getString(4));
				fb.setDOJ(rs.getDate(5));
				fb.setEmailId(rs.getString(6));
				fb.setMobileNo(rs.getString(7));
				fb.setQualification(rs.getString(8));
				fb.setCollegeId(rs.getInt(9));
				fb.setCollegeName(rs.getString(10));
				fb.setSubjectId(rs.getInt(11));
				fb.setSubject(rs.getString(12));
				fb.setCourseId(rs.getInt(13));
				fb.setCourseName(rs.getString(14));
				fb.setCreatedBy(rs.getString(15));
				fb.setModifiedBy(rs.getString(16));
				fb.setCreatedDatetime(rs.getTimestamp(17));
				fb.setModifiedDatetime(rs.getTimestamp(18));

				list.add(fb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception ......" , e);
			throw new ApplicationException("Exception in list method of FacultyModel");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
