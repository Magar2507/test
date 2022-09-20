package in.co.sunrays.project4.model;

import in.co.sunrays.project4.model.StudentModel;
import in.co.sunrays.project4.bean.MarksheetBean;
import in.co.sunrays.project4.bean.StudentBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DatabaseException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC Implementation of Marksheet Model.
 * @author GARIMA
 *
 */
public class MarksheetModel {

	public long nextPK() throws DatabaseException {

		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_MARKSHEET");
			ResultSet rs = pstmt.executeQuery();
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
	 * Adds a Marksheet
	 * 
	 * @param bean
	 * @throws DatabaseException
	 * 
	 */

	public int add(MarksheetBean mb) throws Exception, DuplicateRecordException {

		// log.debug("Model add Started");

		Connection conn = null;

		// // get Student Name
		StudentModel sm = new StudentModel();
		StudentBean sb = sm.findByPK(mb.getStudentId());
		String studentname = (sb.getFirstName() + " " + sb.getLastName());

		MarksheetBean duplicateMarksheet = findByRollNo(mb.getRollNo());
		int pk = 0;

		if (duplicateMarksheet != null) {
			throw new DuplicateRecordException("Roll Number already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextPK());
			ps.setString(2, mb.getRollNo());
			ps.setLong(3, mb.getStudentId());
			ps.setString(4, studentname);
			ps.setInt(5, mb.getPhysics());
			ps.setInt(6, mb.getChemistry());
			ps.setInt(7, mb.getMaths());
			ps.setString(8, mb.getCreatedBy());
			ps.setString(9, mb.getModifiedBy());
			ps.setTimestamp(10, mb.getCreatedDatetime());
			ps.setTimestamp(11, mb.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");
		} finally {
			System.out.println("INSERTION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}

	/**
	 * Deletes a Marksheet
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(MarksheetBean mb) throws Exception {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_marksheet WHERE ID=?");

			ps.setLong(1, mb.getId());

			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {

			System.out.println("DELETION Done");
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Finds Marksheet by Roll No
	 * 
	 * @param rollNo : get parameter
	 * @return bean
	 * @throws DuplicateRecordException
	 */

	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		// log.debug("Model findByRollNo Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");

		Connection conn = null;
		MarksheetBean mb = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, rollNo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				mb = new MarksheetBean();
				mb.setId(rs.getLong(1));
				mb.setRollNo(rs.getString(2));
				mb.setStudentId(rs.getLong(3));
				mb.setName(rs.getString(4));
				mb.setPhysics(rs.getInt(5));
				mb.setChemistry(rs.getInt(6));
				mb.setMaths(rs.getInt(7));
				mb.setCreatedBy(rs.getString(8));
				mb.setModifiedBy(rs.getString(9));
				mb.setCreatedDatetime(rs.getTimestamp(10));
				mb.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new ApplicationException("Exception in getting marksheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model findByRollNo End");
		return mb;
	}

	/**
	 * Finds Marksheet by PK
	 * 
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public MarksheetBean findByPK(long pk) throws Exception {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE ID=?");
		MarksheetBean mb = null;
		Connection conn = null;
	
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				mb = new MarksheetBean();
				mb.setId(rs.getLong(1));
				mb.setRollNo(rs.getString(2));
				mb.setStudentId(rs.getLong(3));
				mb.setName(rs.getString(4));
				mb.setPhysics(rs.getInt(5));
				mb.setChemistry(rs.getInt(6));
				mb.setMaths(rs.getInt(7));
				mb.setCreatedBy(rs.getString(8));
				mb.setModifiedBy(rs.getString(9));
				mb.setCreatedDatetime(rs.getTimestamp(10));
				mb.setModifiedDatetime(rs.getTimestamp(11));
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return mb;

	}

	/**
	 * Updates a Marksheet
	 * 
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(MarksheetBean mb) throws ApplicationException, DuplicateRecordException {

		// log.debug("Model update Started");
		Connection conn = null;
		MarksheetBean beanExist = findByRollNo(mb.getRollNo());
		// Check if updated Roll no already exist
		if (beanExist != null && beanExist.getId() != mb.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}

		// get Student Name
		StudentModel sModel = new StudentModel();
		StudentBean studentbean = sModel.findByPK(mb.getStudentId());

		mb.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, mb.getRollNo());
			ps.setLong(2, mb.getStudentId());
			ps.setString(3, mb.getName());
			ps.setInt(4, mb.getPhysics());
			ps.setInt(5, mb.getChemistry());
			ps.setInt(6, mb.getMaths());
			ps.setString(7, mb.getCreatedBy());
			ps.setString(8, mb.getModifiedBy());
			ps.setTimestamp(9, mb.getCreatedDatetime());
			ps.setTimestamp(10, mb.getModifiedDatetime());
			ps.setLong(11, mb.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			// log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Marksheet ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

//		log.debug("Model update End");
	}

	/**
	 * Searches Marksheet
	 * 
	 * @param bean : Search Parameters
	 * @throws DatabaseException
	 */

	public List search(MarksheetBean mb) throws ApplicationException {
		return search(mb, 0, 0);
	}

	/**
	 * Searches Marksheet with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * 
	 * @throws DatabaseException
	 */

	public List search(MarksheetBean mb, int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET where true=true");

		if (mb != null) {
			System.out.println("service" + mb.getName());
			if (mb.getId() > 0) {
				sql.append(" AND id = " + mb.getId());
			}
			if (mb.getRollNo() != null && mb.getRollNo().length() > 0) {
				sql.append(" AND roll_no like '" + mb.getRollNo() + "%'");
			}
			if (mb.getName() != null && mb.getName().length() > 0) {
				sql.append(" AND name like '" + mb.getName() + "%'");
			}
			if (mb.getPhysics() != 0 && mb.getPhysics() > 0) {
				sql.append(" AND physics = " + mb.getPhysics());
			}
			if (mb.getChemistry() != 0 && mb.getChemistry() > 0) {
				sql.append(" AND chemistry = " + mb.getChemistry());
			}
			if (mb.getMaths() != 0 && mb.getMaths() > 0) {
				sql.append(" AND maths = '" + mb.getMaths());
			}

		}

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
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				mb = new MarksheetBean();
				mb.setId(rs.getLong(1));
				mb.setRollNo(rs.getString(2));
				mb.setStudentId(rs.getLong(3));
				mb.setName(rs.getString(4));
				mb.setPhysics(rs.getInt(5));
				mb.setChemistry(rs.getInt(6));
				mb.setMaths(rs.getInt(7));
				mb.setCreatedBy(rs.getString(8));
				mb.setModifiedBy(rs.getString(9));
				mb.setCreatedDatetime(rs.getTimestamp(10));
				mb.setModifiedDatetime(rs.getTimestamp(11));
				list.add(mb);
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new ApplicationException("Update rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

// log.debug("Model search End");
		return list;
	}

	/**
	 * Gets List of Marksheet
	 * 
	 * @return list : List of Marksheets
	 * @throws DatabaseException
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		// log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_MARKSHEET");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		MarksheetBean mb = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				mb = new MarksheetBean();
				mb.setId(rs.getLong(1));
				mb.setRollNo(rs.getString(2));
				mb.setStudentId(rs.getLong(3));
				mb.setName(rs.getString(4));
				mb.setPhysics(rs.getInt(5));
				mb.setChemistry(rs.getInt(6));
				mb.setMaths(rs.getInt(7));
				mb.setCreatedBy(rs.getString(8));
				mb.setModifiedBy(rs.getString(9));
				mb.setCreatedDatetime(rs.getTimestamp(10));
				mb.setModifiedDatetime(rs.getTimestamp(11));
				list.add(mb);
			}
			rs.close();
		} catch (Exception e) {
			// log.error(e);
			throw new ApplicationException("Exception in getting list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model list End");
		return list;

	}

	/**
	 * get Merit List of Marksheet with pagination
	 * 
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model MeritList Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"SELECT ID, ROLL_NO , NAME , PHYSICS , CHEMISTRY , MATHS , (PHYSICS + CHEMISTRY + MATHS) as total from ST_MARKSHEET WHERE (PHYSICS>33 AND CHEMISTRY>33 AND MATHS>33) ORDER BY TOTAL DESC");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetBean mb = new MarksheetBean();
				mb.setId(rs.getInt(1));
				mb.setRollNo(rs.getString(2));
				mb.setName(rs.getString(3));
				mb.setPhysics(rs.getInt(4));
				mb.setChemistry(rs.getInt(5));
				mb.setMaths(rs.getInt(6));
				list.add(mb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
			throw new ApplicationException("Exception in getting merit list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model MeritList End");
		return list;
	}
}