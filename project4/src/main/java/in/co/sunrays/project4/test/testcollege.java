package in.co.sunrays.project4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.project4.bean.CollegeBean;
import in.co.sunrays.project4.model.CollegeModel;

public class testcollege {

	public static void main(String[] args) throws Exception {
		//testAdd();
		//testDelete();
	//	testUpdate();
		//testfindbyname();
		//testfindbypk();
		//testsearch();
		
	}

	public static void testsearch() throws Exception {
		CollegeBean cb= new CollegeBean();
		CollegeModel cm= new CollegeModel();
		List list= new ArrayList();
		cb.setName("VIKAS");
		
		list=cm.search(cb, 1, 10);
		Iterator it=list.iterator();
		while(it.hasNext()) {
	      cb=(CollegeBean) it.next();
	      System.out.println(cb.getId());
          System.out.println(cb.getName());
          System.out.println(cb.getAddress());
          System.out.println(cb.getState());
			
		}
		
	}

	public static void testfindbypk() throws Exception {
       CollegeBean cb=new CollegeBean();
		
		CollegeModel cm= new CollegeModel();
	    cb =cm.findbypk(3);
		
		System.out.println(cb.getId());
		System.out.println(cb.getAddress());
		System.out.println(cb.getCity());
		System.out.println(cb.getName());
		System.out.println(cb.getPhoneNo());
		System.out.println(cb.getCreatedBy());
		System.out.println(cb.getModifiedBy());
		System.out.println(cb.getCreatedDatetime());
		System.out.println(cb.getModifiedDatetime());
		
	}

	public static void testfindbyname() throws Exception {
		CollegeBean cb=new CollegeBean();
		
		CollegeModel cm= new CollegeModel();
	    cb =cm.findbyname("RAHUL");
		
		System.out.println(cb.getId());
		System.out.println(cb.getAddress());
		System.out.println(cb.getCity());
		System.out.println(cb.getName());
		System.out.println(cb.getPhoneNo());
		System.out.println(cb.getCreatedBy());
		System.out.println(cb.getModifiedBy());
		System.out.println(cb.getCreatedDatetime());
		System.out.println(cb.getModifiedDatetime());
		
	}

	public static void testUpdate() throws Exception {
		CollegeBean cb=new CollegeBean();
		
		cb.setName("LOVELY PROFESSIONAL UNIVERSITY");
		cb.setId(7);
		CollegeModel cm= new CollegeModel();
		cm.update(cb);
		
	}

	public static void testDelete() throws Exception {
		CollegeBean cb= new CollegeBean();
		CollegeModel cm= new CollegeModel();
		cb.setId(10);
		cm.delete(cb);
		}

	public static void testAdd() throws Exception {
		
		CollegeBean cb= new CollegeBean();
		CollegeModel cm= new CollegeModel ();
		cb.setName("MITTAL COLLEGE");
		cb.setCity("BHOPAL");
		cb.setAddress("KAROND");
		cb.setPhoneNo("9876543210");
		cb.setState("M.P.");
		cb.setCreatedBy("ADMIN");
		cb.setModifiedBy("ADMIN");
		cb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		cb.setModifiedDatetime(new Timestamp(new Date().getTime()));
	
		cm.add(cb);
		
		
	}
}