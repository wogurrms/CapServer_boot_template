package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Tobacco;
import kr.ac.hansung.cse.model.User;

@Repository
@Transactional
public class TobaccoDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Tobacco getTobaccoById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Tobacco tobacco = (Tobacco) session.get(Tobacco.class, id);

		return tobacco;
	}

	public List<Tobacco> getTobaccos() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from Tobacco");
		List<Tobacco> tobaccoList = query.list();

		return tobaccoList;
	}

	public void addTobacco(Tobacco tobacco) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tobacco);
		session.flush();
	}

	public void deleteTobacco(Tobacco tobacco) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(tobacco);
		session.flush();
	}

	public void editTobacco(Tobacco tobacco) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(tobacco);
		session.flush();
	}
}
