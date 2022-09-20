package in.co.sunrays.project4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.project4.bean.BaseBean;
import in.co.sunrays.project4.bean.UserBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.model.UserModel;
import in.co.sunrays.project4.util.DataUtility;
import in.co.sunrays.project4.util.PropertyReader;
import in.co.sunrays.project4.util.ServletUtility;


/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User.
 * @author GARIMA
 *
 */
@ WebServlet("/ctl/UserListCtl")
public class UserListCtl extends BaseCtl {
    private static Logger log = Logger.getLogger(UserListCtl.class);
    protected void preload(HttpServletRequest request){
		UserModel um = new UserModel();
		try{
			List slist=um.list();
			request.setAttribute("name", slist);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        UserBean ub = new UserBean();

        ub.setId(DataUtility.getLong(request
                .getParameter("firstName")));

        ub.setLastName(DataUtility.getString(request.getParameter("lastName")));

        ub.setLogin(DataUtility.getString(request.getParameter("login")));

        return ub;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("UserListCtl doGet Start");
        List list = null;
        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        UserBean ub = (UserBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        
        // get the selected checkbox ids array for delete list
        String[] ids = request.getParameterValues("ids");
        UserModel um = new UserModel();
        try {
            list = um.search(ub, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
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
        log.debug("UserListCtl doPOst End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("UserListCtl doPost Start");
        List list = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;
        UserBean ub = (UserBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        // get the selected checkbox ids array for delete list
        String[] ids = request.getParameterValues("ids");
        UserModel um = new UserModel();
        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_RESET.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
                return;
            }
            else if (OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
                return;
            }else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.USER_CTL, request, response);
                return;
            } else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    UserBean deletebean = new UserBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        um.delete(deletebean);
                        }
                    ServletUtility.setSuccessMessage("User Data Deleted Successfully ", request);
                    
                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }
            }
            list = um.search(ub, pageNo, pageSize);
            ServletUtility.setList(list, request);
            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
           // ServletUtility.setList(list, request);
            ServletUtility.setBean(ub, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("UserListCtl doGet End");
    }

    @Override
    protected String getView() {
        return ORSView.USER_LIST_VIEW;
    }

}
