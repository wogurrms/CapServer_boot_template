package kr.ac.hansung.cse.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.ChartResponseData;
import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.Record;
import kr.ac.hansung.cse.model.ResponseData;
import kr.ac.hansung.cse.model.User;
import kr.ac.hansung.cse.service.RecordService;

@Controller
public class RecordController {
	@Autowired
	private RecordService recordService;
	
	@RequestMapping("/records")
	public String getRecords(Model model){
		List<Record> records = recordService.getRecords();
		model.addAttribute("records",records);
		return "records";
	}

	@RequestMapping("/records/{recordId}")
	public String getRecordDetail(@PathVariable int recordId, Model model){
		Record record = recordService.getRecordById(recordId);
		model.addAttribute("record",record);
		return "recorddetail";
	}
	
	@RequestMapping("/todayamount")
	public String getTodayAmount(Model model){
	
		int todayAmount = recordService.getTodayAmount();
		float myAvg = recordService.getAvgAmount();
		ResponseData responseData = new ResponseData();
		responseData.setTodayAmount(todayAmount);
		responseData.setAvg(myAvg);
		model.addAttribute("responseData",responseData);
		return "todayamount";
	}
	

	@RequestMapping("/chartFromRecord")
	public String chartFromRecord(Model model){
		List<ChartResponseData> chartResponseData = recordService.getChartResponseData();
		model.addAttribute("results",chartResponseData);
		return "chart";
	}

	@RequestMapping(value = "/mynicotine", method = RequestMethod.GET)
	public String myNicotine(Model model) throws ParseException {

		Date currentDate = new Date();
		SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = formatter.format(currentDate);
		
		NicotineResponseData nicotineResponseData = recordService.getLatestNicotine();
		double elapsedTime =(double)(formatter.parse(now).getTime() - nicotineResponseData.getDate().getTime())/1000.0;
		
		double currentNico = nicotineResponseData.getNicotine() * Math.pow(0.5, elapsedTime/7200.0);
		
		
		double formattedNico = Double.parseDouble(String.format("%.2f",currentNico));
		
		
		model.addAttribute("mynicotine",formattedNico);
		return "mynicotine";
	}
}
