package kr.ac.hansung.cse.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.User;

@Repository
@Transactional
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public User getUserById(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);

		return user;
	}

	public List<User> getUsers() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User");
		List<User> userList = query.list();

		return userList;
	}

	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		session.flush();
	}

	public void deleteUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
		session.flush();
	}

	public void editUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		session.flush();
	}

	public User getUserByNick(String nick) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User where nick=:nick");
		query.setParameter("nick", nick);
		
		User user = (User) query.uniqueResult();
		return user;
	}

	public int getUserCountByDate(Date date) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("select count(*) from User where joinDate < :date");
		query.setParameter("date", date);
		Long userCount = (Long)query.uniqueResult();
		
		return userCount.intValue();
	}

}
