package in.co.sunrays.project4.controller;

import in.co.sunrays.project4.controller.BaseCtl;
import in.co.sunrays.project4.controller.ORSView;
import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.CollegeBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.CollegeModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.DataValidator;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College.
 * @author GARIMA
 *
 */
@WebServlet("/ctl/CollegeCtl")
public class CollegeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	// private static Logger log = Logger.getLogger(CollegeCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		// log.debug("CollegeCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Name must contain only Characters ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("state"))) {
			request.setAttribute("state", "State Name must contain only  Characters ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("city"))) {
			request.setAttribute("city", "City Name must contain only  Characters ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("phoneno"))) {
			request.setAttribute("phoneno", PropertyReader.getValue("error.require", "Phone No"));
			pass = false;
		} else if (!DataValidator.isMobileNo(request.getParameter("phoneno"))) {
			request.setAttribute("phoneno", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}

		// log.debug("CollegeCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		// log.debug("CollegeCtl Method populatebean Started");

		CollegeBean cb = new CollegeBean();

		cb.setId(DataUtility.getLong(request.getParameter("id")));

		cb.setName(DataUtility.getString(request.getParameter("name")));

		cb.setAddress(DataUtility.getString(request.getParameter("address")));

		cb.setState(DataUtility.getString(request.getParameter("state")));

		cb.setCity(DataUtility.getString(request.getParameter("city")));

		cb.setPhoneNo(DataUtility.getString(request.getParameter("phoneno")));

		populateDTO(cb, request);

		// log.debug("CollegeCtl Method populatecb Ended");

		return cb;
	}

	/**
	 * Contains display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModel cm = new CollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			CollegeBean cb;
			try {
				cb = cm.findbypk(id);
				ServletUtility.setBean(cb, request);
			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// log.debug("CollegeCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		CollegeModel cm = new CollegeModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			CollegeBean cb = (CollegeBean) populateBean(request);

			try {
				if (id > 0) {
					cm.update(cb);
					ServletUtility.setBean(cb, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {
					long pk = cm.add(cb);
					cb.setId(pk);
					ServletUtility.setBean(cb, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(cb, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			CollegeBean cb = (CollegeBean) populateBean(request);
			try {
				cm.delete(cb);
				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;

			} catch (ApplicationException e) {
				// log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		// log.debug("CollegeCtl Method doGet Ended");
	}

	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}