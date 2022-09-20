package in.co.sunrays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.FacultyBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.model.FacultyModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;

/**
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty.
 * @author GARIMA
 *
 */
@WebServlet ("/ctl/FacultyListCtl")
public class FacultyListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(FacultyListCtl.class);
	protected void preload(HttpServletRequest request){
		FacultyModel fm = new FacultyModel();
		try{
			List slist=fm.list();
			request.setAttribute("name", slist);
			
		}catch(Exception e){
			e.printStackTrace();
		}
    }
	protected BaseBean populateBean(HttpServletRequest request) {

		FacultyBean fb = new FacultyBean();

		fb.setId(DataUtility.getLong(request.getParameter("firstname")));
		fb.setLastName(DataUtility.getString(request.getParameter("lastname")));
		fb.setEmailId(DataUtility.getString(request.getParameter("login")));
		fb.setGender(DataUtility.getString(request.getParameter("gender")));
	//	fb.setDateofjoining(DataUtility.getDate(request.getParameter("doj")));
		return fb;
	}
    /**
     * Contains Display logics
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		List list;
		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		FacultyModel fm = new FacultyModel();
		FacultyBean fb = (FacultyBean) populateBean(request);
		
		String op = DataUtility.getString(request.getParameter("operation"));
	    String[] ids = request.getParameterValues("ids");
	    	
		
		try {
			list = fm.search(fb, pageNo, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
			ServletUtility.setList(list, request);
	        ServletUtility.setPageNo(pageNo, request);
	        ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);
	        
        } catch (ApplicationException e) {
			e.printStackTrace();
        	log.error(e);
			ServletUtility.handleException(e, request, response);
			return ;
		}
	    
		log.debug(" DoGet Method of Faculty Model End");
	}
    /**
     * Contains Submit logics
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list;


		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? 
		DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		
		String op = DataUtility.getString(request.getParameter("operation"));

		FacultyBean fb = (FacultyBean)populateBean(request);
		FacultyModel fm = new FacultyModel();

		String[] ids = (String[]) request.getParameterValues("ids");

		
				if (OP_SEARCH.equalsIgnoreCase(op)) 
				{
					pageNo = 1;
				} 
				else if (OP_NEXT.equalsIgnoreCase(op)) 
				{
					pageNo++;
				} 
				else if (OP_PREVIOUS.equalsIgnoreCase(op)) 
				{
					if(pageNo>1){
						pageNo--;
					}
					else{
					pageNo=1;
				}
				}
				else if (OP_NEW.equalsIgnoreCase(op)) 
				{
					ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
					return ;
				}				
				else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
					return;
				}
				
				else if (OP_DELETE.equalsIgnoreCase(op)) 
				{	
						pageNo =1;
						if(ids !=null && ids.length !=0){
						FacultyBean deletebean = new FacultyBean();
							for (String id : ids) {
								deletebean.setId(DataUtility.getInt(id));
								try {
									fm.delete(deletebean);
								} catch (ApplicationException e) {
									e.printStackTrace();
									log.error(e);
									ServletUtility.handleException(e, request, response);
									return;
								}
								ServletUtility.setSuccessMessage("Faculty Data Deleted Succesfully", request);
							}
							
						}else{
							ServletUtility.setErrorMessage("Select at least one record", request);
						}
					}	
				try {
					list=fm.search(fb, pageNo, pageSize);
					ServletUtility.setBean(fb, request);

				} catch (ApplicationException e) {
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
				}
				
				if(list == null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)){
					ServletUtility.setErrorMessage("No Record Found", request);
				}
				
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);
	
				
				 log.debug("UserListCtl doPost End");	
		}

	@Override
	protected String getView() {
		return ORSView.FACULTY_LIST_VIEW;
	}

}
