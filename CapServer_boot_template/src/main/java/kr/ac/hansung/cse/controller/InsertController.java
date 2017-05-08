package kr.ac.hansung.cse.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.Record;
import kr.ac.hansung.cse.model.Tobacco;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.RecordService;
import kr.ac.hansung.cse.service.TobaccoService;
import kr.ac.hansung.cse.service.UserService;

@Controller
public class InsertController {

	@Autowired
	private UserService userService;
	@Autowired
	private RecordService recordService;
	@Autowired
	private TobaccoService tobaccoService;

	@RequestMapping("/insertUser")
	public String insert() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = formatter.format(new Date());

		Tobacco tobacco = new Tobacco();
		tobacco.setName("말보로레드");
		tobacco.setPrice(4500);
		tobacco.setNicotine(8);

		User user = new User();
		user.setNick("wogur");
		user.setTobac(tobacco);

		Record record = new Record();
		record.setDate(new Date());
		record.setNicotine(0);
		record.setUser(user);

		tobaccoService.addTobacco(tobacco);
		userService.addUser(user);
		recordService.addRecord(record);

		return "home";
	}

	@RequestMapping("/insertRecord")
	public String insertRecord() throws ParseException {
		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = formatter.format(currentDate);

		double formattedNico = 0.0;
		
		NicotineResponseData nicotineResponseData = recordService.getLatestNicotine();
		if (nicotineResponseData != null) {
			double elapsedTime = (double) (formatter.parse(now).getTime() - nicotineResponseData.getDate().getTime())
					/ 1000.0;
			double currentNico = nicotineResponseData.getNicotine() * Math.pow(0.5, elapsedTime / 7200.0);
			formattedNico = Double.parseDouble(String.format("%.2f", currentNico));
		}

		User user = userService.getUserByNick("wogur");

		Record record = new Record();
		record.setUser(user);
		record.setDate(formatter.parse(now));
		record.setNicotine(user.getTobac().getNicotine() + formattedNico);

		recordService.addRecord(record);

		return "home";
	}

	@RequestMapping("/insertYesterDayRecord")
	public String insertYesterDayRecord() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String yesterday = "2017-05-01 00:37:20";

		User user = userService.getUserByNick("wogur");

		Record record = new Record();
		record.setUser(user);
		record.setDate(formatter.parse(yesterday));
		record.setNicotine(user.getTobac().getNicotine());

		recordService.addRecord(record);

		return "home";
	}
}
