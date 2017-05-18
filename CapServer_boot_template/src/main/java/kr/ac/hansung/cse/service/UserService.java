package kr.ac.hansung.cse.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.cse.dao.UserDAO;
import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.model.User;

@Service
public class UserService {

	@Autowired
	private UserDAO userDao;
	
	public List<User> getUsers(){
		return userDao.getUsers();
	}
	
	public void addUser(User user){
		userDao.addUser(user);
	}
	
	public void deleteUser(User user){
		userDao.deleteUser(user);
	}

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public User getUserByNick(String nick) {
		return userDao.getUserByNick(nick);
	}
	
	public void editUser(User user) {
		userDao.editUser(user);
	}

	public boolean isUserExist(User user){
		return userDao.getUserByNick(user.getNick()) != null; 
	}

	public int getUserCountByDate(Date date) {
		return userDao.getUserCountByDate(date); 
	}

}
