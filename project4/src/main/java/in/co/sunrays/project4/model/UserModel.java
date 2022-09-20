package in.co.sunrays.project4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import in.co.sunrays.project4.bean.UserBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DatabaseException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.exception.RecordNotFoundException;
import in.co.sunrays.project4.util.EmailBuilder;
import in.co.sunrays.project4.util.EmailMessage;
import in.co.sunrays.project4.util.EmailUtility;
import in.co.sunrays.project4.util.JDBCDataSource;

/**
 * JDBC Implementation of UserModel.
 * @author GARIMA
 *
 */
public class UserModel {

	private int roleId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	/**
	 * Find next PK
	 *
	 */
	public Integer nextPK() throws DatabaseException {
		// log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			// log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model nextPK End");
		return pk + 1;
	}
	/**
	 *Add a User
	 *
	 */

	public long add	(UserBean ub) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		UserBean existbean = findByLogin(ub.getLogin());
		if (existbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, nextPK());
			ps.setString(2, ub.getFirstName());
			ps.setString(3, ub.getLastName());
			ps.setString(4, ub.getLogin());
			ps.setString(5, ub.getPassword());
			ps.setString(6, ub.getConfirmPassword());
			ps.setDate(7, new java.sql.Date(ub.getDob().getTime()));
			ps.setString(8, ub.getMobileNo());
			ps.setLong(9, ub.getRoleId());
			ps.setString(10, ub.getGender());
			ps.setString(11, ub.getCreatedBy());
			ps.setString(12, ub.getModifiedBy());
			ps.setTimestamp(13, ub.getCreatedDatetime());
			ps.setTimestamp(14, ub.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("INSERTION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model add End");
		return pk;
	}
	/**
	 *delete a User
	 *
	 */
	public void delete(UserBean ub) throws ApplicationException {
		// log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
			ps.setLong(1, ub.getId());
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
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			System.out.println("DELETION DONE");
			JDBCDataSource.closeConnection(conn);
		}
	}
	/**
	 *find a User by loginId
	 *
	 */
	public UserBean findByLogin(String login) throws ApplicationException {
		// log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserBean ub = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setFirstName(rs.getString(2));
				ub.setLastName(rs.getString(3));
				ub.setLogin(rs.getString(4));
				ub.setPassword(rs.getString(5));
				ub.setConfirmPassword(rs.getString(6));
				ub.setDob(rs.getDate(7));
				ub.setMobileNo(rs.getString(8));
				ub.setRoleId(rs.getInt(9));
				ub.setGender(rs.getString(10));
				ub.setCreatedBy(rs.getString(11));
				ub.setModifiedBy(rs.getString(12));
				ub.setCreatedDatetime(rs.getTimestamp(13));
				ub.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			System.out.println("FIND BY LOGIN DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByLogin End");
		return ub;
	}
	/**
	 *find User by PK
	 *
	 */
	public UserBean findByPK(long pk) throws ApplicationException {
		// log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserBean ub = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ub = new UserBean();
				
				ub.setId(rs.getInt(1));
				ub.setFirstName(rs.getString(2));
				ub.setLastName(rs.getString(3));
				ub.setLogin(rs.getString(4));
				ub.setPassword(rs.getString(5));
				ub.setConfirmPassword(rs.getString(6));
				ub.setDob(rs.getDate(7));
				ub.setMobileNo(rs.getString(8));
				ub.setRoleId(rs.getInt(9));
				ub.setGender(rs.getString(10));
				ub.setCreatedBy(rs.getString(11));
				ub.setModifiedBy(rs.getString(12));
				ub.setCreatedDatetime(rs.getTimestamp(13));
				ub.setModifiedDatetime(rs.getTimestamp(14));

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			System.out.println("FIND BY PK DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model findByPK End");
		return ub;
	}
	/**
	 *update a User
	 *
	 */

	public void update(UserBean ub) throws ApplicationException, DuplicateRecordException {
		// log.debug("Model update Started");
		Connection conn = null;

		UserBean beanExist = findByLogin(ub.getLogin());
		// Check if updated LoginId already exist
		if (beanExist != null && !(beanExist.getId() == ub.getId())) {
			throw new DuplicateRecordException("LoginId is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,CONFIRM_PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,GENDER=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, ub.getFirstName());
			ps.setString(2, ub.getLastName());
			ps.setString(3, ub.getLogin());
			ps.setString(4, ub.getPassword());
			ps.setString(5, ub.getConfirmPassword());
			ps.setDate(6, new java.sql.Date(ub.getDob().getTime()));
			ps.setString(7, ub.getMobileNo());
			ps.setLong(8, ub.getRoleId());
			ps.setString(9, ub.getGender());
			ps.setString(10, ub.getCreatedBy());
			ps.setString(11, ub.getModifiedBy());
			ps.setTimestamp(12, ub.getCreatedDatetime());
			ps.setTimestamp(13, ub.getModifiedDatetime());
			ps.setLong(14, ub.getId());
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
			throw new ApplicationException("Exception in updating User ");
		} finally {
			System.out.println("UPDATION DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model update End");
	}
	/**
	 *search a User
	 *
	 */
	public List search(UserBean ub) throws ApplicationException {
		return search(ub, 0, 0);
	}
	/**
	 *search a User with pagination
	 *
	 */

	public List search(UserBean ub, int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

		if (ub != null) {
			if (ub.getId() > 0) {
				sql.append(" AND id = " + ub.getId());
			}
			if (ub.getFirstName() != null && ub.getFirstName().length() > 0) {
				sql.append(" AND FIRST_NAME like '" + ub.getFirstName() + "%'");
			}
			if (ub.getLastName() != null && ub.getLastName().length() > 0) {
				sql.append(" AND LAST_NAME like '" + ub.getLastName() + "%'");
			}
			if (ub.getLogin() != null && ub.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + ub.getLogin() + "%'");
			}
			if (ub.getPassword() != null && ub.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + ub.getPassword() + "%'");
			}
			if (ub.getDob() != null && ub.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + ub.getDob());
			}
			if (ub.getMobileNo() != null && ub.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + ub.getMobileNo());
			}
			if (ub.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + ub.getRoleId());
			}

			if (ub.getGender() != null && ub.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + ub.getGender() + "%'");
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setFirstName(rs.getString(2));
				ub.setLastName(rs.getString(3));
				ub.setLogin(rs.getString(4));
				ub.setPassword(rs.getString(5));
				ub.setConfirmPassword(rs.getString(6));
				ub.setDob(rs.getDate(7));
				ub.setMobileNo(rs.getString(8));
				ub.setRoleId(rs.getInt(9));
				ub.setGender(rs.getString(10));
				ub.setCreatedBy(rs.getString(11));
				ub.setModifiedBy(rs.getString(12));
				ub.setCreatedDatetime(rs.getTimestamp(13));
				ub.setModifiedDatetime(rs.getTimestamp(14));

				list.add(ub);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search user");
		} finally {
			System.out.println("SEARCH DONE");
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model search End");
		return list;
	}
	/**
	 *list of User
	 *
	 */

	public List list() throws ApplicationException {
		return list(0, 0);
	}
	/**
	 *list of User with pagination
	 *
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		// log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_USER");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UserBean ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setFirstName(rs.getString(2));
				ub.setLastName(rs.getString(3));
				ub.setLogin(rs.getString(4));
				ub.setPassword(rs.getString(5));
				ub.setConfirmPassword(rs.getString(6));
				ub.setDob(rs.getDate(7));
				ub.setMobileNo(rs.getString(8));
				ub.setRoleId(rs.getInt(9));
				ub.setGender(rs.getString(10));
				ub.setCreatedBy(rs.getString(11));
				ub.setModifiedBy(rs.getString(12));
				ub.setCreatedDatetime(rs.getTimestamp(13));
				ub.setModifiedDatetime(rs.getTimestamp(14));

				list.add(ub);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			System.out.println("LIST DONE");
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model list End");
		return list;

	}
	/**
	 *authenticate a User
	 *
	 */

	public UserBean authenticate(String login, String password) throws Exception {
		// log.debug("Model authenticate Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN = ? AND PASSWORD = ?");
		UserBean ub = null;
		Connection conn = null;

		try {			
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setFirstName(rs.getString(2));
				ub.setLastName(rs.getString(3));
				ub.setLogin(rs.getString(4));
				ub.setPassword(rs.getString(5));
				ub.setConfirmPassword(rs.getString(6));
				ub.setDob(rs.getDate(7));
				ub.setMobileNo(rs.getString(8));
				ub.setRoleId(rs.getInt(9));
				ub.setGender(rs.getString(10));
				ub.setCreatedBy(rs.getString(11));
				ub.setModifiedBy(rs.getString(12));
				ub.setCreatedDatetime(rs.getTimestamp(13));
				ub.setModifiedDatetime(rs.getTimestamp(14));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("AUTHENTICATION DONE");
			JDBCDataSource.closeConnection(conn);
		}

		// log.debug("Model authenticate End");
		return ub;
	}
	/**
	 *Get Role of a User
	 *
	 */

	public List getRole(UserBean ub) throws ApplicationException {
		// log.debug("Model get roles Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ROLE_ID=?");
		Connection conn = null;
		List list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1, ub.getRoleId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ub = new UserBean();
				ub.setId(rs.getInt(1));
				ub.setFirstName(rs.getString(2));
				ub.setLastName(rs.getString(3));
				ub.setLogin(rs.getString(4));
				ub.setPassword(rs.getString(5));
				ub.setConfirmPassword(rs.getString(6));
				ub.setDob(rs.getDate(7));
				ub.setMobileNo(rs.getString(8));
				ub.setRoleId(rs.getInt(9));
				ub.setGender(rs.getString(10));
				ub.setCreatedBy(rs.getString(11));
				ub.setModifiedBy(rs.getString(12));
				ub.setCreatedDatetime(rs.getTimestamp(13));
				ub.setModifiedDatetime(rs.getTimestamp(14));

				list.add(ub);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			System.out.println("GET BY ROLEID DONE");
			JDBCDataSource.closeConnection(conn);
		}
		// log.debug("Model get roles End");
		return list;
	}
	/**
	 *Change Password
	 *
	 */

	public boolean changePassword(int id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		// log.debug("model changePassword Started");
		boolean flag = false;
		UserBean beanExist = null;

		beanExist = findByPK(id);
		if (beanExist != null && beanExist.getPassword().equals(oldPassword)) {

			beanExist.setPassword(newPassword);
			try {
				update(beanExist);
			} catch (DuplicateRecordException e) { // log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}
			flag = true;
		} else {
			throw new RecordNotFoundException("Login does not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", beanExist.getLogin());
		map.put("password", beanExist.getPassword());
		map.put("firstName", beanExist.getFirstName());
		map.put("lastName", beanExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(beanExist.getLogin());
		msg.setSubject("SUNRAYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

	//	EmailUtility.sendMail(msg);

		// log.debug("Model changePassword End"); 
		return flag;

	}

	public UserBean updateAccess(UserBean ub) throws ApplicationException {
		return null;
	}
	/**
	 *Reset User
	 *
	 */
	public int registerUser(UserBean ub) throws ApplicationException, DuplicateRecordException, RecordNotFoundException {

		// log.debug("Model add Started");

		int pk = (int) add(ub);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", ub.getLogin());
		map.put("password", ub.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(ub.getLogin());
		msg.setSubject("Registration is successful for ORS Project");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		return pk;
	}
	/**
	 *Reset Password
	 *
	 */

	public boolean resetPassword(UserBean ub) throws ApplicationException {

		String newPassword = String.valueOf(new Date().getTime()).substring(0, 4);
		UserBean userData = findByPK(ub.getId());
		userData.setPassword(newPassword);

		try {
			update(userData);
		} catch (DuplicateRecordException e) {
			return false;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", ub.getLogin());
		map.put("password", ub.getPassword());
		map.put("firstName", ub.getFirstName());
		map.put("lastName", ub.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(ub.getLogin());
		msg.setSubject("Password has been reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return true;
	}
	/**
	 *Forget Password
	 *
	 */

	public boolean forgetPassword(String login) throws ApplicationException, RecordNotFoundException {
		
		 UserBean userData = findByLogin(login);
	        boolean flag = false;

	        if (userData == null) {
	            throw new RecordNotFoundException("Email ID does not exist!");

	        }

	        HashMap<String, String> map = new HashMap<String, String>();
	        map.put("login", userData.getLogin());
	        map.put("password", userData.getPassword());
	        map.put("firstName", userData.getFirstName());
	        map.put("lastName", userData.getLastName());
	        
	        String message = EmailBuilder.getForgetPasswordMessage(map);
	        
	        EmailMessage msg = new EmailMessage();
	        msg.setTo(login);
	        msg.setSubject("SUNRAYS ORS Password Reset");
	        msg.setMessage(message);
	        msg.setMessageType(EmailMessage.HTML_MSG);
	        
	     boolean check =   EmailUtility.sendMail(msg);
	        
	        flag = check;

	        return flag;
	    }

	

}
