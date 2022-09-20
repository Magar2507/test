package in.co.sunrays.project4.test;

import java.sql.Timestamp;
import java.util.Date;

import in.co.sunrays.project4.bean.CourseBean;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.CourseModel;
import in.co.sunrays.project4.util.DataValidator;

public class testcourse {

	public static void main(String[] args) throws DuplicateRecordException, Exception {
		//testadd();
		//testfindbypk();

		String som = "Skjlefjks@123";
		System.out.println(som);

		boolean viks = 	DataValidator.isPassword(som);
		
		System.out.println(viks);
	}

	public static void testfindbypk() throws Exception {
		 CourseBean stb=new CourseBean();
			
			CourseModel stm= new CourseModel();
		    stb =stm.findByPk(2);
			
			System.out.println(stb.getId());
			//System.out.println(stb.getCourse_Id());
			System.out.println(stb.getName());
			System.out.println(stb.getCreatedBy());
			System.out.println(stb.getModifiedBy());
			System.out.println(stb.getCreatedDatetime());
			System.out.println(stb.getModifiedDatetime());
		
		
	}

	public static void testadd() throws Exception, DuplicateRecordException {
		CourseBean crsb=new CourseBean();
		CourseModel crsm=new CourseModel();
		crsb.setName("eng.phy");
		crsb.setDuration("2yrs");
		crsb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		
		crsm.add(crsb);
		
	}

}
