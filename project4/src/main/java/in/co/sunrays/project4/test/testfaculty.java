package in.co.sunrays.project4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.project4.bean.FacultyBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.model.FacultyModel;

public class testfaculty {

	public static void main(String[] args) throws Exception {

		testAdd();
		// testLIst();

	}

	private static void testLIst() throws ApplicationException {
		// TODO Auto-generated method stub
		FacultyBean fb = new FacultyBean();
		FacultyModel fm = new FacultyModel();

		List list = new ArrayList();
		list = fm.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			fb = (FacultyBean) it.next();
			System.out.println(fb.getCollegeId());
			System.out.println(fb.getCourseName());
			// System.out.println(fb.getDescription());
			System.out.println(fb.getCreatedBy());
		}
	}

	public static void testAdd() throws Exception {
		// TODO Auto-generated method stub
		FacultyBean fb = new FacultyBean();
		FacultyModel fm = new FacultyModel();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/DD/YYYY");

		fb.setFirstName("RISHABH");
		fb.setLastName("BAJAJ");
		fb.setGender("MALE");
		fb.setCollegeId(1);

		fb.setCourseId(1);
		fb.setDOJ(sdf.parse("12/12/2008"));
		fb.setSubjectId(1);
		fb.setQualification("MBA");
		fb.setMobileNo("9876543211");
		fb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		fb.setModifiedDatetime(new Timestamp(new Date().getTime()));
		fm.add(fb);

	}

}
