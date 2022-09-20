package in.co.sunrays.project4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.project4.bean.TimetableBean;
import in.co.sunrays.project4.exception.ApplicationException;
import in.co.sunrays.project4.exception.DuplicateRecordException;
import in.co.sunrays.project4.model.TimetableModel;

public class testimetable {

	public static void main(String[] args) throws Exception {
		// testAdd();
		 //testcheckbysem();
		// testlist();
		// testupdate();
		//testFindbyupk();

		
	}

	private static void testFindbyupk() throws ApplicationException {
		// TODO Auto-generated method stub

		TimetableBean stb = new TimetableBean();

		TimetableModel stm = new TimetableModel();

		stb = stm.findByPK(2);

		System.out.println(stb.getId());
		System.out.println(stb.getCourseId());
		System.out.println(stb.getCourseName());
		System.out.println(stb.getCreatedBy());
		System.out.println(stb.getModifiedBy());
		System.out.println(stb.getCreatedDatetime());
		System.out.println(stb.getModifiedDatetime());

	}

	public static void testupdate() throws Exception, DuplicateRecordException {
		// TODO Auto-generated method stub
		TimetableBean tb = new TimetableBean();
		TimetableModel tm = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date();
		dt = sdf.parse("03/03/2012");
		tb.setCourseName("botany");
		
		tb.setSubjectId(2);
		tb.setCourseId(1);
		tb.setExamDate(dt);
		tb.setCreatedDatetime(new Timestamp(new Date().getTime()));
		tb.setId(4);

		tm.update(tb);

	}

	public static void testlist() throws Exception {
		// TODO Auto-generated method stub
		TimetableBean tb = new TimetableBean();
		TimetableModel tm = new TimetableModel();
		List list = new ArrayList();
		list = tm.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			tb = (TimetableBean) it.next();
			System.out.println(tb.getCourseId());
			System.out.println(tb.getCourseName());
			// System.out.println(fb.getDescription());
			System.out.println(tb.getCreatedBy());
		}

	}

	
	public static void testAdd() throws Exception, DuplicateRecordException {
		// TODO Auto-generated method stub
		TimetableBean tb = new TimetableBean();
		TimetableModel tm = new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date();
		dt = sdf.parse("03/03/2012");

		tb.setCourseName("Coghhkjgfmmerce");
		tb.setCourseId(4);
		tb.setSubjectName("maths");
		tb.setSubjectId(2);
		tb.setExamDate(dt);
		tb.setExamTime("4:00");
		
		tm.add(tb);

	}

}
