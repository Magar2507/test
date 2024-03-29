package in.co.sunrays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.bean.SubjectBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.model.SubjectModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;


/**
 * and delete operations of Subject.
 * @author GARIMA
 *
 */
@WebServlet("/ctl/SubjectListCtl")
public class SubjectListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(SubjectListCtl.class);

	protected void preload(HttpServletRequest request) {

		SubjectModel stm = new SubjectModel();
		CourseModel crsm = new CourseModel();

		List<SubjectBean> list = null;
		List<CourseBean> list2 = null;

		try {
			list = stm.list();
			list2 = crsm.list();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

		request.setAttribute("subjectList", list);
		request.setAttribute("courseList", list2);
	}

	protected BaseBean populateBean(HttpServletRequest request) {

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("subjectname")));
		bean.setCourseId(DataUtility.getInt(request.getParameter("coursename")));

		populateDTO(bean, request);
		return bean;
	}

	/**
	 * Contains display logics
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();
		/*
		 * String op = DataUtility.getString(request.getParameter("operation"));
		 * String[] ids = request.getParameterValues("ids");
		 */
		try {
			list = model.search(bean, pageNo, pageSize);
			ServletUtility.setList(list, request);

			if (list == null && list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("do Get Method of SubjectList Ctl Ended ");
	}

	/**
	 * Contains Submit logics
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("DoPost of SubjectlistCtl started");

		List list;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		SubjectModel model = new SubjectModel();
		SubjectBean bean = (SubjectBean) populateBean(request);

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			if (pageNo > 1) {
				pageNo--;
			} else {
				pageNo = 1;
			}
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				SubjectBean deletebean = new SubjectBean();

				for (String id : ids) {
					deletebean.setId(DataUtility.getInt(id));
					try {
						model.delete(deletebean);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}
					ServletUtility.setSuccessMessage("Subject Data Deleted Successfully ", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageNo, pageSize);
			ServletUtility.setBean(bean, request);

		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
