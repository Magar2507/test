package in.co.sunrays.project4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.project4.bean.MarksheetBean;
import in.co.sunrays.project4.model.MarksheetModel;

public class testmarksheet {

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		testfindByPK();
	//	 testfindbyrollno();
		// testUpdate();
		// testsearch();
		//testmeritlist();

	}

	public static void testmeritlist() throws Exception {
		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		ArrayList list= new ArrayList();
		list= (ArrayList) mm.getMeritList(1, 5);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			mb = (MarksheetBean) it.next();
			System.out.println(mb.getId());
			System.out.println(mb.getRollNo());
			System.out.println(mb.getName());
			System.out.println(mb.getPhysics());
			System.out.println(mb.getChemistry());
			System.out.println(mb.getMaths());
		}

		
		

	}

	public static void testsearch() throws Exception {
		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		List list = new ArrayList();
		mb.setName("RAVI");
		list = mm.search(mb, 1, 10);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			mb = (MarksheetBean) it.next();
			System.out.println(mb.getId());
			System.out.println(mb.getRollNo());
			System.out.println(mb.getName());
			System.out.println(mb.getPhysics());
			System.out.println(mb.getChemistry());
			System.out.println(mb.getMaths());
		}

	}

	public static void testUpdate() throws Exception {
		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		mb.setChemistry(88);
		mb.setMaths(90);
		mb.setPhysics(100);
		mb.setName("ravi");
		mb.setId(3);

		mm.update(mb);

	}

	public static void testfindbyrollno() throws Exception {
		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		mb = mm.findByRollNo("103");
		System.out.println(mb.getId());
		System.out.println(mb.getRollNo());
		System.out.println(mb.getMaths());
		System.out.println(mb.getName());
		System.out.println(mb.getPhysics());

	}

	public static void testfindByPK() throws Exception {
		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		mb = mm.findByPK(3);
		System.out.println(mb.getId());
		System.out.println(mb.getRollNo());
		System.out.println(mb.getMaths());
		System.out.println(mb.getName());
		System.out.println(mb.getPhysics());

	}

	public static void testDelete() throws Exception {
		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		mb.setId(2);
		mm.delete(mb);

	}

	public static void testAdd() throws Exception {

		MarksheetBean mb = new MarksheetBean();
		MarksheetModel mm = new MarksheetModel();
		mb.setStudentId(119);
		mb.setName("SALMAN");
		mb.setRollNo("16");
		mb.setMaths(55);
		mb.setChemistry(65);

		mm.add(mb);

	}

}
