package in.co.sunrays.project4.test;

import java.text.SimpleDateFormat;

import in.co.sunrays.project4.bean.StudentBean;
import in.co.sunrays.project4.model.StudentModel;

public class teststudent {

	public static void main(String[] args) throws Exception {
		//testDelete();
		//testfindbyemailid();
		//testfindbypk();
		testAdd();
		//testUpdate();

	}

	public static void testUpdate() throws Exception {
		StudentBean sb=new StudentBean ();
		StudentModel sm=new StudentModel();
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		
		sb.setFirstName("AMAN");
		sb.setLastName("VAISHNAV");
		sb.setDob(sdf.parse("13/12/2008"));
		sb.setId(10);
		sm.update(sb);
	}

	public static void testAdd() throws Exception {
		StudentBean sb=new StudentBean ();
		StudentModel sm=new StudentModel();
		SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
		
		
		//sb.setCollegeName("FHDFDHGFH");
		sb.setFirstName("RAVI");
		sb.setLastName("JAIN");
		sb.setEmail("rja@gmail.com");
		sb.setMobileNo("7897489898");
		sb.setDob(sdf.parse("13/12/2008"));
		
		sm.add(sb);
		
		
	}

	public static void testfindbypk() throws Exception {
		StudentBean sb=new StudentBean ();
		StudentModel sm=new StudentModel();
		sb=sm.findByPK(1);
	
		System.out.println(sb.getCollegeId());
		System.out.println(sb.getCollegeName());
		System.out.println(sb.getFirstName());
		System.out.println(sb.getLastName());
		System.out.println(sb.getMobileNo());
		
		
	}

	public static void testfindbyemailid() throws Exception {
		StudentBean sb=new StudentBean ();
		StudentModel sm=new StudentModel();
		sb=sm.findByEmailId("rajatmishra@gmail.co");
	
		System.out.println(sb.getCollegeId());
		System.out.println(sb.getCollegeName());
		System.out.println(sb.getFirstName());
		System.out.println(sb.getLastName());
		System.out.println(sb.getMobileNo());
		
	}

	public static void testDelete() throws Exception {
		StudentBean sb=new StudentBean ();
		StudentModel sm=new StudentModel();
		sb.setId(3);
		sm.delete(sb);
		
	}

}
