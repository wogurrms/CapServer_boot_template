package kr.ac.hansung.cse.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.ChartResponseData;
import kr.ac.hansung.cse.model.ChartResponseDataAvg;
import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.Record;

@Repository
@Transactional
public class RecordDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Record getRecordById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Record record = (Record) session.get(Record.class, id);

		return record;
	}

	public List<Record> getRecords() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Record");
		List<Record> recordList = query.list();

		return recordList;
	}

	public void addRecord(Record record) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(record);
		session.flush();
	}

	public void deleteRecord(Record record) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(record);
		session.flush();
	}

	public void editRecord(Record record) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(record);
		session.flush();
	}

	public int getTodayAmount(int uid) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Record r where r.date > curdate() and user_id = :uid");
		query.setParameter("uid", uid);
		Long todayAmount = (Long)query.uniqueResult();
		return todayAmount.intValue();
	}

	

	public float getAvgAmount(int uid) {
		float avg = 0;
		float sum = 0;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Record r where user_id = :uid group by cast(r.date as date)");
		query.setParameter("uid", uid);
		ArrayList<Long> responseList = (ArrayList<Long>)query.list();

		
		for(Long response : responseList){
			System.out.println(response.intValue());
		}
		
		for(Long response : responseList){
			sum += response.intValue();
		}
		avg = sum/responseList.size();
		return avg;
	}


	public List<ChartResponseData> getChartResponseData(int uid) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select new kr.ac.hansung.cse.model.ChartResponseData( count(*), cast(r.date as date) )"
				+ "from Record r where user_id = :uid group by cast(r.date as date)";
		Query query = session.createQuery(hqlQuery);
		query.setParameter("uid", uid);

		List<ChartResponseData> results = query.list();
		return results;
	}
	
	
	public List<ChartResponseDataAvg> getChartResponseDataAvg() {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select new kr.ac.hansung.cse.model.ChartResponseDataAvg( count(*)/userCount , cast(r.date as date) as date )"
				+ "from Record r group by cast(r.date as date)";
		Query query = session.createQuery(hqlQuery);

		List<ChartResponseDataAvg> results = query.list();
		return results;
	}

	public Long getRecordAvgByDate(String date) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select count(*)/userCount from Record r "
				+ "where year(r.date) = :year and month(r.date) = :month and day(r.date) = :day "
				+ "group by cast(r.date as date)";

		String[] splitted = date.split("-");
		int year = Integer.parseInt(splitted[0]);
		int month = Integer.parseInt(splitted[1]);
		int day = Integer.parseInt(splitted[2]);
		
		Query query = session.createQuery(hqlQuery);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("day", day);
		
		Long results = (Long) query.uniqueResult();
		return results;
	}
	

	public List<ChartResponseData> getDailyChartResponseData(int uid, String date) throws ParseException {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select new kr.ac.hansung.cse.model.ChartResponseData( count(*) ,cast(r.date as time) ) "
				+ "from Record r where user_id = :uid and year(r.date) = :year and month(r.date) = :month and day(r.date) = :day "
				+ "group by hour(r.date)";
		
		String[] splitted = date.split("-");
		int year = Integer.parseInt(splitted[0]);
		int month = Integer.parseInt(splitted[1]);
		int day = Integer.parseInt(splitted[2]);
		
		Query query = session.createQuery(hqlQuery);
		query.setParameter("uid", uid);
		query.setParameter("year", year);
		query.setParameter("month", month);
		query.setParameter("day", day);
		
		List<ChartResponseData> results = query.list();
		System.out.println(results.size()+"개의 데이터 검색");
		return results;
	}

	public NicotineResponseData getLatestNicotine(int uid) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select new kr.ac.hansung.cse.model.NicotineResponseData(date, nicotine) "
				+ "from Record where record_id = (select max(record_id) from Record where user_id = :uid)";
		Query query = session.createQuery(hqlQuery);
		query.setParameter("uid", uid);
		
		NicotineResponseData result = (NicotineResponseData) query.uniqueResult();

		return result;
	}

	public int getAllRecordCountByUid(int uid) {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select count(*) from Record where user_id = :uid";
		Query query = session.createQuery(hqlQuery);
		query.setParameter("uid", uid);
		
		Long result = (Long) query.uniqueResult();

		return result.intValue();
	}

}
