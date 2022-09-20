package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.project4.bean.CollegeBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DatabaseException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.util.JDBCDataSource;


/**
 * JDBC Implementation of CollegeModel.
 * @author GARIMA
 *
 */
public class CollegeModel {
	/**
	 * Find next PK of College
	 *
	 */
	public long nextPK() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM st_college");
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
	 * Add a College
	 *
	 */

	public long add(CollegeBean cb) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		int pk = 0;

		CollegeBean duplicateCollegeName = findbyname(cb.getName());
		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("College Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO st_college VALUES(?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, nextPK());
			ps.setString(2, cb.getName());
			ps.setString(3, cb.getAddress());
			ps.setString(4, cb.getState());
			ps.setString(5, cb.getCity());
			ps.setString(6, cb.getPhoneNo());
			ps.setString(7, cb.getCreatedBy());
			ps.setString(8, cb.getModifiedBy());
			ps.setTimestamp(9, cb.getCreatedDatetime());
			ps.setTimestamp(10, cb.getModifiedDatetime());

			ps.executeUpdate();
			conn.commit();

			System.out.println("Insertion Done");
			ps.close();
		} catch (Exception e) {
			// log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}
	/**
	 * Delete a College
	 *
	 */

	public void delete(CollegeBean cb) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM st_college WHERE ID=?");

			ps.setLong(1, cb.getId());

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
	 * Update a College
	 *
	 */
	public void update(CollegeBean cb) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		CollegeBean beanExist = findbyname(cb.getName());

		// Check if updated College already exist
		if (beanExist != null && beanExist.getId() != cb.getId()) {

			throw new DuplicateRecordException("College is already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?, ADDRESS=?, STATE=?, CITY=?, PHONE_NO=?, CREATED_BY=?, MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");

			ps.setString(1, cb.getName());
			ps.setString(2, cb.getAddress());
			ps.setString(3, cb.getState());
			ps.setString(4, cb.getCity());
			ps.setString(5, cb.getPhoneNo());
			ps.setString(6, cb.getCreatedBy());
			ps.setString(7, cb.getModifiedBy());
			ps.setTimestamp(8, cb.getCreatedDatetime());
			ps.setTimestamp(9, cb.getModifiedDatetime());
			ps.setLong(10, cb.getId());

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
			throw new ApplicationException("Exception in updating College ");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 * Find by name of a College
	 *
	 */

	public CollegeBean findbyname(String name) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE NAME=?");
		Connection conn = null;
		CollegeBean cb = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cb = new CollegeBean();
				cb.setId(rs.getLong(1));
				cb.setName(rs.getString(2));
				cb.setAddress(rs.getString(3));
				cb.setState(rs.getString(4));
				cb.setCity(rs.getString(5));
				cb.setPhoneNo(rs.getString(6));
				cb.setCreatedBy(rs.getString(7));
				cb.setModifiedBy(rs.getString(8));
				cb.setCreatedDatetime(rs.getTimestamp(9));
				cb.setModifiedDatetime(rs.getTimestamp(10));
			}
			conn.commit();
			ps.execute();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting College by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return cb;
	}
	/**
	 * Find by PK of a College
	 *
	 */

	public CollegeBean findbypk(long pk) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		StringBuffer sql = new StringBuffer("Select * from st_college where ID=?");
		Connection conn = null;
		CollegeBean cb = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cb = new CollegeBean();
				cb.setId(rs.getLong(1));
				cb.setName(rs.getString(2));
				cb.setAddress(rs.getString(3));
				cb.setState(rs.getString(4));
				cb.setCity(rs.getString(5));
				cb.setPhoneNo(rs.getString(6));
				cb.setCreatedBy(rs.getString(7));
				cb.setModifiedBy(rs.getString(8));
				cb.setCreatedDatetime(rs.getTimestamp(9));
				cb.setModifiedDatetime(rs.getTimestamp(10));
			}
			conn.commit();
			ps.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return cb;
	}
	/**
	 * Search of a College
	 *
	 */

	public List search(CollegeBean cb, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");

		if (cb != null) {
			if (cb.getId() > 0) {
				sql.append(" AND id = " + cb.getId());
			}
			if (cb.getName() != null && cb.getName().length() > 0) {
				sql.append(" AND NAME like '" + cb.getName() + "%'");
			}
			if (cb.getAddress() != null && cb.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + cb.getAddress() + "%'");
			}
			if (cb.getState() != null && cb.getState().length() > 0) {
				sql.append(" AND STATE like '" + cb.getState() + "%'");
			}
			if (cb.getCity() != null && cb.getCity().length() > 0) {
				sql.append(" AND CITY like '" + cb.getCity() + "%'");
			}
			if (cb.getPhoneNo() != null && cb.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + cb.getPhoneNo());
			}

		}// if page size is greater than zero then apply pagination
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
				cb = new CollegeBean();
				cb.setId(rs.getLong(1));
				cb.setName(rs.getString(2));
				cb.setAddress(rs.getString(3));
				cb.setState(rs.getString(4));
				cb.setCity(rs.getString(5));
				cb.setPhoneNo(rs.getString(6));
				cb.setCreatedBy(rs.getString(7));
				cb.setModifiedBy(rs.getString(8));
				cb.setCreatedDatetime(rs.getTimestamp(9));
				cb.setModifiedDatetime(rs.getTimestamp(10));
				list.add(cb);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	public List search(CollegeBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List list() throws Exception {
		return list(0,0);
	}
	/**
	 * Find list of a College
	 *
	 */

	public List list(int pageNo, int pageSize) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
		// if page size is greater than zero then apply pagination

		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			CollegeBean cb = null;
			while (rs.next()) {
				cb = new CollegeBean();

				cb.setId(rs.getLong(1));
				cb.setName(rs.getString(2));
				cb.setAddress(rs.getString(3));
				cb.setState(rs.getString(4));
				cb.setCity(rs.getString(5));
				cb.setPhoneNo(rs.getString(6));
				cb.setCreatedBy(rs.getString(7));
				cb.setModifiedBy(rs.getString(8));
				cb.setCreatedDatetime(rs.getTimestamp(9));
				cb.setModifiedDatetime(rs.getTimestamp(10));
				list.add(cb);
			}
			conn.commit();
			ps.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
}
