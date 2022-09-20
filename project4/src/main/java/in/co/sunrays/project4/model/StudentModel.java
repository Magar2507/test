package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.project4.model.CollegeModel;
import in.co.sunrays.project4.bean.CollegeBean;
import in.co.sunrays.project4.bean.StudentBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DatabaseException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;

/**
 * JDBC Implementation of Student Model.
 * @author GARIMA
 *
 */
public class StudentModel {
	/**
	 * Find next PK
	 *
	 */
	public long nextPK() throws Exception {

		Connection conn = null;

		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_student");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	/**
	 * Add a Student
	 *
	 */
	public int add(StudentBean sb) throws Exception {
		Connection conn = null;

		// get College Name
		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findbypk(sb.getCollegeId());
		String collegename = cb.getName();
		StudentBean duplicateName = findByEmailId(sb.getEmail());
		int pk = 0;

		if (duplicateName != null) {
			throw new DuplicateRecordException("Email already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_STUDENT VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPK());
			ps.setLong(2, sb.getCollegeId());
			ps.setString(3, collegename);
			ps.setString(4, sb.getFirstName());
			ps.setString(5, sb.getLastName());
			ps.setDate(6, new java.sql.Date(sb.getDob().getTime()));
			ps.setString(7, sb.getMobileNo());
			ps.setString(8, sb.getEmail());
			ps.setString(9, sb.getCreatedBy());
			ps.setString(10, sb.getModifiedBy());
			ps.setTimestamp(11, sb.getCreatedDatetime());
			ps.setTimestamp(12, sb.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
			System.out.println("INSERTION DONE");
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;

	}

	/**
	 * Delete a Student
	 *
	 */

	public void delete(StudentBean sb) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_STUDENT WHERE ID=?");
			ps.setLong(1, sb.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {
			System.out.println("DELETION DONE");
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * update a Student
	 *
	 */

	public void update(StudentBean sb) throws Exception {
		// log.debug("Model update Started");
		Connection conn = null;

		StudentBean beanExist = findByEmailId(sb.getEmail());

// Check if updated Roll no already exist
		if (beanExist != null && beanExist.getId() != sb.getId()) {
			throw new DuplicateRecordException("Email Id is already exist");
		}

// get College Name
		CollegeModel cm = new CollegeModel();
		CollegeBean cb = cm.findbypk(sb.getCollegeId());
		String collegeName = cb.getName();

		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_STUDENT SET COLLEGE_ID=?,COLLEGE_NAME=?,FIRST_NAME=?,LAST_NAME=?,DATE_OF_BIRTH=?,MOBILE_NO=?,EMAIL=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setLong(1, sb.getCollegeId());
			ps.setString(2, collegeName);
			ps.setString(3, sb.getFirstName());
			ps.setString(4, sb.getLastName());
			ps.setDate(5, new java.sql.Date(sb.getDob().getTime()));
			ps.setString(6, sb.getMobileNo());
			ps.setString(7, sb.getEmail());
			ps.setString(8, sb.getCreatedBy());
			ps.setString(9, sb.getModifiedBy());
			ps.setTimestamp(10, sb.getCreatedDatetime());
			ps.setTimestamp(11, sb.getModifiedDatetime());
			ps.setLong(12, sb.getId());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Student ");
		} finally {
			System.out.println("UPDATION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model update End");
	}

	/**
	 * find a Student by email Id
	 *
	 */

	public StudentBean findByEmailId(String Email) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE EMAIL=?");
		StudentBean sb = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, Email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb = new StudentBean();

				sb.setId(rs.getLong(1));
				sb.setCollegeId(rs.getLong(2));
				sb.setCollegeName(rs.getString(3));
				sb.setFirstName(rs.getString(4));
				sb.setLastName(rs.getString(5));
				sb.setDob(rs.getDate(6));
				sb.setMobileNo(rs.getString(7));
				sb.setEmail(rs.getString(8));
				sb.setCreatedBy(rs.getString(9));
				sb.setModifiedBy(rs.getString(10));
				sb.setCreatedDatetime(rs.getTimestamp(11));
				sb.setModifiedDatetime(rs.getTimestamp(12));

			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return sb;
	}

	/**
	 * find a Student by PK
	 *
	 */
	public StudentBean findByPK(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE ID=?");
		StudentBean sb = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb = new StudentBean();
				
				sb.setId(rs.getLong(1));
				sb.setCollegeId(rs.getLong(2));
				sb.setCollegeName(rs.getString(3));
				sb.setFirstName(rs.getString(4));
				sb.setLastName(rs.getString(5));
				sb.setDob(rs.getDate(6));
				sb.setMobileNo(rs.getString(7));
				sb.setEmail(rs.getString(8));
				sb.setCreatedBy(rs.getString(9));
				sb.setModifiedBy(rs.getString(10));
				sb.setCreatedDatetime(rs.getTimestamp(11));
				sb.setModifiedDatetime(rs.getTimestamp(12));
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return sb;
	}

	/**
	 * search a student with pagination
	 *
	 */

	public List search(StudentBean sb, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_STUDENT WHERE 1=1");

		if (sb != null) {
			if (sb.getId() > 0) {
				sql.append(" AND id = " + sb.getId());
			}
			if (sb.getFirstName() != null && sb.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + sb.getFirstName() + "%'");
			}
			if (sb.getLastName() != null && sb.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + sb.getLastName() + "%'");
			}
			if (sb.getDob() != null && sb.getDob().getDate() > 0) {
				sql.append(" AND Date_of_Birth = " + sb.getDob());
			}
			if (sb.getMobileNo() != null && sb.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO like '" + sb.getMobileNo() + "%'");
			}
			if (sb.getEmail() != null && sb.getEmail().length() > 0) {
				sql.append(" AND EMAIL like '" + sb.getEmail() + "%'");
			}
			if (sb.getCollegeName() != null && sb.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME = " + sb.getCollegeName());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sb = new StudentBean();
				sb.setId(rs.getLong(1));
				sb.setCollegeId(rs.getLong(2));
				sb.setCollegeName(rs.getString(3));
				sb.setFirstName(rs.getString(4));
				sb.setLastName(rs.getString(5));
				sb.setDob(rs.getDate(6));
				sb.setMobileNo(rs.getString(7));
				sb.setEmail(rs.getString(8));
				sb.setCreatedBy(rs.getString(9));
				sb.setModifiedBy(rs.getString(10));
				sb.setCreatedDatetime(rs.getTimestamp(11));
				sb.setModifiedDatetime(rs.getTimestamp(12));
				list.add(sb);
			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model search End");
		return list;
	}

	/**
	 * list of student
	 *
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * list of Student with pagination
	 *
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_STUDENT");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		StudentBean sb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				sb = new StudentBean();
				sb.setId(rs.getLong(1));
				sb.setCollegeId(rs.getLong(2));
				sb.setCollegeName(rs.getString(3));
				sb.setFirstName(rs.getString(4));
				sb.setLastName(rs.getString(5));
				sb.setDob(rs.getDate(6));
				sb.setMobileNo(rs.getString(7));
				sb.setEmail(rs.getString(8));
				sb.setCreatedBy(rs.getString(9));
				sb.setModifiedBy(rs.getString(10));
				sb.setCreatedDatetime(rs.getTimestamp(11));
				sb.setModifiedDatetime(rs.getTimestamp(12));
				list.add(sb);
			}
			rs.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model list End");
		return list;

	}
}