package in.co.sunrays.project4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.RoleBean;
import in.co.sunrays.project4.bean.UserBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.model.RoleModel;
import in.co.sunrays.project4.model.UserModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.DataValidator;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login.
 * @author GARIMA
 *
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("LoginCtl Method validate Started");

		boolean pass = true;

		String op = request.getParameter("operation");
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		// String login = request.getParameter("login");

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass 	= false;
		} 
//			  else if (!DataValidator.isPassword(request.getParameter("password"))) {
//			  request.setAttribute(
//			 "password","Password must contain alphanumeric characters"); pass = false; }

		log.debug("LoginCtl Method validate Ended");
		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("LoginCtl Method populatebean Started");

		UserBean ub = new UserBean();

		ub.setId(DataUtility.getInt(request.getParameter("id")));
		ub.setLogin(DataUtility.getString(request.getParameter("login")));
		ub.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");

		return ub;
	}

	/**
	 * Display Login form
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel um = new UserModel();
		RoleModel rm = new RoleModel();

		int id = DataUtility.getInt(request.getParameter("id"));
		
		if (OP_LOG_OUT.equals(op)) {

			session = request.getSession();
			session.invalidate();

			ServletUtility.setSuccessMessage("User Logged Out Successfully", request);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);

			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");

	}

	/**
	 * Submitting or login action performing
	 *
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		log.debug(" Method doPost Started");

		if (session != null) {
			System.out.println("session created");
		} else {
			System.out.println("session not created");
		}
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		UserModel um = new UserModel();
		RoleModel rm = new RoleModel();
		String str = request.getParameter("URI");
		int id = DataUtility.getInt(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean ub = (UserBean) populateBean(request);

			try {

				ub = um.authenticate(ub.getLogin(), ub.getPassword());

				if (ub != null) {
					session.setAttribute("user", ub);
					long roleId = ub.getRoleId();

					RoleBean rb = rm.findByPK(roleId);

					if (rb != null) {
						session.setAttribute("role", rb.getName());
					}

					if (str == null || "null".equalsIgnoreCase(str)) {
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						// System.out.println("Uri got " + str);
						ServletUtility.redirect(str, request, response);
						return;
					}

					// ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
					// return;
				} else {
					ub = (UserBean) populateBean(request);
				//	System.out.println(" Login  " + ub.getLogin());
					ServletUtility.setBean(ub, request);
					ServletUtility.setErrorMessage("Invalid Login Id And Password!", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} /*
			 * else if (OP_LOG_OUT.equals(op)) {
			 * 
			 * session = request.getSession();
			 * 
			 * session.invalidate();
			 * 
			 * ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
			 * 
			 * return;
			 * 
			 * }
			 */ else if (OP_SIGN_UP.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}