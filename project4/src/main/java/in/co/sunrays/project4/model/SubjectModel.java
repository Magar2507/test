package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.bean.SubjectBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;

/**
 * JDBC Implementation of Subject Model.
 * @author GARIMA
 *
 */
public class SubjectModel {
	/**
	 * Find next PK
	 *
	 */

	public long nextPK() throws ApplicationException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_SUBJECT");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception ...", e);
			throw new ApplicationException("Exception in NextPk of subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}
	/**
	 * add a subject
	 *
	 */
	public int add(SubjectBean stb) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPk(stb.getCourseId());
		String courseName= coubean.getName();
	
		SubjectBean DuplicateSubjectName = findByName(stb.getName());
		if(DuplicateSubjectName != null){
			throw new DuplicateRecordException("Subject name Already Exsist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			System.out.println(pk + " in ModelJDBC");

			conn.setAutoCommit(false); // Begin transaction

			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_SUBJECT VALUES(?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, nextPK());
			ps.setString(2, stb.getName());
			ps.setString(3, stb.getDescription());
			ps.setString(4, stb.getCreatedBy());
			ps.setString(5, stb.getModifiedBy());
			ps.setTimestamp(6, stb.getCreatedDatetime());
			ps.setTimestamp(7, stb.getModifiedDatetime());
			ps.setString(8, courseName);
			ps.setLong(9, stb.getCourseId());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}
	/**
	 * delete a subject
	 *
	 */
	public void delete(SubjectBean stb) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_SUBJECT WHERE ID=?");
			ps.setLong(1, stb.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException(
						"Exception in Rollback of Delte Method of Subject Model" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delte Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * update a subject
	 *
	 */
	public void update(SubjectBean stb) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model update Started");
		Connection conn = null;
		CourseModel coumodel= new CourseModel();
		CourseBean coubean=    coumodel.findByPk(stb.getCourseId());
		String courseName= coubean.getName();
		
		
		SubjectBean beanExist = findByName(stb.getName());
		if(beanExist != null && stb.getId() != stb.getId()){
			throw new DuplicateRecordException("Subject name Already Exsist");
		}
		try {

			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement("UPDATE ST_SUBJECT SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=?,COURSE_NAME=?,COURSE_ID=?, WHERE ID=?");
			ps.setString(1, stb.getName());
			ps.setString(2, stb.getDescription());
			ps.setString(3, stb.getCreatedBy());
			ps.setString(4, stb.getModifiedBy());
			ps.setTimestamp(5, stb.getCreatedDatetime());
			ps.setTimestamp(6, stb.getModifiedDatetime());
			ps.setString(7, courseName);
			ps.setLong(8, stb.getCourseId());
			ps.setLong(9, stb.getId());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			// throw new ApplicationException("Exception in updating Subject ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * find a subject by name
	 *
	 */
	public SubjectBean findByName(String name) throws ApplicationException {
		// log.debug("Subject Model findByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE NAME=?");
		Connection conn = null;
		SubjectBean stb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				stb = new SubjectBean();

				stb.setId(rs.getLong(1));
				stb.setName(rs.getString(2));
				stb.setDescription(rs.getString(3));
				stb.setCreatedBy(rs.getString(4));
				stb.setModifiedBy(rs.getString(5));
				stb.setCreatedDatetime(rs.getTimestamp(6));
				stb.setModifiedDatetime(rs.getTimestamp(7));
				stb.setCourseName(rs.getString(8));
				stb.setCourseId(rs.getInt(9));
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			e.printStackTrace();
			
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Subject Model findByName method End");
		return stb;
	}
	/**
	 * find a subject by PK
	 *
	 */
	
	public SubjectBean findByPK(int pk) throws ApplicationException {
		// log.debug("Subject Model findBypk method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE ID=?");
		Connection conn = null;
		SubjectBean stb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				stb = new SubjectBean();

				stb.setId(rs.getLong(1));
				stb.setName(rs.getString(2));
				stb.setDescription(rs.getString(3));
				stb.setCreatedBy(rs.getString(4));
				stb.setModifiedBy(rs.getString(5));
				stb.setCreatedDatetime(rs.getTimestamp(6));
				stb.setModifiedDatetime(rs.getTimestamp(7));
				stb.setCourseName(rs.getString(8));
				stb.setCourseId(rs.getInt(9));
				
				
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in findByPk Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		// log.debug("Subject Model findByPk method End");
		return stb;
	}
	/**
	 * search a subject
	 *
	 */

	public List search(SubjectBean stb) throws ApplicationException {
		return search(stb, 0, 0);
	}
	/**
	 * search subject with pagination
	 *
	 */

	public List search(SubjectBean stb, int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Subject Model search method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1 ");
		System.out.println("model search" + stb.getId());

		if (stb != null) {
			if (stb.getId() > 0) {
				sql.append(" AND ID = " + stb.getId());
			}
			if (stb.getCourseId() > 0) {
				sql.append(" AND COURSE_ID = " + stb.getCourseId());
			}

			if (stb.getName() != null && stb.getName().length() > 0) {
				sql.append(" AND NAME like '" + stb.getName() + "%'");
			}
			if (stb.getCourseName() != null && stb.getCourseName().length() > 0) {
				sql.append(" AND COURSE_NAME like '" + stb.getCourseId() + "%'");
			}
			if (stb.getDescription() != null && stb.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + stb.getDescription() + " % ");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + " , " + pageSize);
		}
		
		Connection conn = null;
		ArrayList list = new ArrayList();
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				stb = new SubjectBean();

				stb.setId(rs.getLong(1));
				stb.setName(rs.getString(2));
				stb.setDescription(rs.getString(3));
				stb.setCreatedBy(rs.getString(4));
				stb.setModifiedBy(rs.getString(5));
				stb.setCreatedDatetime(rs.getTimestamp(6));
				stb.setModifiedDatetime(rs.getTimestamp(7));
				stb.setCourseName(rs.getString(8));
				stb.setCourseId(rs.getInt(9));
				
				list.add(stb);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("database Exception....", e);
			throw new ApplicationException("Exception in search Method of Subject Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		return list;
	}
	/**
	 * list of subject
	 *
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}
	/**
	 * list of subject with pagination
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Subject Model list method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT ");

		// Page Size is greater then Zero then apply pagination
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
				SubjectBean stb = new SubjectBean();

				stb.setId(rs.getLong(1));
				stb.setName(rs.getString(2));
				stb.setDescription(rs.getString(3));
				stb.setCreatedBy(rs.getString(4));
				stb.setModifiedBy(rs.getString(5));
				stb.setCreatedDatetime(rs.getTimestamp(6));
				stb.setModifiedDatetime(rs.getTimestamp(7));
				stb.setCourseName(rs.getString(8));
				stb.setCourseId(rs.getInt(9));
				list.add(stb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Subject Model list method End");
		return list;
	}
}