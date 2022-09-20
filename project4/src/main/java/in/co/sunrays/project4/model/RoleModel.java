package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



import in.co.sunrays.project4.exception.DatabaseException;
import in.co.sunrays.project4.exception.RecordNotFoundException;
import in.co.sunrays.project4.bean.RoleBean;
import in.co.sunrays.project4.bean.UserBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;


/**
 * JDBC Implementation of Role Model.
 * @author GARIMA
 *
 */
public class RoleModel {
	/**
	 * Find next PK
	 *
	 */
	public long nextPK() throws Exception {
		
		Connection conn = null;

		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_role");
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
	 *Add a Role
	 *
	 */

	public int add(RoleBean rb) throws ApplicationException, DuplicateRecordException, RecordNotFoundException, Exception {
		Connection conn = null;
		int pk = 0;
		RoleBean existbean = findByName(rb.getName());
		if (existbean != null) {
			throw new DuplicateRecordException("Role already exists");
		}
		
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);// Begin transaction

			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
			ps.setLong(1, nextPK());
			ps.setString(2, rb.getName());
			ps.setString(3, rb.getDescription());
			ps.setString(4, rb.getCreatedBy());
			ps.setString(5, rb.getModifiedBy());
			ps.setTimestamp(6, rb.getCreatedDatetime());
			ps.setTimestamp(7, rb.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();

		} catch (Exception e) {
			e.printStackTrace();
			// log.error("DataException.", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();

			}

		} finally {
			System.out.println("INSERTION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}
	/**
	 * Delete a Role
	 *
	 */

	
	public void delete(RoleBean rb) throws ApplicationException {
		// log.debug("Model deleted started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// Begin transaction
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_ROLE WHERE ID=?");
			ps.setLong(1, rb.getId());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			// log.error("Database Exception",e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete rollback exception" + ex.getMessage());
			}
		} finally {
			System.out.println("DELETION DONE");
			JDBCDataSource.closeConnection(conn);
		}
			}
	/**
	 * Update a Role
	 *
	 */
	public void update(RoleBean bean) throws ApplicationException {
		// log.debug("Model Updated Started");
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());
			pstmt.executeUpdate();
			conn.commit();// End Transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception.., e");
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Updating Role");
		} finally {
			System.out.println("UPDATION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model Update End");
	}

	/**
	 * Find a Role by PK
	 *
	 */
	public RoleBean findByPK(long PK) throws ApplicationException {
		
		RoleBean rb = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_ROLE WHERE ID=?");
			ps.setLong(1, PK);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rb = new RoleBean();
				rb.setId(rs.getLong(1));
				rb.setName(rs.getString(2));
				rb.setDescription(rs.getString(3));
				rb.setCreatedBy(rs.getString(4));
				rb.setModifiedBy(rs.getString(5));
				rb.setCreatedDatetime(rs.getTimestamp(6));
				rb.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		} catch (Exception e) {
			
		} finally {
			System.out.println("DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model FindByPK End");
		return rb;

	}
	/**
	 * Find a Role by NAME
	 *
	 */

	public RoleBean findByName(String Name) throws Exception {
		// log.debug("Model FindBY EmailId Started");
		RoleBean rb = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_ROLE WHERE NAME=?");
			ps.setString(1, Name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rb = new RoleBean();
				rb.setId(rs.getInt(1));
				rb.setName(rs.getString(2));
				rb.setDescription(rs.getString(3));
				rb.setCreatedBy(rs.getString(4));
				rb.setModifiedBy(rs.getString(5));
				rb.setCreatedDatetime(rs.getTimestamp(6));
				rb.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			// throw new RecordNotFoundException("name not avalaible");

		} finally {
			System.out.println("DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model FindByEmail End");
		return rb;

	}
	/**
	 *search a role
	 *
	 */
	
	public List search(RoleBean rb) throws ApplicationException {
		return Search(rb, 0, 0);
	}
	/**
	 * search a role with pagination
	 *
	 */
	public List Search(RoleBean rb, int pageNo, int pageSize) {
		// log.debug("Model Search Started");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE WHERE 1=1");

		ArrayList list = new ArrayList();

		if (rb != null) {
			if (rb.getId() > 0) {
				sql.append(" And id = " + rb.getId());
			}
			if (rb.getName() != null && rb.getName().length() > 0) {
				sql.append(" And Name Like '" + rb.getName() + "%'");
			}
			if (rb.getDescription() != null && rb.getDescription().length() > 0) {
				sql.append(" And Description like '" + rb.getDescription() + "%'");
				System.out.println(sql);
			}
		}
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rb = new RoleBean();
				rb.setId(rs.getInt(1));
				rb.setName(rs.getString(2));
				rb.setDescription(rs.getString(3));
				rb.setCreatedBy(rs.getString(4));
				rb.setModifiedBy(rs.getString(5));
				rb.setCreatedDatetime(rs.getTimestamp(6));
				rb.setModifiedDatetime(rs.getTimestamp(7));
				list.add(rb);
			}

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model search end");
		return list;
	}

	/**
	 * list of Role
	 *
	 */
	public List list() throws ApplicationException {
		return list(0, 0);

	}
	/**
	 * list of Role with pagination
	 *
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_ROLE");
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
				RoleBean rb = new RoleBean();
				rb.setId(rs.getInt(1));
				rb.setName(rs.getString(2));
				rb.setDescription(rs.getString(3));
				rb.setCreatedBy(rs.getString(4));
				rb.setModifiedBy(rs.getString(5));
				rb.setCreatedDatetime(rs.getTimestamp(6));
				rb.setModifiedDatetime(rs.getTimestamp(7));
				list.add(rb);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model list End");
		return list;

	}
}