package in.co.sunrays.project4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.project4.bean.RoleBean;
import in.co.sunrays.project4.model.RoleModel;

public class testrole {
	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testfindbypk();
		// testfindbyname();
		//testsearch();
		testlist();

	}

	public static void testlist() throws Exception {
		// TODO Auto-generated method stub
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();
		List list = new ArrayList();
		list=rm.list(1,10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			rb = (RoleBean) it.next();
			System.out.println(rb.getId());
			System.out.println(rb.getName());
			System.out.println(rb.getDescription());
			System.out.println(rb.getCreatedBy());
			

		}
		
		
		
	}

	public static void testsearch() {
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();
		List list = new ArrayList();
		rb.setName("RAVI");
		list = rm.Search(rb, 1, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			rb = (RoleBean) it.next();
			System.out.println(rb.getName());
			System.out.println(rb.getCreatedBy());
			System.out.println(rb.getDescription());

		}

	}

	private static void testfindbyname() throws Exception {
		// TODO Auto-generated method stub
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();
		rb = rm.findByName("Ravi");

		System.out.println(rb.getId());
		System.out.println(rb.getDescription());

	}

	public static void testfindbypk() throws Exception {
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();
		rb = rm.findByPK(2);

		System.out.println(rb.getName());
		System.out.println(rb.getDescription());

	}

	public static void testUpdate() throws Exception {
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();

		rb.setDescription("TEACHER");
		rb.setId(1);
		rm.update(rb);

	}

	public static void testDelete() throws Exception {
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();
		rb.setId(2);
		rm.delete(rb);

	}

	public static void testAdd() throws Exception {
		RoleBean rb = new RoleBean();
		RoleModel rm = new RoleModel();

		rb.setName("HUMAN");
		rb.setDescription("PERSON");
		
		Date d= new Date();
		rb.setCreatedDatetime(new Timestamp(d.getTime()));

		rm.add(rb);
	}

}
