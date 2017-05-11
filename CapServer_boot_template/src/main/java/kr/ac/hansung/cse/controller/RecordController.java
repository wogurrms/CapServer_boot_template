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
	
	
}
