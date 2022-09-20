package in.co.sunrays.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.CollegeBean;
import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.bean.FacultyBean;
import in.co.sunrays.project4.bean.SubjectBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.CollegeModel;
import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.model.FacultyModel;
import in.co.sunrays.project4.model.SubjectModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.DataValidator;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;

/**
 * Faculty functionality Controller. Performs operation for add, update and get
 * Faculty.
 * @author GARIMA
 *
 */
@WebServlet ("/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyCtl.class);
	
	protected void preload (HttpServletRequest request){
		
		CourseModel crsm = new CourseModel();
		CollegeModel cm = new CollegeModel();
		SubjectModel stm = new SubjectModel();
		
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<CollegeBean> colist = new ArrayList<CollegeBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		
		try {
			clist = crsm.list();
			colist = cm.list();
			slist = stm.list();
			
				request.setAttribute("CourseList", clist);
				request.setAttribute("CollegeList", colist);
				request.setAttribute("SubjectList", slist);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected boolean validate(HttpServletRequest request){
		
		log.debug("Validate Method of Faculty Ctl Started");
		boolean pass = true;
		if(DataValidator.isNull(request.getParameter("firstname"))){
			request.setAttribute("firstname", PropertyReader.getValue("error.require", "First Name"));
			 pass = false;
		}else if (!DataValidator.isName(request.getParameter("firstname"))) {
			request.setAttribute("firstname", "First name must contain only Characters");
			 pass = false ;
		}
		if(DataValidator.isNull(request.getParameter("lastname"))){
			request.setAttribute("lastname", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("lastname"))) {
			request.setAttribute("lastname", "Last name must contain only Characters");
			 pass = false ;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("doj"))) {
			request.setAttribute("doj", PropertyReader.getValue("error.require", "Date of Joining "));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("qualification"))){
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("qualification"))) {
			request.setAttribute("qualification", "Qualification name must contain only Characters");
			 pass = false ;
		}
		if(DataValidator.isNull(request.getParameter("loginid"))){
			request.setAttribute("loginid", PropertyReader.getValue("error.require", "Login Id "));
			pass = false;
		}else if (!DataValidator.isEmail(request.getParameter("loginid"))) {
			request.setAttribute("loginid", PropertyReader.getValue("error.email", "Email ID "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", PropertyReader.getValue("error.require", "Mobile No "));
			pass = false;
		}else if (!DataValidator.isMobileNo(request.getParameter("mobileno"))) {
			request.setAttribute("mobileno", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("collegeid"))) {
			request.setAttribute("collegeid", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("courseid"))) {
			request.setAttribute("courseid", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("subjectid"))) {
			request.setAttribute("subjectid", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		log.debug("validate Ended");
		return pass;
	}
	
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("populate bean faculty ctl started");
		
		FacultyBean fb = new FacultyBean();
	
		fb.setId(DataUtility.getLong(request.getParameter("id")));
		fb.setFirstName(DataUtility.getString(request.getParameter("firstname")));
		fb.setLastName(DataUtility.getString(request.getParameter("lastname")));
		fb.setGender(DataUtility.getString(request.getParameter("gender")));
		fb.setDOJ(DataUtility.getDate(request.getParameter("doj")));
		
		
//		System.out.println("populate fb ..."+request.getParameter("doj"));
		fb.setQualification(DataUtility.getString(request.getParameter("qualification")));
		fb.setEmailId(DataUtility.getString(request.getParameter("loginid")));
		fb.setMobileNo(DataUtility.getString(request.getParameter("mobileno")));
		fb.setCollegeId(DataUtility.getInt(request.getParameter("collegeid")));
		fb.setCourseId(DataUtility.getInt(request.getParameter("courseid")));
		fb.setSubjectId(DataUtility.getInt(request.getParameter("subjectid")));
//		fb.setCourseName(DataUtility.getString(request.getParameter("courseid")));
//		fb.setSubjectName(DataUtility.getString(request.getParameter("subjectid")));
		populateDTO(fb, request);
		log.debug("populate fb faculty ctl Ended");
		return fb;
	}
	
	
    /**
     * Contains Display logics
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.debug("Do get of faculty ctl Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		//Get Model
		FacultyModel fm = new FacultyModel();
		int id = (int) DataUtility.getLong(request.getParameter("id"));
		
		if(id>0 || op!= null){
			FacultyBean fb;
			try {
				fb = fm.findByPK(id);
			ServletUtility.setBean(fb, request);
			
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		log.debug("Do get of  faculty ctl Ended");
		ServletUtility.forward(getView(), request, response);
	}

    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post of  faculty ctl Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));	
			
		// Get Model
		FacultyModel fm = new FacultyModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean fb = (FacultyBean) populateBean(request);
			
			try{
			if(id > 0){
				fm.update(fb);
				ServletUtility.setBean(fb, request);
				ServletUtility.setSuccessMessage("Faculty Data Successfully Updated", request);
			}else{
			long pk = fm.add(fb);
		//		bean.setId(pk);
			ServletUtility.setBean(fb, request);
			ServletUtility.setSuccessMessage("Faculty Successfully Registered", request);
			}
			
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ; 
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(fb, request);
				ServletUtility.setErrorMessage("Faculty already Exists!", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}/*else if (OP_DELETE.equalsIgnoreCase(op)) {
			FacultyBean bean =(FacultyBean) populateBean(request);
	
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
	}*/	else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return ;
			}			
	else if (OP_CANCEL.equalsIgnoreCase(op) ) {
		ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		return ;
	}			
		
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post of  faculty ctl Ended");
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}