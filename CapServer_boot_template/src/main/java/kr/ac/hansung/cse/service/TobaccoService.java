package kr.ac.hansung.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.TobaccoDAO;
import kr.ac.hansung.cse.model.Tobacco;

@Service
public class TobaccoService {

	@Autowired
	private TobaccoDAO tobaccoDao;

	public List<Tobacco> getTobaccos() {
		return tobaccoDao.getTobaccos();
	}

	public void addTobacco(Tobacco tobacco) {
		tobaccoDao.addTobacco(tobacco);
	}

	public void deleteTobacco(Tobacco tobacco) {
		tobaccoDao.deleteTobacco(tobacco);
	}

	public Tobacco getTobaccoById(int id) {
		return tobaccoDao.getTobaccoById(id);
	}

	public void editTobacco(Tobacco tobacco) {
		tobaccoDao.editTobacco(tobacco);
	}

	public Tobacco getTobaccoByBrandAndName(String tobaccoBrandName, String tobaccoName) {
		return tobaccoDao.getTobaccoByBrandAndName(tobaccoBrandName, tobaccoName);
	}
}
