package kr.ac.hansung.cse.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hansung.cse.model.ChartResponseData;
import kr.ac.hansung.cse.service.RecordService;

@Controller
public class ChartController {
	@Autowired
	RecordService recordService;

}
