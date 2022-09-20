package in.co.sunrays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.MarksheetBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.MarksheetModel;
import in.co.sunrays.project4.model.StudentModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.DataValidator;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;

/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet.
 * 
 * @author GARIMA
 *
 */
@WebServlet("/ctl/MarksheetCtl")
public class MarksheetCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel sm = new StudentModel();
		try {
			List l = sm.list();
			request.setAttribute("studentList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MarksheetCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Roll Number must be alphanumeric");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		
		}
		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics Number"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Number"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths Number"));
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics", "Marks can not be greater than 100");
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks can not be less than 0");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks can not be greater than 100");
			pass = false;
		}
		if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks can not be less than 0");
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("maths")) > 100) {
			request.setAttribute("maths", "Marks can not be greater than 100");
			pass = false;
		}

		if (DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "Marks can not be less than 0");
			pass = false;
		}

		/*
		 * if (DataValidator.isNull(request.getParameter("studentId"))) {
		 * request.setAttribute("studentId", PropertyReader.getValue("error.require",
		 * "Student Name")); pass = false; }
		 */

		log.debug("MarksheetCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("MarksheetCtl Method populatebean Started");

		MarksheetBean mb = new MarksheetBean();

		mb.setId(DataUtility.getLong(request.getParameter("id")));

		mb.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		// mb.setName(DataUtility.getString(request.getParameter("name")));

		mb.setPhysics(DataUtility.getInt(request.getParameter("physics")));

		mb.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

		mb.setMaths(DataUtility.getInt(request.getParameter("maths")));

		mb.setStudentId(DataUtility.getLong(request.getParameter("name")));

		populateDTO(mb, request);

		System.out.println("Population done");

		log.debug("MarksheetCtl Method populatebean Ended");

		return mb;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MarksheetCtl Method doGet Started");

		// String op = DataUtility.getString(request.getParameter("operation"));
		MarksheetModel mm = new MarksheetModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			MarksheetBean mb;
			try {
				mb = mm.findByPK(id);
				ServletUtility.setBean(mb, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
	}

	/**
	 * Contains Submit logics
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("MarksheetCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		MarksheetModel mm = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			MarksheetBean mb = (MarksheetBean) populateBean(request);
			try {
				if (id > 0) {
					mm.update(mb);

					ServletUtility.setBean(mb, request);
					ServletUtility.setSuccessMessage("Data is successfully updated", request);
				} else {

					long pk = mm.add(mb);
					mb.setId(pk);

					ServletUtility.setBean(mb, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(mb, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} /*
			 * else if (OP_DELETE.equalsIgnoreCase(op)) {
			 * 
			 * MarksheetBean mb = (MarksheetBean) populateBean(request);
			 * System.out.println("in try"); try { mm.delete(mb);
			 * ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			 * System.out.println("in try"); return; } catch (ApplicationException e) {
			 * System.out.println("in catch"); log.error(e);
			 * ServletUtility.handleException(e, request, response); return; } catch
			 * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 * 
			 * }
			 */ else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doPost Ended");
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}