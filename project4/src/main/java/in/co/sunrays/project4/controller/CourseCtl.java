package in.co.sunrays.project4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.controller.BaseCtl;
import in.co.sunrays.project4.controller.CourseCtl;
import in.co.sunrays.project4.controller.ORSView;
import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.DataValidator;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;


/**
 * Course functionality Controller. Performs operation for add, update and get
 * Course.
 * @author GARIMA
 *
 */
@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(CourseCtl.class);

	protected boolean validate(HttpServletRequest request) {
		log.debug("CourseCtl validate started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "Course name must contain only Characters");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("description"))) {
			request.setAttribute("description", "Description name must contain only Characters");
			pass = false;
		}

		log.debug("CourseCtl validate End");
		return pass;
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("CourseCtl PopulatedBean started");
		CourseBean crsb = new CourseBean();

		crsb.setId(DataUtility.getLong(request.getParameter("id")));
		crsb.setName(DataUtility.getString(request.getParameter("name")));
		crsb.setDuration(DataUtility.getString(request.getParameter("duration")));
		crsb.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(crsb, request);
		log.debug("CourseCtl PopulatedBean End");
		return crsb;
	}

	/**
	 * Contains Display logics
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do get method od courseCtl started");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get Model
		CourseModel crsm = new CourseModel();
		int id = (int) DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {
			CourseBean crsb;
			try {
				crsb = crsm.findByPk(id);
				ServletUtility.setBean(crsb, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do Post method of CourseCtl started ");
		String op = DataUtility.getString(request.getParameter("operation"));

		// Get Model
		CourseModel crsm = new CourseModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CourseBean crsb = (CourseBean) populateBean(request);
			try {
				if (id > 0) {
					crsm.update(crsb);
					ServletUtility.setBean(crsb, request);
					ServletUtility.setSuccessMessage("Course data is successfully updated", request);
				} else {
					long pk = crsm.add(crsb);
					// bean.setId(pk);
					ServletUtility.setBean(crsb, request);
					ServletUtility.setSuccessMessage("Course is successfully saved", request);
				}

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(crsb, request);
				ServletUtility.setErrorMessage("Course Name Already Exist", request);

			}
		} /*
			 * else if (OP_DELETE.equalsIgnoreCase(op)) { CourseBean bean =(CourseBean)
			 * populateBean(request); try { model.delete(bean);;
			 * ServletUtility.redirect(ORSView.COURSE_CTL, request, response); return; }
			 * catch (ApplicationException e) { log.error(e);
			 * ServletUtility.handleException(e, request, response); return ; } }
			 */
		else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
		log.debug("Do Post method CourseCtl Ended");

	}

	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}
