package kr.ac.hansung.cse.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.RecordDAO;
import kr.ac.hansung.cse.model.ChartResponseData;
import kr.ac.hansung.cse.model.ChartResponseDataAvg;
import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.Record;

@Service
public class RecordService {

	@Autowired
	private RecordDAO recordDao;

	public List<Record> getRecords() {
		return recordDao.getRecords();
	}

	public void addRecord(Record record) {
		recordDao.addRecord(record);
	}

	public void deleteRecord(Record record) {
		recordDao.deleteRecord(record);
	}

	public Record getRecordById(int id) {
		return recordDao.getRecordById(id);
	}

	public void editRecord(Record record) {
		recordDao.editRecord(record);
	}

	public int getTodayAmount(int uid) {
		return recordDao.getTodayAmount(uid);
	}

	public float getAvgAmount(int uid) {
		return recordDao.getAvgAmount(uid);
	}

	public List<ChartResponseData> getChartResponseData(int uid) {
		return recordDao.getChartResponseData(uid);
	}

	public List<ChartResponseData> getDailyChartResponseData(int uid, String date) throws ParseException {
		return recordDao.getDailyChartResponseData(uid, date);
	}
	
	public NicotineResponseData getLatestNicotine(int uid) {
		return recordDao.getLatestNicotine(uid);
	}

	public int getAllRecordCountByUid(int uid) {
		return recordDao.getAllRecordCountByUid(uid);
	}

	public List<ChartResponseDataAvg> getChartResponseDataAvg() {
		return recordDao.getChartResponseDataAvg();
	}
	
	public Long getRecordAvgByDate(String date){
		return recordDao.getRecordAvgByDate(date);
	}


}
