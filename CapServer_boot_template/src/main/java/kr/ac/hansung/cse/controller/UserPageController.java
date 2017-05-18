package kr.ac.hansung.cse.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hansung.cse.model.ChartResponseData;
import kr.ac.hansung.cse.model.ChartResponseDataAvg;
import kr.ac.hansung.cse.model.NicotineResponseData;
import kr.ac.hansung.cse.model.RankingResponseData;
import kr.ac.hansung.cse.model.ResponseData;
import kr.ac.hansung.cse.model.Tobacco;
import kr.ac.hansung.cse.model.User;
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
	public String fagerstrom() {
		return "fagerstrom";
	}

	@RequestMapping(value = "/ranking", method = RequestMethod.GET)
	public String ranking() {
		
		return "ranking";
	}

	@RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String calendar() {
		
		return "calendar";
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rankingFromRecordToJsonArray", method = RequestMethod.GET)
	public @ResponseBody JSONObject rankingFromRecordToRest() {


		JSONObject data = new JSONObject();
		JSONObject ajaxObjCols1 = new JSONObject();
		JSONObject ajaxObjCols2 = new JSONObject();
		JSONObject ajaxObjCols3 = new JSONObject();
		JSONArray ajaxArrCols = new JSONArray();
		JSONArray ajaxArrRows = new JSONArray();

		int size = 0;

		ajaxObjCols1.put("id", "username");
		ajaxObjCols1.put("label", "UserName");
		ajaxObjCols1.put("pattern", "");
		ajaxObjCols1.put("type", "string");

		ajaxObjCols2.put("id", "count");
		ajaxObjCols2.put("label", "TodayCount");
		ajaxObjCols2.put("pattern", "");
		ajaxObjCols2.put("type", "number");
		
		ajaxObjCols3.put("id", "avg");
		ajaxObjCols3.put("label", "Average");
		ajaxObjCols3.put("pattern", "");
		ajaxObjCols3.put("type", "number");

		ajaxArrCols.add(ajaxObjCols1);
		ajaxArrCols.add(ajaxObjCols2);
		ajaxArrCols.add(ajaxObjCols3);

		List<RankingResponseData> rankingResponseDataList = new ArrayList<RankingResponseData>();
		List<User> userList = userService.getUsers();
		for(User user : userList){
			RankingResponseData rankingResponseData = new RankingResponseData();
			rankingResponseData.setUsername(user.getNick());
			rankingResponseData.setTodayAmount(recordService.getTodayAmount(user.getUid()));
			rankingResponseData.setAvg(recordService.getAvgAmount(user.getUid()));
			rankingResponseDataList.add(rankingResponseData);
		}
		
		size = rankingResponseDataList.size();

		for (int i = 0; i < size; i++) {
			JSONObject legend = new JSONObject();
			legend.put("v", rankingResponseDataList.get(i).getUsername().toString());
			legend.put("f", null);

			JSONObject value1 = new JSONObject();
			value1.put("v", rankingResponseDataList.get(i).getTodayAmount());
			value1.put("f", null);

			JSONObject value2 = new JSONObject();
			value2.put("v", rankingResponseDataList.get(i).getAvg());
			value2.put("f", null);

			
			JSONArray cValueArray = new JSONArray();
			cValueArray.add(legend);
			cValueArray.add(value1);
			cValueArray.add(value2);

			JSONObject cValueObj = new JSONObject();
			cValueObj.put("c", cValueArray);

			ajaxArrRows.add(cValueObj);

		}

		data.put("cols", ajaxArrCols);
		data.put("rows", ajaxArrRows);
		System.out.println(data);

		return data;
	}

	@RequestMapping(value = "/fagerstromresult", method = RequestMethod.GET)
	public String fagerStromResult(@RequestParam("radio-1") int value1,
			@RequestParam("radio-2") int value2, @RequestParam("radio-3") int value3,
			@RequestParam("radio-4") int value4, Model model, Authentication authentication) {
		
		String username = authentication.getName();
		
		User user = userService.getUserByNick(username);

		int avgAmount = (int) recordService.getAvgAmount(user.getUid());
		int value5 = 0;

		String nicotineDependence = "";
		String userStatus = "";
		HashMap<String, String> resultMap = new HashMap<String, String>();

		if (avgAmount <= 10) {
			value5 = 0;
		} else if (avgAmount <= 20 && avgAmount > 10) {
			value5 = 1;
		} else if (avgAmount <= 30 && avgAmount > 20) {
			value5 = 2;
		} else if (avgAmount > 30) {
			value5 = 3;
		}
		int sum = value1 + value2 + value3 + value4 + value5;

		if (sum <= 2 && sum >= 0) {
			nicotineDependence = "매우 낮은 니코틴 의존도";
			userStatus = "니코틴 의존도가 낮은 상태로 의존도가 높아지기 전에 금연을 시도해야 합니다.";
		} else if (sum <= 4 && sum >= 3) {
			nicotineDependence = "낮은 니코틴 의존도";
			userStatus = "니코틴 의존도가 낮은 상태로 의존도가 높아지기 전에 금연을 시도해야 합니다.";
		} else if (sum == 5) {
			nicotineDependence = "중간 정도의 니코틴 의존도";
			userStatus = "곧 금연을 시작하지 않으면 니코틴 의존도가 높아져 심각한 중독상태로 발전 할 수 있습니다.";
		} else if (sum <= 7 && sum >= 6) {
			nicotineDependence = "높은 니코틴 의존도";
			userStatus = "자신의 흡연을 조절 할 수 없고, 니코틴 중독 치료에 유효한 금연 " + "치료제 처방이나 니코틴 대체제 사용에 대해 의사와 논의할 필요가 있습니다.";
		} else if (sum <= 8 && sum >= 10) {
			nicotineDependence = "매우 높은 니코틴 의존도";
			userStatus = "자신의 흡연을 조절 할 수 없고, 니코틴 중독 치료에 유효한 금연 " + "치료제 처방이나 니코틴 대체제 사용에 대해 의사와 논의할 필요가 있습니다.";
		}

		resultMap.put("nicotineDependence", nicotineDependence);
		resultMap.put("userStatus", userStatus);

		model.addAttribute("resultMap", resultMap);

		return "fagerstromresult";
	}


	@RequestMapping(value = "/todayamount", method = RequestMethod.GET)
	public String getTodayAmount(Model model, Authentication authentication) {
		
		String username = authentication.getName();
		
		User user = userService.getUserByNick(username);

		int todayAmount = recordService.getTodayAmount(user.getUid());
		float myAvg = recordService.getAvgAmount(user.getUid());
		ResponseData responseData = new ResponseData();
		responseData.setTodayAmount(todayAmount);
		responseData.setAvg(myAvg);
		model.addAttribute("responseData", responseData);
		return "todayamount";
	}

	@RequestMapping(value = "/chartFromRecord", method = RequestMethod.GET)
	public String chartFromRecord() {
		return "chart";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/chartFromRecordToJsonArray", method = RequestMethod.GET)
	public @ResponseBody JSONObject chartFromRecordToRest(Model model, Authentication authentication) {

		String username = authentication.getName();

		User user = userService.getUserByNick(username);

		JSONObject data = new JSONObject();
		JSONObject ajaxObjCols1 = new JSONObject();
		JSONObject ajaxObjCols2 = new JSONObject();
		JSONObject ajaxObjCols3 = new JSONObject();
		JSONArray ajaxArrCols = new JSONArray();
		JSONArray ajaxArrRows = new JSONArray();

		int size = 0;

		ajaxObjCols1.put("id", "date");
		ajaxObjCols1.put("label", "snsr");
		ajaxObjCols1.put("pattern", "");
		ajaxObjCols1.put("type", "string");

		ajaxObjCols2.put("id", "count");
		ajaxObjCols2.put("label", "My Count");
		ajaxObjCols2.put("pattern", "");
		ajaxObjCols2.put("type", "number");

		ajaxObjCols3.put("id", "count");
		ajaxObjCols3.put("label", "User Avg");
		ajaxObjCols3.put("pattern", "");
		ajaxObjCols3.put("type", "number");
		
		ajaxArrCols.add(ajaxObjCols1);
		ajaxArrCols.add(ajaxObjCols2);
		ajaxArrCols.add(ajaxObjCols3);

		List<ChartResponseData> chartResponseDataList = recordService.getChartResponseData(user.getUid());
		List<ChartResponseDataAvg> chartResponseDataAvgList = recordService.getChartResponseDataAvg();

		size = chartResponseDataList.size();

		for (int i = 0; i < size; i++) {
			JSONObject legend = new JSONObject();
			legend.put("v", chartResponseDataList.get(i).getDate().toString());
			legend.put("f", null);

			JSONObject value1 = new JSONObject();
			value1.put("v", chartResponseDataList.get(i).getCount());
			value1.put("f", null);

			JSONObject value2 = new JSONObject();
			value2.put("v", chartResponseDataAvgList.get(i).getAvg());
			value2.put("f", null);
			
			JSONArray cValueArray = new JSONArray();
			cValueArray.add(legend);
			cValueArray.add(value1);
			cValueArray.add(value2);

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
	@RequestMapping(value = "/chartFromRecordToJsonArrayDaily/{date}", method = RequestMethod.GET)
	public @ResponseBody JSONObject chartFromDailyRecordToRest(@PathVariable("date") String date,
			Authentication authentication) throws ParseException {

		String username = authentication.getName();

		User user = userService.getUserByNick(username);

		JSONObject data = new JSONObject();
		JSONObject ajaxObjCols1 = new JSONObject();
		JSONObject ajaxObjCols2 = new JSONObject();
		JSONArray ajaxArrCols = new JSONArray();
		JSONArray ajaxArrRows = new JSONArray();

		int size = 0;

		ajaxObjCols1.put("id", "date");
		ajaxObjCols1.put("label", "snsr");
		ajaxObjCols1.put("pattern", "");
		ajaxObjCols1.put("type", "string");

		ajaxObjCols2.put("id", "count");
		ajaxObjCols2.put("label", "count");
		ajaxObjCols2.put("pattern", "");
		ajaxObjCols2.put("type", "number");

		ajaxArrCols.add(ajaxObjCols1);
		ajaxArrCols.add(ajaxObjCols2);

		List<ChartResponseData> chartResponseDataList = recordService.getDailyChartResponseData(user.getUid(), date);
		size = chartResponseDataList.size();

		for (int i = 0; i < size; i++) {
			JSONObject legend = new JSONObject();
			legend.put("v", chartResponseDataList.get(i).getDate().toString().substring(0, 2)+"시");
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

	@RequestMapping(value = "/mynicotine", method = RequestMethod.GET)
	public String myNicotine(Model model, Authentication authentication) throws ParseException {
		
		String username = authentication.getName();
		
		User user = userService.getUserByNick(username);

		Date currentDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formatter.format(currentDate);
		
		double elapsedTime = 0;
		double currentNico = 0;
		
		NicotineResponseData nicotineResponseData = recordService.getLatestNicotine(user.getUid());
		if (nicotineResponseData != null) {
			elapsedTime = (double) (formatter.parse(now).getTime() - nicotineResponseData.getDate().getTime())
					/ 1000.0;
			currentNico = nicotineResponseData.getNicotine() * Math.pow(0.5, elapsedTime / 7200.0);
		}

		

		double formattedNico = Double.parseDouble(String.format("%.2f", currentNico));
		
		System.out.println(formattedNico);
		
		model.addAttribute("mynicotine", formattedNico);
		return "mynicotine";
	}
	
	@RequestMapping(value = "/spend", method = RequestMethod.GET)
	public String mySpendMoney(Model model, Authentication authentication) throws ParseException {
		
		String username = authentication.getName();

		User user = userService.getUserByNick(username);
		Tobacco tobacco = user.getTobac();
		int price = tobacco.getTobaccoPrice();
		int myTotalCount = recordService.getAllRecordCountByUid(user.getUid());
		int myTotalSpend = price * myTotalCount;  
		
		model.addAttribute("myTotalSpend", myTotalSpend);
		return "spend";
	}
	
}
