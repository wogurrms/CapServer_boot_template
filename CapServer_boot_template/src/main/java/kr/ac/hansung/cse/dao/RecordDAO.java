package kr.ac.hansung.cse.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.ChartResponseData;
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

	public int getTodayAmount() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Record r where r.date > curdate()");
		Long todayAmount = (Long)query.uniqueResult();
		return todayAmount.intValue();
	}

	

	public float getAvgAmount() {
		float avg = 0;
		float sum = 0;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from Record r group by cast(r.date as date)");
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

	public List<ChartResponseData> getChartResponseData() {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select new kr.ac.hansung.cse.model.ChartResponseData( count(*) , cast(r.date as date) )"
				+ "from Record r group by cast(r.date as date)";
		Query query = session.createQuery(hqlQuery);

		List<ChartResponseData> results = query.list();
		return results;
	}

	public NicotineResponseData getLatestNicotine() {
		Session session = sessionFactory.getCurrentSession();
		String hqlQuery = "select new kr.ac.hansung.cse.model.NicotineResponseData(date, nicotine) "
				+ "from Record where record_id = (select max(record_id) from Record)";
		Query query = session.createQuery(hqlQuery);
		
		NicotineResponseData result = (NicotineResponseData) query.uniqueResult();

		return result;
	}
}
