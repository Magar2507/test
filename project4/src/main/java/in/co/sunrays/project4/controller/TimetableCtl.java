package in.co.sunrays.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.bean.SubjectBean;
import in.co.sunrays.project4.bean.TimetableBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.model.SubjectModel;
import in.co.sunrays.project4.model.TimetableModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.DataValidator;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;

/**
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty.
 * 
 * @author GARIMA
 *
 */
@WebServlet("/ctl/TimetableCtl")
public class TimetableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimetableCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModel crsm = new CourseModel();
		SubjectModel stm = new SubjectModel();
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = crsm.list();
			slist = stm.list();
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExTime"))) {
			request.setAttribute("ExTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}

		/*
		 * if (DataValidator.isNull(request.getParameter("description"))) {
		 * request.setAttribute("description", "Please Select Description"); pass =
		 * false; }
		 */

		log.debug("validate method of TimeTable Ctl End");
		return pass;
	}

	protected TimetableBean populateBean(HttpServletRequest request) {
		log.debug("populateBean method of TimeTable Ctl start");
		TimetableBean tb = new TimetableBean();

		System.out.println("Din;ldo");
		tb.setId(DataUtility.getLong(request.getParameter("id")));

		tb.setSubjectId(DataUtility.getInt(request.getParameter("subjectId")));
		// tb.setSubject_Name(DataUtility.getString(request.getParameter("Subject_Name")));
		tb.setCourseId(DataUtility.getInt(request.getParameter("courseId")));
		// tb.setCourse_Name(DataUtility.getString(request.getParameter("Course_Name")));
		tb.setSemester(DataUtility.getString(request.getParameter("semester")));
		// tb.setDescription(DataUtility.getString(request.getParameter("description")));
		tb.setExamDate(DataUtility.getDate(request.getParameter("ExDate")));
		tb.setExamTime(DataUtility.getString(request.getParameter("ExTime")));

		populateDTO(tb, request);
		log.debug("populateBean method of TimeTable Ctl End");
		return tb;
	}

	/**
	 * Contains Display logics
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do Get method of TimeTable Ctl Started");
		// System.out.println("Timetable ctl .do get started........>>>>>");

		String op = DataUtility.getString(request.getParameter("operation"));
		int id = (int) DataUtility.getLong(request.getParameter("id"));

		TimetableModel tm = new TimetableModel();
		TimetableBean tb = null;
		if (id > 0) {
			try {
				tb = tm.findByPK(id);
				ServletUtility.setBean(tb, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}

		log.debug("do Get method of TimeTable Ctl End");
		System.out.println("Timetable ctl .do get End........>>>>>");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post method of TimeTable Ctl start");

		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimetableModel tm = new TimetableModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimetableBean tb = (TimetableBean) populateBean(request);
			try {
				if (id > 0) {
					tm.update(tb);
					ServletUtility.setSuccessMessage("TimeTable Data is Successfully Updated", request);
				} else {
					tm.add(tb);
					System.out.println("in add method out");
					ServletUtility.setSuccessMessage("TimeTable is Successfully Saved", request);
				}
				ServletUtility.setBean(tb, request);

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setBean(tb, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
