package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.RecordDAO;
import kr.ac.hansung.cse.model.ChartResponseData;
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

	public int getTodayAmount() {
		return recordDao.getTodayAmount();
	}

	public float getAvgAmount() {
		return recordDao.getAvgAmount();
	}

	public List<ChartResponseData> getChartResponseData() {
		return recordDao.getChartResponseData();
	}

	public NicotineResponseData getLatestNicotine() {
		return recordDao.getLatestNicotine();
	}

}
