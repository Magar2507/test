package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DatabaseException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;

/**
 * JDBC Implementation of Course Model.
 * @author GARIMA
 *
 */
public class CourseModel {
	/**
	 * Next PK Course
	 *
	 */

	public long nextPK() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM ST_COURSE");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
// log.error("Exception in Database..", e);
			throw new DatabaseException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}
	/**
	 * Add a Course
	 *
	 */

	public long add(CourseBean crsb) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		CourseBean duplicateCourseName = findByName(crsb.getName());
		if (duplicateCourseName != null) {
			throw new DuplicateRecordException("Course Name already Exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_COURSE VALUES(?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPK());
			ps.setString(2, crsb.getName());
			ps.setString(3, crsb.getDuration());
			ps.setString(4, crsb.getDescription());
			ps.setString(5, crsb.getCreatedBy());
			ps.setString(6, crsb.getModifiedBy());
			ps.setTimestamp(7, crsb.getCreatedDatetime());
			ps.setTimestamp(8, crsb.getModifiedDatetime());
			
			ps.executeUpdate();

			conn.commit();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add Rollback Exception.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Course Add method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}
	/**
	 * Delete a Course
	 *
	 */
	
	public void delete(CourseBean crsb) throws ApplicationException {
// log.debug("CourseModel Delete Method Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_COURSE WHERE ID=?");
			ps.setLong(1, crsb.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Exception in Rollback Method" + ex.getMessage());
			}
			throw new ApplicationException("Exception in Delete Method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * Update a Course
	 *
	 */

	public void update(CourseBean crsb) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;

		CourseBean beanExist = findByName(crsb.getName());
		if (beanExist != null && beanExist.getId() != crsb.getId()) {
			throw new DuplicateRecordException("Course Already Exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_COURSE SET NAME=?,DURATION=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, crsb.getName());
			ps.setString(2, crsb.getDuration());
			ps.setString(3, crsb.getDescription());
			ps.setString(4, crsb.getCreatedBy());
			ps.setString(5, crsb.getModifiedBy());
			ps.setTimestamp(6, crsb.getCreatedDatetime());
			ps.setTimestamp(7, crsb.getModifiedDatetime());
			ps.setLong(8, crsb.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
// log.debug("Database Exception ...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : Exception in Rollback.." + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating the Course Model");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * find by name of a Course
	 *
	 */

	public CourseBean findByName(String name) throws ApplicationException {
// log.debug("Course Model FindByName method Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE NAME=?");
		CourseBean crsb = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
                crsb = new CourseBean();
                crsb.setId(rs.getLong(1));
                crsb.setName(rs.getString(2));
                crsb.setDescription(rs.getString(3));
                crsb.setDuration(rs.getString(4));
                crsb.setCreatedBy(rs.getString(5));
                crsb.setModifiedBy(rs.getString(6));
                crsb.setCreatedDatetime(rs.getTimestamp(7));
                crsb.setModifiedDatetime(rs.getTimestamp(8));
               
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
         } finally {
			JDBCDataSource.closeConnection(conn);
		}
// log.debug("Course Model FindByName method End");
		return crsb;
	}
	/**
	 * find by pk of a Course
	 *
	 */

	public CourseBean findByPk(int pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE ID=?");
		CourseBean crsb = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				crsb = new CourseBean();
				crsb.setId(rs.getLong(1));
                crsb.setName(rs.getString(2));
                crsb.setDescription(rs.getString(3));
                crsb.setDuration(rs.getString(4));
                crsb.setCreatedBy(rs.getString(5));
                crsb.setModifiedBy(rs.getString(6));
                crsb.setCreatedDatetime(rs.getTimestamp(7));
                crsb.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
// log.error("DatabaseException ... ", e);
			throw new ApplicationException("Exception : Exception in the findbyPk method");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
// log.debug("CourseModel FindbyPK method End");
		return crsb;
	}

	/**
	 * search a Course
	 *
	 */
	
	public List search(CourseBean crsb) throws ApplicationException {
		return search(crsb, 0, 0);
	}

	/**
	 * search a Course
	 *
	 */

	public List search(CourseBean crsb, int pageNo, int pageSize) throws ApplicationException {

		System.out.println("inside search");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		if (crsb != null) {
			if (crsb.getId() > 0) {
				sql.append(" AND id = " + crsb.getId());
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				crsb = new CourseBean();
				crsb.setId(rs.getLong(1));
                crsb.setName(rs.getString(2));
                crsb.setDescription(rs.getString(3));
                crsb.setDuration(rs.getString(4));
                crsb.setCreatedBy(rs.getString(5));
                crsb.setModifiedBy(rs.getString(6));
                crsb.setCreatedDatetime(rs.getTimestamp(7));
                crsb.setModifiedDatetime(rs.getTimestamp(8));
				list.add(crsb);

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception in the Search Method" + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("----------------------------------->>>>" + list.size());
		return list;
	}
	/**
	 * list of Course
	 *
	 */
	

	public List list() throws ApplicationException {
		return list(0, 0);
	}
	/**
	 * list of Course
	 *
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {
// log.debug("CourseModel List Method End");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE");
// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CourseBean crsb = new CourseBean();
				crsb.setId(rs.getLong(1));
                crsb.setName(rs.getString(2));
                crsb.setDescription(rs.getString(3));
                crsb.setDuration(rs.getString(4));
                crsb.setCreatedBy(rs.getString(5));
                crsb.setModifiedBy(rs.getString(6));
                crsb.setCreatedDatetime(rs.getTimestamp(7));
                crsb.setModifiedDatetime(rs.getTimestamp(8));
				list.add(crsb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

			throw new ApplicationException("Exception : Exception in CourseModel List method " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}