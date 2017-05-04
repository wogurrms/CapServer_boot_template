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
	
	@RequestMapping(value = "/chart", method = RequestMethod.GET)
	public String chart() {
		return "chart";
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/chartFromRecordToJsonArray", method=RequestMethod.GET)
	public @ResponseBody JSONObject chartFromRecordToRest(Model model){
		JSONObject data = new JSONObject();
		JSONObject ajaxObjCols1 = new JSONObject();
		JSONObject ajaxObjCols2 = new JSONObject();
		JSONArray ajaxArrCols = new JSONArray();		
		JSONArray ajaxArrRows = new JSONArray();

		int size = 0;
		
		
		ajaxObjCols1.put("id","");
		ajaxObjCols1.put("label","snsr");
		ajaxObjCols1.put("pattern","");
		ajaxObjCols1.put("type","string");
		
		ajaxObjCols2.put("id","");
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
	
}
