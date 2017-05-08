package kr.ac.hansung.cse.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hansung.cse.model.ChartResponseData;
import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.ResponseData;
import kr.ac.hansung.cse.service.RecordService;
import kr.ac.hansung.cse.service.TobaccoService;
import kr.ac.hansung.cse.service.UserService;

@Controller
@RequestMapping("/userpage")
public class UserPageController {
	@Autowired
	private RecordService recordService;
	@Autowired
	private TobaccoService tobaccoService;
	@Autowired
	private UserService userService;
	
	
	
	@RequestMapping(value = "/fagerstrom", method = RequestMethod.GET)
	public String fagerstrom(@PathVariable("username") String username) {
		return "fagerstrom";
	}

	@RequestMapping(value = "/ranking/{username}", method = RequestMethod.GET)
	public String ranking(@PathVariable("username") String username) {
		return "ranking";
	}

	@RequestMapping(value = "/fagerstromresult/{username}", method = RequestMethod.GET)
	public String fagerStromResult(@RequestParam("radio-1")int value1, @RequestParam("radio-2")int value2,
			@RequestParam("radio-3")int value3, @RequestParam("radio-4")int value4, Model model) {
		
		int avgAmount = (int)recordService.getAvgAmount();
		int value5 = 0;
		
		String nicotineDependence ="";
		String userStatus = "";
		HashMap<String,String> resultMap = new HashMap<String,String>();
		
		if(avgAmount <= 10) {
			value5 = 0;
		}else if(avgAmount <= 20 && avgAmount > 10){
			value5 = 1;
		}else if(avgAmount <= 30 && avgAmount > 20){
			value5 = 2;
		}else if(avgAmount > 30){
			value5 = 3;
		}
		int sum = value1 + value2 + value3 + value4 + value5 ;
		
		if(sum <= 2 && sum >= 0){
			nicotineDependence = "매우 낮은 니코틴 의존도";
			userStatus = "니코틴 의존도가 낮은 상태로 의존도가 높아지기 전에 금연을 시도해야 합니다.";
		}else if(sum <= 4 && sum >= 3){
			nicotineDependence = "낮은 니코틴 의존도";
			userStatus = "니코틴 의존도가 낮은 상태로 의존도가 높아지기 전에 금연을 시도해야 합니다.";
		}else if(sum == 5){
			nicotineDependence = "중간 정도의 니코틴 의존도";
			userStatus = "곧 금연을 시작하지 않으면 니코틴 의존도가 높아져 심각한 중독상태로 발전 할 수 있습니다.";
		}else if(sum <= 7 && sum >= 6){
			nicotineDependence = "높은 니코틴 의존도";
			userStatus = "자신의 흡연을 조절 할 수 없고, 니코틴 중독 치료에 유효한 금연 "
					+ "치료제 처방이나 니코틴 대체제 사용에 대해 의사와 논의할 필요가 있습니다.";
		}else if(sum <= 8 && sum >= 10){
			nicotineDependence = "매우 높은 니코틴 의존도";
			userStatus = "자신의 흡연을 조절 할 수 없고, 니코틴 중독 치료에 유효한 금연 "
					+ "치료제 처방이나 니코틴 대체제 사용에 대해 의사와 논의할 필요가 있습니다.";
		}
		
		resultMap.put("nicotineDependence", nicotineDependence);
		resultMap.put("userStatus", userStatus);
		
		model.addAttribute("resultMap",resultMap);
		
		return "fagerstromresult";
	}

	@RequestMapping(value = "/spend/{username}", method = RequestMethod.GET)
	public String spend(@PathVariable("username") String username) {
		return "spend";
	}

	@RequestMapping(value= "/todayamount/{username}", method = RequestMethod.GET)
	public String getTodayAmount(@PathVariable("username") String username, Model model){
	
		int todayAmount = recordService.getTodayAmount();
		float myAvg = recordService.getAvgAmount();
		ResponseData responseData = new ResponseData();
		responseData.setTodayAmount(todayAmount);
		responseData.setAvg(myAvg);
		model.addAttribute("responseData",responseData);
		return "todayamount";
	}
	

	@RequestMapping(value= "/chartFromRecord", method = RequestMethod.GET)
	public String chartFromRecord(Model model){
		List<ChartResponseData> chartResponseData = recordService.getChartResponseData();
		model.addAttribute("results",chartResponseData);
		return "chart";
	}

	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/chartFromRecordToJsonArray/{username}", method=RequestMethod.GET)
	public @ResponseBody JSONObject chartFromRecordToRest(@PathVariable("username") String username, Model model){
		JSONObject data = new JSONObject();
		JSONObject ajaxObjCols1 = new JSONObject();
		JSONObject ajaxObjCols2 = new JSONObject();
		JSONArray ajaxArrCols = new JSONArray();		
		JSONArray ajaxArrRows = new JSONArray();

		int size = 0;
		
		
		ajaxObjCols1.put("id","date");
		ajaxObjCols1.put("label","snsr");
		ajaxObjCols1.put("pattern","");
		ajaxObjCols1.put("type","string");
		
		ajaxObjCols2.put("id","count");
		ajaxObjCols2.put("label","value");
		ajaxObjCols2.put("pattern","");
		ajaxObjCols2.put("type","number");
		
		
		ajaxArrCols.add(ajaxObjCols1);
		ajaxArrCols.add(ajaxObjCols2); 

		List<ChartResponseData> chartResponseDataList = recordService.getChartResponseData();
		size = chartResponseDataList.size();
		
		for(int i = 0; i<size ; i++){
			JSONObject legend = new JSONObject();
			legend.put("v", chartResponseDataList.get(i).getDate().toString());
			legend.put("f", null);
			
			JSONObject value = new JSONObject();
			value.put("v", chartResponseDataList.get(i).getCount());
			value.put("f", null);
			
			JSONArray cValueArray = new JSONArray();
			cValueArray.add(legend);
			cValueArray.add(value);
			
			JSONObject cValueObj = new JSONObject();
			cValueObj.put("c", cValueArray);
			
			ajaxArrRows.add(cValueObj);
			
		}

		data.put("cols", ajaxArrCols);
		data.put("rows", ajaxArrRows);
		System.out.println(data);
		
		return data;
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/chartFromRecordToJsonArrayDaily/{username}/{date}", method=RequestMethod.GET)
	public @ResponseBody JSONObject chartFromDailyRecordToRest(@PathVariable("date") String date) throws ParseException{

		
		JSONObject data = new JSONObject();
		JSONObject ajaxObjCols1 = new JSONObject();
		JSONObject ajaxObjCols2 = new JSONObject();
		JSONArray ajaxArrCols = new JSONArray();		
		JSONArray ajaxArrRows = new JSONArray();

		int size = 0;
		
		
		ajaxObjCols1.put("id","date");
		ajaxObjCols1.put("label","snsr");
		ajaxObjCols1.put("pattern","");
		ajaxObjCols1.put("type","string");
		
		ajaxObjCols2.put("id","count");
		ajaxObjCols2.put("label","value");
		ajaxObjCols2.put("pattern","");
		ajaxObjCols2.put("type","number");
		
		
		ajaxArrCols.add(ajaxObjCols1);
		ajaxArrCols.add(ajaxObjCols2); 

		List<ChartResponseData> chartResponseDataList = recordService.getDailyChartResponseData(date);
		size = chartResponseDataList.size();
		
		for(int i = 0; i<size ; i++){
			JSONObject legend = new JSONObject();
			legend.put("v", chartResponseDataList.get(i).getDate().toString());
			legend.put("f", null);
			
			JSONObject value = new JSONObject();
			value.put("v", chartResponseDataList.get(i).getCount());
			value.put("f", null);
			
			JSONArray cValueArray = new JSONArray();
			cValueArray.add(legend);
			cValueArray.add(value);
			
			JSONObject cValueObj = new JSONObject();
			cValueObj.put("c", cValueArray);
			
			ajaxArrRows.add(cValueObj);
			
		}

		data.put("cols", ajaxArrCols);
		data.put("rows", ajaxArrRows);
		System.out.println(data);
		
		return data;
	}
	
	
	
	@RequestMapping(value = "/mynicotine/{username}", method = RequestMethod.GET)
	public String myNicotine(@PathVariable("username") String username, Model model) throws ParseException {

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
