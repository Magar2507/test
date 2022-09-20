package in.co.sunrays.project4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.project4.bean.SubjectBean;
import in.co.sunrays.project4.model.SubjectModel;

public class testsubject {

	public static void main(String[] args) throws Exception {
		testlist();
	//	TESTADD();
		//testfindbypk();

	}

	private static void testfindbypk() throws Exception {
		// TODO Auto-generated method stub
		 SubjectBean stb=new SubjectBean();
			
			SubjectModel stm= new SubjectModel();
		    stb =stm.findByPK(3);
			
			System.out.println(stb.getId());
			System.out.println(stb.getCourseId());
			System.out.println(stb.getCourseName());
			System.out.println(stb.getCreatedBy());
			System.out.println(stb.getModifiedBy());
			System.out.println(stb.getCreatedDatetime());
			System.out.println(stb.getModifiedDatetime());
		
	}

	public static void TESTADD() throws Exception {
		// TODO Auto-generated method stub
		SubjectBean stb = new SubjectBean();
		SubjectModel stm = new SubjectModel();
		stb.setCourseId(4);
		stb.setCourseName("BCA");
		stb.setDescription("Education");
		stb.setName("computing");
		stb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		stb.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		stm.add(stb);
		
	}

	public static void testlist() throws Exception {
		SubjectBean stb = new SubjectBean();
		SubjectModel stm = new SubjectModel();
		List list = new ArrayList();
		list=stm.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			stb = (SubjectBean) it.next();
			System.out.println(stb.getCourseId());
			System.out.println(stb.getCourseName());
			System.out.println(stb.getDescription());
			System.out.println(stb.getCreatedBy());
			

		}
		
	}

}
