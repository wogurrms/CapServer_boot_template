package kr.ac.hansung.cse.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.Record;
import kr.ac.hansung.cse.model.Tobacco;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.RecordService;
import kr.ac.hansung.cse.service.TobaccoService;
import kr.ac.hansung.cse.service.UserService;


@RestController
@RequestMapping("/api")
public class RestAPIController {
	@Autowired
	private RecordService recordService;
	
	@Autowired
	private TobaccoService tobaccoService;
	
	@Autowired
	private UserService userService;
	
	// ----------------------------- Create a Record -----------------------------------
	
	@RequestMapping(value="/records", method=RequestMethod.POST)	// Request body(json)에 사용자의 정보가 json 포맷으로 넘어옴
	public ResponseEntity<Void> createRecord(@RequestBody Record record, 
			UriComponentsBuilder ucBuilder) {
		// Body 부분이 없다는 의미로 <Void> 를 넣어줌
		System.out.println("Request RestAPI Controller");
		String username = "";
		double nicotine = 0.0;
		double formattedCurrentNico = 0.0;
		
		User user = record.getUser();
		username = user.getNick();
		
		User userFromDB = userService.getUserByNick(username);
		nicotine = userFromDB.getTobac().getTobaccoNicotine();
		
		int userCount = userService.getUserCountByDate(record.getDate());
		
		NicotineResponseData nicotineResponseData = recordService.getLatestNicotine(userFromDB.getUid());
		if (nicotineResponseData != null) {
			double elapsedTime = (double) (record.getDate().getTime() - nicotineResponseData.getDate().getTime())
					/ 1000.0;
			double currentNico = nicotineResponseData.getNicotine() * Math.pow(0.5, elapsedTime / 7200.0);
			formattedCurrentNico = Double.parseDouble(String.format("%.2f", currentNico));
		}
		
		
		record.setUserCount(userCount);
		record.setUser(userFromDB);
		record.setNicotine(nicotine + formattedCurrentNico);
		
		recordService.addRecord(record);
		
		// header 에 사용자의 uri 를 넘겨줌
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/records/{id}").
				buildAndExpand(record.getRecord_id()).toUri());
		return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}

	// ----------------------------- Retrieve All Users -----------------------------
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers(){
		// ResponseEntity = header, body(json), HTTP.status 세가지를 저장해서 넘길 수 있음.
		// JSON format 으로 바꾸어서 넘겨줌 ( jackson.databind )
		List<User> users = userService.getUsers();
		if(users.isEmpty()){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	// ----------------------------- Retrieve Avg By Date --------------------------
	
	@RequestMapping(value="/avg/{date}", method=RequestMethod.GET)
	public ResponseEntity<Long> getUser(@PathVariable("date") String date){
		Long result = recordService.getRecordAvgByDate(date);
		if(result == null){
			result = 0L;
		}
		return new ResponseEntity<Long>(result,HttpStatus.OK);
	}
	
	// ----------------------------- Retrieve TobaccoId By Nickname --------------------------
	
	@RequestMapping(value="/tobacco/{username}", method=RequestMethod.GET)
	public ResponseEntity<Integer> getTobaccoId(@PathVariable("username") String username){
		User user = userService.getUserByNick(username);
		Tobacco tobacco = user.getTobac();
		int result = tobacco.getTobac_id();
		
		return new ResponseEntity<Integer>(result,HttpStatus.OK);
	}
	
//	
//	// ----------------------------- Retrieve Single User --------------------------
//	
//	@RequestMapping(value="/users/{userId}", method=RequestMethod.GET)
//	public ResponseEntity<User> getUser(@PathVariable("userId") int userId){
//		User user = userService.getUserById(userId);
//		if(user == null ){
//			// to do : custom Exception
//			throw new UserNotFoundException(id);
//		}
//		return new ResponseEntity<User>(user,HttpStatus.OK);
//	}
	
	// ----------------------------- Create a User -----------------------------------
	
		@RequestMapping(value="/users", method=RequestMethod.POST)	// Request body(json)에 사용자의 정보가 json 포맷으로 넘어옴
		public ResponseEntity<Void> createUser(@RequestBody User user, 
				UriComponentsBuilder ucBuilder) {
			// Body 부분이 없다는 의미로 <Void> 를 넣어줌
			System.out.println("Request RestAPI Controller");
			if(userService.isUserExist(user)){
				// to do Exception , User가 이미 존재할 경우 오류
			}
			String tobaccoBrandName = user.getTobac().getTobaccoBrand();
			String tobaccoName = user.getTobac().getTobaccoName();
			
			Tobacco tobacco = tobaccoService.getTobaccoByBrandAndName(tobaccoBrandName, tobaccoName);
			
			Date joinDate = new Date();
			
			user.setTobac(tobacco);
			user.setEnabled(1);
			user.setAuthority("ROLE_USER");
			user.setJoinDate(joinDate);
			userService.addUser(user);
			
			// header 에 사용자의 uri 를 넘겨줌
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(ucBuilder.path("/api/users/{id}").
					buildAndExpand(user.getNick()).toUri());
			return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
		}
		
		/*
	// ----------------------------- Update a User --------------------------
		
		@RequestMapping(value="/users/{userId}", method=RequestMethod.PUT)
		public ResponseEntity<User> udpateUser(@PathVariable("userId") int userId, @RequestBody User user){
			User currentUser = userService.getUserById(userId);
			if(currentUser == null ){
				// to do Exception 
				throw new UserNotFoundException(id);
			}
			currentUser.setMac(user.getMac());
			currentUser.setNick(user.getNick());
			currentUser.setTobac(user.getTobac());
			
			userService.editUser(currentUser);
			return new ResponseEntity<User>(currentUser,HttpStatus.OK);
		}
		
	// ----------------------------- Delete a User --------------------------
		
		@RequestMapping(value="/users/{id}", method=RequestMethod.DELETE)
		public ResponseEntity<User> deleteUser(@PathVariable("id") long id){
			
			User user = userService.findById(id);
			if(user == null ){
				// to do Exception 
				throw new UserNotFoundException(id);
			}
			userService.deleteUserById(id);
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		
	// ----------------------------- Delete All User --------------------------
		
		@RequestMapping(value="/users", method=RequestMethod.DELETE)
		public ResponseEntity<User> deleteAllUsers(){
			
			userService.deleteAllUsers();
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}*/
}
